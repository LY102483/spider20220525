<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<div id="test">

</div>
</body>
<script>
    $.ajax({
        type: "post",
        // dataType: "jsonp",
        url: '/yiqing/getContainer?pageIndex=2262',
        // data: dataurl,
        success: function (list) {
            var html='';
            html+=list[0].content;
        },
        error : function() {
            // 出现错误
            alert("请求错误");
        },

    });
</script>
</html>