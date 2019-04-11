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
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;
/**
 *
 * @author nikhil
 */
public class AddDetailsServlet extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           HttpSession session = request.getSession();
           int user_id = (int)session.getAttribute("userid");
           //int user_id = 1;
           System.out.println("user id = " + user_id);
           String fullname = request.getParameter("fullname");
           String age = request.getParameter("age");
           String sex = request.getParameter("sex");
           String skills = request.getParameter("skills");
           float tencgpa = Float.parseFloat(request.getParameter("tencgpa"));
           float twelvecgpa = Float.parseFloat(request.getParameter("twelvecgpa"));
           String uniname = request.getParameter("uni");
           float unicgpa = Float.parseFloat(request.getParameter("unicgpa"));
           String projects = request.getParameter("projects");
           int exp = Integer.parseInt(request.getParameter("exp"));
           String comp = request.getParameter("comp");
           String prevpost = request.getParameter("prevpost");
           String applypost = request.getParameter("applypost");
           
           //now create the pdf document
            PDDocument document = new PDDocument();
            PDPage myPage = new PDPage();
            document.addPage(myPage);
            String dir = "/home/nikhil/NetBeansProjects/HEREiaM/web/images/";
            dir = dir + user_id;
            dir = dir + ".pdf";
            try (PDPageContentStream cont = new PDPageContentStream(document, myPage)) {

                cont.beginText();

                cont.setFont(PDType1Font.HELVETICA_BOLD, 24);
                cont.setLeading(26.5f);

                cont.newLineAtOffset(25, 700);
                String line1 = fullname;
                cont.showText(line1);

                cont.newLine();
                
                cont.setFont(PDType1Font.HELVETICA, 16);
                String line2 = "Age : " + age;
                cont.showText(line2);
                cont.newLine();
                String line3 = "Sex : " + sex;
                cont.showText(line3);
                cont.newLine();
                String line4 = "University : " + uniname;
                cont.showText(line4);
                cont.newLine();
                
                cont.setFont(PDType1Font.HELVETICA_BOLD, 20);
                String line5 = "EDUCATIONAL DETAILS";
                cont.showText(line5);
                cont.newLine();
                cont.setFont(PDType1Font.HELVETICA, 16);
                String line6 = "Class X CGPA : " + tencgpa;
                cont.showText(line6);
                cont.newLine(); 
                String line7 = "Class XII CGPA : " + twelvecgpa;
                cont.showText(line7);
                cont.newLine();
                String line8 = "University CGPA : " + unicgpa;
                cont.showText(line8);
                cont.newLine();
                
                cont.setFont(PDType1Font.HELVETICA_BOLD, 24);
                String line9 = "SKILLS";
                cont.showText(line9);
                cont.newLine();
                cont.setFont(PDType1Font.HELVETICA, 16);
                String line10 = skills;
                cont.showText(line10);
                cont.newLine();
                
                cont.setFont(PDType1Font.HELVETICA_BOLD, 24);
                String line11 = "PROJECTS";
                cont.showText(line11);
                cont.newLine();
                cont.setFont(PDType1Font.HELVETICA, 16);
                String line12 = projects;
                cont.showText(line12);
                cont.newLine();
                
                cont.setFont(PDType1Font.HELVETICA_BOLD, 24);
                String line13 = "PROFESSIONAL DETAILS";
                cont.showText(line13);
                cont.newLine();
                cont.setFont(PDType1Font.HELVETICA, 16);
                String line14 = "Experience: " + exp;
                cont.showText(line14);
                cont.newLine();
                String line15 = "Previous Company: " + comp;
                cont.showText(line15);
                cont.newLine();
                String line16 = "Previous Post: " + prevpost;
                cont.showText(line16);
                cont.newLine();
                String line17 = "Applying for Post: " + applypost;
                cont.showText(line17);
                cont.newLine();
                
                cont.endText();
            }
            document.save(dir);

            System.out.println("PDF created");
            document.close();
           
           try {
                out.println("here1\n");
                Class.forName("com.mysql.jdbc.Driver");
                out.println("here2\n");

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HERE_I_AM", "shnik", "shnik256");
                out.println("here3\n");
                PreparedStatement pst = conn.prepareStatement("INSERT INTO EDUCATIONAL_PROFESSIONAL_DETAILS VALUES(?,?,?,?,?,?,?,?,?,?)");
                pst.setInt(1, user_id);
                pst.setFloat(2, tencgpa);
                pst.setFloat(3, twelvecgpa);
                pst.setString(4, uniname);
                pst.setFloat(5, unicgpa);
                pst.setString(6, projects);
                pst.setInt(7, exp);
                pst.setString(8, comp);
                pst.setString(9, prevpost);
                pst.setString(10, applypost);
                out.println("here4");
                //pst.setString(1, user);
                //pst.setString(2, pass);
                int rs = pst.executeUpdate();
                out.println("here");

                
                RequestDispatcher myDispatch = request.getRequestDispatcher("home.html");
                myDispatch.forward(request, response);
   
            
            } catch (ClassNotFoundException | SQLException e) {
                out.println("error");
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
            //out.println("error");
        }
    }
}