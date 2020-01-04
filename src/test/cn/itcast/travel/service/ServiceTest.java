package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserServiceImpl;
import org.junit.Test;

public class ServiceTest {

    @Test
    public void test1(){
        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("12345678");
        UserService service = new UserServiceImpl();
        User u = service.login(user);
        System.out.println(u.getStatus()+u.getName());

    }


}
