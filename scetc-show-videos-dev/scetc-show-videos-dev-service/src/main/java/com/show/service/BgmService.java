package com.show.service;

import java.util.List;

import com.show.pojo.Bgm;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 2024ibge 林奕斌
 * 创建时间：2024年7月4日 下午8:57:53
 */
@Component
public interface BgmService 
{
  //查询bgm列表
	List<Bgm> queryBgmList();
  //查询单个bgmid
    public Bgm queryBgmById(String bgmId);


}
