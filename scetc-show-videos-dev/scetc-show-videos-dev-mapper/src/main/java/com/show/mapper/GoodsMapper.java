package com.show.mapper;
import com.show.pojo.Goods;
import com.show.utils.MyMapper;
import com.show.vo.GoodsVo;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsMapper extends MyMapper<Goods> {
    Goods getGoodById(String goodId);
    List<Goods> getUserGoods(String userId);
    void updateGood(Goods good);
    void deleteGood(String goodId);
    @Select(value = "select * from Goods where status = '待售出' GROUP BY id")
    List<Goods> queryAll1();

    @Select(value = "select * from Goods where id BETWEEN 1 AND 3")
    List<Goods> dailyGoods();

    @Select("SELECT * FROM Goods where id = #{id} GROUP BY id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "intro", column = "intro"),
            @Result(property = "price", column = "price"),
            @Result(property = "value", column = "value"),
            @Result(property = "status", column = "status"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "isCheck", column = "is_check"),
            @Result(property = "imgeList", column = "id", javaType = List.class, many = @Many(select = "getImgByGoodsId"))
    })
    List<GoodsVo> queryGoodsVo(@Param("id") String id);
    @Select("SELECT imgs from Goods WHERE id = #{id}")
    List<String> getImgByGoodsId(String id);
@Select(value = "  SELECT * FROM goods   \n" +
        "        WHERE status = '待售出'   \n" +
        "        AND is_check = 1   \n" +
        "        AND title LIKE CONCAT('%', #{keyword}, '%') " +
        "GROUP BY\n" +
        "\tgoods.id ")
    List<Goods> searchByKeyword(@Param("keyword") String keyword);
    @Select("SELECT * FROM goods WHERE status = #{status} AND uid = #{uid} group by id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "intro", column = "intro"),
            @Result(property = "price", column = "price"),
            @Result(property = "value", column = "value"),
            @Result(property = "status", column = "status"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "isCheck", column = "is_check"),

    })
    List<Goods> findGoodsByStatusAndId(@Param("status") String status, @Param("uid") String uid);

    @Update("UPDATE goods SET status ='已售出' WHERE id = #{id}")
    int updateProductStatusById(@Param("id") String id);
}
