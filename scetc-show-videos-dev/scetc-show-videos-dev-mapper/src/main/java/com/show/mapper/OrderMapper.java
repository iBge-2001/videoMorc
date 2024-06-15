package com.show.mapper;

import com.show.pojo.Orders;
import com.show.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface OrderMapper extends MyMapper<Orders> {

    @Update(value = "update orders SET status=#{status} where good_id = #{good_id}")
    void update(@Param("good_id") String good_id,@Param("status") String status);
}
