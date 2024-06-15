package com.show.service;

import com.show.pojo.Users;
import com.show.vo.CommentsVo;
import org.apache.commons.pool2.UsageTracking;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Component
public interface CommentsService {
    /**
     * 功能描述：根据博客id，查询此博客的所有评论信息
     * @param videoId 博客id
     * @return 博客的评论信息
     */
    List<CommentsVo> queryFirstCommentByVideoId(String videoId);
    List<CommentsVo> querySecondCommentByVideoId(String videoId,String lastId);

    /**
     * 功能描述：根据评论id查询用户信息
     * @param fromUserId 评论id
     * @return 评论信息，携带用户信息
     */
    Users queryUserByFromUserId(String fromUserId);

    String queryCount(String videoId);
}
