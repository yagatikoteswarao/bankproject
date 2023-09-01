package com.jsp.sbibank;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.SeekableByteChannel;
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
import javax.websocket.Session;

@WebServlet("/SendAmount")
public class SendAmount extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sendamount = req.getParameter("sendamount");
		String url = "jdbc:mysql://localhost:3306/teca44?user=root&password=12345";
		HttpSession session = req.getSession();
		double sendamount1 = Double.parseDouble(sendamount);
		String rmobilenumber = (String) session.getAttribute("rdmobilenumber");
		double rdamount =	(double) session.getAttribute("rdamount");
		double sdamount = (double) session.getAttribute("damount");
		String rname = (String) session.getAttribute("rname");
		String sname = (String) session.getAttribute("sname");
		String smobilenumber = (String) session.getAttribute("mb");
		PrintWriter writer2 = resp.getWriter();
		resp.setContentType("text/html");
		if (sendamount1 > 0) {
			if (sdamount >= sendamount1) {
				double add = rdamount + sendamount1;
				double sub = sdamount - sendamount1;
				String updates = "update bank set amount=? where mobile_number=?";
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection connection = DriverManager.getConnection(url);
					PreparedStatement ps1 = connection.prepareStatement(updates);
					ps1.setDouble(1, sub);
					ps1.setString(2, smobilenumber);
					int result1 = ps1.executeUpdate();
					if (result1 > 0) {
						String updater = "update bank set amount=? where mobile_number=?";
						PreparedStatement ps2 = connection.prepareStatement(updater);
						ps2.setDouble(1, add);
						ps2.setString(2, rmobilenumber);
						int result2 = ps2.executeUpdate();
						if (result2 > 0) {
							RequestDispatcher dispatcher = req.getRequestDispatcher("WelcomeToSBI.html");
							dispatcher.include(req, resp);
							writer2.println("<center><span>Amount Transfered SuccessFully...</span><br></center>");
							writer2.println("<center><span>Name :"+sname+"</span><br></center>");
							writer2.println("<center><span> Amount :"+sendamount1+"</span><br></center>");
							writer2.println("<center><span> Mobile number:"+smobilenumber.substring(0,4)+"XXXX"+smobilenumber.substring(8,10));
							writer2.println("<center><h1> Avalable balance is :"+add+"</h1></center>");
							RequestDispatcher dispatcher1 = req.getRequestDispatcher("WelcomeToSBI.html");
							dispatcher1.include(req, resp);
//							
//							writer2.println("<center><h1> Mobile number is :"+rmobilenumber.substring(0,4)+"XXXX"+rmobilenumber.substring(8,10));
						} else {
							RequestDispatcher dispatcher = req.getRequestDispatcher("SendAmount.html");
							dispatcher.include(req, resp);
							writer2.println("<center><h1 style='color:red'>Invalid Amount....</h1></center>");
						}
					}
				} catch (SQLException e) {
					// TODO: handle exception
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else
			{
				RequestDispatcher dispatcher = req.getRequestDispatcher("SendAmount.html");
				dispatcher.include(req, resp);
				writer2.println("<center><h1 style='color:red'>You Have Low Balance....</h1></center>");
			}
		}
		else
		{
			RequestDispatcher dispatcher = req.getRequestDispatcher("SendAmount.html");
			dispatcher.include(req, resp);
			writer2.println("<center><h1 style='color:red'>Invalid Amount....</h1></center>");
			
		}

	}
}
