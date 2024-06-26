package com.show.admin.scetc.mapper;

import java.util.List;

import com.show.admin.scetc.pojo.Count;
import org.apache.ibatis.annotations.Param;

import com.show.admin.scetc.pojo.Bgm;
import com.show.admin.scetc.utils.MyMapper;
import org.apache.ibatis.annotations.Select;

public interface BgmMapper extends MyMapper<Bgm> {

	/**
	 * 查询全部的背景音乐
	 * 
	 * @param keyword
	 * @return
	 */
	List<Bgm> queryAll(@Param("keyword") String keyword, @Param("title") String title);

	/**
	 * 根据名称查询出指定的背景音乐列表
	 * 
	 * @param name
	 * @return
	 */
	List<Bgm> selectBgmByName(String name);
	@Select(value = "SELECT\n" +
			"\tCOUNT(bgm.id) as bgmCount\n" +
			"FROM\n" +
			"\tbgm")
	Integer getCount();
}
