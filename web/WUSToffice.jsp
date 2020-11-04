<%@ page import="web.servlet.ApprovalServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>武汉科技大学教务处后台管理</title>

    <style>
        body{
            background: url("img/backgroundo.png") no-repeat;
            background-size:100%;
        }

        .top{
            float: top;
            position: absolute;
            left: calc(50% - 300px);
        }

        .top > p:first-child{
            color: green;
            font-size: 30px;
        }

        .top > p:last-child{
            color: green;
            font-size: 20px;
        }

        .center{
            /*整个页面的正中间*/
            position: absolute;
            top: calc(50% - 50px);
            left: calc(50% - 250px);
            width: 500px;
            border: 10px;
        }

        #button1,#button2,#button3{
            width: 150px;
            height: 40px;
            background-color: green;
            border: 1px solid green;
            color: white;
            margin-top: 20px;
            font-size: 20px;
        }

    </style>



</head>
<body>
<div class="background">

    <div class="top">
        <p>武汉科技大学教务处后台管理</p>
        <p>Wuhan University of Science and Technology Back Office Management of Academic Affairs Office</p>
    </div>

    <div class="center">
        <input type="button" id="button1" value="上传成绩">
        <input type="button" id="button2" value="批改作业">
        <input type="button" id="button3" value="查看课程">
    </div>

</div>

<script>
    var button1 = document.getElementById("button1");
    var button2 = document.getElementById("button2");
    var button3 = document.getElementById("button3");

    <% ApprovalServlet apr =new ApprovalServlet(); %>
    <%
    if(apr.Run_App((HttpServletRequest)request,'d'))
            out.print("alert('武汉科技大学教务处后台管理欢迎您')");
    else {
        out.print("alert('您没有该权限'); window.location='loginWUST.html'");
    }
    %>
    //绑定点击事件
    button1.onclick = function(){
        alert("可以查看相关权限");
    }

    //绑定点击事件
    button2.onclick = function(){
        alert("可以查看相关权限");
    }

    //绑定点击事件
    button3.onclick = function(){
        alert("可以查看相关权限");
    }

</script>

</body>
</html>