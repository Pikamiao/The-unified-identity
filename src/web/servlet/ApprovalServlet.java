package web.servlet;

import log.log;
import web.common.Connector;
import web.pojo.role;
import web.service.ApprovalService;
import web.common.DESUtil;
import web.service.RunService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/ApprovalServlet")
public class ApprovalServlet extends HttpServlet {
    //Map map = new HashMap();
    String Ksess = null;
    log log = new log();
    public boolean Approval_a (HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(Approval(request,response,'a'))
            return true;
        else return false;
    }

    public boolean Approval_b (HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(Approval(request,response,'b'))
            return true;
        else return false;
    }

    public boolean Approval_c (HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(Approval(request,response,'c'))
            return true;
        else return false;
    }

    public boolean Approval_d (HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(Approval(request,response,'d'))
            return true;
        else return false;
    }

    /*
    用以辅助前端生成服务审批请求
     */
    private boolean Approval(HttpServletRequest request, HttpServletResponse response, char id) throws IOException {
        DESUtil desUtil = new DESUtil();
        final Base64.Decoder decoder = Base64.getDecoder();
        final Base64.Encoder encoder = Base64.getEncoder();
        Cookie[] cookies = request.getCookies();
        String str = null;
        String username = null;
        role loginrole = null;
        try {
            //遍历cookies
            if(cookies!=null){
                for(Cookie cookie:cookies){
                    String name = cookie.getName();
                    //获得客户端发送的permit
                    String value = cookie.getValue();
                    if(name.equals("permit")){
                        str = value;
                    }
                    if(name.equals("username")) {
                        Connection connection = Connector.getConnection();
                        username = value;
                        String sql ="select user_role.role_id,role from user_role,role where userName='"
                                +username+"' and user_role.role_id = role.role_id;";
                        //TODO 日志记录
                        System.out.println(sql);
                        java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        ResultSet resultSet = preparedStatement.executeQuery();
                        int role_id = -1;
                        String Role=null;
                        if (resultSet.next()) {
                            role_id = Integer.parseInt(resultSet.getString("role_id"));
                            Role = resultSet.getString("role");
                        }
                        if(role_id!=-1)
                            loginrole=new role(Role,role_id);
                    }
                }
            }
            if(loginrole==null)
                return false;
            Ksess = str.substring(0,8);//前8位为会话密钥
            //TODO 因为模拟服务器，所以会话密钥无法存储，先存储在文本中
            File file =new File("Ksess.txt");
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(file.getName(),false);
            fileWritter.write(Ksess);
            fileWritter.close();

            byte[] Qgrant = ("Request -" + id).getBytes("ISO-8859-1");//生成所请求的服务，可使用Request -加abcd即对应的服务
            Qgrant = desUtil.encrypt(Qgrant,Ksess.getBytes("ISO-8859-1"));
            byte[] Tgrant = decoder.decode(str.substring(8,str.length()-7));//通行证即除去会话密钥与Success验证码的部分
            //合并通行证和请求为向服务审批发送的审批消息
            byte[] Sgrant = new byte[Qgrant.length + Tgrant.length];
            System.arraycopy(Qgrant, 0, Sgrant, 0, Qgrant.length);
            System.arraycopy(Tgrant, 0, Sgrant, Qgrant.length, Tgrant.length);
            ApprovalService Apps = new ApprovalService();
            if(Apps.receive(response,encoder.encodeToString(Sgrant),loginrole))
                return true;
        } catch (Exception e) {
            log.Approvallog(str,username,1);
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /*
    用以辅助前端解析服务卡并生成执行请求
     */
    public boolean Run_App(HttpServletRequest request,char id) throws IOException {
        String Tserv=null;
        BufferedReader reader=new BufferedReader(new FileReader("Ksess.txt"));
        Ksess=reader.readLine();
        reader.close();
        //Ksess = Ksess.substring(0,8);
        System.out.println(Ksess);

        DESUtil des = new DESUtil();
        final Base64.Encoder encoder = Base64.getEncoder();
        final Base64.Decoder decoder = Base64.getDecoder();
        RunService Runs = new RunService();
        try {
            Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
            if(cookies!=null) {
                for (Cookie cookie : cookies) {
                    String name = cookie.getName();
                    //获得客户端发送的permit
                    String value = cookie.getValue();
                    if (name.equals("Tserv-"+id)) {
                        Tserv = value;
                    }
                }
            }
            //Tserv = (String) map.get("Tserv-"+id);
            System.out.println(Tserv);
            byte[] rspMessage = des.decrypt(decoder.decode(Tserv),Ksess.getBytes("ISO-8859-1"));
            Tserv = new String(rspMessage,"ISO-8859-1");
            String Ksess2 = Tserv.substring(0,8);
            byte[] Qserv = ("Run -"+id).getBytes("ISO-8859-1");
            Qserv = des.encrypt(Qserv,Ksess2.getBytes("ISO-8859-1"));
            if(Runs.Run(encoder.encodeToString(Qserv) + Tserv.substring(8))) {//发送到应用服务器
                return true;
            }
        } catch (Exception e) {
            log.Runlog(Tserv,Ksess,1);
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
