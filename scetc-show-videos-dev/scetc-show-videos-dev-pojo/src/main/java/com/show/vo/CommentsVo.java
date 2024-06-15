package com.show.vo;

import com.show.pojo.Comments;
import com.show.pojo.Users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

public class CommentsVo extends Comments {
    private List<CommentsVo> nextNodes = new ArrayList<>();
    private String id;
    private Boolean second;

    public Boolean getSecond() {
        return second;
    }

    public void setSecond(Boolean second) {
        this.second = second;
    }
//    @Column(name = "video_id")
//    private String videoId;
//
//    @Column(name = "from_user_id")
//    private String fromUserId;
//
//    @Column(name = "create_time")
//    private Date createTime;
//
//    private String comment;
    
    private String nickName;
    private Users user;

    public Users getUser() {
        return user;
    }

    public List<CommentsVo> getNextNodes() {
        return nextNodes;
    }

    public void setNextNodes(List<CommentsVo> nextNodes) {
        this.nextNodes = nextNodes;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public CommentsVo() {
    }

    public CommentsVo(CommentsVo commentsVo) {
    this.setComment(commentsVo.getComment());
    this.setId(commentsVo.getId());
    this.setSecond(commentsVo.getSecond());
    this.setLastId(commentsVo.getLastId());
    this.setUser(commentsVo.getUser());
    this.setNickName(commentsVo.getNickName());
    this.setFromUserId(commentsVo.getFromUserId());
    this.setCreateTime(commentsVo.getCreateTime());
    }



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
//    public String getVideoId() {
//        return videoId;
//    }

    /**
     * @param videoId
     */
//    public void setVideoId(String videoId) {
//        this.videoId = videoId;
//    }

    /**
     * @return from_user_id
     */
//    public String getFromUserId() {
//        return fromUserId;
//    }

    /**
     * @param fromUserId
     */
//    public void setFromUserId(String fromUserId) {
//        this.fromUserId = fromUserId;
//    }

    /**
     * @return create_time
     */
//    public Date getCreateTime() {
//        return createTime;
//    }

    /**
     * @param createTime
     */
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }

    /**
     * @return comment
     */
//    public String getComment() {
//        return comment;
//    }


//    public void setComment(String comment) {
//        this.comment = comment;
//    }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

//	@Override
//	public String toString() {
//		return "CommentsVo [id=" + id + ", videoId=" + videoId + ", fromUserId=" + fromUserId + ", createTime="
//				+ createTime + ", comment=" + comment + ", nickName=" + nickName + "]";
//	}


    @Override
    public String toString() {
        return "CommentsVo{" +
                "nextNodes=" + nextNodes +
                ", id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}