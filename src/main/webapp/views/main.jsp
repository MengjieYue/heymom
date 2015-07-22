<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String UserName="";
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>河马办公室</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="/static/js/jquery.js" type="text/javascript"></script>
    <script src="/static/js/prefixfree.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/button.css" />
    <link rel="stylesheet" type="text/css" href="/static/css/demo.css" />
    <script type="text/javascript">
        $(function(){
        	$(".bt").css("opacity","1");  
        	$("#actions").hide();
        	allA=$("#actions").find("a");
        	allLi=$("#actions").find("li");   
        	for(var i=0;i<allLi.length;i++){
        		allLi[i].onmouseover=function(){
        			this.className="liclass";
        			this.childNodes[0].style.color = '#fff';
        		};
        		allLi[i].onmouseout=function(){
        			this.className="";
        			this.childNodes[0].style.color ='';
        		};
        	}  	        	
        });
    </script>
	<style type="text/css">
		/* #actions a:HOVER {
			background: #ddd;
		} */
		 #actions{
			background: none repeat scroll 0 0 #fff;
			border: 1px solid #ccc;
 			position: absolute; 
			right: 0; 
			top:33px;
			text-align:center;
			width: 85px;
			z-index: 1;
			border-radius:8px;
		} 
		#arrow{
			position: absolute;
			top:-13px;
			right: 15px;
			width: 0;
			height: 0;
			font-size: 0;
			border: 7px solid;
			border-color: transparent transparent #fff transparent;
		}
		#arrow-shadow{
			position: absolute;
			top:-14px;
			right: 15px;
			width: 0;
			height: 0;
			font-size: 0;
			border: 7px solid;
			border-color: transparent transparent #ccc transparent;
		}
		#actions li{
			list-style: none;
			cursor: pointer;
			font-size: 13px;
			line-height: 30px;
		}
		.aclass{
			color: #fff;
		}
		.liclass{
			background: #099;
		}
		
		
		#center:HOVER {
			background: #ddd;
		}
	</style>
  </head>
  
  <body>
    <div class="container1">
        <br><br><br>
        <header>
            <h1>河马村 <span>管理后台</span></h1>
			<br>
        </header>
        <div id="box">
        <%
        	if((String)request.getAttribute("User")!=null){
        		UserName=(String)request.getAttribute("User");
        	}
        	else{
        		UserName=(String)request.getAttribute("userName");
        	}
         %>
            <div id="welcome" style="font-style:italic;color:#404A54;line-height: 30px;">
            	<label>欢迎您，<%=UserName %></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	<a id="center" onclick="ShowMenu()" style="cursor: pointer;font-style:normal;color:#09F;font-weight:bold; display: inline;"><img src="/static/images/cog.png">个人中心</a>
            	<ul id="actions" style="cursor: pointer;font-style:normal;">
            		<div id="arrow-shadow"></div>
            		<div id="arrow"></div>
            		<li><a href="/backend/center/message" >群发短信</a></li>
            		<li><a >修改密码</a></li>
            		<li><a href="/backend/index/login" >退出登录</a></li>
            	</ul>	
            </div>
            <div class="bt">
                <div class="button" onclick="Link(0)"></div>
                <div class="button1" onclick="Link(1)"></div>
                <div class="button2" onclick="Link(2)"></div>
                <div class="button3" onclick="Link(3)"></div>
                <div class="button4" onclick="Link(4)"></div>
                <div style="clear:both"> </div> 
            </div>
        </div>
    </div>
    <script>
    	function Link(num){
    		switch(num){
    			case 0:location.href="/backend/activity/list";
    				break;
   				case 1:location.href="/backend/delivery/list";
   					break;
				case 2:location.href="/backend/coupon/list";
					break;
				case 3:location.href="/backend/provider/list";
   					break;
				case 4:location.href="/backend/customized/list";
					break;
				default:location.href="/backend/choose/main";
    		}
    		
    	}
    	/* var obj=$("#center");
    	obj.onclick=function(ev){
    		var ev=ev||window.event;
    		$("#actions").toggle();
    		document.onclick=function(){
    			$("#actions").hide();
    		}
    		ev.cancelBubble=true;
    	} */
    	 function ShowMenu(){
    		$("#actions").toggle();
    	} 
    </script>               
  </body>
</html>
