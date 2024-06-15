package com.show.admin.scetc.mapper;

import com.show.admin.scetc.pojo.Goods;
import com.show.admin.scetc.utils.MyMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsMapper extends MyMapper<Goods> {
    @Select("SELECT * FROM Goods GROUP BY id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "intro", column = "intro"),
            @Result(property = "price", column = "price"),
            @Result(property = "value", column = "value"),
            @Result(property = "status", column = "status"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "isCheck", column = "is_check"),
            @Result(property = "images", column = "id", javaType = List.class, many = @Many(select = "getImgByGoodsId"))
    })
   List<Goods> getGoodsWithListImgs();

    @Select("SELECT imgs from Goods WHERE id = #{id}")
    List<String> getImgByGoodsId(String id);
    @Update("UPDATE goods set is_check = #{isCheck} where id = #{id}")
    Boolean updateCheck(@Param("id") String id,@Param("isCheck") Integer isCheck);
    @Delete("DELETE FROM goods WHERE id = #{id}")
    Boolean deleteGoodById(String id);
}
