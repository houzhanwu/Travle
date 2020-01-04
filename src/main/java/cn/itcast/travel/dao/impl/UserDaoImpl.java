package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class UserDaoImpl  implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *根据用户名查询用户信息
     * @param username
     * @return
     */
    @Override
    public User findByUserName(String username) {
        User user = null;
        try{
            //定义sql
            String sql = "select * from tab_user where Username = ?";
            //执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){
            e.printStackTrace();
        }

        return user;
    }

    /**
     * 保存用户到数据库
     * @param user
     */
    @Override
    public void save(User user) {
        try{
            //定义sql
            String sql = "insert into  tab_user(username,password,name,birthday,sex,telephone,email,status,code) values (?,?,?,?,?,?,?,?,?)";
            //执行sql
            template.update(sql, user.getUsername(), user.getPassword(),
                    user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),user.getStatus(),user.getCode());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据code（激活码）查找用户
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {

        User user = null;
        try{
            //定义sql
            String sql = "select * from tab_user where code =?";
            //执行sql
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 修改用户激活状态
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status ='Y' where uid =?";
        template.update(sql,user.getUid());
    }

    /**
     * 判断账户或密码是否正确
     * @return
     */
    @Override
    public User findByUserNameAndPassord(String username,String password) {
        User u =null;
        try{
            //定义sql
            String sql = "select * from tab_user where username=? and password=?";
            //执行sql
            u = template.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),username,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return u;
    }
}
