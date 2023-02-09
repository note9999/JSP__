package com.emp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emp.dao.EmpDAO;


@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	String lv = request.getParameter("lv");
	
	EmpDAO dao = EmpDAO.getInstance();
	
	// ���̵�, ���, ���� üũ�ϴ� �޼��� ȣ��
	// ���̵�Ʋ������ -1, ���Ʋ���� �� 0, ���� Ʋ���� �� 1
	// �����ڷα���:2 �Ϲݻ���α���:3
	int result = dao.userCheck(id, pw, lv);
	
	if(result==2||result==3) {
		//�α��� ����ó��
	} else {
		if(result==1) {
			request.setAttribute("msg", "������ ��ġ���� �ʽ��ϴ�.");
		}else if(result==0) {
			request.setAttribute("msg", "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		} else {
			request.setAttribute("msg", "���̵� �������� �ʽ��ϴ�.");
			}
		
		}
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}
}
