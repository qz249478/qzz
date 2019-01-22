package com.common;

import com.yahoo.search.ImageSearchRequest;
import com.yahoo.search.ImageSearchResult;
import com.yahoo.search.ImageSearchResults;
import com.yahoo.search.SearchClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URLEncoder;

@WebServlet(name = "SearchServlet",urlPatterns = "/search")
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        String word = request.getParameter("word");
        String type = request.getParameter("type");
        String allowedAdult = request.getParameter("allowedAdult");
        boolean adult = "true".equals(allowedAdult);

        PrintWriter out = response.getWriter();

        SearchClient client = new SearchClient("javasdktest");
        try{
            if("image".equals(type)){
                ImageSearchRequest imageSearchRequest = new ImageSearchRequest(URLEncoder.encode(word,"UTF-8"));
                imageSearchRequest.setAdultOk(adult);
                imageSearchRequest.setResults(20);
                imageSearchRequest.setStart(BigInteger.valueOf(0));
                ImageSearchResults imageSearchResults = client.imageSearch(imageSearchRequest);
                for (ImageSearchResult result:imageSearchResults.listResults()){
                    out.print(result.getClickUrl() + result.getTitle());
                }

            }else if("video".equals("type")){

            }else{

            }
        }
        catch ( Exception e){
            e.printStackTrace();
        }
        out.flush();
        out.close();

    }
}
