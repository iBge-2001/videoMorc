package com.show.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.show.pojo.Category;
import com.show.service.CategoryService;
import com.show.utils.LybJsonResult;

@RestController
@RequestMapping("/category")
/**
 * 
 * @author 2024ibpg 林奕斌 创建时间：2024年3月11日 下午4:14:49
 */
public class CategoryController extends BasicController {
	
	@Autowired
	private CategoryService categoryService;
	@RequestMapping("queryAll")
	public LybJsonResult queryAll()
	{
		List<Category> list=categoryService.queryCategroyList();
		new LybJsonResult();
		return LybJsonResult.ok(list);
	}


	
}
