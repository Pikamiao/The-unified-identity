package web.service;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import web.common.Connector;
import web.common.DESUtil;


public class userServiceImpl implements userService {

	//int roleId=(Integer) null;

	public boolean regis(String userName, String password) {
		Connection connection = Connector.getConnection();
		String sql = "select * from user where userName=?";
		String sql2 = "insert into user(userName,password) values(?,?)";
		try {
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);//
			preparedStatement.setString(1, userName);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				connection.close();
				preparedStatement.close();
				resultSet.close();
				return false;
			} else {
				java.sql.PreparedStatement preparedStatement1 = connection.prepareStatement(sql2);
				preparedStatement1.setString(1, userName);
				preparedStatement1.setString(2, password);//
				//preparedStatement1.setString(2, MD5Util.md5(password));//

				//preparedStatement1.execute();
				int a = preparedStatement1.executeUpdate();
				if (a > 0) {
					connection.close();
					preparedStatement1.close();
					preparedStatement.close();
					resultSet.close();
					return true;
				} else {
					connection.close();
					preparedStatement1.close();
					preparedStatement.close();
					resultSet.close();

					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();//�ڴ���ҵ���п����ڴ˴���¼��־@�Ʒ�@�Ÿ�
		}

		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * 生成随机字符串
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

	public String login(String userName) throws IOException {
		// TODO Auto-generated method stub
		DESUtil des = new DESUtil();
		final Base64.Encoder encoder = Base64.getEncoder();
		final String Kgrant = "Hellowrd";//服务器密钥Kgrant
		if (userName == null || "".equals(userName.trim())) {
			return null;
		}
		try {
			Connection connection = Connector.getConnection();
			String sql ="select password from user where userName='"+userName+"';";
			java.sql.PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			String password=null;
			if (resultSet.next()){
				password = resultSet.getString("password");
			}
			if(password == null)
				return null;
			password=password.substring(0,8);
			String Ksess = getRandomString(8);//这里生成会话密钥Ksess
			byte[] Tgrant = (Ksess + "Request").getBytes("ISO-8859-1");//这里生成通行证
			Tgrant = des.encrypt(Tgrant,Kgrant.getBytes("ISO-8859-1"));//使用服务器密钥加密通行证
			byte[] text = des.encrypt((Ksess + encoder.encodeToString(Tgrant) + "Success")
					.getBytes("ISO-8859-1"),password.getBytes("ISO-8859-1"));
			final String permit = encoder.encodeToString(text);
			return  permit;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public int getRoleId(String userName) {
		
		Connection connection = Connector.getConnection();
		String sql = "select role_id from user_role where userName=?";
		java.sql.PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();//�ڴ���ҵ���п����ڴ˴���¼��־@�Ʒ�@�Ÿ�
		}
		try {
			preparedStatement.setString(1, userName);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("role_id");}
			else
				return (Integer) null;
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();//�ڴ���ҵ���п����ڴ˴���¼��־@�Ʒ�@�Ÿ�
		}
		return (Integer) null;

	}

	public String role(String userName) {
	int roleId;
		Connection connection = Connector.getConnection();
		String sql = "select role_id from user_role where userName=?";
		String sql1 = "select role from role where role_id=?";
		java.sql.PreparedStatement preparedStatement = null;
		java.sql.PreparedStatement preparedStatement1 = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement1 = connection.prepareStatement(sql1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();//�ڴ���ҵ���п����ڴ˴���¼��־@�Ʒ�@�Ÿ�
		}
		try {
			preparedStatement.setString(1, userName);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				roleId = resultSet.getInt("role_id");
				System.out.println(roleId);
				
				preparedStatement1.setInt(1, roleId);
				ResultSet resultSet1 = preparedStatement1.executeQuery();
				 resultSet1.next();
				 return resultSet1.getString("role");

			} 
			else
				return "";
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();//�ڴ���ҵ���п����ڴ˴���¼��־@�Ʒ�@�Ÿ�
		}
		return "";
		
		
	}
	public List<String> getAuthList(String userName){
		int roleId;
		roleId=getRoleId(userName);
		List<String> authList=new ArrayList<>();
		Connection connection=Connector.getConnection();
		String sql="select auth_id from role_auth where role_id=?";
		java.sql.PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,roleId);
			ResultSet resultSet=preparedStatement.executeQuery();
			int count=0;
			//����
			while(resultSet.next()){
				count++;
				String sql1="select authority from authority where auth_id=?";
				try {
					java.sql.PreparedStatement preparedStatement1= connection.prepareStatement(sql1);
					preparedStatement1.setInt(1,resultSet.getInt("auth_id"));
					ResultSet resultSet1=preparedStatement1.executeQuery();
					if(resultSet1.next()){
					authList.add(resultSet1.getString("authority"));
					
					preparedStatement1.close();
					
					resultSet1.close();
					System.out.println(count);
					
					}} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();//�ڴ���ҵ���п����ڴ˴���¼��־@�Ʒ�@�Ÿ�
				}
		
			}
			connection.close();
			resultSet.close();
			preparedStatement.close();
			
			return authList;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();//�ڴ���ҵ���п����ڴ˴���¼��־@�Ʒ�@�Ÿ�
		}
		return null;
		
	}}
	

