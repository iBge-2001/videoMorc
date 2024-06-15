package com.show.vo;

import com.show.pojo.Goods;
import lombok.Data;

import java.util.List;
@Data
public class GoodsVo extends Goods {
    List<String> imgeList;
}
