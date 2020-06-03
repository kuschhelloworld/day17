package cn.itcast.dao.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.domain.User;
import cn.itcast.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
        /**
         * 使用JDBC操作数据库
         */
        String sql = "select * from user";
        List<User> userList = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return userList;
    }
    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        String sql = "select * from user where username = ? and password = ?";
        try {
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(User user) {
        //添加语句
        String sql = "insert into user values (null,?,?,?,?,?,?,null,null)";
        template.update(sql, user.getName(), user.getGender(), user.getAge(),
                user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void deleteUser(int id) {
        //删除语句
        String sql = "delete from user where id = ?";
        template.update(sql, id);
    }

    @Override
    public User findById(int id) {
        String sql = "select * from user where id=?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sql = "update user set name=?, gender=?, age=? ,address=? ,qq=?, email=? where id=?";
        template.update(sql, user.getName(), user.getGender(), user.getAge(),
                user.getAddress(), user.getQq(), user.getEmail(), user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
//        定义模板SQL
        String sql = "select count(*) from user where 1 = 1  ";
        //拼接sql
        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        //定义参数集合
        List<Object> params = new ArrayList<Object>();

        for (String key : keySet) {
            //排除分页参数
            if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;//结束当前循环，开始下一次
            }
            //获取value
            String values = condition.get(key)[0];
            //判断value是否有值
            if (values != null && !"".equals(values)) {
                //有值
                sb.append("  and " + key + " like ?  ");
                params.add("%" + values + "%");//加?条件的值
            }
        }
//        System.out.println("----------UserDaoImpl中------findTotalCount展示拼接的Sql和参数数组集合params------");
//        System.out.println("sql:::"+sb.toString());//对应的SQL语句
//        System.out.println("params:::"+params);//参数
        Integer int1 = template.queryForObject(sb.toString(), Integer.class, params.toArray());
        return int1;
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from user where 1 = 1 ";
        //拼接sql
        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        //定义参数集合
        List<Object> params = new ArrayList<Object>();

        for (String key : keySet) {
            //排除分页参数
            if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;//结束当前循环，开始下一次
            }
            //获取value
            String values = condition.get(key)[0];
            //判断value是否有值
            if (values != null && !"".equals(values)) {
                //有值
                sb.append("  and " + key + " like ?  ");
                params.add("%" + values + "%");//加?条件的值
            }
        }
        //添加分页查询参数值
        sb.append(" limit ?,?");
        params.add(start);
        params.add(rows);

//        System.out.println("----------UserDaoImpl中------findByPage展示拼接的Sql和参数数组集合params------");
//        System.out.println("sql:"+sb.toString());
//        System.out.println("params:"+params);

        return template.query(sb.toString(), new BeanPropertyRowMapper<User>(User.class), params.toArray());
    }
}
