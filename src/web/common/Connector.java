package web.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//�������ݿ�
public class Connector {
	private static Connection connection=null;
	private static String DriverName = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/scs_db";
	private static String username="root";
	private static String password="chc++wdhxh";
	public static Connection getConnection(){
		try{Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection(url,username,password);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("�������쳣");
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("��������ʧ��");
		}
		
	return connection;

	}
	
	//�ر����ݿ�����
public static boolean closeConnection(Connection connection){
	try{
	connection.close();}
	catch(SQLException e){
		e.printStackTrace();
		return false;
	}
	return true;
	
}}

