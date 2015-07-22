<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
	<meta charset="UTF-8" />
    <title>群发短信</title>
    <script src="/static/js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/static/css/demo.css" />
    <style type="text/css">
        .table {}
        .table td{ background:#fbfcfc;  border-bottom:solid 1px #e0e0e0; padding:8px 10px; }
        .table tr.odd td{ background:#f8f8f8; }
        
    </style>
  </head>
  
  <body style="text-align:center;">
        <div class="container1">
            <br><br><br>
            <header>
                <h1>河马村 <span>管理后台</span></h1>
				<br>
            </header>
            
        </div>
        
        <div id="messagebox" >
            <form action="/backend/center/sendMessage" method="post">
                <div class="table" style="min-height:580px;height: auto;">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="min-height:580px;height: auto;">
                        <tr>
                            <td style="width: 8%">收件人</td>
                            <td style="width: 92% ; text-align:center"><input name="receivers" style="width:98%;"></td>                        
                        </tr>
                        <tr>
                            <td style="width: 8%">内容</td>
                            <td style="width: 92% ; text-align:center"><textarea name="content" style="width:98%;min-height:500px;height: auto;"></textarea></td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center">
                            	<button type="submit" style="margin-right: 30px;" onclick="Alert()">发送</button>
                            	<a href="/backend/choose/main"><button type="button">返回</button></a>
                           	</td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>

        <div style="margin:10px auto;text-align:center;">copyright:杭州菏马村信息技术有限公司</div>
    <script type="text/javascript">
    	function Alert(){
    		alert("发送成功！！");
    		return false;
    	}
    </script>
    </body>
</html>
