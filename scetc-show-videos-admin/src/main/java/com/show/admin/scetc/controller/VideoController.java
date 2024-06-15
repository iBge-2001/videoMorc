package com.show.admin.scetc.controller;

import com.show.admin.scetc.annotation.SysLog;
import com.show.admin.scetc.pojo.Video;
import com.show.admin.scetc.pojo.VideosVo;
import com.show.admin.scetc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.show.admin.scetc.pojo.PageResult;
import com.show.admin.scetc.service.VideoService;
import com.show.admin.scetc.utils.LybJsonResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;

/**
 * 视频操作
 * 
 * @author ibge
 *
 */
@RestController
@RequestMapping("/video")
public class VideoController extends BasicController {

	@Autowired
	private VideoService videoService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/welcome")
	public String welcome() {
		return "welcome to my wolrd";
	}

	/**
	 * 查詢視頻的分類
	 * 
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@PostMapping("/selectVideoType")
	@SysLog
	public LybJsonResult selectVideoType(String keyword,
                                         @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                                         @RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize) {
		PageResult pageResult = videoService.selectVideoType(page, pageSize, keyword);
		return LybJsonResult.ok(pageResult);
	}

	/**
	 * 分頁查詢視頻
	 * 
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @param status
	 * @return
	 */
	@GetMapping("/queryAll")
	@SysLog
	public LybJsonResult queryAll(String keyword, String title,
                                  @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
                                  @RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize, String status) {
		if (page == null) {
			page = 1;
		}
		System.out.println("分类关键字"+"title:"+title);
		PageResult pageResult = videoService.queryAll(page, pageSize, keyword, title);
		return LybJsonResult.ok(pageResult);
	}

	/**
	 * 根據狀態來更新狀態
	 * 
	 * @param status
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateVideo")
	@SysLog
	public LybJsonResult updateVideo(String status, String id) {

		if (status.equals(DELETE)) {
			videoService.delete(id);
		}
		return LybJsonResult.ok();
	}
	@GetMapping("/{id}")
	public LybJsonResult getOne(@PathVariable String id) {
		Video one = videoService.selectOne(id);
		System.out.println(one);
		return  LybJsonResult.ok(one);
	}
	@DeleteMapping("/delete/{id}")
	public LybJsonResult deleteOrder(@PathVariable String id) {
		int i = videoService.deleteByPrimaryKey(id);
		if (i>0) {
			return LybJsonResult.ok();
		}else return LybJsonResult.errorException("删除失败");
	}
	/**
	 * 编辑视频
	 * 
	 * @return
	 */
	@PutMapping("/editVideos")
	@SysLog
	public LybJsonResult editVideosSubmit(@RequestBody Video video) {

//		String label = categoryService.selectLbel(video.getVideoCategory());
//		video.setVideoCategory(video.getVideoCategory());
//		video.setCoverPath(coverPath.getOriginalFilename());
		Integer update = videoService.update(video);
		if (update>0){
			return LybJsonResult.ok();// 编辑视频
		}else return LybJsonResult.errorMsg("编辑失败");
	}

}
