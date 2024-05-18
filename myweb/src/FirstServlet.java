import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import database.Jdbc;

public class FirstServlet extends HttpServlet {
    //继承HttpServlet类，并重写里面doGet和doPost方法(快捷键Alt+insert)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.print("doget");
        doPost(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");//设置页面编码方式，避免生成乱码
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //String user = request.getParameter("user");//获取username框里的内容
        //String user = request.getParameter("city");//获取username框里的内容
        //String password = request.getParameter("password");//获取password框里的内容

        // 获取表单数据
        //String action = request.getParameter("action");
        String city = request.getParameter("city");
        // 打印获取到的数据（仅用于调试）
        //System.out.println("Action: " + action);
        System.out.println("City: " + city);
        Jdbc jdbc = new Jdbc();
        ArrayList<String> data = jdbc.query(city);
        // 将 JSON 字符串写入响应
        out.print(data);
        System.out.println(data);
        // 关闭 PrintWriter
        out.close();

    }
/*
    @Override

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException , IOException{
        //System.out.println("first servlet 被访问了");
        Jdbc jdbc = new Jdbc();
        jdbc.query();

    }
*/
}
