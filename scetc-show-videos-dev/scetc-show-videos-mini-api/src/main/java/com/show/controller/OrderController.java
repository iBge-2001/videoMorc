package com.show.controller;

import com.show.mapper.GoodsMapper;
import com.show.mapper.OrderMapper;
import com.show.pojo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderMapper orderService;
    @Autowired
    GoodsMapper goodsMapper;
    @PostMapping("/Insert")
    public ResponseEntity<?> createOrder(@RequestBody Orders ordersRequest) {
        try {
            ordersRequest.setCreate_time(new Date());
            // 调用订单服务生成订单
            orderService.insert(ordersRequest);
            goodsMapper.updateProductStatusById(ordersRequest.getGood_id());
            // 返回订单创建结果
            return new ResponseEntity<>(ordersRequest, HttpStatus.OK);
        } catch (Exception e) {
            // 处理异常并返回错误信息
            return new ResponseEntity<>("订单生成失败：" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateOrder(@RequestBody Orders ordersRequest) {
        try {

            orderService.update(ordersRequest.getId(),ordersRequest.getStatus());
            // 返回订单创建结果
            return new ResponseEntity<>(ordersRequest, HttpStatus.OK);
        } catch (Exception e) {
            // 处理异常并返回错误信息
            return new ResponseEntity<>("发货失败" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
