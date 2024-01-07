package com.servlet.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logIn")
public class LoginForm extends HttpServlet{

	/**
	 * @author Naveen K Wodeyar
	 * @Date 01-01-2024
	 */
	private static final long serialVersionUID = 5367057451405748693L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mail = req.getParameter("mail");
		String password = req.getParameter("password");
		
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/reg2023","root","boss");
			
			PreparedStatement ps = con.prepareStatement("SELECT * FROM register WHERE email=? AND password=?");
								ps.setString(1, mail);
								ps.setString(2, password);
								
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				
				HttpSession session = req.getSession();
							session.setAttribute("session_email", rs.getString("email"));
				RequestDispatcher rd = req.getRequestDispatcher("/profile.jsp");
									rd.include(req, resp);
			}else {
				
				pw.print("<h2> Credentials mismatched, Please provide correct details to login.</h2>");
				RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
									rd.include(req, resp);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
