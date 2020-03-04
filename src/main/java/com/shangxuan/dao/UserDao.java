package com.shangxuan.dao;

import com.shangxuan.bean.UserInfo;

import java.util.List;

public interface UserDao {
    public List<UserInfo> findAll();
    public boolean addUser(UserInfo user);
    public boolean deleteUser(int id);
    public boolean updateUser(UserInfo user);
    public List<UserInfo> findOne(int id);
}

