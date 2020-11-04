package web.servlet;

import log.log;
import web.common.MD5Util;
import web.common.DESUtil;
import web.service.userServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;


@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码
        request.setCharacterEncoding("utf-8");
        DESUtil des = new DESUtil();
        log log = new log();
        final Base64.Decoder decoder = Base64.getDecoder();
        //获取请求参数
        //根据参数名称获取参数值
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        MD5Util md5Util = new MD5Util();
        password = md5Util.md5(password).substring(0,8);
        //调用userServiceImpl的login方法
        userServiceImpl loginUser=new userServiceImpl();
        String Message = loginUser.login(username);
        if(Message!=null) {
            byte[] permit = null;//使用密码机密通行证
            try {
                permit = des.decrypt(decoder.decode(Message), password.getBytes("ISO-8859-1"));
            } catch (Exception e) {
                log.Loginlog(username,1);
                request.getRequestDispatcher("/login.html").forward(request,response);
                e.printStackTrace();
            }
            String str = new String(permit, "ISO-8859-1");
            String checkcode = str.substring(str.length() - 7);//取出验证码并查看是否正确
            if (checkcode.equals("Success")) {//如果为Success即证明用户密码输入正确
                //登陆成功
                //存储数据，把成功登录的用户名存储到request域中
                //创建cookie对象
                Cookie cookie = new Cookie("username",username);
                //向服务器请求数据，将username直接发到服务器
                //发送cookie
                response.addCookie(cookie);
                Cookie cookie1 = new Cookie("permit",str);
                response.addCookie(cookie1);
                //重定向sendRedirect（转发一般都是转发到servlet）
                response.sendRedirect("WUST.jsp");
                System.out.println("success");
            } else {
                System.out.println("登录失败");
                log.Loginlog(username,2);
                //登陆失败，转发
                request.getRequestDispatcher("/login.html").forward(request,response);
            }
        } else {
            //System.out.println("登录失败");
            log.Loginlog(username,3);
            //登陆失败，转发
            request.getRequestDispatcher("/login.html").forward(request,response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
