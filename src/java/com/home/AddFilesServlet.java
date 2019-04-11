package com.home;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

public class AddFilesServlet extends HttpServlet {
   
   private boolean isMultipart;
   private String filePath;
   private int maxFileSize = 1048576;
   private int maxMemSize = 4 * 1024;
   private File file ;

   public void init( ){
      // Get the file location where it would be stored.
      filePath = "/home/nikhil/NetBeansProjects/HEREiaM/web/images/"; 
   }
   
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, java.io.IOException {
       
       HttpSession session = request.getSession();
       int user_id = (int)session.getAttribute("userid");
       System.out.println("user id = " + user_id);
       
      // Check that we have a file upload request
      isMultipart = ServletFileUpload.isMultipartContent(request);
      response.setContentType("text/html");
      java.io.PrintWriter out = response.getWriter( );
   
      if( !isMultipart ) {
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet upload</title>");  
         out.println("</head>");
         out.println("<body>");
         out.println("<p>No file uploaded</p>"); 
         out.println("</body>");
         out.println("</html>");
         return;
      }
  
      DiskFileItemFactory factory = new DiskFileItemFactory();
   
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
   
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File("c:\\temp"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
   
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );

      try { 
         // Parse the request to get file items.
         List fileItems = upload.parseRequest(request);
	
         // Process the uploaded file items
         Iterator i = fileItems.iterator();

         out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet upload</title>");  
         out.println("</head>");
         out.println("<body>");
   
         while ( i.hasNext () ) {
            FileItem fi = (FileItem)i.next();
            if ( !fi.isFormField () ) {
               // Get the uploaded file parameters
               String fieldName = fi.getFieldName();
               String fileName = fi.getName();
               System.out.println("file name = " + fileName);
               String contentType = fi.getContentType();
               boolean isInMemory = fi.isInMemory();
               long sizeInBytes = fi.getSize();
            
               // Write the file
               if( fileName.lastIndexOf("\\") >= 0 ) {
                  file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
               } else {
                  file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
               }
               fi.write( file ) ;
               out.println("Uploaded Filename: " + fileName + "<br>");
               //out.println("here1\n");
               Class.forName("com.mysql.jdbc.Driver");
               //out.println("here2\n");

               Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HERE_I_AM", "shnik", "shnik256");
               //out.println("here3\n");
               PreparedStatement pst = conn.prepareStatement("INSERT INTO DOCUMENTS_FILES VALUES (?, ?)");
               pst.setInt(1, user_id);
               pst.setString(2, fileName);
               int rs = pst.executeUpdate();
               
            }
         }
         out.println("</body>");
         out.println("</html>");
         } catch(Exception ex) {
            System.out.println(ex);
         }
      }
      
      public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, java.io.IOException {

         throw new ServletException("GET method used with " +
            getClass( ).getName( )+": POST method required.");
      }
}