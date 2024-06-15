package com.show.admin.scetc.mapper;

import java.util.List;
import java.util.Map;

import com.show.admin.scetc.pojo.CategoryVo;
import org.apache.ibatis.annotations.Param;

import com.show.admin.scetc.pojo.Category;
import com.show.admin.scetc.utils.MyMapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface CategoryMapper extends MyMapper<Category> {

	/**
	 * 分页查询全部的专栏
	 * 
	 * @param keyword
	 * @return
	 */
	List<Category> queryAll(@Param("keyword") String keyword, @Param("title") String title);
	@Select(value = "SELECT\n" +
			"\tcategory.`name`\n" +
			"FROM\n" +
			"\tcategory")
	public List<String> getCategoryNameList();
	@Select(value = "select label from category where name = #{name}")
	public String selectLbel(@Param("name") String name);
	@Select("SELECT c.name AS categoryName, COUNT(v.id) AS total_videos  FROM category c LEFT JOIN videos v ON c.label = v.video_category WHERE c.name IN (SELECT name FROM category)GROUP BY c.name;")
	@Results({
			@Result(property = "categoryName", column = "categoryName"),
			@Result(property = "totalVideos", column = "total_videos")
	})
	List<CategoryVo> getCount();
}
