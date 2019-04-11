/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.home;

import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author nikhil
 */
public class ImagesData extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<String> listresult=new ArrayList<String>();
        
        HttpSession session = request.getSession();
        int user_id = (int)session.getAttribute("userid");
        
        try{
            /*
            implement the code to get the strings from the database to here
            */
            //out.println("here1\n");
            Class.forName("com.mysql.jdbc.Driver");
            //out.println("here2\n");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HERE_I_AM", "shnik", "shnik256");
            //out.println("here3\n");
            PreparedStatement pst = conn.prepareStatement("Select DOCS from DOCUMENTS_FILES where USER_ID=?");
            pst.setInt(1, user_id);
            //pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String s = rs.getString("DOCS");
                listresult.add(s);
            }
            /*
            above
            */

            //listresult.add(" /home/nikhil/NetBeansProjects/HEREiaM/Data/constant.jpg ");
            //listresult.add("values2");
            //listresult.add("values3");
            //listresult.add("values4");

            response.setContentType("application/json");
            OutputStream outputStream= response.getOutputStream();
            Gson gson=new Gson();       
            outputStream.write(gson.toJson(listresult).getBytes());
            outputStream.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
