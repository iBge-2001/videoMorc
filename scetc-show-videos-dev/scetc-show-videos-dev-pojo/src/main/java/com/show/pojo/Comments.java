package com.show.pojo;

import java.util.Date;
import javax.persistence.*;

public class Comments {
    private String id;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "from_user_id")
    private String fromUserId;

    @Column(name = "create_time")
    private Date createTime;
    /** 评论上一级的id */
    @Column(name="last_id")
    private String lastId;

    private String comment;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return video_id
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * @param videoId
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * @return from_user_id
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * @param fromUserId
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id='" + id + '\'' +
                ", videoId='" + videoId + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", createTime=" + createTime +
                ", lastId=" + lastId +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }
}