package com.shangxuan.controller;

import com.github.pagehelper.PageInfo;
import com.shangxuan.bean.UserInfo;
import com.shangxuan.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private ServletContext servletContext;
    @RequestMapping("/findAll.do")
    public String findAll(@RequestParam(name="page" ,defaultValue = "1") int pageNum, @RequestParam(name="pageSize" ,defaultValue = "10")int pageSize){
//        List<UserInfo> userInfos=userService.findAll();
//        request.setAttribute("ui",userInfos);
        PageInfo page=userService.findAll(pageNum,pageSize);
        request.setAttribute("page",page);
        return "allUser";

    }

    @RequestMapping("/toAddUser.do")
    public  String addUser(){
        return "addUser";
    }

    @RequestMapping("/save.do")
    public String saveUser(UserInfo user){
        userService.addUser(user);
        return "redirect:/user/findAll.do";
    }

    @RequestMapping("/delete.do")
    public  String  deleteUser(int id){
        userService.deleteUser(id);
        return "redirect:/user/findAll.do";
    }
    @RequestMapping("/toUpdate.do")
    public String toUpdateUser(int id){
        List<UserInfo> userInfos =  userService.findOne(id);
        UserInfo userInfo = userInfos.get(0);
        request.setAttribute("userInfo",userInfo);
        return "updateUser";

    }
    @RequestMapping("/update.do")
    public String updateUser(UserInfo user){
        userService.updateUser(user);
        return "redirect:/user/findAll.do";
    }


    @RequestMapping("/downLoad.do")
    public void downLoad() throws IOException {
        // 完成下载功能的实现
        //1 确定用户要下载的资源是哪一个
        String fileName = request.getParameter("fileName");
        System.out.println(fileName);
        //2 根据用户要下载的资源匹配服务器上的资源
        String path = servletContext.getRealPath("download/"+fileName);
        File file = new File(path);
        // 输入流
        FileInputStream is = new FileInputStream(file);
        // 输出流
        ServletOutputStream os = response.getOutputStream();

        // 3 告诉用户的浏览器是以附件形式打开写回的数据(响应头)
        //细节： 下载专用头 不支持中文附件名
        // 火狐浏览器要的base64位编码   fileName(111.jpg)---base64编码---火狐---自动解码（base64解码）
        // 其它浏览器要的utf-8位的编码  fileName(111.jpg)---utf-8编码--其它浏览器-自动解码（utf-8解码）

        // 获取用户的浏览器 根据不同的浏览器转换不同的编码
        String value = request.getHeader("user-agent");
        String encode=null;
        if(value.contains("Firefox")){
            // base64位
            Base64.Encoder encoder = Base64.getEncoder();
            encode = "=?utf-8?B?" + encoder.encodeToString(fileName.getBytes("utf-8")) + "?=";
        }else{
            // utf-8位
            encode= URLEncoder.encode(fileName,"utf-8");
        }

        response.setHeader("content-disposition","attachment;filename="+encode);

        // 4 一个资源的输入流一个输出流 io流读写即可
        IOUtils.copy(is,os);
        is.close();
        os.close();
    }


    @RequestMapping(value = "/image.do", name = "文件上传")
    public String uploadImage(MultipartFile file) {
        userService.uploadImage(request,file);
        return "success";
    }

}
