package com.show.service;

import java.util.List;

import com.show.pojo.Bgm;
import com.show.pojo.Category;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 2016wlw2 徐塬峰
 * 创建时间：2018年7月4日 下午8:57:53
 */
@Component
public interface CategoryService 
{
  //查询bgm列表
	List<Category> queryCategroyList();
    List<Category> queryCategoryNameList();


}
