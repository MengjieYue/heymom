<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>商家管理</title>   
	<meta charset="UTF-8" />
    <script src="/static/js/jquery.js" type="text/javascript"></script>
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
				$("#pre").attr('disabled','disabled'); 
			}
			else{
				$("#pre").removeattr('disabled');
			}
			if(pageIndex==pages){
				$("#next").attr('disabled','disabled');
			}
			else{
				$("#next").removeattr('disabled'); 
			}

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
                                    <h2 class="left"><a href="/backend/choose/main" style="cursor: pointer;color: white;">主页  -> </a>供应商管理</h2>
                                </div>
                                <div class="table" id="OddTable">
                                    <table id="StoreTable" width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <th style="width: 1%"></th>
                                            <th style="width: 20% ; text-align:center">供应商</th>
                                            <th style="width: 16% ; text-align:center">商家类型</th>
                                            <th style="width: 15% ; text-align:center">城市</th>
                                            
                                            <th style="width: 20% ; text-align:center">联系电话</th>
                                            <th style="width: 14% ; text-align:center">编辑</th>
                                            <th style="width: 14% ; text-align:center" class="ac">删除</th>
                                        </tr>
                                        <c:forEach var="node" items="${list.content }">
                                        	<tr>
	                                            <td><input type="checkbox" class="checkbox" /></td>
	                                            <td ><h3><a style="text-decoration: underline; text-align:center;" >${node.name }</a></h3></td>
	                                            <c:choose>
	                                            	<c:when test="${node.type=='0' }">
	                                            		<td style="text-align:center;">核心商家</td>
	                                            	</c:when>
	                                            	<c:when test="${node.type=='1' }">
	                                            		<td style="text-align:center;">河马定制商家</td>
	                                            	</c:when>
	                                            	<c:otherwise>
	                                            		<td style="text-align:center;">普通商家</td>
	                                            	</c:otherwise>
	                                            </c:choose>
	                                            <td style="text-align:center;">${node.city.name }</td>
	                                           
	                                            <td style="text-align:center;">${node.contactPhone }</td>
	                                            <td ><a style="cursor: pointer;" class="ico edit" onclick="var id=${node.id}; EditProvider(id)">编辑</a></td>
	                                            <td ><a style="cursor: pointer;" href="/backend/provider/delete?activityProviderId=${node.id }" class="ico del">删除</a></td>
                                        	</tr>
                                        </c:forEach>
                                        
                                        <tr>
                                            <td></td>
                                            <td  style="text-align:left"><img src="/static/images/add.png"><a style="cursor: pointer;" onclick="AddProvider()">新增供应商</a></td>
                                            <td ></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                          
                                            <td ></td>
                                        </tr>
                                        <tr>
                                            <td><input type="checkbox" class="checkbox" onclick="SetAllChecked()"/></td>
                                            <td style="text-align:left"><h3>全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style="cursor: pointer;" class="ico del">删除</a></h3></td>
                                            <td ></td>
                                            <td></td>
                                            <td></td>
                                            <td ></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                    </table>
                                </div>
                                <a href="/backend/provider/list?currentPage=<%=pageNum-1 %>"><button id="pre" type="button" style="margin-right: 20px">上一页</button></a>
                               	<a href="/backend/provider/list?currentPage=<%=pageNum+1 %>"><button id="next" type="button" >下一页</button></a>
                            </div>
                            <div class="cl">&nbsp;</div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="text-align:center;width:988px;background-color: #F5F5F5; margin: 10px auto;">copyright:杭州菏马村信息技术有限公司</div>
        </div>
        
        <div class="overlay" id="spm" style="display: none;"></div>
        
        <div id="AddProvider"  class="Popup" style="display: none; width: 250px; height: 250px;">
            <div class="Popup_top">
                <h2 style="font-family:Georgia, Times, Times New Roman, serif;margin:0;display: inline;">新增提供商</h2>
                <a class="Close" style="cursor: pointer;" onclick="Close(this,'Popup')"><img alt="关闭" src="/static/images/gif-0992.gif" /></a>
            </div>
            <div class="Popup_cen">
                <form  action="/backend/provider/add" method="post">
		            <table width="100%" height="auto" border="0"  cellspacing="0" cellpadding="0"  rules="all" class="table">
		                <tr>
		                    <td>
		                        <lable for="name">供应商：</lable><input name="name" style="width: 153px">
		                    </td>
		                </tr>
		                <tr>
		                    <td>
		                        <lable for="type">类&nbsp&nbsp&nbsp&nbsp&nbsp型：</lable>
		                        <select name="type" style="width: 145px">
		                        	<option value="0">核心商家</option>
		                        	<option value="1">河马定制商家</option>
		                        	<option value="2">普通商家</option>
		                        </select>
		                    </td>
		                </tr>
		                <tr>
		                    <td>
		                        <lable for="city">城&nbsp&nbsp&nbsp&nbsp&nbsp市：</lable>
		                        <select name="city" style="width: 145px">
		                        	<option value="2">杭州市</option>
		                        </select>
		                    </td>
		                </tr>
		                <tr>
		                    <td>
		                        <lable for="phone">电&nbsp&nbsp&nbsp&nbsp&nbsp话：</lable><input name="contactPhone" style="width: 153px">
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
      
        <div id="EditProvider"  class="Popup1" style="display: none; width: 250px; height: 250px;">
            <div class="Popup_top">
                <h2 style="font-family:Georgia, Times, Times New Roman, serif;margin:0;display: inline;">编辑提供商</h2>
                <a class="Close" style="cursor: pointer;" onclick="Close(this,'Popup1')"><img alt="关闭" src="/static/images/gif-0992.gif" /></a>
            </div>
            <div class="Popup_cen">
                <form  action="/backend/provider/update" method="post">
		            <table width="100%" height="auto" border="0"  cellspacing="0" cellpadding="0"  rules="all" class="table">
		                <tr>
		                    <td>
		                        <input type="hidden" id="Providerid" name="providerId">
		                        <lable for="name">供应商：</lable><input id="Providername" name="name" style="width: 153px" >
		                    </td>
		                </tr>
		                <tr>
		                    <td>
		                        <lable for="type">类&nbsp&nbsp&nbsp&nbsp&nbsp型：</lable>
		                        <select id="Providertype" name="type" style="width: 145px">
		                        	<option value="0">核心商家</option>
		                        	<option value="1">河马定制商家</option>
		                        	<option value="2">普通商家</option>
		                        </select>
		                    </td>
		                </tr>
		                <tr>
		                    <td>
		                        <lable for="city">城&nbsp&nbsp&nbsp&nbsp&nbsp市：</lable>
		                        <select id="Providercity" name="city" style="width: 145px">
		                        	<option value="1">杭州市</option>
		                        </select>
		                    </td>
		                </tr>
		                <tr>
		                    <td>
		                        <lable for="phone">电&nbsp&nbsp&nbsp&nbsp&nbsp话：</lable><input id="Providerphone" name="contactPhone" style="width: 153px">
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
          function AddProvider(){
              $('#AddProvider').css('display','block');
              var target=$('.Popup');
              $("#spm").show();
              
              var targetWidth=target.outerWidth()/2;
              var targetHeight=target.outerHeight()/2;
              target.css({"margin-top":-parseInt(targetHeight)+"px","margin-left":-parseInt(targetWidth)+"px"});
              target.show();
              return false;
          }
        
        function EditProvider(providerId){
			$.getJSON("/backend/provider/searchOne?providerId="+providerId+"",function(data){
				$("#Providername")[0].value=data.name;
				$("#Providertype")[0].value=data.type;
				$("#Providercity")[0].value=data.city.id;
				$("#Providerphone")[0].value=data.contactPhone;
				$("#Providerid")[0].value=data.id;
			});
			
			$('#EditProvider').css('display','block');
            var target=$('.Popup1');
            $("#spm").show();
            var targetWidth=target.outerWidth()/2;
            var targetHeight=target.outerHeight()/2;
            target.css({"margin-top":-parseInt(targetHeight)+"px","margin-left":-parseInt(targetWidth)+"px"});
            target.show();
            return false;
		  }
	
          function Close(e,classname){
              $(e).parents("."+classname).hide();
              $("#spm").hide();
          }
	
          function SetAllChecked(){
          	if ($(".checkbox").attr('checked')==true) 
              {
                  $(".checkbox").removeAttr('checked');
              }
              
              else
              {
                  $(".checkbox").attr('checked','true');
              }
          } 
	
		  
          
        </script>              
  </body>
  
</html>
