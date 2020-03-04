<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<html>
<body>
<h2>Hello World!</h2>
<a href="${pageContext.request.contextPath}/user/findAll.do">查询所有用户</a>
<h2>下载资源列表</h2>
<a href="${pageContext.request.contextPath}/user/downLoad.do?fileName=1.txt">下载文档</a><br/>
<a href="${pageContext.request.contextPath}/user/downLoad.do?fileName=迪丽热巴.jpg">下载图片</a><br/>
<a href="${pageContext.request.contextPath}/user/downLoad.do?fileName=3.mp3">下载音乐</a><br/>

<form action="${pageContext.request.contextPath}/user/image.do" method="post" enctype="multipart/form-data">
    <input type="file" name="file" value="文件上传">
    <input type="submit" value="上传">
</form>
</body>
</html>

