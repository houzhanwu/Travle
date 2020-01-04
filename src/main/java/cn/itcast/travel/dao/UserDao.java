package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;


public interface UserDao {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public User findByUserName(String username);

    /**
     * 保存用户到数据库
     * @param user
     */
    public void save(User user);

    /**
     * 根据code（激活码）查找用户
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 修改用户激活状态
     * @param user
     */
    void updateStatus(User user);


    /**
     * 判断用户账号，密码是否正确
     * @return
     */
    User findByUserNameAndPassord(String username,String password);
}
