package com.show.impl;

import java.util.*;

import com.show.mapper.*;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.show.pojo.Comments;
import com.show.pojo.PageResult;
import com.show.pojo.SearchRecords;
import com.show.pojo.UsersLikeVideos;
import com.show.pojo.UsersReport;
import com.show.pojo.Videos;
import com.show.pojo.VideosVo;
import com.show.service.VideoService;
import com.show.vo.CommentsVo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 
 * @author 2016wlw2 徐塬峰 创建时间：2018年7月6日 下午3:57:02
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS)

public class VideosServiceImpl implements VideoService {
	@Autowired
	private Sid sid;// 生成唯一主键
	@Autowired
	private VideosMapper videosMapper;
	@Autowired
	private UsersMapper usersMapper;
	@Autowired
	private VideosVoMapper videosVoMapper;
	@Autowired
	private SearchRecordsMapper searchRecordsMapper;
	@Autowired
	private UsersLikeVideosMapper usersLikeVideosMapper;
	@Autowired
	private CommentsMapper commentsMapper;
	@Autowired
	private UsersReportMapper userReportMapper;
	// 假设有一个递减因子，用于减少后续匹配的权重
	double weightDecayFactor = 0.8; // 例如，每次匹配权重减少20%
	double baseWeight = 1.0; // 第一个匹配的权重
	double score = 0.0; // 视频的初始得分

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String saveVideo(Videos video) {

		String id = sid.nextShort();
		video.setId(id);
		videosMapper.insertSelective(video);
		return id;

	}

	/**
	 * 修改視頻的封面
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateVideo(String id, String coverPath) {
		Videos video = new Videos();
		video.setId(id);
		video.setCoverPath(coverPath);
		videosMapper.updateByPrimaryKeySelective(video);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public PageResult getAllVideos(Videos videos, Integer isSaveRecord, Integer page, Integer pageSize,
			String category) {

		// 保存热搜词
		String desc = videos.getVideoDesc();
		if (isSaveRecord != null && isSaveRecord == 1) {
			SearchRecords record = new SearchRecords();
			record.setContent(desc);
			String recordId = sid.nextShort();
			record.setId(recordId);
			searchRecordsMapper.insertSelective(record);
		}
		// 对查询进行优化
		PageHelper.startPage(page, pageSize);
		List<VideosVo> list = videosVoMapper.queryAllVideos(desc, category);
		PageInfo<VideosVo> pageList = new PageInfo<>(list);
		PageResult pageResult = new PageResult();
		pageResult.setPage(page);
		pageResult.setTotal(pageList.getPages());
		pageResult.setRows(list);
		pageResult.setRecords(pageList.getTotal());
		return pageResult;
	}
	/*
	 * @Override public PageResult getAllVideos(Integer page, Integer
	 * pageSize,String category) { //对查询进行优化 PageHelper.startPage(page, pageSize);
	 * List<VideosVo> list = videosVoMapper.queryAllVideos(null); PageInfo<VideosVo>
	 * pageList = new PageInfo<>(list); PageResult pageResult = new PageResult();
	 * pageResult.setPage(page); pageResult.setTotal(pageList.getPages());
	 * pageResult.setRows(list); pageResult.setRecords(pageList.getTotal()); return
	 * pageResult; }
	 */
//	//根据分类去查询当前视频
//	public PageResult getAllVideos(Integer page, Integer pageSize,String category) 
//	{
//		//对查询进行优化
//		PageHelper.startPage(page, pageSize);
//		List<VideosVo> list = videosVoMapper.queryAllVideos(null);
//		PageInfo<VideosVo> pageList = new PageInfo<>(list);
//		PageResult pageResult = new PageResult();
//		pageResult.setPage(page);
//		pageResult.setTotal(pageList.getPages());
//		pageResult.setRows(list);
//		pageResult.setRecords(pageList.getTotal());
//		return pageResult;
//	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void userLikeVideo(String userId, String videoId, String videoCreaterId) {

		// 1.保存用户的喜欢点赞和关联关系表
		// 2.视频喜欢的数量累加
		// 3.用户受喜欢数量的累加
		String likeId = sid.nextShort();
		UsersLikeVideos ulv = new UsersLikeVideos();
		ulv.setId(likeId);
		ulv.setUserId(userId);
		ulv.setVideoId(videoId);
		usersLikeVideosMapper.insert(ulv);
		videosVoMapper.addVideoLikeCount(videoId);
		usersMapper.addReceiveLikeCount(videoCreaterId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
		/*
		 * String likeId=sid.nextShort(); UsersLikeVideos ulv=new UsersLikeVideos();
		 * ulv.setId(likeId); ulv.setUserId(userId); ulv.setUserId(videoId);
		 */

		Example example = new Example(UsersLikeVideos.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		criteria.andEqualTo("videoId", videoId);
		usersLikeVideosMapper.deleteByExample(example);
		videosVoMapper.reduceAddVideoLikeCount(videoId);
		usersMapper.reduceReceiveLikeCount(videoCreaterId);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> getHotWords() {
		return searchRecordsMapper.getHotWords();
	}

	/**
	 * 保存视频的评论
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED) // 事务的传播级别
	public void saveComment(String userId, String videoId, String comment,String lastId) {

		Comments comments = new Comments();
		comments.setVideoId(videoId);
		comments.setLastId(lastId);
		comments.setCreateTime(new Date());
		comments.setFromUserId(userId);
		comments.setId(sid.nextShort());
		comments.setComment(comment);
		commentsMapper.insert(comments);// 将该评论插入数据库

	}

//	@Override
//	public List<CommentsVo> queryCommentsByVideoId(String videoId) {
//		// 根据当前的Video的id号来查询出挡墙的视频的所有评论
//		List<CommentsVo> commentsAll = commentsMapper.queryAllByVideoId(videoId);
//		return commentsAll;
//	}

	// 根据当前用户的id查询出当前用户所上传的所有视频
	public List<VideosVo> queryVideosByUser(String userId) {
		return videosVoMapper.queryVideosByUser(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void reportVideoByUser(String dealUserId, String dealVideoId, String userId, String title, String content) {

		UsersReport userReport = new UsersReport();
		userReport.setContent(content);
		userReport.setTitle(title);
		userReport.setCreateDate(new Date());
		userReport.setDealVideoId(dealVideoId);
		userReport.setId(sid.nextShort());
		userReport.setDealUserId(dealUserId);
		userReport.setUserid(userId);
		userReportMapper.insert(userReport);

	}

	@Override
	public List<VideosVo> queryLikeVideosByUser(String userId) {
		return videosVoMapper.queryLikeVideosByUser(userId);
	}
	public PageResult recommendVideosByCategoriesWithWeights(int page,List<String> userFavoriteCategories, int count) {
		PageHelper.startPage(page, count);
		List<VideosVo> allVideos = videosVoMapper.queryAllVideos("","");; // 假设此方法从数据库获取所有视频
		List<VideosVo> recommendedVideos = new ArrayList<>();
		 Map<String, Double> categoryWeights = new HashMap<>();
		// 遍历所有视频，为每个视频计算得分
		for (VideosVo video : allVideos) {
			for (int i = 0; i < userFavoriteCategories.size(); i++) {
				String category = userFavoriteCategories.get(i);
				if (video.getVideoCategory().equals(category)) {
					// 根据位置计算权重，第一个位置使用baseWeight，后续递减
					double categoryWeight = baseWeight * Math.pow(weightDecayFactor, i);
					score += categoryWeight;
					break; // 如果一个视频只属于一个类别或我们只关心第一个匹配的类别，则退出循环
				}
			}
			if (score > 0) { // 只有当视频得分大于0时才加入推荐列表
				video.setScore(score); // 假设Video类有一个setScore方法来存储得分
				recommendedVideos.add(video);
			}
		}
		// 按得分降序排序推荐列表
		recommendedVideos.sort(Comparator.comparingDouble(VideosVo::getScore).reversed());
		// 如果推荐列表中的视频数量超过所需数量，则截取前count个
		if (recommendedVideos.size() > count) {
			recommendedVideos = recommendedVideos.subList(0, count);
		}
		PageInfo<VideosVo> pageList = new PageInfo<>(recommendedVideos);
		PageResult pageResultList = new PageResult();
		pageResultList.setPage(page);
		pageResultList.setTotal(pageList.getPages());
		pageResultList.setRows(recommendedVideos);
		pageResultList.setRecords(pageList.getTotal());
		return pageResultList;
	}

	@Override
	public Map<String, Double> loadUserCategoryWeights(String userId) {
		// 这里是加载逻辑，可能会查询数据库、文件或其他存储

		// 以下是一个简单的模拟
		Map<String, Double> weights = new HashMap<>();
		weights.put("Action", 5.0); // 用户对动作类视频的权重为5.0
		weights.put("Comedy", 3.5); // 用户对喜剧类视频的权重为3.5
		// ... 其他类别和权重 ...
		return weights;
	}

}
