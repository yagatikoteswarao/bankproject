package com.jsp.sbibank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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

import com.sun.crypto.provider.RSACipher;

@WebServlet("/RMobile")
public class RMobile  extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mnumber = req.getParameter("mnumber");
		String url="jdbc:mysql://localhost:3306/teca44?user=root&password=12345";
		String select ="select * from bank where mobile_number=?";
		PrintWriter writer = resp.getWriter();
		HttpSession session = req.getSession();
		resp.setContentType("text/html");
		double sdamount = (Double) session.getAttribute("damount");
		String sdmobilenumber= (String) session.getAttribute("mobilenumber");
		String spassword = (String) session.getAttribute("password");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement ps = connection.prepareStatement(select);
			ps.setString(1,mnumber);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				String dmobilenumber=rs.getString(3);
				session.setAttribute("rdmobilenumber", dmobilenumber);
				double rdamount= rs.getDouble(5);
				session.setAttribute("rdamount",rdamount);
				String rname=rs.getString(2);
				session.setAttribute("rname", rname);
				
				RequestDispatcher dispatcher = req.getRequestDispatcher("SendAmount.html");
				dispatcher.include(req, resp);
			}
			else
			{
				RequestDispatcher dispatcher = req.getRequestDispatcher("RMobile.html");
				dispatcher.include(req, resp);
				writer.println("<center><h1 style='color:red';>Invalid Amount.....</h1></center>");	
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
