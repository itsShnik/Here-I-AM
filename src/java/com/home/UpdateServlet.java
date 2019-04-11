/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.home;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;

/**
 *
 * @author nikhil
 */
public class UpdateServlet extends HttpServlet {
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        /* TODO output your page here. You may use following sample code. */
        System.out.println("working");
        HttpSession session = request.getSession();
        int user_id = (int)request.getSession().getAttribute("userid");
        System.out.println("working");
        System.out.println("userid = " + user_id);
        String jsonstring = request.getParameter("apts");
        //System.out.println("json = " + jsonstring);

        if(jsonstring != null){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                //out.println("here2\n");

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HERE_I_AM", "shnik", "shnik256");
                //out.println("here3\n");
                PreparedStatement pst = conn.prepareStatement("INSERT INTO FINAL_SCHEDULE VALUES(?, ?, ?)");
                pst.setInt(1, user_id);
                pst.setString(2, jsonstring);

                //parse the json
                JSONParser parse = new JSONParser();
                JSONObject jobj = (JSONObject)parse.parse(jsonstring);
                //System.out.println("json = " + jobj);
                //get individual elements
                String name = (String)jobj.get("name");
                String start = (String)jobj.get("startTime");
                String end = (String)jobj.get("endTime");
                String day = (String)jobj.get("day");

                String hist_string = "Event Name : " + name;
                hist_string = hist_string + "\ndate : " + day ;
                hist_string = hist_string + "\nstart : " + start;
                hist_string = hist_string + "\nend = " + end;
                System.out.println(hist_string);

                pst.setString(3, hist_string);
                int rs = pst.executeUpdate();
                System.out.println("res = " + rs);
                //System.out.println("name = " + name);
                //System.out.println("start = " + start);
                //System.out.println("end = " + end);
                //push the individual elements into a new json object of required format
                JSONObject DataSend = new JSONObject();
                //create starttime and endtime jsons
                JSONObject start_time = new JSONObject();
                start_time.put("dateTime", start);
                JSONObject end_time = new JSONObject();
                end_time.put("dateTime", end);
                //put all into datasend
                DataSend.put("end", end_time);
                DataSend.put("start", start_time);
                DataSend.put("summary", name);
                //stringify the json to send to the api
                String finalDataSend = DataSend.toString();
                System.out.println("finalDataSend = " + finalDataSend);

            }catch(Exception e){
                e.printStackTrace();
            }
        }
            
            
    }
    /*
    public void EmailSend(String email, String hist_string){
        try{
            String host ="smtp.gmail.com" ;
            String user = "thereareyou007@gmail.com";
            String pass = "hereareyou123";
            String to = "divyangmittal77@gmail.com";
            String from = "thereareyou007@gmail.com";
            String subject = "Bungle in the jungle";
            String messageText = "Your Is Test Email :";
            boolean sessionDebug = false;
            
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");
//            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            javax.mail.Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); 
            //sg.setSentDate(new Date());
            msg.setText(hist_string);
            //hread.sleep(duration);
            Transport transport=mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("message send successfully");
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
    }*/
}

