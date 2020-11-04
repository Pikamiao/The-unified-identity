package web.service;

import log.log;
import web.common.Connector;
import web.common.DESUtil;
import web.pojo.role;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Random;

import static java.util.Arrays.copyOf;

public class ApprovalService {
    final String Kserv = "Heloword";//������������ԿKserv
    log log = new log();
    //TODO ��ѯ�û��Ƿ�ӵ�и�Ȩ��
    private boolean CheckApproval(char Approval,int id) {
        int auth_id = Approval-'a'+1;
        try {
            Connection connection = Connector.getConnection();
            String sql ="select * from role_auth where role_id='"+id+"' and auth_id = '"+auth_id+"';";
            //TODO ��־��¼
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /*
     * ��������ַ���
     */
    private static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    //��������������
    public boolean receive(HttpServletResponse response, String Message, role Role) throws IOException {
        try {
            DESUtil des = new DESUtil();
            final Base64.Encoder encoder = Base64.getEncoder();
            final Base64.Decoder decoder = Base64.getDecoder();
            final String Kgrant = "Hellowrd";//��������ԿKgrant
            byte[] Sgrant = decoder.decode(Message);
            byte[] Tgrant = new byte[16];//ͨ��֤
            byte[] Qgrant =  copyOf(Sgrant,Sgrant.length-16);//����
            System.arraycopy(Sgrant,Sgrant.length-16,Tgrant,0,16);
            Tgrant = des.decrypt(Tgrant,Kgrant.getBytes("ISO-8859-1"));
            String Ksess = new String(Tgrant,"ISO-8859-1");
            if(Ksess.substring(8).equals("Request")) {//��֤һ��
                Ksess = Ksess.substring(0,8);
                Qgrant = des.decrypt(Qgrant,Ksess.getBytes("ISO-8859-1"));
                String Request = new String(Qgrant,"ISO-8859-1");
                char Approval = Request.trim().charAt(Request.length()-1);
                if(CheckApproval(Approval,Role.getRole_id())) {
                    String Ksess2 = getRandomString(8);
                    byte[] Tserv = des.encrypt((Ksess2+"OK").getBytes("ISO-8859-1"),
                            Kserv.getBytes("ISO-8859-1"));
                    String rspMessage = Ksess2 + encoder.encodeToString(Tserv);
                    rspMessage = encoder.encodeToString(des.encrypt(rspMessage.getBytes("ISO-8859-1"),
                            Ksess.getBytes("ISO-8859-1")));
                    //map.put("Tserv-"+Approval,rspMessage);
                    Cookie cookie = new Cookie("Tserv-"+Approval,rspMessage);
                    response.addCookie(cookie);
                    //System.out.println(rspMessage);
                    System.out.println("Approval success");
                    return true;
                }
            }
        } catch (Exception e) {
            log.Approvallog(Message,Role.getRole(),2);
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
