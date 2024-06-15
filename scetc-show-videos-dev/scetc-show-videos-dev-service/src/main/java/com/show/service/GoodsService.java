package com.show.service;

import com.show.mapper.GoodsMapper;
import com.show.pojo.Goods;
import com.show.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface GoodsService {
    public List<Goods> getUserGoods(String userId);

    public List<Goods> getAllGoods();
    void insertGoods(Goods goods);

    List<Goods> dailyGoods();

    List<GoodsVo> queryGoodsVo(String id);

    List<Goods> searchByKeyword(String keyword);

    List<Goods> findGoodsByStatusAndId(String status, String uid);

    void delete(String id);
}
