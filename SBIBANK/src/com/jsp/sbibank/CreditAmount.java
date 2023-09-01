package com.jsp.sbibank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CreditAmount")
public class CreditAmount extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mobilenumber = req.getParameter("mb");
		String password = req.getParameter("password");
		String url="jdbc:mysql://localhost:3306/teca44?user=root&password=12345";
		String select ="select * from bank where mobile_number=? and password=?";
		PrintWriter writer = resp.getWriter();  
		resp.setContentType("text/html");
		HttpSession session =req.getSession();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1,mobilenumber);
			ps.setString(2,password);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				double damount=rs.getDouble(5);
				session.setAttribute("damount",damount);
				session.setAttribute("mb", mobilenumber);
		 		session.setAttribute("password",password);
				RequestDispatcher dispatcher = req.getRequestDispatcher("CAmount.html");
				dispatcher.include(req, resp);
			}
			else
			{
				RequestDispatcher dispatcher = req.getRequestDispatcher("CreditAmount.html");
				dispatcher.include(req, resp);
				writer.println("<center><h1 style='color:red'>Invalid Details.....</h1></center>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
