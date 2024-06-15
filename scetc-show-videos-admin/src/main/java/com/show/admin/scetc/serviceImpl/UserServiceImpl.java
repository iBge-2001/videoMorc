package com.show.admin.scetc.serviceImpl;
import com.show.admin.scetc.mapper.UserMapper;
import com.show.admin.scetc.pojo.UserConsole;
import com.show.admin.scetc.pojo.Users;
import com.show.admin.scetc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public Boolean update1(String id, Integer status) {
        return userMapper.updateStatus(id,status)>0;
    }

    @Override
    public List<Users> list() {
        return userMapper.selectAll();
    }

    @Override
    public boolean remove(String id) {
        return userMapper.deleteByPrimaryKey(id)>0;

    }

    @Override
    public Boolean update(String id, String password) {
        return userMapper.updatePassword(id,password);
    }

    @Override
    public List<UserConsole> getUserConsole() {
        List<UserConsole> userConsoleVideo = userMapper.seleceAll1();
        for (UserConsole obj : userConsoleVideo) {
            obj.setStatus("上传视频");
        };
        List<UserConsole> userConsoleComments = userMapper.seleceAll2();
        for (UserConsole obj : userConsoleComments) {
            obj.setStatus("评论");
        };
        List<UserConsole> userConsoleGoods = userMapper.seleceAll3();
        for (UserConsole obj : userConsoleGoods) {
            obj.setStatus("上传商品");
        };
        List<UserConsole> list = new ArrayList<>();
            list.addAll(userConsoleVideo);
            list.addAll(userConsoleComments);
            list.addAll(userConsoleGoods);
        Collections.sort(list, Comparator.comparing(UserConsole::getTime).reversed());
        return list.subList(0, Math.min(list.size(), 10));
    }
}
