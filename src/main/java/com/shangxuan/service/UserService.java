package com.shangxuan.service;


import com.github.pagehelper.PageInfo;
import com.shangxuan.bean.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    
    public boolean addUser(UserInfo user);
    public boolean deleteUser(int id);
    public boolean updateUser(UserInfo user);
    public List<UserInfo> findOne(int id);

    PageInfo findAll(int pageNum, int pageSize);

    String uploadImage(HttpServletRequest request, MultipartFile file);
}

