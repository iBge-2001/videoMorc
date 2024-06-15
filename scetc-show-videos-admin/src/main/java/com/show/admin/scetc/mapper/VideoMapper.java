package com.show.admin.scetc.mapper;

import com.show.admin.scetc.pojo.Video;
import com.show.admin.scetc.utils.MyMapper;
import org.apache.ibatis.annotations.Select;

public interface VideoMapper extends MyMapper<Video> {

    @Select(value = "SELECT\n" +
            "\tCOUNT(videos.id) as videoNum\n" +
            "FROM\n" +
            "\tvideos")
    Integer getVideoCount();
    @Select(value = "SELECT\n" +
            "\tSUM(videos.view_num) as viewCount\n" +
            "FROM\n" +
            "\tvideos")
    Integer getViewCount();
}
