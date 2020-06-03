package cn.itcast.util;

/*
* Druid连接池的工具类
* */

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
    //定义一个成员变量
    private static DataSource ds;
    static {
        //加载配置文件
        Properties pro = new Properties();
        try {
            //注意这句话，写错无数遍
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            pro.load(is);
            ds = DruidDataSourceFactory.createDataSource(pro);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接方法
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    //释放资源
    public static void close(Statement stmt, Connection conn) {
        close(null,stmt,conn);

    }

    public static void close(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();//释放连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();//释放连接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
                try {
                    conn.close();//归还连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }

    //获取连接池的方法
    public static DataSource getDataSource(){
        return ds;
    }

}
