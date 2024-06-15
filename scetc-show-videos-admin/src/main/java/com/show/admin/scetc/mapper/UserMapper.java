package com.show.admin.scetc.mapper;

import com.show.admin.scetc.pojo.UserConsole;
import com.show.admin.scetc.pojo.Users;
import com.show.admin.scetc.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper extends MyMapper<Users> {

    @Update("UPDATE users set status = #{status} where id = #{id}")
    public int updateStatus(@Param("id") String id, @Param("status") Integer status);

    @Update("UPDATE users set password = #{password} where id = #{id}")
    Boolean updatePassword(@Param("id") String id,@Param("password") String password);

    @Select("SELECT videos.create_time as time, users.nickname as user FROM videos INNER JOIN users ON videos.user_id = users.id")
    List<UserConsole> seleceAll1();
    @Select("SELECT comments.create_time as time, users.username as user FROM users INNER JOIN comments ON users.id = comments.from_user_id")
    List<UserConsole> seleceAll2();
    @Select("SELECT users.username as user, goods.date as time\n" +
            "FROM users INNER JOIN goods ON  users.id = goods.uid")
    List<UserConsole> seleceAll3();
    @Select(value = "SELECT\n" +
            "\tCOUNT(users.id) as useCount\n" +
            "FROM\n" +
            "\tusers")
    Integer getUserCount();
}