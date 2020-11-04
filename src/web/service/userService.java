package web.service;

import java.io.IOException;
import java.util.List;


public interface userService {
	public  boolean regis(String userName, String password) ;
	//boolean login(String userName, String password) throws IOException;
	String login(String userName) throws IOException;
	String role(String userName);
	public List<String> getAuthList(String userName);
	public int getRoleId(String userName);
	//boolean updateInfo(int userId,String address,String phone,String name);
}
