package com.show.admin.scetc.serviceImpl;

import com.show.admin.scetc.mapper.GoodsMapper;
import com.show.admin.scetc.pojo.Goods;
import com.show.admin.scetc.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Override
    public List<Goods> getGoodsWithListImgs() {
        return goodsMapper.getGoodsWithListImgs();
    }

    @Override
    public Boolean check(String id, Integer isCheck) {
       return goodsMapper.updateCheck(id,isCheck);
    }

    @Override
    public Boolean deleteGoodById(String id) {
        return goodsMapper.deleteGoodById(id);
    }

    @Override
    public Goods selectOne(String id) {
        return  goodsMapper.selectByPrimaryKey(id);
    }
    @Override
    public Integer update(Goods goods) {
        int i = goodsMapper.updateByPrimaryKey(goods);
        return i;
    }
    @Override
    public void insertGoods(Goods goods) {

        goodsMapper.insert(goods);
    }
}
