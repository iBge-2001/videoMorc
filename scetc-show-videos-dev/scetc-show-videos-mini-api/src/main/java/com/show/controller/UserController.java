package com.show.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.show.pojo.Users;
import com.show.service.UserService;
import com.show.utils.LybJsonResult;
import com.show.vo.Publisher;
import com.show.vo.UsersVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "用户业务接口 ", tags = { "头像上传" })
@RequestMapping("/user")
/**
 * 
 * @author 2024ibge林奕斌  创建时间：2024年4月11日 下午4:14:49
 */
public class UserController extends BasicController {
	@Autowired
	private UserService userService;
	/**
	 * logout 登出
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "用户上传头像", notes = "用户上传头像接口")
	@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
	@PostMapping("/uploadFace")
	public LybJsonResult uploadFace(String userId, @RequestParam("file") MultipartFile[] files) {

		
		if(StringUtils.isBlank(userId))
		{
			return LybJsonResult.errorException("用户id不能为空...");
		}
		
		// 文件保存命名空间
//		String fileSpace = "G://imooc_videos_dev";
		// 保存到数据库路径 相对路径
		String uploadPathDB = "/" + userId + "/face";

		if (files != null && files.length > 0) {

			FileOutputStream fileOutputStream = null;
			InputStream inputStream = null;
			try {
				String fileName = files[0].getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
					String finalFacePath = FILe_SPACE + "/" + uploadPathDB + "/" + fileName;
					// 数据库保存路径
					uploadPathDB += ("/" + fileName);

					File outFile = new File(finalFacePath);
					//
					if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
						// 创建父亲文件夹
						outFile.getParentFile().mkdirs();
					}

					fileOutputStream = new FileOutputStream(outFile);
					inputStream = files[0].getInputStream();

					IOUtils.copy(inputStream, fileOutputStream);
					 //调用接口
					
				}
				else
				{//增加校验防止入侵攻击
					return LybJsonResult.errorException("上传失败了");
					
				}
			} catch (IOException e) {
				e.printStackTrace();
				return LybJsonResult.errorException("上传失败了");
			}
			finally
			{
                //如果不为空
				//则flush 关闭
				try {
					fileOutputStream.flush();
					IOUtils.closeQuietly(fileOutputStream);
					IOUtils.closeQuietly(inputStream);

				} catch (IOException e) {
					
					e.printStackTrace();
				}

				
				
			}
		}
		
		Users user=new Users();
		user.setId(userId);
		user.setFaceImage(uploadPathDB);
		userService.updateUserInfo(user);
		return LybJsonResult.ok(user);
      
	}
	/**
	 * 根据userid去查询用户相关的信息
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "查询信息接口", notes = "查询用户信息接口")
	@ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
	@PostMapping("/query")
	public LybJsonResult uploadFace(String userId)
	{
		if(StringUtils.isBlank(userId))
		{
	       return LybJsonResult.errorMsg("用户id不能为空");
		}
	   Users userInfo=userService.queryUserInfo(userId);
	   UsersVo userVO=new UsersVo();
	   BeanUtils.copyProperties(userInfo, userVO);
       return LybJsonResult.ok(userVO);
	}
	/**
	 * 查询是否被跟随
	 * @param userId
	 * @param fanId
	 * @return
	 */
	@PostMapping("/queryIsFollowed")
	public LybJsonResult queryIsFollowed(String userId, String fanId)
	{
		if(StringUtils.isBlank(userId)||StringUtils.isBlank(fanId))
		{
	       return LybJsonResult.errorMsg("用户id不能为空");
		}
	   boolean isFollowed=userService.queryIsFollowed(userId, fanId);
       return LybJsonResult.ok(isFollowed);
	}
	@PostMapping("/queryPublisher")
	public LybJsonResult queryPublisher(String loginUserId, String videoId, String publisherUserId)
	{
		System.out.println("queryPublisher:loginUserId:"+loginUserId+"videoId:"+videoId+"publisherUserId:"+publisherUserId);
		if(StringUtils.isBlank(loginUserId)||StringUtils.isBlank(videoId)||StringUtils.isBlank(publisherUserId))
		{
	       return LybJsonResult.errorMsg("");
		}

	//1.查询视频发布者的信息 
    //2.查询当前登录者和视频的点赞关系
	   Users userInfo=userService.queryUserInfo(publisherUserId);
	   UsersVo publisher=new UsersVo();
	   BeanUtils.copyProperties(userInfo, publisher);
	   
	   boolean userLikeVideo =userService.isUserLikeVideo(loginUserId, videoId);
	   Publisher publish =new Publisher();
	   publish.setPulisher(publisher);
	   publish.setUserLikeVideo(userLikeVideo);
		System.out.println(publish);
       return LybJsonResult.ok(publish);
	}
	
	@PostMapping(value = "/userFollow" )//用戶关注
	public LybJsonResult userFollow(String userId, String fanId)//用户的id 用户的关注的id
	{
		userService.userFollow(userId,fanId);
		return LybJsonResult.ok();
	}
	@PostMapping(value = "/userUnFollow" )//用戶取消关注
	public LybJsonResult userUnFollow(String userId, String fanId)//用户的id 用户的关注的id
	{ 
		userService.userUnFollow(userId,fanId);
		return LybJsonResult.ok();
	}
	@GetMapping("/followings/{fanId}")
	public ResponseEntity<List<Users>> getFollowings(@PathVariable String fanId) {
		List<Users> followingsList = userService.getFollowings(fanId);
		return ResponseEntity.ok(followingsList);
	}
	@GetMapping("/user/{id}")
	public ResponseEntity<Users> queryUserInfo(@PathVariable String id) {
		Users users = userService.queryUserInfo(id);
		return ResponseEntity.ok(users);
	}
}
