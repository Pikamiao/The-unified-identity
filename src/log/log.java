package log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class log {
    public log() {
        File file = new File("log");
        if(!file.exists()) {
            file.mkdir();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
        file = new File("log/" + df.format(new Date()));
        if(!file.exists()) {
            file.mkdir();
        }
    }

    /*
    密码输入错误，或未启动数据库链接
     */
    public void Loginlog(String username,int grade) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        //System.out.println(df.format(new Date()));
        String filepath="log/"+df.format(new Date())+"/"+date.format(new Date()) + ".log";
        File file = new File(filepath);
        if(!file.exists()) {
            file.createNewFile();
        }
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String ErrorMessage ="LoginError:" + grade +"\t" + date.format(new Date())+ "\t" + username + ", Login fail.\n";
        FileWriter fileWritter = new FileWriter(filepath,true);
        fileWritter.write(ErrorMessage);
        fileWritter.close();
    }

    /*
    通行证解析失败时，记录
     */
    public void Approvallog(String Message, String user_role, int grade) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        //System.out.println(df.format(new Date()));
        String filepath="log/"+df.format(new Date())+"/"+date.format(new Date()) + ".log";
        File file = new File(filepath);
        if(!file.exists()) {
            file.createNewFile();
        }
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String ErrorMessage = "ApprovalError:" + grade + "\t" + date.format(new Date()) + "\t";
        if (grade == 1)
            ErrorMessage = ErrorMessage +"user-" + user_role + ", permit-" + Message + ".\n";
        if (grade == 2)
            ErrorMessage = ErrorMessage +"role-" + user_role + ", message-" + Message + ".\n";
        FileWriter fileWritter = new FileWriter(filepath,true);
        fileWritter.write(ErrorMessage);
        fileWritter.close();
    }

    /*
    服务卡解析失败时，记录日志
     */
    public void Runlog(String Tserv, String Ksess, int grade) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        //System.out.println(df.format(new Date()));
        String filepath="log/"+df.format(new Date())+"/"+date.format(new Date()) + ".log";
        File file = new File(filepath);
        if(!file.exists()) {
            file.createNewFile();
        }
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String ErrorMessage = "RunError:" + grade + "\t" + date.format(new Date()) + "\tTserv:" + Tserv + "\tKsess:"+ Ksess + ".\n";
        FileWriter fileWritter = new FileWriter(filepath,true);
        fileWritter.write(ErrorMessage);
        fileWritter.close();
    }

    /*
    当程序启动线程不够时，进行记录
     */
    public void Runlog(char App,int grade) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        //System.out.println(df.format(new Date()));
        String filepath="log/"+df.format(new Date())+"/"+date.format(new Date()) + ".log";
        File file = new File(filepath);
        if(!file.exists()) {
            file.createNewFile();
        }
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String ErrorMessage = "RunError:" + grade + "\t" + date.format(new Date()) + "\tApplication-" + App+", Unable to start.\n";
        FileWriter fileWritter = new FileWriter(filepath,true);
        fileWritter.write(ErrorMessage);
        fileWritter.close();
    }
}
