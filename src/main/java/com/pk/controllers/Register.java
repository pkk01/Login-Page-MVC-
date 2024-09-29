package com.pk.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pk.dbconn.DbConnection;

@WebServlet("/regForm")
public class Register extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		String myName = req.getParameter("name1");
		String myEmail = req.getParameter("email1");
		String myPassword = req.getParameter("pass1");
		String myCity = req.getParameter("city1");

		try {
			Connection con = DbConnection.getConnection();
			String insertSqlQuery = "INSERT INTO REGISTER VALUES (?,?,?,?)"; // positional parameters
			PreparedStatement preparedStatement = con.prepareStatement(insertSqlQuery);

			// Assigning the values
			preparedStatement.setString(1, myName);
			preparedStatement.setString(2, myEmail);
			preparedStatement.setString(3, myPassword);
			preparedStatement.setString(4, myCity);

			int count = preparedStatement.executeUpdate();

			if (count > 0) {

				out.println("<h3 style = 'color: green'> Registed Successfully </h3>");
				RequestDispatcher rd = req.getRequestDispatcher("/login.html");
				rd.include(req, resp);
			}
			else {
				out.println("<h3 style = 'color: red'> User not registed due to some errors</h3>");
				
				RequestDispatcher rd = req.getRequestDispatcher("/register.html");
				rd.include(req, resp);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
