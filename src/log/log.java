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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//�������ڸ�ʽ
        file = new File("log/" + df.format(new Date()));
        if(!file.exists()) {
            file.mkdir();
        }
    }

    /*
    ����������󣬻�δ�������ݿ�����
     */
    public void Loginlog(String username,int grade) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
        //System.out.println(df.format(new Date()));
        String filepath="log/"+df.format(new Date())+"/"+date.format(new Date()) + ".log";
        File file = new File(filepath);
        if(!file.exists()) {
            file.createNewFile();
        }
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
        String ErrorMessage ="LoginError:" + grade +"\t" + date.format(new Date())+ "\t" + username + ", Login fail.\n";
        FileWriter fileWritter = new FileWriter(filepath,true);
        fileWritter.write(ErrorMessage);
        fileWritter.close();
    }

    /*
    ͨ��֤����ʧ��ʱ����¼
     */
    public void Approvallog(String Message, String user_role, int grade) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
        //System.out.println(df.format(new Date()));
        String filepath="log/"+df.format(new Date())+"/"+date.format(new Date()) + ".log";
        File file = new File(filepath);
        if(!file.exists()) {
            file.createNewFile();
        }
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
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
    ���񿨽���ʧ��ʱ����¼��־
     */
    public void Runlog(String Tserv, String Ksess, int grade) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
        //System.out.println(df.format(new Date()));
        String filepath="log/"+df.format(new Date())+"/"+date.format(new Date()) + ".log";
        File file = new File(filepath);
        if(!file.exists()) {
            file.createNewFile();
        }
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
        String ErrorMessage = "RunError:" + grade + "\t" + date.format(new Date()) + "\tTserv:" + Tserv + "\tKsess:"+ Ksess + ".\n";
        FileWriter fileWritter = new FileWriter(filepath,true);
        fileWritter.write(ErrorMessage);
        fileWritter.close();
    }

    /*
    �����������̲߳���ʱ�����м�¼
     */
    public void Runlog(char App,int grade) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
        //System.out.println(df.format(new Date()));
        String filepath="log/"+df.format(new Date())+"/"+date.format(new Date()) + ".log";
        File file = new File(filepath);
        if(!file.exists()) {
            file.createNewFile();
        }
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
        String ErrorMessage = "RunError:" + grade + "\t" + date.format(new Date()) + "\tApplication-" + App+", Unable to start.\n";
        FileWriter fileWritter = new FileWriter(filepath,true);
        fileWritter.write(ErrorMessage);
        fileWritter.close();
    }
}
