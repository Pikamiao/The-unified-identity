<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>武科大校园美景</title>

</head>
<body>

<img id="img" src="img/wust1.png" width="100%">;

<script>
    /*在页面上显示图片
    定义方法修改src属性
    定义定时器每隔三秒调用方法，改变图片*/
    //改变图片
    var number = 1;
    function fun() {
        number++;
        if(number>5){
            number=1;
        }
        var img = document.getElementById("img");
        img.src="img/wust"+number+".png";
    }
    //定义定时器
    setInterval(fun,2000);

</script>
</body>
</html>