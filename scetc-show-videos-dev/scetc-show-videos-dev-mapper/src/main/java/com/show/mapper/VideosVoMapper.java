package com.show.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.show.pojo.Videos;
import com.show.pojo.VideosVo;
import com.show.utils.MyMapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface VideosVoMapper extends MyMapper<Videos> {
	
	public List<VideosVo> queryAllVideos(@Param("videoDesc") String videoDesc,@Param("videoCategory") String videoCategory);
	public void addVideoLikeCount(String videoId);
	public void reduceAddVideoLikeCount(String videoId);
	public List<VideosVo> queryVideosByUser(String userId);

	@Select(value = "SELECT videos.* FROM users_like_videos  INNER JOIN videos ON  users_like_videos.video_id = videos.id where users_like_videos.user_id = #{userId} ")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "videoDesc", column = "video_desc"),
			@Result(property = "videoPath", column = "video_path"),
			@Result(property = "videoSeconds", column = "video_seconds"),
			@Result(property = "videoWidth", column = "video_width"),
			@Result(property = "videoHeight", column = "video_height"),
			@Result(property = "likeCounts", column = "like_counts"),
			@Result(property = "status", column = "status"),
			@Result(property = "createTime", column = "create_time"),
			@Result(property = "coverPath", column = "cover_path")
	})
	List<VideosVo> queryLikeVideosByUser(@Param("userId") String userId);
}