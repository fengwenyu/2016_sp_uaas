<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2014-10-13
  Time: 14:24
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>跳转中...</title>
    <script type="text/javascript">
        window.onload = function(){
           window.location.href =  '${params.clientUrl}?token=${params.token}'
        }
    </script>
</head>
<body>
跳转中...
</body>
</html>