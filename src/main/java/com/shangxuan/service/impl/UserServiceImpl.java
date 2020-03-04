package com.shangxuan.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shangxuan.bean.UserInfo;
import com.shangxuan.dao.UserDao;
import com.shangxuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean addUser(UserInfo user) {
        return userDao.addUser(user);
    }
    @Override
    public boolean deleteUser(int id) {
        return userDao.deleteUser(id);
    }
    @Override
    public boolean updateUser(UserInfo user) {
        return userDao.updateUser(user);
    }

    @Override
    public List<UserInfo> findOne(int id) {
        return userDao.findOne(id);
    }

    @Override
    public PageInfo findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserInfo> list = userDao.findAll();
        return new PageInfo(list,5);
    }

    @Override
    public String uploadImage(HttpServletRequest request, MultipartFile file) {

        String filename = UUID.randomUUID() + file.getOriginalFilename();
        try {
            String contexPath= request.getSession().getServletContext().getRealPath("/");
            file.transferTo(new File(contexPath+filename));
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败");
        }
    }
}
