package com.show.admin.scetc.controller;

import com.show.admin.scetc.annotation.SysLog;
import com.show.admin.scetc.mapper.OrderMapper;
import com.show.admin.scetc.pojo.Goods;
import com.show.admin.scetc.pojo.Orders;
import com.show.admin.scetc.pojo.Video;
import com.show.admin.scetc.service.GoodsService;
import com.show.admin.scetc.utils.LybJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import java.util.List;

@RestController
@RequestMapping("/Goods")
public class goodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderMapper orderMapper;

    @GetMapping("/all")
    public List<Goods> getAllUser(){
        List<Goods> list = goodsService.getGoodsWithListImgs();
        return list;
    }
    @GetMapping("/{id}")
    public LybJsonResult getOne(@PathVariable String id) {
        Goods one = goodsService.selectOne(id);
        System.out.println(one);
        return  LybJsonResult.ok(one);
    }
    @PutMapping("/editGoods")
    @SysLog
    public LybJsonResult editVideosSubmit(@RequestBody Goods goods) {

//		String label = categoryService.selectLbel(video.getVideoCategory());
//		video.setVideoCategory(video.getVideoCategory());
//		video.setCoverPath(coverPath.getOriginalFilename());
        Integer update = goodsService.update(goods);
        if (update>0){
            return LybJsonResult.ok();// 编辑视频
        }else return LybJsonResult.errorMsg("编辑失败");
    }
    @PutMapping("/check")
    public LybJsonResult updateStatus(@RequestBody Goods goods){
        String id = goods.getId();
        Integer isCheck = goods.getIsCheck();
        Boolean flag = goodsService.check(id, isCheck);
        if (flag) {
            return LybJsonResult.ok();
        }else return LybJsonResult.errorException("状态切换失败");
    }
    @DeleteMapping("/{id}")
    public LybJsonResult deleteGoodById(@PathVariable String id) {
        Boolean aBoolean = goodsService.deleteGoodById(id);
        if (aBoolean) {
            return LybJsonResult.ok();
        }else return LybJsonResult.errorException("删除失败");
    }
    @PostMapping("/upload")
    public String addGoods( MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空，请选择一个文件上传。";
        }
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();

            // 获取文件的字节
            byte[] bytes = file.getBytes();

            // 这里可以添加保存文件的逻辑，例如将文件保存到服务器的指定目录
            String filePath = "D:/show_videos_dev/"+"admin"+"/goodsface/";
            File file1 = new File(filePath);
            if (!file1.exists()) {
                // Create the directory if it does not exist
                file1.mkdirs();
            }
            File dest = new File(filePath + file.getOriginalFilename());
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
                return("上传失败");
            }
            return  "admin"+"/goodsface/"+file.getOriginalFilename();
        } catch (Exception e) {
            return "文件上传失败：" + e.getMessage();
        }
    }
        // 将上传的文件保存到特定位置



        // 根据需要处理上传的图片和其他信息
        // 您可以将文件路径保存到数据库或执行任何其他操作
        // 创建Goods对象
//        Goods goods = new Goods();
//        goods.setImage("/"+"admin"+"/goodsface/" + image.getOriginalFilename());
//        goods.setId(goodsId);
//        goods.setDate(new Date());
//        goods.setPrice(price);
//        goods.setUid("admin");
//        goods.setTitle(title);
//        goods.setIntro(introText);
//        goods.setValue(value);
//        goods.setStatus(saleStatus);
//        System.out.println("insert");
//
//        // 调用服务层保存Goods对象到数据库
//        goodsService.insertGoods(goods);
//        return ResponseEntity.ok().body("OK");
    @GetMapping("/getOrder")
    public List<Orders> getOrder(){
        List<Orders> list = orderMapper.selectAll();
        return list;
    }
    @DeleteMapping("/delete/{id}")
    public LybJsonResult deleteOrder(@PathVariable String id) {
        int i = orderMapper.deleteByPrimaryKey(id);
        if (i>0) {
            return LybJsonResult.ok();
        }else return LybJsonResult.errorException("删除失败");
    }

    @PostMapping(value = "/insert")
    public String uploadFace( @RequestBody Goods goods)
    {

        // 根据需要处理上传的图片和其他信息
        // 您可以将文件路径保存到数据库或执行任何其他操作
        for(String image : goods.getImages()) {
            goods.setImgs(image);
            goods.setId(goods.getId());
            goods.setDate(new Date());
            goods.setPrice(goods.getPrice());
            goods.setUid("admin");
            goods.setTitle(goods.getTitle());
            goods.setIntro(goods.getIntro());
            goods.setValue(goods.getValue());
            goods.setIsCheck(0);
            goods.setStatus("待售出");
            System.out.println("insert");
            goodsService.insertGoods(goods);
        }

        return "上传成功";

    }
}
