package com.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.emp.vo.EmpVO;

public class EmpDAO {
	
	private EmpDAO() {
		
	}
	private static EmpDAO instance = new EmpDAO();
	
	public static EmpDAO getInstance() {
		return instance;
	}
	public Connection getConnection() throws Exception{
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
		Connection conn = ds.getConnection();
		
		return conn;
	}
	 
	public int userCheck(String id, String pw, String lv) {
		 int result=0; // ����� ����
		 
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 
		 String sql= "select*from emp where id=?";
		 
		 try {
			 conn = getConnection();
			 pstmt = conn.prepareStatement(sql);
			 
			 pstmt.setString(1, id);
			 
			 rs= pstmt.executeQuery(); // executeQuery�� ���� ���� ��Ų �� rs��� ��ü�� ���� 
			 
			 if(rs.next()) {
				 if(pw.equals(rs.getString("pw"))) {
					 //���̵� ��ġ, ��й�ȣ�� ��ġ
					 if(lv.equals(rs.getString("lv"))) {
						 // ���̵� ��ġ, ����� ��ġ, ���� ��ġ
						 if(lv.equals("A"))
							 result =2; // ������ ������ 
						 else 
							 result =3; // ������ �Ϲݻ��
					 } else {
						 // ���̵�� ����� ��ġ�ϴµ� ������ ��ġ��������
						 result=1;
					 }
				 }else {
					 // ���̵�� ��ġ�ϴµ� ����� ��ġ��������
					 result=0;
				 }
			 }else {
				 result = -1; // ���̵� Ʋ��
			 }
		 } catch(Exception e) {
			e.printStackTrace();
		 } finally {
			 try {
				 rs.close();
				 pstmt.close();
				 conn.close();
			 } catch(Exception e) {
				 e.printStackTrace();
			 }
		 }
		return result;
	}

	public EmpVO getEmp(String id) {
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 
		 EmpVO vo =null;
		 
		 String sql= "select*from emp where id=?";
		 
		 try {
			 conn = getConnection();
			 pstmt = conn.prepareStatement(sql);
			 
			 pstmt.setString(1, id);
			 
			 rs= pstmt.executeQuery(); // executeQuery�� ���� ���� ��Ų �� rs��� ��ü�� ���� 
			 
			 if(rs.next()) {
				 vo = new EmpVO();
				 
				 vo.setId(rs.getString("id"));
				 vo.setPw(rs.getString("pw"));
				 vo.setName(rs.getString("name"));
				 vo.setLv(rs.getString("lv"));
				 vo.setEnter(rs.getDate("enter"));
				 vo.setGender(rs.getString("gender"));
				 vo.setPhone(rs.getString("phone"));
			 }
		     } catch(Exception e) {
				e.printStackTrace();
			 } finally {
				 try {
					 rs.close();
					 pstmt.close();
					 conn.close();
				 } catch(Exception e) {
					 e.printStackTrace();
				 }
			 }
			return vo;
		}
}
