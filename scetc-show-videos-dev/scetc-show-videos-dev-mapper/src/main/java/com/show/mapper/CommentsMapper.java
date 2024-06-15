package com.show.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.show.pojo.Comments;
import com.show.utils.MyMapper;
import com.show.vo.CommentsVo;
import org.apache.ibatis.annotations.Select;

public interface CommentsMapper extends MyMapper<Comments> {

	List<CommentsVo> queryAllByVideoId(@Param("videoId") String videoId);

	@Select(value = "select count(*) from comments where video_id = #{videoId}")
	String queryCountByVideoId(@Param("videoId") String videoId);
}