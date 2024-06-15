package com.show.mapper;

import com.show.pojo.Users;
import com.show.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UsersMapper extends MyMapper<Users> {
	
	/**
	 * 累加
	 * @param userId
	 */
	public void addReceiveLikeCount(String userId);
	/**
	 * 累减
	 * @param userId
	 */
	public void reduceReceiveLikeCount(String userId);
	/**
	 * 粉丝累加
	 */
	public void addFansCount(String userId);
	/**
	 * 粉丝累减
	 * @param userId
	 */
	public void reduceFansCount(String userId);
	
	
	/**
	 * 用户关注的数量增加
	 * @param userId
	 */
	public void followWithAdd(String userId);
	
	/**
	 * 用户关注的数量减少
	 * @param userId
	 */
	
	public void followWithReduce(String userId);

	Users findById(@Param("userId") String userId);

	@Select(value = "SELECT\n" +
			"\tvideos.video_category, \n" +
			"\tCOUNT(videos.video_category) as count\n" +
			"FROM\n" +
			"\tusers\n" +
			"\tINNER JOIN\n" +
			"\tusers_like_videos\n" +
			"\tON \n" +
			"\t\tusers.id = users_like_videos.user_id\n" +
			"\tINNER JOIN\n" +
			"\tvideos\n" +
			"\tON \n" +
			"\t\tusers_like_videos.video_id = videos.id\n" +
			"WHERE\n" +
			"\tusers.id = #{userId}\n" +
			"GROUP BY\n" +
			"\tvideos.video_category\n" +
			"ORDER BY\n" +
			"\tvideos.video_category DESC")
	List<Map<String,Long>> categoryByUserId(@Param("userId") String userId);

	@Select(value = "SELECT videos.video_category, COUNT(videos.video_category) FROM\n" +
			"\tusers INNER JOIN users_like_videos ON users.id = users_like_videos.user_idINNER JOIN videosON \tusers_like_videos.video_id = videos.id\n" +
			"WHERE\tusers.id = #{userId}\n" +
			"GROUP BYvideos.video_category")
	List<Map<String,Double>> getWeight(@Param("userId") String userId);
}