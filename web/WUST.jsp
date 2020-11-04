<%--
  Created by IntelliJ IDEA.
  User: 程总
  Date: 2020/5/6
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>武汉科技大学</title>
    <style>

        body{
            background: url("img/background2.png") no-repeat;
            background-size:100%;
        }

        .background{
            width: 950px;
            height: 600px;
            /* rgba(0, 0, 0, 0.2) 前三个确定颜色(RGB)，最后0.8确定透明度 */
            background: rgba(255, 255, 255, 0.8) none repeat scroll !important;
            background-color: white;
            /*让div居中*/
            margin:auto;
            vertical-align: middle;
            /*padding: 15px;//内边距*/
            /*外边距*/
            margin-top: 200px;
        }

        .left{
            float: left;
            margin: 50px;
        }

        /*第一个元素*/
        .left > p:first-child{
            color: blueviolet;
            font-size: 30px;
        }

        .left > p:last-child{
            color: blueviolet;
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
            background-color: blueviolet;
            border: 1px solid blueviolet;
            color: white;
            margin-top: 20px;
            font-size: 20px;
        }

        #button4,#button5{
            width: 250px;
            height: 40px;
            background-color: blueviolet;
            border: 1px solid blueviolet;
            color: white;
            margin-top: 20px;
            font-size: 20px;
        }

        .right{
            float: right;
            margin: 50px;
        }

        .right > p:first-child{
            color: blueviolet;
            font-size: 20px;
        }

    </style>

</head>
<body>

<div class="background">
    <div class="left">
        <p>武汉科技大学</p>
        <p>Wuhan University of Science and Technology</p>
    </div>

    <div class="center">
        <input type="button" id="button1" value="武科大图书馆">
        <input type="button" id="button2" value="武科大教务处">
        <input type="button" id="button3" value="武科大校园一览">
        <input type="button" id="button4" value="武科大图书馆后台管理">
        <input type="button" id="button5" value="武科大教务处后台管理">
    </div>

    <div class="right">
        <p>欢迎用户
            <%
                //获取cookie
                Cookie[] cookies = request.getCookies();
                //遍历cookies
                if(cookies!=null){
                    for(Cookie cookie:cookies){
                        String name = cookie.getName();
                        //获得客户端发送的username
                        String value = cookie.getValue();
                        //重新设置cookie值，更新cookie，重新发送cookie
                        if(name.equals("username")){
                            out.write(value);
                        }
                    }
                }
            %>
        </p>
    </div>

</div>

<%@ page import="web.servlet.ApprovalServlet" %>

<script>
    /*根据id获取元素*/
    var button1 = document.getElementById("button1");
    var button2 = document.getElementById("button2");
    var button3 = document.getElementById("button3");
    var button4 = document.getElementById("button4");
    var button5 = document.getElementById("button5");

    <% ApprovalServlet apr =new ApprovalServlet(); %>

    //绑定点击事件，只要登录就可以进
    button1.onclick = function(){
        <%
        if(apr.Approval_a((HttpServletRequest) request,(HttpServletResponse) response))
            out.print("window.location='WUSTlibraryALL.jsp'");
        else
            out.print("alert('您没有该权限')");//未登录无权限
        %>
    }

    //绑定点击事件，只要登录就可以进
    button2.onclick = function(){
        <%
        if(apr.Approval_b((HttpServletRequest) request,(HttpServletResponse) response))
            out.print("window.location='WUSTofficeALL.jsp'");
        else
            out.print("alert('您没有该权限')");
        %>
    }

    //绑定点击事件//武科大校园一览，所有用户都可以进入，无需登录
    button3.onclick = function(){
        location.href="WUSTview.jsp";
    }

    //绑定点击事件，跳转武科大图书馆后台管理
    button4.onclick = function(){
        <%
        if(apr.Approval_c((HttpServletRequest) request,(HttpServletResponse) response))
            out.print("window.location='WUSTlibrary.jsp'");
        else
            out.print("alert('您没有该权限')");
        %>
    }

    //绑定点击事件，跳转武科大教务处后台管理
    button5.onclick = function(){
        <%
        if(apr.Approval_d((HttpServletRequest) request,(HttpServletResponse) response))
            out.print(" window.location='WUSToffice.jsp'");
        else
            out.print("alert('您没有该权限')");
        %>
    }


</script>

</body>
</html>
