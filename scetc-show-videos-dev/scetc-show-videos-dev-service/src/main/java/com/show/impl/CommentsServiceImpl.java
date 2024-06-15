package com.show.impl;

import com.show.mapper.CommentsMapper;
import com.show.mapper.ObserveMapper;
import com.show.mapper.UsersMapper;
import com.show.pojo.Comments;
import com.show.pojo.Users;
import com.show.pojo.Videos;
import com.show.service.CommentsService;
import com.show.vo.CommentsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    ObserveMapper observeMapper;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    CommentsMapper commentsMapper;
    /**
     * 功能描述：根据博客id，查询此博客的所有评论信息
     *
     * @param videoId 博客id
     * @return 博客的评论信息
     */
    @Override
    public List<CommentsVo> queryFirstCommentByVideoId(String videoId ) {
        //所有未处理的一级评论集合
        List<CommentsVo> firstObserveList = observeMapper.queryFirstObserveList(videoId);
        //所有未处理的二级评论集合
        System.out.println(firstObserveList);
//        List<CommentsVo> secondObserveList = observeMapper.querySecondObserveList(videoId,firstObserveList);
        for (CommentsVo element : firstObserveList) {
            // 使用 querySecondObserveList 方法获取相关信息
            List<CommentsVo> secondInfoList = observeMapper.querySecondObserveList(videoId,element.getId());
            // 如果返回的结果不为空，将该元素的 second 属性设置为 true
            if (secondInfoList != null && !secondInfoList.isEmpty()) {
                element.setSecond(true);

            }else{
                element.setSecond(false);

            }
        }
        //将二级评论用链表的方式添加到一级评论
//        List<CommentsVo> list = addAllNode(firstObserveList, secondObserveList);

        //控制台打印评论回复
//        show(list);
        System.out.println(firstObserveList);
        //返回处理后的评论信息
        return firstObserveList;
    }

    @Override
    public List<CommentsVo> querySecondCommentByVideoId(String videoId, String lastId) {
        //所有未处理的二级评论集合
        List<CommentsVo> secondObserveList = observeMapper.querySecondObserveList(videoId,lastId);
        return secondObserveList;
    }


    /**
     * 功能描述：根据评论id查询用户信息
     *
     * @param fromUserId 评论id
     * @return 评论信息，携带用户信息
     */
	@Override
	public Users queryUserByFromUserId (String fromUserId ) {
//		Comments comments = commentsMapper.selectByFromUserId(fromUserId);
		Users users = usersMapper.selectByPrimaryKey(fromUserId);
//		CommentsVo commentsVo = new CommentsVo();
//        commentsVo.setComment(comments.getComment());
//        commentsVo.setUser(user);
		return users;
	}

    @Override
    public String queryCount(String videoId) {
        return commentsMapper.queryCountByVideoId(videoId);
    }


    /**
     * 功能描述：将单个node添加到链表中
     *
     * @param firstList   第一层评论集合（链表）
     * @param commentsNode 非第一层评论的回复信息
     * @return 是否添加
     */
    private boolean addNode ( List<CommentsVo> firstList, CommentsVo commentsNode ) {
        //循环添加
        for (CommentsVo node : firstList) {
            //判断留言的上一段是否是这条留言（判断这条回复，是否是当前评论的回复）
            if (node.getId().equals(commentsNode.getLastId())) {
                //是，添加，返回true
                node.getNextNodes().add(commentsNode);
                return true;
            } else {
                //否则递归继续判断
                if (node.getNextNodes().size() != 0) {
                    if (addNode(node.getNextNodes(), commentsNode)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 功能描述：将查出来的lastId不为null的回复都添加到第一层Node集合中
     *
     * @param firstList 第一层评论集合（链表）
     * @param thenList  非第一层评论集合（链表）
     * @return 所有评论集合（非第一层评论集合对应添加到第一层评论集合，返回）
     */
    private List<CommentsVo> addAllNode ( List<CommentsVo> firstList, List<CommentsVo> thenList ) {
        while (thenList.size() != 0) {
            int size = thenList.size();
            for (int i = 0; i < size; i++) {
                if (addNode(firstList, new CommentsVo(thenList.get(i)))) {
                    thenList.remove(i);
                    i--;
                    size--;
                }
            }
        }
        return firstList;
    }

    /**
     * 功能描述：打印评论的链表回复信息
     *
     * @param list 评论信息（链表集合）
     */
    private void show ( List<CommentsVo> list ) {
        for (CommentsVo node : list) {
            System.out.println(node.getNickName() + " 用户回复了" + node.getLastId() + "：" + node.getComment());
            //递归打印回复信息
            if (node.getNextNodes().size() != 0) {
                show(node.getNextNodes());
            }
        }
    }


}
