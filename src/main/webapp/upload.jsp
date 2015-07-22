<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Upload of Heymom</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Login Form with HTML5 and CSS3"/>

    <link rel="stylesheet" type="text/css" href="/static/css/demo.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/static/css/animate-custom.css"/>
    <script src="/static/js/jquery.js" type="text/javascript"></script>
    <script src="/static/js/ajaxfileupload.js" type="text/javascript"></script>
    <script type="text/javascript">
        function ajaxFileUpload() {
            $.ajaxFileUpload({
                url: '/backend/upload',
                secureuri: false,
                fileElementId: 'avatarPic',
                dataType: 'text',
                success: function (result) {
                    $('#echo').html($('<img/>').attr('src',result)).append('<br/><br/><br/>').append(result);
                },
                error: function () {
                    alert("页面数据异常，请重试！");
                }

            });
        }
        $(document).ready(function () {
            $('.confirm-btn').click(function () {
                ajaxFileUpload();
            });
        });
    </script>
</head>
<body>
<div class="container">
    <span><input type="file" size="50" name="file" id="avatarPic"></span>
    <a class="confirm-btn"><span>上传</span></a>
    <div id="echo"></div>
</div>
</body>
</html>