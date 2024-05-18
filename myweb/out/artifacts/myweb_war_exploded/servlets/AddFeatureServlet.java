package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.Jdbc;

@WebServlet(name = "AddFeatureServlet")
public class AddFeatureServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取地图数据
        String id = request.getParameter("id");
        String member = request.getParameter("member");
        String date = request.getParameter("date");
        String place = request.getParameter("place");
        Jdbc jdbc = new Jdbc();
        jdbc.addFeatures(id, member, date, place);

        // 返回成功响应
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("Data added successfully to the database22.");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
