package com.pk.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pk.dbconn.DbConnection;
import com.pk.model.User;

@WebServlet("/loginForm")
public class Login extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		String myEmail = req.getParameter("email1");
		String myPass = req.getParameter("pass1");

		try {
			Connection con = DbConnection.getConnection();

			String selectQuery = "SELECT * FROM register WHERE email = ? AND password = ?";

			PreparedStatement pstm = con.prepareStatement(selectQuery);

			pstm.setString(1, myEmail);
			pstm.setString(2, myPass);

			ResultSet rs = pstm.executeQuery();

			if (rs.next()) {
				
				User user = new User();
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setCity(rs.getString("city"));
				
				HttpSession session = req.getSession();
				session.setAttribute ("session_user", user);
				
				RequestDispatcher rd = req.getRequestDispatcher("/profile.jsp");
				rd.forward(req, resp);
				
			} else {
				out.println("<h3 style = 'color : red'> Email id and password did not matched </h3>");

				RequestDispatcher rd = req.getRequestDispatcher("/login.html");
				rd.include(req, resp);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
