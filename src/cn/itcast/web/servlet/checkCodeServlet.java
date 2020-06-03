package cn.itcast.web.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/checkCodeServlet")
public class checkCodeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        //验证码
        //创建一个对象，在内存中画图（创建一个对象）
        int width = 100;
        int height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//RGB三原色

        //美化图片 1：设置背景色
        Graphics g = image.getGraphics();//获取画笔对象
        g.setColor(Color.yellow);//设置画笔颜色
        g.fillRect(0, 0, width, height);//填充粉色
        //美化图片 2：画蓝色边框
        g.setColor(Color.BLUE);
        g.drawRect(0, 0, width - 1, height - 1);//边框也是有像素的，容易画到外边，减一更有视觉效果
        //美化图片 3：写验证码字
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb=new StringBuilder();
        Random ran = new Random();//创建Random对象
        for (int i = 1; i <= 4; i++) {
            int index = ran.nextInt(str.length());//生成随机角标索引
            char ch = str.charAt(index);//根据索引获取随机字符
            sb.append(ch);
            g.setFont(new Font("黑体",Font.BOLD,24));
            g.drawString(ch + "",  20 * i, 25);
        }
        String checkCode_session = sb.toString();
//        System.out.println("CheckCodeServlet：：："+checkCode_session);
        //将验证码存入Session，以达到数据共享目的
        request.getSession().setAttribute("checkCode_session",checkCode_session);

        //美化图片 4：画混淆线
        for (int i = 1; i < 10; i++) {
            g.setColor(Color.GREEN);
            int x1 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int x2 = ran.nextInt(width);
            int y2 = ran.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }
        //将图片输出到页面展示
        ServletOutputStream os = response.getOutputStream();
        ImageIO.write(image, "jpg", os);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
