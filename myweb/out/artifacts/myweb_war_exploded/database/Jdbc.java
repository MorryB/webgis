package database;

import java.sql.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

public class Jdbc {
    private class Point {
        String id;
        String date;
        String place;
        String member;
        String geojson;
    }

    public ArrayList<String> query(String cityName){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        //String json = "404";
        Gson gson = new Gson();
        ArrayList<String> resultList = new ArrayList<>();

        try {
            //1.注册驱动
            Class.forName("org.postgresql.Driver");
            //2.获取连接
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/webgis","postgres","bhm076");
            System.out.println("connect database successfully!");
            //3.获取sql对象
            stmt = conn.createStatement();
            //4.定义sql
            String sql = "SELECT place, member, ST_AsGeoJSON(geom) AS geojson FROM groupfp WHERE place LIKE '%" + cityName + "%'";
            //5.执行sql
            rs = stmt.executeQuery(sql);//查询语句
            //6.遍历输出结果
            while(rs.next()){
                //获取数据
                Point point = new Point();
                point.place = rs.getString("place");
                point.member = rs.getString("member");
                point.geojson = rs.getString("geojson");
                //System.out.println("地点:"+place+" geojson:"+json);
                resultList.add(gson.toJson(point));
            }
            //7.释放资源
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return resultList;
    }
    public void addFeatures(String id, String member, String date, String place){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        //String json = "404";
        Gson gson = new Gson();
        ArrayList<String> resultList = new ArrayList<>();


        // JDBC 连接和插入数据
        try {
            //1.注册驱动
            //Class.forName("org.postgresql.Driver");
            //2.获取连接
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/webgis","postgres","bhm076");
            System.out.println("addFeatures: connect database successfully!");
            //3.获取sql对象
            stmt = conn.createStatement();
            //4.定义sql
            String sql = "INSERT INTO your_table (id, member, date, place) VALUES (?, ?, ?, ?)";
            //5.执行sql
            rs = stmt.executeQuery(sql);//查询语句

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, member);
            statement.setString(3, date);
            statement.setString(4, place);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public String provSta(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        //String json = "404";
        Gson gson = new Gson();
        String jsonString = null;
        // JDBC 连接和插入数据
        try {
            //1.注册驱动
            Class.forName("org.postgresql.Driver");
            //2.获取连接
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/webgis","postgres","bhm076");
            System.out.println("provSta: connect database successfully!");
            //3.获取sql对象
            stmt = conn.createStatement();
            //4.定义sql
            String sql = "SELECT json_agg(json_data) AS provSta FROM (SELECT json_build_object('value', COUNT(groupfp.id), 'name', china.name) AS json_data FROM china JOIN groupfp ON ST_Contains(china.geom, groupfp.geom) GROUP BY china.name) AS subquery;";
            /*
            SELECT json_agg(json_data) AS provSta
            from(
                SELECT json_build_object('value', COUNT(groupfp.id), 'name', china.name) AS json_data
                FROM china
                JOIN groupfp
                ON ST_Contains(china.geom, groupfp.geom)
                GROUP BY china.name
            )AS subquery;
            */
            //5.执行sql
            rs = stmt.executeQuery(sql);
            System.out.println(rs);

            // 提取ResultSet中的数据
            if (rs.next()) {
                jsonString = rs.getString("provSta");
            }
            return jsonString;

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            // 处理ClassNotFoundException异常的逻辑...
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            // 在finally块中关闭资源
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

}
