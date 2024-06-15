package com.show.admin.scetc.service;

import com.show.admin.scetc.pojo.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> getGoodsWithListImgs();
    void insertGoods(Goods goods);
    Boolean check(String id, Integer isCheck);
    Boolean deleteGoodById(String id);

    Goods selectOne(String id);

    Integer update(Goods goods);
}
