<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>无标题文档</title>
<link href="${ pageContext.request.contextPath}/css/adminstyle.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="${ pageContext.request.contextPath}/js/jquery.js"></script>

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>


</head>

<body style="background:#f0f9fd;">
	<div class="lefttop"><span></span>管理系统</div>
    <dl class="leftmenu">
  <dd>
    <div class="title">
    <span><img src="${pageContext.request.contextPath}/admin/images/leftico01.png" /></span>基本设置
    </div>
    	<ul class="menuson">
        <li><cite></cite><a href="index.jsp" target="rightFrame">轮播图片</a><i></i></li>
        <li><cite></cite><a href="imgtable.jsp" target="rightFrame">栏目设置</a><i></i></li>
        <li><cite></cite><a href="error.jsp" target="rightFrame">404页面</a><i></i></li>
        </ul>    
    </dd>
   <dd>
      <div class="title">
	  <span><img src="${ pageContext.request.contextPath}/admin/images/leftico03.png" /></span>班级管理</div>
       <ul class="menuson">
        <li><cite></cite><a href="${ pageContext.request.contextPath}/classlist.do"  target="rightFrame">管理班级</a><i></i></li>
        <li><cite></cite><a href="${ pageContext.request.contextPath}/toaddclass.do" target="rightFrame">添加班级</a><i></i></li>
        </ul>     
    </dd> 
    
    <dd>
    <div class="title">
    <span><img src="${ pageContext.request.contextPath}/admin/images/leftico04.png" /></span>教师管理</div>
    <ul class="menuson">
        <li><cite></cite><a href="${ pageContext.request.contextPath}/teacherlist.do"  target="rightFrame">管理教师</a><i></i></li>
        <li><cite></cite><a href="${ pageContext.request.contextPath}/toaddteacher.do" target="rightFrame">添加教师</a><i></i></li>
        <li><cite></cite><a href="${ pageContext.request.contextPath}/toteachersproduce.do" target="rightFrame">队伍简介</a><i></i></li>
     </ul>     
    </dd> 
    
    <dd>
    <div class="title">
    <span><img src="${ pageContext.request.contextPath}/admin/images/leftico02.png" /></span>学生管理</div>
    <ul class="menuson">
        <li><cite></cite><a href="${ pageContext.request.contextPath}/userlist.do"  target="rightFrame">管理学生</a><i></i></li>
        <li><cite></cite><a href="${ pageContext.request.contextPath}/toadduser.do" target="rightFrame">添加学生</a><i></i></li>
		<li><cite></cite><a href="save.action"  target="rightFrame">导入学生</a><i></i></li>
        </ul>     
    </dd> 
   
    <dd>
      <div class="title"><span><img src="${ pageContext.request.contextPath}/admin/images/leftico02.png" /></span>教学管理</div>
      <ul class="menuson">
        <li><cite></cite><a href="${ pageContext.request.contextPath}/courselist.do"  target="rightFrame">管理教学内容</a><i></i></li>
        <li><cite></cite><a href="${ pageContext.request.contextPath}/toaddcourse.do" target="rightFrame">添加教学内容</a><i></i></li>
    </ul>
    </dd>  
    
     <dd>
      <div class="title"><span><img src="${ pageContext.request.contextPath}/admin/images/leftico03.png" /></span>实验管理</div>
      <ul class="menuson">
        <li><cite></cite><a href="${ pageContext.request.contextPath}/experimentlist.do"  target="rightFrame">管理实验信息</a><i></i></li>
        <li><cite></cite><a href="${ pageContext.request.contextPath}/toaddexperiment.do" target="rightFrame">添加实验信息</a><i></i></li>
    </ul>
    </dd>  
    
    <dd>
      <div class="title"><span><img src="${ pageContext.request.contextPath}/admin/images/leftico04.png" /></span>新闻公告</div>
      <ul class="menuson">
        <li><cite></cite><a href="${ pageContext.request.contextPath}/newslist.do"  target="rightFrame">管理信息</a><i></i></li>
        <li><cite></cite><a href="${ pageContext.request.contextPath}/toaddnews.do" target="rightFrame">添加信息</a><i></i></li>
    </ul>
    </dd>   
    
    <dd>
      <div class="title">
	  <span><img src="${ pageContext.request.contextPath}/admin/images/leftico02.png" /></span>互动交流管理</div>
       <ul class="menuson">
        <li><cite></cite><a href="${ pageContext.request.contextPath}/comlist.do"  target="rightFrame">管理互动交流</a><i></i></li>
        </ul>     
    </dd> 
    
   
    
     
   
     
   
     
    <dd>
      <div class="title"><span><img src="${ pageContext.request.contextPath}/admin/images/leftico01.png" /></span>优秀学生</div>
      <ul class="menuson">
        <li><cite></cite><a href="${ pageContext.request.contextPath}/excellentlist.do"  target="rightFrame">管理优秀学生</a><i></i></li>
        <li><cite></cite><a href="${ pageContext.request.contextPath}/toaddexcellent.do" target="rightFrame">添加优秀学生</a><i></i></li>
    </ul>
    </dd>  
    
     <dd>
      <div class="title"><span><img src="${ pageContext.request.contextPath}/admin/images/leftico01.png" /></span>优秀教师</div>
      <ul class="menuson">
        <li><cite></cite><a href="${ pageContext.request.contextPath}/excellentlist.do"  target="rightFrame">管理优秀教师</a><i></i></li>
        <li><cite></cite><a href="${ pageContext.request.contextPath}/toaddexcellent.do" target="rightFrame">添加优秀教师</a><i></i></li>
    </ul>
    </dd>  
    
  </dl>
</body>
</html>
