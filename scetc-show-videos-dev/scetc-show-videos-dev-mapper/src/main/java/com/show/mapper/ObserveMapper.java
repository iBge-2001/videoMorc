package com.show.mapper;

import com.show.pojo.Comments;
import com.show.utils.MyMapper;
import com.show.vo.CommentsVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public interface ObserveMapper extends MyMapper<Comments> {
    /**
     * 功能描述：根据视频id和lastId为空，查询所有的一级评论信息集合
     *
     * @param videoId 视频id
     * @return 一级评论信息集合
     * @author RenShiWei
     * Date: 2020/4/16 10:37
     */
    @Select("SELECT * FROM comments c LEFT JOIN users u " +
            "ON u.id=c.from_user_id " +
            "WHERE c.video_id= #{videoId} AND c.last_id is null")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "video_id", property = "videoId"),
            @Result(column = "from_user_id", property = "fromUserId"),
            @Result(column = "comment", property = "comment"),
//            @Result(column = "from_user_id", property = "user",
//                    one = @One(select = "com.show.mapper.UsersMapper.queryUserForObserve",
//                            fetchType = FetchType.EAGER)),
            @Result(column = "last_id", property = "lastId"),
            @Result(column = "create_time", property = "createTime")
//            @Result(column = "create_date", property = "createDate"),
//            @Result(column = "update_date", property = "updateDate")
    })
    List<CommentsVo> queryFirstObserveList(@Param("videoId") String videoId);


    /**
     * 功能描述：根据博客id和lastId不为空，查询所有的二级评论信息集合
     *
     * @param videoId 视频id
     * @return 二级评论信息集合
     * @author RenShiWei
     * Date: 2020/4/16 10:37
     */
    @Select("SELECT u.username, \n" +
            "\tu.face_image, \n" +
            "\tu.nickname, \n" +
            "\tc.* FROM comments c LEFT JOIN users u " +
            "ON c.from_user_id=u.id " +
            "WHERE c.video_id= #{videoId} AND c.last_id =#{lastId}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "video_id", property = "videoId"),
            @Result(column = "from_user_id", property = "fromUserId"),
            @Result(column = "comment", property = "comment"),
//            @Result(column = "observer_id", property = "user",
//                    one = @One(select = "com.show.mapper.UsersMapper.queryUserForObserve",
//                            fetchType = FetchType.EAGER)),
            @Result(column = "last_id", property = "lastId"),
            @Result(column = "create_time", property = "createTime"),
//            @Result(column = "is_delete", property = "delete"),
//            @Result(column = "create_date", property = "createDate"),
//            @Result(column = "update_date", property = "updateDate")
    })
    List<CommentsVo> querySecondObserveList(@Param("videoId") String videoId,@Param("lastId") String lastId);

}
