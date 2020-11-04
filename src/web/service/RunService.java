package web.service;

import web.common.DESUtil;
import log.log;

import java.io.IOException;
import java.util.Base64;

public class RunService {
    final String Kserv = "Heloword";//审批服务器密钥Kserv
    log log = new log();
    private boolean RunApproval(char App) {
        switch (App){
            case 'a':
                //TODO .....如果应用程序可以运行,则Run,如果应用程序已经被占满则return false
                return true;
            case 'b':
                //TODO .....如果应用程序可以运行,则Run,如果应用程序已经被占满则return false
                return true;
            case 'c':
                //TODO .....如果应用程序可以运行,则Run,如果应用程序已经被占满则return false
                return true;
            case 'd':
                return true;
            default:
                break;
        }
        return false;
    }

    public boolean Run (String message) throws IOException {
        DESUtil des = new DESUtil();
        char App = 0;
        final Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] Qserv = decoder.decode(message.substring(0,12));
            byte[] Tserv = decoder.decode(message.substring(12));
            Tserv = des.decrypt(Tserv,Kserv.getBytes("ISO-8859-1"));
            String Ksess2 = new String(Tserv,"ISO-8859-1");
            if (Ksess2.substring(Ksess2.length()-2).equals("OK")) {
                Ksess2 = Ksess2.substring(0, Ksess2.length() - 2);
                Qserv = des.decrypt(Qserv, Ksess2.getBytes("ISO-8859-1"));
                String Request = new String(Qserv, "ISO-8859-1");
                App = Request.trim().charAt(Request.trim().length() - 1);
                if(RunApproval(App)) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.Runlog(App,2);
            return  false;
        }
        log.Runlog(App,2);
        return false;
    }
}
