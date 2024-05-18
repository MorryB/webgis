package servlets;

import database.Jdbc;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "provStaServlet")
public class ProvStaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Jdbc jdbc = new Jdbc();
        String provStaResult = jdbc.provSta();
        System.out.println(provStaResult);

        // 返回成功响应
        // 设置响应的字符编码为UTF-8
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println(provStaResult);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
