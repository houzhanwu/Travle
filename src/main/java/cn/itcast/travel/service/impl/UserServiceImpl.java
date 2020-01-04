package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.List;

public class UserServiceImpl implements UserService {

    UserDao dao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        //1.根据用户名查找用户对象
        User u = dao.findByUserName(user.getUsername());
        if (u != null){
            //用户名存在, 注册失败
            return false;
        }
        //2.用户名不存在，保存信息
        //2.1设置激活码， 唯一字符串
        user.setCode(UuidUtil.getUuid());   //调用UuidUtil 工具类获取字符串
        //2.2设置激活状态
        user.setStatus("N");
        dao.save(user);

        //3.激活邮件发送
        String content = "<a href ='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail("1747644936@qq.com",content,"激活邮件");

        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user= dao.findByCode(code);
        if (user != null){
            //2.调用dao的修改激活的方法
            dao.updateStatus(user);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 用户登陆
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        //查找用户账号密码
        User u =null;
        try{
            u = dao.findByUserNameAndPassord(user.getUsername(),user.getPassword());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  u;
    }


}
