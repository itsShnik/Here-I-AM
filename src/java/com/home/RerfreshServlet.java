/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.home;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.*;
import java.io.OutputStream;
import java.util.ArrayList;
/**
 *
 * @author nikhil
 */
public class RerfreshServlet extends HttpServlet {

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
        //response.setContentType("text/html;charset=UTF-8");
        System.out.println("here");
        HttpSession session = request.getSession();
        int user_id = (int)session.getAttribute("userid");
        System.out.println("user id = " + user_id);
        
        ArrayList<String> listresult=new ArrayList<String>();
        
        try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HERE_I_AM", "shnik", "shnik256");

                PreparedStatement pst = conn.prepareStatement("SELECT EVENTS FROM FINAL_SCHEDULE WHERE USER_ID = ?");
                pst.setInt(1, user_id);
                ResultSet rs = pst.executeQuery();

                while(rs.next()){
                    String s = rs.getString("EVENTS");
                    System.out.println(s);
                    listresult.add(s);
                }



                response.setContentType("application/json");
                OutputStream outputStream= response.getOutputStream();
                Gson gson=new Gson();       
                //System.out.println("list = ");
                outputStream.write(gson.toJson(listresult).getBytes());
                outputStream.flush();

        }catch(Exception e){
                e.printStackTrace();
        }

    }
}
