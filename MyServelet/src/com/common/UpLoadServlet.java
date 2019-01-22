package com.common;

import com.sun.xml.internal.bind.v2.TODO;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class UpLoadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print("请以post方式上传文件");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File file1 = null,file2 = null;
        String description1 = null,description2 = null;
        DiskFileUpload diskFileUpload = new DiskFileUpload();
        try {
            List<FileItem> list = diskFileUpload.parseRequest(req);
            for (FileItem fileItem:list){
                if (fileItem.isInMemory()){
                    if ("description1".equals(fileItem.getFieldName())){
                        description1 = new String(fileItem.toString().getBytes(),"UTF-8");
                    }
                    if ("description2".equals(fileItem.getFieldName())){
                        description2 = new String(fileItem.toString().getBytes(),"UTF-8");
                    }
                }else {
                    if ("file".equals(fileItem.getFieldName())){
                        File remoteFile = new File(new String(fileItem.getName().getBytes(),"UTF-8"));
                        file1 = new File(this.getServletContext().getRealPath("attachment"),remoteFile.getName());
                        file1.getParentFile().mkdirs();
                        file1.createNewFile();
                        InputStream inputStream = fileItem.getInputStream();
                        OutputStream outputStream = new FileOutputStream(file1);
                        try {
                            byte[] buffer = new byte[1024];
                            int len = 0;
                            while ((len = inputStream.read(buffer)) > -1){
                                outputStream.write(buffer,0,len);
                            }
                        }catch (Exception e){

                        }finally {
                            outputStream.close();
                            inputStream.close();
                        }
                    }if ("file2".equals(fileItem.getFieldName())){

                    }
                }
            }
        }catch (Exception e){

        }if (file1 != null){
            
        }

    }
}
