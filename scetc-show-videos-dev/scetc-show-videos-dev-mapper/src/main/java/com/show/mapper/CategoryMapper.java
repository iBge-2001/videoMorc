package com.show.mapper;

import com.show.pojo.Category;
import com.show.utils.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper extends MyMapper<Category> {
    @Select(value = "SELECT\n" +
            "\tcategory.`name`\n" +
            "FROM\n" +
            "\tcategory")
    public List<Category> getCategoryNameList();
}