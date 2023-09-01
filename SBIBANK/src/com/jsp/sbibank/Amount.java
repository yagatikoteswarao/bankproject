package com.jsp.sbibank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Amount")
public class Amount  extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String amount = req.getParameter("amount");
		String url="jdbc:mysql://localhost:3306/teca44?user=root&password=12345";
		double amount1 = Double.parseDouble(amount);
		HttpSession session =req.getSession();
		Double damount=(Double) session.getAttribute("damount");
		String mb = (String) session.getAttribute("mb");
		String password=(String) session.getAttribute("password");
		PrintWriter writer = resp.getWriter();
		resp.setContentType("text/html");
		
		if(amount1>0)
		{
			if(damount>=amount1)
			{
				//4000=5000-1000
				double sub=damount-amount1;
				String update="update bank set amount=? where mobile_number=? and password=?";
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection connection = DriverManager.getConnection(url);
					PreparedStatement ps = connection.prepareStatement(update);
					ps.setDouble(1, sub);
					ps.setString(2, mb);
					ps.setString(3, password);
					int result = ps.executeUpdate();
					if(result>0)
					{
						RequestDispatcher dispatcher = req.getRequestDispatcher("WelcomeToSBI.html");
						dispatcher.include(req, resp);
						writer.println("<center><h1>WithDraw Successfull....</h1></center>");
					}
					else
					{
						RequestDispatcher dispatcher = req.getRequestDispatcher("WelcomeToSBI.html");
						dispatcher.include(req, resp);
						writer.println("<center><h1 style='color:green';>Server Busy....</h1></center>");
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
		else
		{
			RequestDispatcher dispatcher = req.getRequestDispatcher("amount.html");
			dispatcher.include(req, resp);
			writer.println("<center><h1 style='color:red';>Insufficient Balance</h1></center>");
		}
	}
}









