package com.show.impl;

import com.show.mapper.GoodsMapper;
import com.show.pojo.Goods;
import com.show.service.GoodsService;
import com.show.vo.GoodsVo;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> getUserGoods(String userId) {
        return goodsMapper.getUserGoods(userId);
    }

    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.queryAll1();
    }

    @Override
    public void insertGoods(Goods goods) {

    goodsMapper.insert(goods);
    }

    @Override
    public List<Goods> dailyGoods() {
        return goodsMapper.dailyGoods();
    }

    @Override
    public List<GoodsVo> queryGoodsVo(String id) {
        return goodsMapper.queryGoodsVo(id);
    }

    @Override
    public List<Goods> searchByKeyword(String keyword) {
        return goodsMapper.searchByKeyword( keyword);
    }

    @Override
    public List<Goods> findGoodsByStatusAndId(String status, String uid) {
        return goodsMapper.findGoodsByStatusAndId(status, uid);
    }

    @Override
    public void delete(String id) {
        goodsMapper.deleteByPrimaryKey(id);
    }
}
