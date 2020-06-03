package cn.itcast.service;

import cn.itcast.domain.PageBean;
import cn.itcast.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户管理业务接口
 */
public interface UserService {
    /**
     * 查询所有用户信息
     * @return
     */
    public List<User> findAll();
    public User login(User user);

    /**
     * 保存user
     * @param user
     */
    void addUser(User user);

    /**
     * 根据id删除
     * @param id
     */
    void deleteUser(String id);


    /**
     * 根据Id查询
     * @param id
     * @return
     */
    User findUserById(String id);

    /**
     * 更新数据
     * @param user
     */
    void updateUser(User user);

    /**
     * 删除选中用户
     * @param uids
     */
    void delSelectedUser(String[] uids);

    /**
     * 分页查询,条件查询
     * @param currentPage
     * @param rows
     * @param condition
     * @return
     */
    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
