/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.login;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

/**
 * Servlet implementation class LoginServlet
 */

public class ForgotVerify extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        //out.println("Here, you'll see the result\n");
        String email = request.getParameter("email");
        String phoneNo = request.getParameter("phoneNo");
        //out.println("Here, you'll see the result\n");
        try {
            //out.println("here1\n");
            Class.forName("com.mysql.jdbc.Driver");
            //out.println("here2\n");
            
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HERE_I_AM", "shnik", "shnik256");
            //out.println("here3\n");
            PreparedStatement pst = conn.prepareStatement("Select * from USER_INFO where EMAIL_ID=? and PHONE_NO=?");
            pst.setString(1, email);
            pst.setString(2, phoneNo);
            ResultSet rs = pst.executeQuery();
            //out.println("here");
            if (rs.next()) {
                out.println("Correct login credentials");
                RequestDispatcher myDispatch = request.getRequestDispatcher("home.html");
                myDispatch.forward(request, response);
            } 
            else {
                out.println("Incorrect login credentials");
            }
            
        } 
        catch (ClassNotFoundException | SQLException e) {
            out.println("error");
            e.printStackTrace();
        }
    }
}
