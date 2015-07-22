<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	String User=(String)request.getAttribute("userName");
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>资源位管理</title>
    
	<script src="/static/js/jquery.js" type="text/javascript"></script>
        <script type="text/javascript" src="/static/js/jquery-1.5.1.js"></script>
        <script type="text/javascript" src="/static/js/ui.tab.js"></script>
        <link rel="stylesheet" type="text/css" href="/static/css/demo.css" />     
        <link rel="stylesheet" type="text/css" href="/static/css/activitylist.css"/>  
        <script type="text/javascript">
            $(document).ready(function(){
                document.getElementById("content").style.minHeight = document.documentElement.clientHeight-155+"px"; 
        
		        
            });
        </script>
        <script type="text/javascript">
            $(document).ready(function(){
                var tab = new $.fn.tab({
                    tabList:"#demo1 .ui-tab-container .ui-tab-list li",
                    contentList:"#demo1 .ui-tab-container .ui-tab-content"
                });  
            }); 
        </script>

  </head>
  
  <body>
    <div class="container">
            <br><br><br>
            <header>
                <h1>河马村 <span>管理后台</span></h1>
				<br>
            </header>
            <div id="container">
            	<div id="welcome" style="font-style:italic;color:#404A54;float: right;margin-bottom: 6px;">欢迎您，<%=User %>     </div>
                <div class="shell">
                    <div id="main">
                        <div class="cl">&nbsp;</div>
                        <div id="content">
                            <div class="box">
                                <div class="box-head">
                                    <h2 class="left"><a href="/backend/choose/main" style="cursor: pointer;color: white;">主页  -> </a>资源位管理</h2>
                                </div>
                                <div class="table" id="OddTable">
                                    <table id="StoreTable" width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <th style="width: 1%"></th>
                                            <th style="width: 35% ; text-align:center">活动主题</th>
                                            <th style="width: 24% ; text-align:center">资源位置</th>
                                            <th style="width: 20% ; text-align:center">优先级</th>
                                            <th style="width: 20% ; text-align:center">编辑</th>
                                            
                                        </tr>
                                        <c:forEach var="node" items="${list.content }">
	                                        <tr>
	                                            <td><input type="checkbox" class="checkbox" /></td>
	                                            <td align="center"><h3><a class="station" style="text-decoration: underline;cursor:pointer;">${node.contentMap.activityName }</a></h3></td>
	                                            <td align="center">${node.name }</td>
	                                            <td align="center">${node.hotPriority }</td>
	                                            <td align="center"><a class="ico edit" onclick="EditDelivery('${node.id }','${node.name }')" style="cursor: pointer;">编辑</a></td>
	                                            
	                                        </tr>
                                        </c:forEach>
                                        
                                    </table>
                                </div>
                                
                            </div>
                            <div class="cl">&nbsp;</div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="text-align:center;width:988px;background-color: #F5F5F5; margin: 10px auto;">copyright:杭州菏马村信息技术有限公司</div>
        </div>
        <div class="overlay" id="spm" style="display: none;"></div>
        
        <div id="EditDelivery"  class="Popup" style="display: none;width: 400px;height: 500px;">
            <div class="Popup_top">
                <h2 id="deliveryTop" style="font-family:Georgia, Times, Times New Roman, serif;margin:0;display: inline;font-size:18px;font-weight:bold"></h2>
                <a class="Close" onclick="Close(this,'Popup')" style="cursor: pointer;"><img alt="关闭" src="/static/images/gif-0992.gif" /></a>
            </div>
            <div class="Popup_cen">
                <form action="/backend/delivery/update" method="post" style="text-align: center;">
                	<input type="hidden" name="deliveryId" id="deliveryId">
                	<button id="pre" type="button" style="float: left;" onclick="PrePage()">上页</button>
                    <button id="next" type="button" style="float:right;" onclick="NextPage()">下页</button><br/> 
                    <table id="ActivityList" class="table" width="100%" border="0" cellspacing="0" cellpadding="0">
                        
                    </table>
                    <button type="submit" style="margin-top: 10px;">保存</button>
                </form>
            </div>
        </div>
        
        <script type="text/javascript">
        	var pageIndex=0;
        	function ShowActivities(){
        		$.getJSON("/backend/activity/listByPage?currentPage="+pageIndex+"",function(data){
                	nowPage=data.currentPage;
                	totalPage=data.totalPages-1;
					ActivityList= $("#ActivityList");
					ActivityList.empty();
                	for(var i=0;i<data.list.content.length;i++){
                		$("#ActivityList").append('<tr><td ><input class="activity" type="radio" name="activity" />"'+data.list.content[i].name+'"</td><td class="status" align="center">"'+data.status[i]+'"</td></tr>');
                		$(".activity")[i].value="{"+'"'+"imgUrl"+'"'+":"+'"'+data.list.content[i].image+'"'+","+'"'+"activityId"+'"'+":"+'"'+data.list.content[i].id+'"'+","+'"'+"activityName"+'"'+":"+'"'+data.list.content[i].name+'"'+"}";
                	}
                	if(nowPage==0){
                		$("#pre")[0].disabled=true;
                	}
                	else{
                		$("#pre")[0].disabled=false;
                	}	
                	if(nowPage==totalPage){
                		$("#next")[0].disabled=true;
                	}
                	else{
                		$("#next")[0].disabled=false;
                	}						
				});
				$('#EditDelivery').css('display','block');
                var target=$('.Popup');
                $("#spm").show();               
                var targetWidth=target.outerWidth()/2;
                var targetHeight=target.outerHeight()/2;
                target.css({"margin-top":-parseInt(targetHeight)+"px","margin-left":-parseInt(targetWidth)+"px"});
                target.show();
                return false;
        	}
        	
            function EditDelivery(deliveryId,deliveryName){
                $('#deliveryTop')[0].innerHTML=deliveryName; 
                $('#deliveryId')[0].value=deliveryId;
                ShowActivities();
                
            }

			function NextPage(){
				pageIndex=pageIndex+1;
				ShowActivities();
			}
			
			function PrePage(){
				pageIndex=pageIndex-1;
				ShowActivities();
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
