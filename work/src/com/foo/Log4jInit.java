package com.foo;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Log4jInit extends HttpServlet {

    public void init() {
        String prefix =  getServletContext().getRealPath("/");
        String file = getInitParameter("log4j-init-file");
        // if the log4j-init-file is not set, then no point in trying
        System.out.print("1111");
        if(file != null) {
            PropertyConfigurator.configure(prefix+file);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
