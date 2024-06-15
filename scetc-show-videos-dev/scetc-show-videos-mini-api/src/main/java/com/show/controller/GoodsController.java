package com.show.controller;

import com.show.pojo.Goods;
import com.show.service.GoodsService;
import com.show.vo.GoodsVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/usergoods/{userId}")
    public List<Goods> getUserItems(@PathVariable String userId) {
        List<Goods> goodsList = goodsService.getUserGoods(userId);
        return goodsService.getUserGoods(userId);
    }
    @ApiOperation(value = "用户上传视频", notes = "用户上传视频接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品id", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "goodsId", value = "统一产品id", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "goodImgs", value = "商品图", required = false, dataType = "String", paramType = "form"), //// 可以不指定
            @ApiImplicitParam(name = "title", value = "产品名称", required = true, dataType = "String", paramType = "form"), // 参数类型为form
            @ApiImplicitParam(name = "introText", value = "产品介绍", required = true, dataType = "String", paramType = "form"), // 参数类型为form
            @ApiImplicitParam(name = "price", value = "价格", required = true, dataType = "BigDecimal", paramType = "form"),
            @ApiImplicitParam(name = "value", value = "成色", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "date", value = "日期", required = false, dataType = "date", paramType = "query"), // 可以不指定
            @ApiImplicitParam(name = "saleStatus", value = "产品状态", required = false, dataType = "String", paramType = "query")// 可以不指定
    })
    @PostMapping(value = "/upload")
    public String uploadFace(String userId, String goodsId,String goodImgs, String title, String introText, BigDecimal price,
                                    String value, Date date, String saleStatus, MultipartFile image
                                   ) {
        // 将上传的文件保存到特定位置
        String filePath = "D:/show_videos_dev/"+userId+"/goodsface/";
        File file = new File(filePath);
        if (!file.exists()) {
            // Create the directory if it does not exist
             file.mkdirs();
        }
        File dest = new File(filePath + image.getOriginalFilename());
                try {
            image.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
        // 根据需要处理上传的图片和其他信息
        // 您可以将文件路径保存到数据库或执行任何其他操作
        Goods goods = new Goods();
        goods.setImgs("/"+userId+"/goodsface/" + image.getOriginalFilename());
        goods.setId(goodsId);
        goods.setDate(new Date());
        goods.setPrice(price);
        goods.setuId(userId);
        goods.setTitle(title);
        goods.setIntro(introText);
        goods.setValue(value);
        goods.setStatus(saleStatus);
        goods.setIsCheck(0);
        System.out.println("insert");

        goodsService.insertGoods(goods);
        return "上传成功";

    }
    @GetMapping(value = "/allgoods")
    public List<Goods> getAllGoods() {
        return goodsService.getAllGoods();
    }
    @GetMapping(value = "/dailyGoods")
    public List<Goods> dailyGoods() {
        return goodsService.dailyGoods();
    }
    @GetMapping(value = "/getDetailById/{id}")
    public List<GoodsVo> getDetailById(@PathVariable String id) {
        return goodsService.queryGoodsVo(id);
    }
    @GetMapping(value = "/searchGoods")
    public List<Goods> searchGoods(@RequestParam String keyword) {
        return goodsService.searchByKeyword(keyword);
    }
    @GetMapping("/getSelfGoodsList")
    public List<Goods> getGoodsByStatusAndId(@RequestParam("status") String status, @RequestParam("uid") String uid) {
        return goodsService.findGoodsByStatusAndId(status, uid);
    }
    @PostMapping("/delete")
    public void deleteGoods(String id){
        goodsService.delete(id);
    }
}
