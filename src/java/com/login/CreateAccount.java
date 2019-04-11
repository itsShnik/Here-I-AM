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
 * Servlet implementation class CreateAccount
 */

public class CreateAccount extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        //out.println("Here, you'll see the result\n");
        String name = request.getParameter("name");
        String user = request.getParameter("username");
        String email = request.getParameter("email");
        String phoneNo = request.getParameter("phoneNo");
        String pass = request.getParameter("pass");
        String confpass = request.getParameter("confpass");
        int userid;
        //out.println("Here, you'll see the result\n");
        if(!pass.equals(confpass)){
            out.println("Error : Passwords do not match");
        }else{
            try {
                //out.println("here1\n");
                Class.forName("com.mysql.jdbc.Driver");
                //out.println("here2\n");

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HERE_I_AM", "shnik", "shnik256");
                //out.println("here3\n");
                PreparedStatement pst1 = conn.prepareStatement("SELECT COUNT(*) FROM USER_INFO");
                ResultSet rs1 = pst1.executeQuery();
                int count = 0;
                while(rs1.next()){
                    count = rs1.getInt("count(*)");
                }
                userid = count + 1;
                out.println(count);
                
                
                PreparedStatement pst = conn.prepareStatement("INSERT INTO USER_INFO VALUES (?, ?, ?, ?, ?, ?)");
                pst.setInt(1, userid);
                pst.setString(2, name);
                pst.setString(3, user);
                pst.setString(4, pass);
                pst.setString(5, email);
                pst.setString(6, phoneNo);
                int i = pst.executeUpdate();
                out.println("Account successfully created !");
                response.sendRedirect("http://localhost:8080/HEREiaM/");
                /*
                if (rs.next()) {
                    out.println("Correct login credentials");
                    response.sendRedirect("home.jsp");
                } 
                else {
                    out.println("Incorrect login credentials");
                }*/
            
            } 
            catch (ClassNotFoundException | SQLException e) {
                out.println("error");
                e.printStackTrace();
            }
        }
        
    }
}
