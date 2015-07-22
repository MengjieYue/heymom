<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	Integer pageNum;
	Integer totalPages;
	Integer type=(Integer)request.getAttribute("type");
	pageNum=(Integer)request.getAttribute("currentPage") ;
	totalPages=(Integer)request.getAttribute("totalPages");
	String User=(String)request.getAttribute("userName");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <meta charset="UTF-8" />
    <title>河马定制题库管理</title>
    <script src="/static/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="/static/js/jquery-1.5.1.js"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/demo.css" />
    <link rel="stylesheet" type="text/css" href="/static/css/activitylist.css"/>
    <script type="text/javascript">
        
        $(document).ready(function(){
            document.getElementById("content").style.minHeight = document.documentElement.clientHeight-155+"px"; 
	        var pageIndex=<%=pageNum%>;
	        var pages=0;
			if(<%=totalPages%>==0){ 
				pages=<%=totalPages%>;
			}
			else{pages=<%=totalPages-1%>;}
			
			if(pageIndex==0){
				$("#pre")[0].disabled=true; 
			}
			else{
				$("#pre")[0].disabled=false; 
			}
			if(pageIndex==pages){
				$("#next")[0].disabled=true;
			}
			else{
				$("#next")[0].disabled=false;
			}

        });
    </script>
    <style type="text/css">
        th.navigation{
            width: auto;
            text-align: center;
        }
    </style>
  </head>
  
  <body>
    <div class="container" style="text-align: center;">
    <br><br><br>
    <header>
        <h1>河马村 <span>管理后台</span></h1>
        <br>
    </header>
     
    <div id="container">
        <div id="main">
        	<div id="welcome" style="font-style:italic;color:#404A54;margin-bottom: 6px;width:100%;text-align: right;">欢迎您，<%=User %></div>
            <div id="content" style="width: 1000px;">
                <div class="box">
                <div class="box-head">
                   <h2 class="left"><a href="/backend/choose/main" style="cursor: pointer;color: white;">主页  -> </a>测试题管理</h2>
                </div>
                <div class="table" id="OddTable">
                    <div id="nav">
                       <table width="100%" border="0" cellspacing="0" cellpadding="0"  >
                           <th class="navigation"><a href="/backend/customized/list?type=0" onclick="SetType(0)" style="cursor: pointer;">语言</a></th>
                           <th class="navigation"><a href="/backend/customized/list?type=1" onclick="SetType(1)" style="cursor: pointer;">数理逻辑</a></th>
                           <th class="navigation"><a href="/backend/customized/list?type=2" onclick="SetType(2)" style="cursor: pointer;">视觉空间</a></th>
                           <th class="navigation"><a href="/backend/customized/list?type=3" onclick="SetType(3)" style="cursor: pointer;">身体动觉</a></th>
                           <th class="navigation"><a href="/backend/customized/list?type=4" onclick="SetType(4)" style="cursor: pointer;">音乐</a></th>
                           <th class="navigation"><a href="/backend/customized/list?type=5" onclick="SetType(5)" style="cursor: pointer;">情绪智力</a></th>
                           <th class="navigation"><a href="/backend/customized/list?type=6" onclick="SetType(6)" style="cursor: pointer;">人际交往</a></th>
                       </table>
                     </div>
                    <table id="StoreTable" width="100%" border="0" cellspacing="0" cellpadding="0">
                       <tr>
                           <th style="width: 1%"></th>
                           <th style="width: 51% ; text-align:center">题目</th>
                           <th style="width: 10% ; text-align:center">性别</th>
                           <th style="width: 10% ; text-align:center">适龄段</th>
                           <th style="width: 10% ; text-align:center">题目维度</th>
                           <th style="width: 9% ; text-align:center">编辑</th>
                           <th style="width: 9% ; text-align:center" class="ac">删除</th>
                       </tr>
                       <c:forEach var="node" items="${list.content }">
	                       <tr>
	                           <td><input type="checkbox" class="checkbox" /></td>                        
	                           <td align="left">${node.decription }</td>
	                           <c:if test="${node.gender==1 }"><td align="center">男</td></c:if>
	                           <c:if test="${node.gender==2 }"><td align="center">女</td></c:if>
	                           <td align="center">${node.age }</td>
	                           <c:choose>
	                           		<c:when test="${node.type==0 }">
	                           			<td align="center">语言类</td>
	                           		</c:when>
	                           		<c:when test="${node.type==1 }">
	                           			<td align="center">数理逻辑</td>
	                           		</c:when>
	                           		<c:when test="${node.type==2 }">
	                           			<td align="center">视觉空间</td>
	                           		</c:when>
	                           		<c:when test="${node.type==3 }">
	                           			<td align="center">身体动觉</td>
	                           		</c:when>
	                           		<c:when test="${node.type==4 }">
	                           			<td align="center">音乐</td>
	                           		</c:when>
	                           		<c:when test="${node.type==5 }">
	                           			<td align="center">情趣智力</td>
	                           		</c:when>
	                           		<c:otherwise>
	                           			<td align="center">人际交往</td>
	                           		</c:otherwise>
	                           		
	                           </c:choose>
	                           
	                           <td align="center" ><a style="cursor: pointer;" class="ico edit" onclick="EditQuestion('${node.id}')">编辑</a></td>
	                           <td align="center" ><a href="/backend/customized/delete?questionId=${node.id }" class="ico del">删除</a></td>
	                       </tr>                       
						</c:forEach>
                       <tr>
                           <td></td>
                           <td  style="text-align:left" colspan="7"><img src="/static/images/add.png"><a style="cursor: pointer;" onclick="AddQuestion()">新增题目</a></td>
                       </tr>
                       <tr>
                           <td><input type="checkbox" class="checkbox" onclick="SetAllChecked()"/></td>
                           <td style="text-align:left" colspan="7"><h3>全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style="cursor: pointer;" class="ico del">删除</a></h3></td>
                       </tr>
                   </table>
                </div>
                <div style="margin-top: 15px;">
                	<a href="/backend/customized/list?currentPage=<%=pageNum-1 %>&type=<%=type%>"><button id="pre" style="margin-right: 20px">上一页</button></a>
                	<a href="/backend/customized/list?currentPage=<%=pageNum+1 %>&type=<%=type%>"><button id="next" >下一页</button></a>
                </div>
                </div>
                <div class="cl">&nbsp;</div>
            </div>
            <div style="text-align:center;width:100% ;background-color: #F5F5F5; margin-top: 10px;">copyright:杭州菏马村信息技术有限公司</div>
        </div>
    </div>

</div>

<div id="AddQuestion"  class="Popup" style="display: none; width: 350px; min-height: 300px;height: auto;">
    <div class="Popup_top">
        <h2 style="font-family:Georgia, Times, Times New Roman, serif;margin:0;display: inline;font-size:18px;font-weight:bold">新增题目</h2>
        <a class="Close" style="cursor: pointer;" onclick="Close(this,'Popup')"><img alt="关闭" src="/static/images/gif-0992.gif" /></a>
    </div>
    <div class="Popup_cen1" style="height: auto">
        <form  action="/backend/customized/add" method="post">
            <table id="Ques" width="100%" height="auto" cellspacing="0" cellpadding="0"  rules="all" class="table">
                <tr>
                    <td >
                        <lable for="title">维度：</lable>
                        <select style="width: 100px" name="type">
                        	<option value="0">语言类</option>
                            <option value="1">数理逻辑类</option>
                            <option value="2">视觉空间类</option>
                            <option value="3">身体动觉类</option>
                            <option value="4">音乐类</option>
                            <option value="5">情绪类</option>
                            <option value="6">人际交往类</option>
                        </select>
                    </td>
                </tr>
                <tr class="options">
                    <td>
                        <lable for="option">选项：</lable>
                        <input name="option[]" style="width: 60px;">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <lable for="score">分值：</lable>
                    	<input name="score[]" style="width: 30px;">
                    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	<button type="button" onclick="Addline()">增加</button>
                    	<button class="Delline" type="button" disabled="disabled" onclick="Delline(this)">删除</button>
                    </td>                   
                </tr>
                
                <tr>
                    <td >
                        <lable for="sex">性别：</lable>
                        <select name="gender" style="width: 70px">
                            <option value="1">男</option>
                            <option value="2">女</option>
                        </select>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <lable for="age">适龄：</lable>
                        <input name="age" style="width: 70px">&nbsp;岁
                        
                    </td>
                </tr>
                
                <tr >
                    <td  >
                        <lable for="question">题目：</lable>
                        <textarea name="description" style="width:100%;height: 70px;margin-top: 5px"></textarea>
                    </td>
                </tr>
                <!--<tr><td></td><td></td></tr>-->
                <!--<tr><td></td><td></td></tr>-->
                <tr>
                    <td align="center" style="border-bottom: 1px">
                        <button class="submit" type="submit" >保存</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div id="EditQuestion"  class="Popup1" style="display: none; width: 350px; min-height: 300px;height: auto;">
    <div class="Popup_top">
        <h2 style="font-family:Georgia, Times, Times New Roman, serif;margin:0;display: inline;font-size:18px;font-weight:bold">编辑题目</h2>
        <a class="Close" style="cursor: pointer;" onclick="Close(this,'Popup1')"><img alt="关闭" src="/static/images/gif-0992.gif" /></a>
    </div>
    <div class="Popup_cen1" style="height: auto">
        <form  action="/backend/customized/edit" method="post">
            <table id="EditQues" width="100%" height="auto" cellspacing="0" cellpadding="0"  rules="all" class="table">
                <tr>
                    <td >
                    	<input id="questionId" type="hidden" name="questionId">
                        <lable for="title">维度：</lable>
                        <select id="questionType" style="width: 100px" name="type">
                        	<option value="0">语言类</option>
                            <option value="1">数理逻辑类</option>
                            <option value="2">视觉空间类</option>
                            <option value="3">身体动觉类</option>
                            <option value="4">音乐类</option>
                            <option value="5">情绪类</option>
                            <option value="6">人际交往类</option>
                        </select>
                    </td>
                </tr>
                
                
                <tr id="ThisLine">
                    <td >
                        <lable for="sex">性别：</lable>
                        <select id="gender" name="gender" style="width: 70px">
                            <option value="1">男</option>
                            <option value="2">女</option>
                        </select>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <lable for="age">适龄：</lable>
                        <input id="age" name="age" style="width: 70px">&nbsp;岁
                        
                    </td>
                </tr>
                
                <tr >
                    <td  >
                        <lable for="question">题目：</lable>
                        <textarea id="description" name="description" style="width:100%;height: 70px;margin-top: 5px"></textarea>
                    </td>
                </tr>
                <!--<tr><td></td><td></td></tr>-->
                <!--<tr><td></td><td></td></tr>-->
                <tr>
                    <td align="center" style="border-bottom: 1px">
                        <button class="submit" type="submit" >保存</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<script type="text/javascript">
	function Addline(){
		var obj = $(".options");
		var index = obj.length-1;
		$(obj[index]).after('<tr class="options"><td><lable for="option">选项：</lable><input name="option[]" style="width: 60px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<lable for="score">分值：</lable>&nbsp;<input name="score[]" style="width: 30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" onclick="Addline()">增加</button>&nbsp;<button class="Delline" type="button" disabled="disabled" onclick="Delline(this)">删除</button></td></tr>');
		var del = $(".Delline");
		if(del.length>2){
			del.attr('disabled',false);
		}
		else{
			del.attr('disabled',true);
		}
	}
	
	function Delline(e){
		var index=e.parentNode.parentNode.rowIndex;
		document.getElementById("Ques").deleteRow(index);
		var del = $(".Delline");
		if(del.length>2){
			del.attr('disabled',false);
		}
		else{
			del.attr('disabled',true);
		}
	}
    
    function EditQuestion(questionId) {
    	$.getJSON("/backend/customized/find?questionId="+questionId+"",function(data){
    		$("#questionId")[0].value=data.id;
    		$("#questionType")[0].value=data.type;
    		$("#gender")[0].value=data.gender;
    		$("#age")[0].value=data.age;
    		$("#description")[0].value=data.decription;
    		var thisline=$("#ThisLine");
    		var length=$("#EditQues").find(".options").length;
    		var exsitLines=$($("#EditQues")[0]).find(".options");
    		for(var i=0;i<length;i++){
    			var index = exsitLines[i].rowIndex;
    			document.getElementById("EditQues").deleteRow(index);
    		}
    		
    		for(var i=0;i<data.options.length;i++){
    			$(thisline).before('<tr class="options"><td><lable for="option">选项：</lable><input value="'+data.options[i].decription+'" name="option[]" style="width: 60px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<lable for="score">分值：</lable><input value="'+data.options[i].value+'" name="score[]" style="width: 30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" onclick="Addline()">增加</button><button class="DellineForEdit" type="button" onclick="Delline(this)">删除</button></td></tr>');
    		}
    		var del = $(".DellineForEdit");
			if(del.length>2){
				del.attr('disabled',false);
			}
			else{
				del.attr('disabled',true);
			}
    	});
    	
		$('#EditQuestion').css('display','block');
        var target=$('.Popup1');
        $("#spm").show();

        var targetWidth=target.outerWidth()/2;
        var targetHeight=target.outerHeight()/2;
        target.css({"margin-top":-parseInt(targetHeight)+"px","margin-left":-parseInt(targetWidth)+"px"});
        target.show();
        return false;
	} 
    
    function AddQuestion(){
        $('#AddQuestion').css('display','block');
        var target=$('.Popup');
        $("#spm").show();

        var targetWidth=target.outerWidth()/2;
        var targetHeight=target.outerHeight()/2;
        target.css({"margin-top":-parseInt(targetHeight)+"px","margin-left":-parseInt(targetWidth)+"px"});
        target.show();
        return false;
    }

    function Show(e){
        switch(e) {
            case 1:
                $(".language").show();
                $(".logic").hide();
                $(".view").hide();
                $(".physic").hide();
                $(".music").hide();
                $(".mood").hide();
                $(".interpersonal").hide();
                break;
            case 2:
                $(".language").hide();
                $(".logic").show();
                $(".view").hide();
                $(".physic").hide();
                $(".music").hide();
                $(".mood").hide();
                $(".interpersonal").hide();
                break;
            case 3:
                $(".language").hide();
                $(".logic").hide();
                $(".view").show();
                $(".physic").hide();
                $(".music").hide();
                $(".mood").hide();
                $(".interpersonal").hide();
                break;
            case 4:
                $(".language").hide();
                $(".logic").hide();
                $(".view").hide();
                $(".physic").show();
                $(".music").hide();
                $(".mood").hide();
                $(".interpersonal").hide();
                break;
            case 5:
                $(".language").hide();
                $(".logic").hide();
                $(".view").hide();
                $(".physic").hide();
                $(".music").show();
                $(".mood").hide();
                $(".interpersonal").hide();
                break;
            case 6:
                $(".language").hide();
                $(".logic").hide();
                $(".view").hide();
                $(".physic").hide();
                $(".music").hide();
                $(".mood").show();
                $(".interpersonal").hide();
                break;
            case 7:
                $(".language").hide();
                $(".logic").hide();
                $(".view").hide();
                $(".physic").hide();
                $(".music").hide();
                $(".mood").hide();
                $(".interpersonal").show();
                break;
        }

    }

    function Close(e,classname){
        $(e).parents("."+classname).hide();
        $("#spm").hide();
    }

    function SetAllChecked(){
        if ($('.checkbox').attr('checked')==true)
        {
            $(".checkbox").removeAttr('checked');
        }

        else
        {
            $(".checkbox").attr("checked",'true');
        }

    }
</script>
  </body>
</html>
