package com.show.controller;

import com.show.pojo.Users;
import com.show.service.CommentsService;
import com.show.vo.CommentsVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    /**
     * 功能描述：根据博客id，查询此博客的所有评论信息（链表类型的数据）
     * @param videoId 视频id
     * @return 博客的评论信息
     */
    @ApiOperation(value = "评论", notes = "评论接口")
//    @ApiImplicitParams(name = "bgmId", value = "背景音乐id", required = false, dataType = "String",paramType = "form")
    @PostMapping("/Observe/{videoId}")
    public ResponseEntity<List<CommentsVo>> queryFirstCommentByVideoId ( @ApiParam(name = "videoId", value = "视频id", required = true) @PathVariable String videoId
    ) {
        System.out.println("11111111111111111");
        List<CommentsVo> comments = commentsService.queryFirstCommentByVideoId(videoId);
        for (CommentsVo commentsVo : comments) {
            Users userInfo = commentsService.queryUserByFromUserId(commentsVo.getFromUserId());
            commentsVo.setUser(userInfo) ; // 将用户信息存储到评论对象中
        };
        return ResponseEntity.ok(comments);
    }
    /**
     * 功能描述：根据博客id，查询此博客的所有评论信息（链表类型的数据）
     * @param videoId 视频id
     * @return 博客的评论信息
     */
    @ApiOperation(value = "评论", notes = "评论接口")
//    @ApiImplicitParams(name = "bgmId", value = "背景音乐id", required = false, dataType = "String",paramType = "form")
    @PostMapping("/getVideoSecondComments")
    public ResponseEntity<List<CommentsVo>> querySecondCommentByVideoId ( String videoId, String lastId)
    {
        System.out.println(videoId+"----------------"+lastId);
        System.out.println("22222222222222");
        List<CommentsVo> comments = commentsService.querySecondCommentByVideoId(videoId, lastId);

        for (CommentsVo commentsVo : comments) {
            Users userInfo = commentsService.queryUserByFromUserId(commentsVo.getFromUserId());
            commentsVo.setUser(userInfo) ; // 将用户信息存储到评论对象中
        };
        return ResponseEntity.ok(comments);
    }
    /**
     * 功能描述：根据评论id查询用户信息（评论信息，携带用户信息）
     * @param fromUserId 评论id
     * @return 评论信息，携带用户信息
     */
//    @PostMapping("/user")
//    public ResponseEntity<CommentsVo> queryObserveUserById (
//            @ApiParam(name = "fromUserId", value = "评论id", required = true) @RequestParam String fromUserId
//    ) {
//        return ResponseEntity.ok(commentsService.queryObserveUserById(fromUserId));
//    }
    @GetMapping("/count/{videoId}")
    public String queryCount(@PathVariable String videoId){
        String aLong = commentsService.queryCount(videoId);
        return aLong;
    }
}
