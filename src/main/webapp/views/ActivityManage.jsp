
<%@page import="org.springframework.data.domain.PageImpl"%>
<%@page import="org.springframework.data.domain.Page"%>
<%@page import="com.heymom.backend.dto.activity.ActivityDto"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	Integer pageNum;
	Integer totalPages;
	pageNum=(Integer)request.getAttribute("currentPage") ;
	totalPages=(Integer)request.getAttribute("totalPages");
	String User=(String)request.getAttribute("userName");
	String A_city= (String)request.getAttribute("city");
	String A_age= (String)request.getAttribute("age");
	String A_type= (String)request.getAttribute("type");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>活动管理</title>
    
	<script src="/static/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="/static/js/jquery-1.5.1.js"></script>
    <script type="text/javascript" src="/static/js/My97DatePicker/WdatePicker.js"></script>
    <script src="/static/js/ajaxfileupload.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/demo.css" />
    <link rel="stylesheet" type="text/css" href="/static/css/activitylist.css"/> 
    <script type="text/javascript">
    $(document).ready(function(e) {
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
		var cityArray="<%=A_city%>";
		var typeArray="<%=A_type%>";
		var ageString="<%=A_age%>";
		others="";
		if(cityArray!="-1"){
			var cityObj=document.getElementsByName("A_city");		
			var cityArrayObj=cityArray.split(",");
			var num=cityArrayObj.length;
			others=others+"&&"+"city="+cityArray;
			for(var i=0;i<num;i++){
				for(var j=0;j<cityObj.length;j++){
					if(cityObj[j].value==cityArrayObj[i]){
						Selected(cityObj[j].nextElementSibling);
						break;
					}
				}
			}
		}
		if(ageString!="-1"){
			var ageObj=document.getElementsByName("radio2");
			others=others+"&&"+"age="+ageString;
			for(var i=0;i<ageObj.length;i++){
				if(ageObj[i].value==ageString){
					Selected(ageObj[i].nextElementSibling);
					break;
				}
			}
		}
		if(typeArray!="-1"){
			var typeObj=document.getElementsByName("A_type");
			var typeArrayObj=typeArray.split(",");
			var num=typeArrayObj.length;
			others=others+"&&"+"type="+typeArray;
			for(var i=0;i<num;i++){
				for(var j=0;j<typeObj.length;j++){
					if(typeObj[j].value==typeArrayObj[i]){
						Selected(typeObj[j].nextElementSibling);
						break;
					}
				}
			}
		}
				
        $("#selectList").find(".more").toggle(function(){
            $(this).addClass("more_bg");
            $(".more-none").show();
        },function(){
            $(this).removeClass("more_bg");
            $(".more-none").hide();
          });
        
       $('.confirm-btn').click(function () {
           ajaxFileUpload();
       });
       $('.confirm-btn1').click(function () {
           ajaxFileUpload1();
       });
    });
    
    </script>
    <script type="text/javascript">
    	function ajaxFileUpload() {
            $.ajaxFileUpload({
                url: '/backend/upload',
                secureuri: false,
                fileElementId: 'avatarPic',
                dataType: 'text',
                success: function (result) {
                    /* $('#echo').html($('<img/>').attr('src',result)).append('<br/><br/><br/>').append(result); */
                    $('#uploadPic').attr('src',result);
                    $('#addImage')[0].value=result;
                },
                error: function () {
                    alert("上传出错，请重试");
                }

            });           
        }
        
        function ajaxFileUpload1() {
            $.ajaxFileUpload({
                url: '/backend/upload',
                secureuri: false,
                fileElementId: 'avatarPic1',
                dataType: 'text',
                success: function (result) {
                    /* $('#echo').html($('<img/>').attr('src',result)).append('<br/><br/><br/>').append(result); */
                    $('#uploadPic1').attr('src',result);
                    $('#editImage')[0].value=result;
                },
                error: function () {
                    alert("上传出错，请重试");
                }

            });
     	}
    </script>
  </head>
  
  <body>
  	<input type="hidden" id="urlSaver" value="/backend/activity/list">
    <div class="container">
            <br><br><br>
            <header style="padding: 0px 30px 0px 30px;">
                <h1>河马村 <span>管理后台</span></h1>
				<br>
            </header>
            
            <div id="container">
            	<div id="welcome" style="font-style:italic;color:#404A54;float: right;margin-bottom: 6px;">欢迎您，<%=User %>     </div>
                <div class="shell">
                    <div id="main">
                        <div class="cl">&nbsp;</div>
                        <div id="content" >
                            <div class="box">
                                <div class="box-head">
                                    <h2 class="left"><a href="/backend/choose/main" style="cursor: pointer;color: white;">主页  -> </a>活动管理</h2>
                                    <div class="right">
                                        <form action="" method="post">
                                        	<label>搜索活动</label>
                                        	<input type="text" class="field small-field" name="name"/>
                                        	<input type="submit" class="button" value="搜索" />
                                        </form>
                                        
                                    </div>
                                </div>
                                <div class="w1200" >
                                  <div class="list-screen">
                                    <div class="screen-term">
                                      <div class="selectNumberScreen">
                                        <div id="selectList" class="screenBox screenBackground">
                                          <dl class="listIndex" attr="city">
                                            <dt>城市</dt>
                                            <dd>
                                              <label><a href="javascript:;" value="-1" attrval="all">不限</a></label>
                                              
                                              <label>
                                                <input name="A_city" type="checkbox" value="2" autocomplete="off"/>
                                                <a href="javascript:;" value="2" attrval="hangzhou">杭州</a></label>
                                              
                                            </dd>
                                          </dl>
                                          <dl class=" listIndex" attr="child">
                                            <dt>年龄段</dt>
                                            <dd>
                                              <label><a href="javascript:;" values2="-1" values1="-1" attrval="all">不限</a> </label>
                                              <label>
                                                <input name="radio2" type="radio" value="1-3" />
                                                <a href="javascript:;" values2="3" values1="1" attrval="1-3">1-3岁</a> </label>
                                              <label>
                                                <input name="radio2" type="radio" value="3-4" />
                                                <a href="javascript:;" values2="4" values1="3" attrval="3-4">3-4岁</a></label>
                                              <label>
                                                <input name="radio2" type="radio" value="4-5" />
                                                <a href="javascript:;" values2="5" values1="4" attrval="4-5">4-5岁</a></label>
                                               <label>
                                                <input name="radio2" type="radio" value="5-6" />
                                                <a href="javascript:;" values2="6" values1="5" attrval="5-6">5-6岁</a></label>
                                                <label>
                                                <input name="radio2" type="radio" value="6-" />
                                                <a href="javascript:;" values2="" values1="6" attrval="older">6岁以上</a></label>
                                            </dd>
                                          </dl>
                                          <dl class="listIndex" attr="kind">
                                            <dt>活动类型</dt>
                                            <dd>
                                              <label><a href="javascript:;" values="" attrval="all">不限</a></label>
                                              <label>
                                                <input name="A_type" type="checkbox" value="0" autocomplete="off"/>
                                                <a href="javascript:;" values="0" attrval="art">艺术类</a></label>
                                              <label>
                                                <input name="A_type" type="checkbox" value="1" autocomplete="off"/>
                                                <a href="javascript:;" values="1" attrval="sport">体育运动</a> </label>
                                              <label>
                                              	<input name="A_type" type="checkbox" value="2" autocomplete="off"/>
                                                <a href="javascript:;" values="2" attrval="language">语言类</a> </label>
                                               <label>
                                              	<input name="A_type" type="checkbox" value="3" autocomplete="off"/>
                                                <a href="javascript:;" values="3" attrval="transition">幼小衔接</a> </label>
                                               <label>
                                              	<input name="A_type" type="checkbox" value="4" autocomplete="off"/>
                                                <a href="javascript:;" values="4" attrval="prepare">入园准备</a> </label>
                                               <label>
                                              	<input name="A_type" type="checkbox" value="5" autocomplete="off"/>
                                                <a href="javascript:;" values="5" attrval="behavior">习惯培养</a> </label>
                                               <label>
                                              	<input name="A_type" type="checkbox" value="6" autocomplete="off"/>
                                                <a href="javascript:;" values="6" attrval="eq">情商培养</a> </label>
                                               <label>
                                              	<input name="A_type" type="checkbox" value="7" autocomplete="off"/>
                                                <a href="javascript:;" values="7" attrval="potency">全脑开发</a> </label>
                                               <label>
                                              	<input name="A_type" type="checkbox" value="8" autocomplete="off"/>
                                                <a href="javascript:;" values="8" attrval="game">游乐类</a> </label>
                                            </dd> 
                                          </dl>
                                        </div>
                                      </div>   
                                    </div>
                                  </div>
                                    
                                  <div class="hasBeenSelected clearfix" style="min-height: 30px;height: auto;">
                                    <div style="float:right;" class="eliminateCriteria">【清空全部】 </div>
                                    <dl>
                                      <dt>已选条件：</dt>
                                      <dd style="DISPLAY: none" class=clearDd>
                                        <div class=clearList></div>
                                    </dl>
                                  </div>
                                  <script type="text/javascript" src="/static/js/shaixuan.js"></script> 
                                </div>
                                <div class="table" id="ActivityList">
                                    <table id="StoreTable" width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <th style="width: 1%"></th>
                                            <th style="width: 35% ; text-align:center">活动主题</th>
                                            <th style="width: 10% ; text-align:center">活动状态</th>
                                            <th style="width: 15% ; text-align:center">报名情况</th>
                                            <th style="width: 19% ; text-align:center">编辑</th>
                                            <th style="width: 20% ; text-align:center" class="ac">删除</th>
                                        </tr>

                                        <%
                                        	PageImpl<ActivityDto> list = (PageImpl)request.getAttribute("list");
                                        	List<String> status=new ArrayList<String>();
                                        	/* list =(PageImpl)request.getAttribute("list"); */
                                        	status=(List)request.getAttribute("status");
                                        	List<ActivityDto> allActivities=list.getContent();
                                        	ActivityDto activity=new ActivityDto();
                                        	String state=new String();
                                        	for(int i=0;i<list.getContent().size();i++){
                                        		activity = list.getContent().get(i);
                                        		state = status.get(i);%>
                                        		<tr>
	                                            <td><input type="checkbox" class="checkbox" /></td>
	                                            <td ><h3><a class="station" style="text-decoration: underline;cursor: pointer;" onclick="ShowActivity(<%=activity.getId()%>)"><%=activity.getName() %></a></h3></td>
	                                            <td><%=state %></td>
	                                            <td style="text-align:center;"><%=activity.getAttendCount() %>人报名/<%=activity.getMaxAttedneeCount() %></td>
	                                            <td style="text-align:center;"><a class="ico edit" onclick="EditActivity(<%=activity.getId() %>)" style="cursor: pointer;">编辑</a></td>
	                                            <td style="text-align:center;"><a href="/backend/activity/delete?id=<%=activity.getId() %>" class="ico del" style="cursor: pointer;">删除</a></td>
	                                        	</tr>
                                        	<% } %>
                                        
                                        <tr>
                                            <td></td>
                                            <td  style="text-align:left"><img src="/static/images/add.png"><a onclick="AddActivity()" style="cursor: pointer;">新增活动</a></td>
                                            <td ></td>
                                            <td></td>
                                            <td ></td>
                                            <td ></td>
                                        </tr>
                                        <tr>
                                            <td><input type="checkbox" class="checkbox" onclick="SetAllChecked()"/></td>
                                            <td style="text-align:left"><h3>全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="ico del"style="cursor: pointer;">删除</a></h3></td>
                                            <td ></td>
                                            <td></td>
                                            <td ></td>
                                            <td ></td>
                                        </tr>
                                    </table>
                                </div>
                                
                                <button id="pre" type="button" style="margin-right: 20px" onclick="AskForPre()">上一页</button>
                                <button id="next" type="button" onclick="AskForNext()">下一页</button>
                            </div>
                            
                            <div class="cl">&nbsp;</div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="text-align:center;width:988px;background-color: #F5F5F5; margin: 10px auto;">copyright:杭州菏马村信息技术有限公司</div>
        </div>
        <div class="overlay" id="spm" style="display: none;"></div>
        <div id="ShowActivity"  class="Popup1" style="display: none;">
            <div class="Popup_top">
                <h2 id="EditNum1" style="font-family:Georgia, Times, Times New Roman, serif;margin:0;display: inline;">活动详情</h2>
                <a class="Close" onclick="Close(this,'Popup1')" style="cursor: pointer;"><img alt="关闭" src="/static/images/gif-0992.gif" /></a>
            </div>
            <div class="Popup_cen" style="font-family:Georgia, Times, Times New Roman, serif;height: 80%">
             <form action="/backend/activity/update " method="post">
          		<table id="ThisActicity" width="100%" height="auto" border="0"  cellspacing="0" cellpadding="0"  rules="all" class="table">
                   <tr>
                       <td>
                       		<input type="hidden" id="activityId" name="id">
                           <lable for="title">主题：</lable><input id="title" name="name" style="width:368px;">
                       </td>
                       <td rowspan="5" style="width:294px;border:1px;" >
                           <div id="localImage"><img id="uploadPic1" style="display: block; width: 100%; height: 150px;"></div>
                       </td>
                   </tr>
                   <tr>
                       <td >
                           <lable for="type">类型：</lable>
                           <input id="type" style="width:130px;" >
                           <select id="typeList" name="type" style="width:130px;" >
                           		<option value="0">艺术类</option>
                                <option value="1">体育运动</option>
                                <option value="2">语言类</option>
                                <option value="3">幼小衔接</option>
                                <option value="4">入园准备</option>
                                <option value="5">习惯培养</option>
                                <option value="6">情商培养</option>
                                <option value="7">全脑开发</option>
                                <option value="8">游乐类</option>
                           </select>
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           <lable for="age">对象：</lable>
                           <input id="age" style="width:128px;" >
                           <div id="ageRange" style="display: inline"><input id="minAge" name="attendee_min_age" style="width:48px"> &nbsp;<label>到</label> &nbsp;<input id="maxAge" name="attendee_max_age" style="width: 48px"></div>
                       </td>                 
                   </tr>
                   <tr id="TimeForShow">
                       <td>
                           <lable for="time">时间：</lable>
                           <input type="text" id="time" style="width:365px;"/>
                       </td>
                   </tr>
                   <tr id="TimeForEdit">
                       <td>
                           <lable for="time">时间：从</lable>
                           <input id="datepicker3" name="startTime" type="text" class="Wdate" style="width:150px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
                           <label>到</label>
                           <input id="datepicker4" name="endTime" type="text" class="Wdate" style="width:150px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'datepicker3\')}'})"/>
                       </td>
                   </tr>
                   <tr>
                       <td>
                           <lable for="price">价钱：</lable>
                           <input id="price" name="price" style="width:70px;" >
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           <lable for="pro">提供商：</lable>
                           <input id="pro" style="width:170px;" >
                           <select id="providerList" style="width: 170px" name="provider">
                              
                           </select>
                       </td>
                   </tr>
                   <tr>
                       <td>
                           <lable for="max">容量：</lable>
                           <input id="maxNum" name="maxAttedneeCount" style="width:74px;" >
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           <lable for="attend">已参加人数：</lable>
                           <input id="attend" name="attend" style="width:74px;" >
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           
                       </td>
                   </tr>
                   <tr>
                   		<td>                 
                           <lable for="initial">初始人数：</lable>
                           <input id="initial" name="initialAttendeeCount" style="width:74px;" >
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           <lable for="follow">收藏人数：</lable>
                           <input id="follow" name="follow" style="width:70px;" >
                   		</td>
                   		<td align="center">
                   			<input type="hidden" id="editImage" name="image">
                            <input type="file" size="50" name="file" id="avatarPic1" style="width:150px;">
                            <a id="up" class="confirm-btn1" style="cursor: pointer;">上传</a>
                   		</td>
                   </tr>
                   <tr>
                       <td colspan="2">                    	   	                 
	                       <label for="long">经度：</label> 
	                       <input id="long" name="longitude" style="width: 80px" >
	                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                       <label for="lat">纬度：</label> 
	                       <input id="lat" name="latitude" style="width: 80px" > 
	                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                       <label for="amenities">周边：</label> 
	                       <input id="amenities" style="width: 337px">
	                       <div id="amenitiesList" style="display: inline">
	                       	   <input type="checkbox" name="amenities" value="0">WiFi
	                           <input type="checkbox" name="amenities" value="1">停车场
	                           <input type="checkbox" name="amenities" value="2">饭店
	                           <input type="checkbox" name="amenities" value="3">旅馆
	                           <input type="checkbox" name="amenities" value="4">超市
	                           <input type="checkbox" name="amenities" value="5">公园
	                       </div>
	                       
                       </td>
                   </tr>
                   <tr>
                       <td colspan="2">
	                       	<lable for="addr">区域：</lable>
	                       	<input id="district" style="width: 70px" >
	                       	<select  id="districtList" style="width: 70px" name="district">
                            	<option value="3">江干区</option>
                            	<option value="4">滨江区</option>
                            	<option value="5">萧山区</option>
                            	<option value="6">上城区</option>
                            	<option value="7">下城区</option>
                            	<option value="8">余杭区</option>
                            	<option value="9">西湖区</option>
                            	<option value="10">拱墅区</option>
                            </select>
	                       	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           <lable for="addr">地址：</lable>
                           <input id="addr" name="address" style="width: 297px;" >
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                           <label for="phone">联系电话：</label> 
	                       <input id="phone" name="contactPhone" style="width: 98px" >
                       </td>
                       
                   </tr>
                   <tr>
                       <td colspan="2">
                           <lable for="coupon">福利：</lable>
                           <input id="coupon" name="coupon" style="width: 680px">
                       </td>
                   </tr>
                   <tr >
                       <td colspan="2">
                           <lable for="detil">详细介绍：</lable>
                           <textarea id="description" class="detil" name="description" style="width:100%;height: 70px;margin-top: 5px"></textarea>
                       </td>
                   </tr>
                   <tr>
                   		<td id="editButton"  align="center" colspan="2" style="border-bottom: 1px;">
                   			<button type="submit" style="margin-right: 20px;">保存</button>                   			
                   		</td>
                   </tr>
               </table>
             </form>
            </div>
        </div>
        <div id="AddActivity"  class="Popup1" style="display: none;">
            <div class="Popup_top">
                <h2 id="EditNum1" style="font-family:Georgia, Times, Times New Roman, serif;margin:0;display: inline;">新增活动</h2>
                <a style="cursor: pointer;" class="Close" onclick="Close(this,'Popup1')"><img alt="关闭" src="/static/images/gif-0992.gif" /></a>
            </div>
            <div class="Popup_cen" style="font-family:Georgia, Times, Times New Roman, serif;height: 80%" >

                <form  action="backend/activity/add" method="post" >
                    <table id="NewActivity" width="100%" height="auto" border="0"  cellspacing="0" cellpadding="0"  rules="all" class="table">
                        <tr>
                            <td>
                                <lable for="title">标题：</lable><input name="name">
                            </td>
                            <td rowspan="5" style="width: 40%;border:1px;" >
                                <div id="localImag"><img id="uploadPic" style="display: block; width: 100%; height: 150px;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td >
                                <lable for="type">类型：</lable>
                                
                                <select name="type">
                                    <option value="0">艺术类</option>
                                    <option value="1">体育运动</option>
                                    <option value="2">语言类</option>
                                    <option value="3">幼小衔接</option>
                                    <option value="4">入园准备</option>
                                    <option value="5">习惯培养</option>
                                    <option value="6">情商培养</option>
                                    <option value="7">全脑开发</option>
                                    <option value="8">游乐类</option>
                                </select>
                            </td>
                            
                        </tr>
                        <tr>
                            <td>
                                <lable for="time">时间：从</lable>
                                <input id="datepicker1" name="startTime" type="text" class="Wdate" style="width:150px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
                                <label>到</label>
                                <input id="datepicker2" name="endTime" type="text" class="Wdate" style="width:150px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'datepicker1\')}'})"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <lable for="time">对象：</lable>
                                    <a> 从</a>
                                    <input name="attendee_min_age" style="width:100px;">
                                    <a> 到</a>
                                    <input name="attendee_max_age" style="width:100px;">
                            </td>
                        </tr>
                        <tr>
                            <td ><label for="long">经度：</label> <input name="longitude" style="width: 130px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label for="lat">纬度：</label> <input name="latitude" style="width: 130px"> </td>
                        </tr>
                        <tr>
                            <td style="border-right: solid 1px #e0e0e0;">
                            	<lable for="addr">区域：</lable>
                            	<select style="width: 70px" name="district">
                                	<option value="3">西湖区</option>
                                	<option value="4">江干区</option>
                                	<option value="5">滨江区</option>
                                	<option value="6">萧山区</option>
                                	<option value="7">上城区</option>
                                	<option value="8">下城区</option>
                                	<option value="9">余杭区</option>
                                	<option value="10">拱墅区</option>
                                </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <lable for="addr">地址：</lable>
                                <input name="address" style="width: 230px;">
                            </td>
                            <td align="center" style="padding-top:3px;border-left: solid 1px #e0e0e0;">
                                <input type="hidden" id="addImage" name="image">
                                <input type="file" size="50" name="file" id="avatarPic" style="width:150px;">
                                <a class="confirm-btn" style="cursor: pointer;">上传</a>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <lable for="provider">供应商：</lable>
                                <select id="provider" style="width: 160px" name="provider">
                                   
                                </select>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
                                <lable for="fee">费用：</lable>
                                <input name="price" style="width: 60px">
                                &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
                                <lable for="max">容量：</lable>
                                <input name="maxAttedneeCount" style="width: 50px">
                                &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
                                <lable for="initial">初始人数：</lable>
                                <input name="initialAttendeeCount" style="width: 50px">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <label for="cel">联系电话：</label>
                                <input name="contactPhone">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <label>周边信息：</label>
                                <input type="checkbox" name="amenities" value="0">WiFi
                                <input type="checkbox" name="amenities" value="1">停车场
                                <input type="checkbox" name="amenities" value="2">饭店
                                <input type="checkbox" name="amenities" value="3">旅馆
                                <input type="checkbox" name="amenities" value="4">超市
                                <input type="checkbox" name="amenities" value="5">公园
                            </td>
                        </tr>
                        <tr >
                            <td colspan="2">
                                <lable for="detil">详细介绍：</lable>
                                <textarea name="description" style="width:100%;height: 70px;margin-top: 5px"></textarea>
                            </td>
                        </tr>
                        <!--<tr><td></td><td></td></tr>-->
                        <!--<tr><td></td><td></td></tr>-->
                        <tr >
                            <td colspan="2" align="center" style="border-bottom: 1px">
                                <button id="SaveNew" class="submit" type="submit" >保存</button>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <script type="text/javascript">
        	function AskForPre(){
        		var nowPage=<%=pageNum%>;
        		var nowUrl=document.getElementById("urlSaver").value;
		        nowPage=nowPage-1;
        		nowUrl=nowUrl+"?currentPage="+nowPage+others;
        		location.href=nowUrl;
        	}
        	function AskForNext(){
        		var nowPage=<%=pageNum%>;
        		var nowUrl=document.getElementById("urlSaver").value;
		        nowPage=nowPage+1;
        		nowUrl=nowUrl+"?currentPage="+nowPage+others;
        		location.href=nowUrl;
        	}
        
        	Date.prototype.format = function (format) {  
		        var o = {  
		            "M+": this.getMonth() + 1, //month  
		            "d+": this.getDate(), //day  
		            "h+": this.getHours(), //hour  
		            "m+": this.getMinutes(), //minute  
		            "s+": this.getSeconds(), //second  
		            "q+": Math.floor((this.getMonth() + 3) / 3), //quarter  
		            "S": this.getMilliseconds() //millisecond  
		        }  
		        if (/(y+)/.test(format))  
		            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
		        for (var k in o)  
		            if (new RegExp("(" + k + ")").test(format))  
		                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
		        return format;  
		    }  
            
            function FindThisActivity(activityId){
            	$.getJSON("/backend/activity/searchOne?activityId="+activityId+"",function(data){
            		var type="";
            		$("#activityId")[0].value=data.id;
            		$("#title")[0].value=data.name;
            		switch(data.type){
	            		case 0:type="艺术类";
	            			break;
	           			case 1:type="体育运动";
            				break;
            			case 2:type="语言类";
            				break;
            			case 3:type="幼小衔接";
            				break;
           				case 4:type="入园准备";
            				break;
            			case 5:type="习惯培养";
            				break;
            			case 6:type="情商培养";
            				break;
            			case 7:type="全脑开发";
            				break;
            			case 8:type="游乐类";
            				break;
            		}
            		$("#typeList")[0].value=data.type;
            		$("#type")[0].value=type;
            		$("#age")[0].value=data.attendeeMinAge+"到"+data.attendeeMaxAge+"岁";
            		var startTime=(new Date(data.startTime)).format("yyyy-MM-dd hh:mm");
            		var endTime=new Date(data.endTime).format("yyyy-MM-dd hh:mm");
            		$("#time")[0].value="从"+startTime+"到"+endTime;
            		$("#datepicker3")[0].value=startTime;
            		$("#datepicker4")[0].value=endTime;
            		$("#price")[0].value=data.price;
            		$("#pro")[0].value=data.provider.name;
            		$("#maxNum")[0].value=data.maxAttedneeCount;
            		$("#attend")[0].value=data.attendCount;
            		$("#initial")[0].value=data.initialAttendeeCount;
            		$("#follow")[0].value=data.followCount;
            		$("#phone")[0].value=data.contactPhone;
            		$("#long")[0].value=data.longitude;
            		$("#lat")[0].value=data.latitude;
            		$("#minAge")[0].value=data.attendeeMinAge;
            		$("#maxAge")[0].value=data.attendeeMaxAge;
            		$("#uploadPic1")[0].src=data.image;
            		var amenities="";
            		var obj=$("#amenitiesList").find("input");
            		for(var k=0;k<data.amenities.length;k++){
            			switch(data.amenities[k]){
            				case 0:amenities=amenities+"wifi"+" ";
            					   obj[0].checked="true";
            					break;
           					case 1:amenities=amenities+"停车场"+" ";
           						   obj[1].checked="true";
            					break;
           					case 2:amenities=amenities+"饭店"+" ";
           						   obj[2].checked="true";
            					break;
           					case 3:amenities=amenities+"旅店"+" ";
           						   obj[3].checked="true";
            					break;
           					case 4:amenities=amenities+"超市"+" ";
           						   obj[4].checked="true";
            					break;
           					case 5:amenities=amenities+"公园"+" ";
           						   obj[5].checked="true";
            					break;
            			}
            		}
            		$("#amenities")[0].value=amenities;
            		$("#district")[0].value=data.district.name;
            		$("#districtList")[0].value=data.district.id;
            		$("#addr")[0].value=data.address;
            		$("#coupon")[0].value=data.coupon;
            		$("#description")[0].value=data.description;
            	}); 
            }
            
            function ShowActivity(activityId){
            	FindThisActivity(activityId);
            	$("#TimeForShow").show();
            	$("#TimeForEdit").hide();
            	$("#providerList").hide();
            	$("#pro").show();
            	$("#districtList").hide();
            	$("#district").show();
            	$("#amenitiesList").hide();
            	$("#amenities").show();
            	$("#age").show();
            	$("#ageRange").hide();
            	$("#typeList").hide();
            	$("#type").show();
            	$("#avatarPic1").hide(); 
            	$("#up").hide();
            	var obj= $("#ShowActivity").find("input");
            	for(k=0;k<obj.length;k++){
            		obj[k].readOnly=true;
            	}
            	$("#description")[0].readOnly=true;
            	$("#editButton").hide();
                $("#ShowActivity").css('display','block');
                $("#spm").show();
               
            }
            
            function EditActivity(activityId){
            	FindThisActivity(activityId);
            	$.getJSON("/backend/activity/addNew",function(data){
					var provider=$('#providerList');
					provider.empty();
					for(var i=0;i<data.length;i++){
						$('<option value="'+data[i].id+'">"'+data[i].name+'"</option>').appendTo(provider);
					}
					
				});
            	$("#TimeForShow").hide();
            	$("#TimeForEdit").show();
            	$("#providerList").show();
            	$("#pro").hide();
            	$("#districtList").show();
            	$("#district").hide();
            	$("#amenitiesList").show();
            	$("#amenities").hide();
            	$("#age").hide();
            	$("#ageRange").show();
            	$("#typeList").show();
            	$("#type").hide();
            	$("#avatarPic1").show();
            	$("#up").show();
            	var obj= $("#ShowActivity").find("input");
            	for(k=0;k<obj.length;k++){
            		obj[k].readOnly=false;
            	}
            	$("#description")[0].readOnly=false;
            	$("#editButton").show();
                $("#ShowActivity").css('display','block');
                $("#spm").show();
           	}
            
			function AddActivity(){
				$.getJSON("/backend/activity/addNew",function(data){
					var provider=$('#provider');
					provider.empty();
					for(var i=0;i<data.length;i++){
						$('<option value="'+data[i].id+'">"'+data[i].name+'"</option>').appendTo(provider);
					}
					
				});
                $('#AddActivity').css('display','block');
                $("#spm").show();
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
