<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    
    <title>福利管理</title>
    
	<script src="/static/js/jquery.js" type="text/javascript"></script>
        <script type="text/javascript" src="/static/js/jquery-1.5.1.js"></script>
        <script type="text/javascript" src="/static/js/ui.tab.js"></script>
        <script type="text/javascript" src="/static/js/My97DatePicker/WdatePicker.js"></script>
        <script src="/static/js/ajaxfileupload.js" type="text/javascript"></script>
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
                    alert("页面数据异常，请重试！");
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
                    alert("页面数据异常，请重试！");
                }

            });
     	}
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
                                    <h2 class="left"><a href="/backend/choose/main" style="cursor: pointer;color: white;">主页  -> </a>福利管理</h2>
                                </div>
                                
                                <div class="table" id="OddTable">
                                    <table id="StoreTable" width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <th style="width: 1%"></th>
                                            <th style="width: 20% ; text-align:center">福利名称</th>
                                            <th style="width: 15% ; text-align:center">提供商</th>
                                            <th style="width: 34% ; text-align:center">有效日期</th>
                                            <th style="width: 9% ; text-align:center">发放数量</th>
                                            <th style="width: 10% ; text-align:center">编辑</th>
                                            <th style="width: 10% ; text-align:center" class="ac">删除</th>
                                        </tr>
                                        <c:forEach var="node" items="${list.content }">
	                                        <tr>
	                                            <td><input type="checkbox" class="checkbox" /></td>
	                                            <td ><h3><a class="station" style="text-decoration: underline;cursor:pointer;" onclick="ShowCoupon('+${node.id}+')">${node.name }</a></h3></td>
	                                            <td >${node.provider.name }</td>
	                                            <td>${node.beginDate }~${node.expireDate }</td>
	                                            <td>${node.maxCount }</td>
	                                            <td ><a class="ico edit" onclick="EditCoupon('+${node.id}+')" style="cursor: pointer;">编辑</a></td>
	                                            <td ><a href="/backend/coupon/delete?couponId=${node.id }" class="ico del" style="cursor: pointer;">删除</a></td>
	                                        </tr>
                                        </c:forEach>
                                        <tr>
                                            <td></td>
                                            <td  style="text-align:left"><img src="/static/images/add.png"><a onclick="AddCoupon()" style="cursor: pointer;">新增福利</a></td>
                                            <td ></td>
                                            <td></td>
                                            <td ></td>
                                            <td ></td>
                                            <td ></td>
                                        </tr>
                                        <tr>
                                            <td><input type="checkbox" class="checkbox" onclick="SetAllChecked()"/></td>
                                            <td style="text-align:left"><h3>全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="ico del" style="cursor: pointer;">删除</a></h3></td>
                                            <td ></td>
                                            <td></td>
                                            <td></td>
                                            <td ></td>
                                            <td ></td>
                                        </tr>
                                    </table>
                                </div>
                                <a href="/backend/coupon/list?currentPage=<%=pageNum-1 %>"><button id="pre" type="button" style="margin-right: 20px">上一页</button></a>
                               	<a href="/backend/coupon/list?currentPage=<%=pageNum+1 %>"><button id="next" type="button" >下一页</button></a>
                            </div>
                            <div class="cl">&nbsp;</div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="text-align:center;width:988px;background-color: #F5F5F5; margin: 10px auto;">copyright:杭州菏马村信息技术有限公司</div>
        </div>
        <div class="overlay" id="spm" style="display: none;"></div>
        <div id="AddCoupon"  class="Popup" style="display: none;height: 400px;width: 600px;">
            <div class="Popup_top">
                <h2 style="font-family:Georgia, Times, Times New Roman, serif;margin:0;display: inline;"></h2>
                <a class="Close" onclick="Close(this,'Popup')" style="cursor: pointer;"><img alt="关闭" src="/static/images/gif-0992.gif" /></a>
            </div>
            <div class="Popup_cen">
                <form  action="/backend/coupon/add" method="post">
		            <table width="100%" height="auto" border="0"  cellspacing="0" cellpadding="0"  rules="all" class="table">
                        <tr>
                            <td>
                                <lable for="title">名称：</lable><input name="name" style="width:286px;">
                            </td>
                            <td rowspan="4" style="width: 40%;" >
                                <div><img id="uploadPic" style="display: block; width: 100%; height: 150px;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td >
                                <lable for="type">类型：</lable>
                                
                                <select name="type" style="width:100px;">
                                    <option value="0">现金券</option>
                                    <option value="1">体验券</option>
                                    
                                </select>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <lable for="type">数量：</lable>
                                <input name="maxCount" style="width:95px;">
                            </td>
                            
                        </tr>
                        <tr>
                            <td>
                                <lable for="time">开始时间：</lable>
                                <input id="datepicker1" name="beginDate" type="text" class="Wdate" style="width:250px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <lable for="time">截止时间：</lable>
                                <input id="datepicker2" name="expireDate" type="text" class="Wdate" style="width:250px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'datepicker1\')}'})"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <lable for="provider">提供商：</lable>
                                <select id="provider" name="providerId" style="width:160px;">
                                
                                
                            	</select>
                            </td>
                            <td align="center" >
                            	<input type="hidden" id="addImage" name="image">
                                <input type="file" size="50" name="file" id="avatarPic" style="width:150px;">
                                <a class="confirm-btn" style="cursor: pointer;">上传</a>
                            </td>
                        </tr>
                        
                        <tr>
                            <td colspan="2">
                                <lable for="discription">描述：</lable>
                                <textarea name="description" style="width:100%;height: 50px;margin-top: 5px"></textarea>
                            </td>
                            
                        </tr>
                        
                        <tr >
                            <td colspan="2" align="center" style="border-bottom: 1px">
                                <button class="submit" type="submit" >保存</button>
                            </td>
                        </tr>
                    </table>
		        </form>
            </div>
        </div>
        
        <div id="EditCoupon"  class="Popup1" style="display: none;height: 400px;width: 600px;">
            <div class="Popup_top">
                <h2 style="font-family:Georgia, Times, Times New Roman, serif;margin:0;display: inline;"></h2>
                <a class="Close" onclick="Close(this,'Popup1')" style="cursor: pointer;"><img alt="关闭" src="/static/images/gif-0992.gif" /></a>
            </div>
            <div class="Popup_cen">
                <form  action="/backend/coupon/update" method="post">
		            <table width="100%" height="auto" border="0"  cellspacing="0" cellpadding="0"  rules="all" class="table">
                        <tr>
                            <td>
                                <input id="couponId" name="couponId" type="hidden">
                                <lable for="title">名称：</lable><input id="name" name="name" style="width:286px;">
                            </td>
                            <td rowspan="4" style="width: 40%;" >
                                <div><img id="uploadPic1" style="display: block; width: 100%; height: 150px;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td >
                                <lable for="type">类型：</lable>
                                
                                <select id="type" name="type" style="width:100px;">
                                    <option value="0">现金券</option>
                                    <option value="1">体验券</option>
                                    
                                </select>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <lable for="type">数量：</lable>
                                <input id="maxCount" name="maxCount" style="width:95px;">
                            </td>
                            
                        </tr>
                        <tr>
                            <td>
                                <lable for="time">开始时间：</lable>
                                <input id="datepicker3" name="beginDate" type="text" class="Wdate" style="width:250px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <lable for="time">截止时间：</lable>
                                <input id="datepicker4" name="expireDate" type="text" class="Wdate" style="width:250px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'datepicker3\')}'})"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <lable for="provider">提供商：</lable>
                                <select id="providerList" name="providerId" style="width:160px;">
                                
                                
                            	</select>
                            </td>
                            <td align="center" >
                            	<input type="hidden" id="editImage" name="image">
                                <input type="file" size="50" name="file" id="avatarPic1" style="width:150px;">
                                <a class="confirm-btn1" style="cursor: pointer;">上传</a>
                            </td>
                        </tr>
                        
                        <tr>
                            <td colspan="2">
                                <lable for="discription">描述：</lable>
                                <textarea id="description" name="description" style="width:100%;height: 50px;margin-top: 5px"></textarea>
                            </td>
                            
                        </tr>
                        
                        <tr >
                            <td colspan="2" align="center" style="border-bottom: 1px">
                                <button class="submit" type="submit" >保存</button>
                            </td>
                        </tr>
                    </table>
		        </form>
            </div>
        </div>
        
        <div id="ShowCoupon"  class="Popup2" style="display: none;height: 400px;width: 600px;">
            <div class="Popup_top">
                <h2 style="font-family:Georgia, Times, Times New Roman, serif;margin:0;display: inline;"></h2>
                <a class="Close" onclick="Close(this,'Popup2')" style="cursor: pointer;"><img alt="关闭" src="/static/images/gif-0992.gif" /></a>
            </div>
            <div class="Popup_cen">
	            <table width="100%" height="auto" border="0"  cellspacing="0" cellpadding="0"  rules="all" class="table">
                       <tr>
                           <td>
                               <lable for="title">名称：</lable><input id="nameForShow" style="width:286px;" readonly="readonly">
                           </td>
                           <td rowspan="4" style="width: 40%;" >
                               <div><img id="imageForShow" style="display: block; width: 100%; height: 150px;"></div>
                           </td>
                       </tr>
                       <tr>
                           <td >
                               <lable for="type">类型：</lable>
                               
                               <input id="typeForShow" style="width:100px;" readonly="readonly">
                               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                               <lable for="type">数量：</lable>
                               <input id="maxCountForShow" style="width:100px;" readonly="readonly">
                           </td>
                           
                       </tr>
                       <tr>
                           <td>
                               <lable for="time">开始时间：</lable>
                               <input id="datepicker3ForShow" style="width:250px;" readonly="readonly"/>
                           </td>
                       </tr>
                       <tr>
                           <td>
                               <lable for="time">截止时间：</lable>
                               <input id="datepicker4ForShow" style="width:250px;" readonly="readonly"/>
                           </td>
                       </tr>
                       <tr>
                           <td>
                               <lable for="provider">提供商：</lable>
                               <input id="providerListForShow" style="width:160px;" readonly="readonly"/>
                           </td>
                           <td align="center" >
                               
                           </td>
                       </tr>
                       
                       <tr>
                           <td colspan="2">
                               <lable for="discription">描述：</lable>
                               <textarea id="descriptionForShow" style="width:100%;height: 50px;margin-top: 5px" readonly="readonly"></textarea>
                           </td>
                           
                       </tr>
                       
                   </table>
            </div>
        </div>
        
        <script type="text/javascript">
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
        	
            function AddCoupon(){
            	$.getJSON("/backend/activity/addNew",function(data){
					var provider=$('#provider');
					provider.empty();
					for(var i=0;i<data.length;i++){
						$('<option value="'+data[i].id+'">"'+data[i].name+'"</option>').appendTo(provider);
					}
					
				});           	
                $('#AddCoupon').css('display','block');
                $("#spm").show();
                var target=$('.Popup');
                var targetWidth=target.outerWidth();
                var targetHeight=target.outerHeight();
                target.css({"margin-top":-parseInt(targetHeight/2)+"px","margin-left":-parseInt(targetWidth/2)+"px"});
                target.show();
                return false;
            }
            
            function EditCoupon(couponId){
            	$.getJSON("/backend/activity/addNew",function(data){
					var provider=$('#providerList');
					provider.empty();
					for(var i=0;i<data.length;i++){
						$('<option value="'+data[i].id+'">"'+data[i].name+'"</option>').appendTo(provider);
					}
					
				});
				
            	$.getJSON("/backend/coupon/searchOne?couponId="+couponId+"",function(data){
					$("#name")[0].value=data.name;
					$("#type")[0].value=data.type;
					$("#maxCount")[0].value=data.maxCount;
					$("#datepicker3")[0].value=(new Date(data.beginDate)).format("yyyy-MM-dd hh:mm");
					$("#datepicker4")[0].value=(new Date(data.expireDate)).format("yyyy-MM-dd hh:mm");
					$("#providerList")[0].value=data.provider.id;
					$("#description")[0].value=data.description;
					$("#couponId")[0].value=data.id;
					$("#uploadPic1")[0].src=data.imageUrl;
					
				});
				
				$('#EditCoupon').css('display','block');
	            var target=$('.Popup1');
	            $("#spm").show();
	            var targetWidth=target.outerWidth()/2;
	            var targetHeight=target.outerHeight()/2;
	            target.css({"margin-top":-parseInt(targetHeight)+"px","margin-left":-parseInt(targetWidth)+"px"});
	            target.show();
	            return false;
            }
            
            function ShowCoupon(couponId){
            	$.getJSON("/backend/coupon/searchOne?couponId="+couponId+"",function(data){
					$("#nameForShow")[0].value=data.name;
					var type='';
					switch(data.type){
						case 0:type='现金券';
							break;
						case 1:type='体验券';
							break;
					}
					$("#typeForShow")[0].value=type;
					$("#maxCountForShow")[0].value=data.maxCount;
					$("#datepicker3ForShow")[0].value=(new Date(data.beginDate)).format("yyyy-MM-dd hh:mm");
					$("#datepicker4ForShow")[0].value=(new Date(data.expireDate)).format("yyyy-MM-dd hh:mm");
					$("#providerListForShow")[0].value=data.provider.name;
					$("#descriptionForShow")[0].value=data.description;
					$("#imageForShow")[0].src=data.imageUrl;
				});
				
				$('#ShowCoupon').css('display','block');
	            var target=$('.Popup2');
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
