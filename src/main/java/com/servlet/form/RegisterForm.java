package com.servlet.form;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/regForm")
public class RegisterForm extends HttpServlet{

	/**
	 * @author Naveen Wodeyar
	 * @Date 01-01-2024
	 */
	private static final long serialVersionUID = -6433974293947454634L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
		
		PrintWriter pw = resp.getWriter();
		
		//details from the request object,
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String gender = req.getParameter("gender");
		String city = req.getParameter("city");
		String password = req.getParameter("password");
		
		// Load the driver,
		try {
			// 1. Load the driver,
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. Establish the connection,
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/reg2023");
			
			// 3. Prepare the statement,
			PreparedStatement ps = con.prepareStatement("INSERT INTO register VALUES(?,?,?,?,?)");
								ps.setString(0, name);
								ps.setString(1, email);
								ps.setString(2, gender);
								ps.setString(3, city);
								ps.setString(4, password);
					int executeUpdate = ps.executeUpdate();
					
					if(executeUpdate > 0){
						
						resp.setContentType("text/html");
						pw.print("<h3 style='green'>User registered successfully,</h3>");
						
						RequestDispatcher rd = req.getRequestDispatcher("register.jsp");
											rd.forward(req, resp);
					}
					else {
						
						resp.setContentType("text/html");
						pw.print("<h3 style='red'>Unable to register,</h3>");
						
						RequestDispatcher rd = req.getRequestDispatcher("register.jsp");
											rd.forward(req, resp);
					}
		}
		catch (Exception e) {
			e.printStackTrace();
			resp.setContentType("text/html");
			pw.print("<h3 style='red'>Exception Occured :"+e.getMessage()+"</h3>");
			
			RequestDispatcher rd = req.getRequestDispatcher("register.jsp");
								rd.forward(req, resp);
		}
	}
}
