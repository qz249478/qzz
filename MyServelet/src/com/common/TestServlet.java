package com.common;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "TestServlet",urlPatterns = "/hello")
public class TestServlet extends HttpServlet {
    private String message = "";
    public static int WIDTH = 120;
    public static int HEIGHT = 25;

    public void init(){
        message = "全峥峥";
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/page/test.html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        response.setContentType("image/jpeg");//设置响应格式
        RenderedImage image = getImage();
        ImageIO.write(image, "jpeg", response.getOutputStream());
        response.sendRedirect("/page/test.html");
//        request.getRequestDispatcher("test.html").forward(request,response);

    }

    private RenderedImage getImage() {
        //创建内存图像
        BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        //勾勒图像
        Graphics graphics = image.getGraphics();
        //设置背景
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        //设置边框
        graphics.setColor(Color.BLUE);
        graphics.drawRect(1, 1, WIDTH-2, HEIGHT-2);
        //画干扰线
        graphics.setColor(Color.YELLOW);
        for(int i=0;i<8;i++){
            int xStart = new Random().nextInt(WIDTH);
            int yStart = new Random().nextInt(HEIGHT);
            int xEnd = new Random().nextInt(WIDTH);
            int yEnd = new Random().nextInt(HEIGHT);
            graphics.drawLine(xStart, yStart, xEnd, yEnd);
        }
        //写随机数
        graphics.setColor(Color.RED);
        int x = 5;
        for(int i=0;i<4;i++){
            graphics.drawString(new Random().nextInt(9)+"", x, 20);
            x+=30;
        }
        return image;
    }

}
