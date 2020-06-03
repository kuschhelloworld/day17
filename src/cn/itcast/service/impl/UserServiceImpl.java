package cn.itcast.service.impl;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;
import cn.itcast.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public List<User> findAll() {
        //调用DAO完成查询
        List<User> all = userDao.findAll();
        return all;
    }

    @Override
    public User login(User user) {
        try {
            User userByUsernameAndPassword = userDao.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
            return userByUsernameAndPassword;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addUser(User user) {
        userDao.add(user);
    }

    @Override
    public void deleteUser(String id) {
        userDao.deleteUser(Integer.parseInt(id));
    }

    @Override
    public User findUserById(String id) {
        User byId = userDao.findById(Integer.parseInt(id));
        return byId;
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void delSelectedUser(String[] uids) {
        if (uids != null && uids.length > 0) {
            for (String uid : uids) {
                userDao.deleteUser(Integer.parseInt(uid));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        if(currentPage<=0){
            currentPage=1;
        }

        //创建一个空的PageBean
        PageBean<User> pb = new PageBean<User>();
        //设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //调用DAO查询总记录数
        int totalCount = userDao.findTotalCount(condition);
        pb.setTotalCount(totalCount);

        //调用DAO查询List集合
        int start = (currentPage - 1) * rows;//计算开始的记录索引
        List<User> list = userDao.findByPage(start,rows,condition);
        pb.setList(list);
        //计算总页码
        int totalPage = (totalCount % rows) == 0 ? (totalCount / rows) : (totalCount / rows) + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }
}
