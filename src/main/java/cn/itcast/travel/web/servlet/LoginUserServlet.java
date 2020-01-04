package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 登陆
 */
@WebServlet("/loginUserServlet")
public class LoginUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //校验验证码
        String check = request.getParameter("check");    //输入的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");   //服务器端生成的验证码
        session.removeAttribute("CHECKCODE_SERVER");               //保证验证码只能使用一次
        if (checkcode_server ==null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            //调用消息类， 记录错误信息
            ResultInfo info = new ResultInfo();
            info.setErrorMsg("验证码错误！！！");
            info.setFlag(false);
            //将info序列化为json对象
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);

            //将json数据写回客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);

        }else{
            //验证码正确
            //获取登陆信息
            Map<String, String[]> map = request.getParameterMap();
            //封装信息
            User user = new User();
            try {
                BeanUtils.populate(user,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //调用service层完成登陆
            UserService service = new UserServiceImpl();
            User u = service.login(user);
            //创建消息类
            ResultInfo info = new ResultInfo();
            if (u == null){
                //用户名或密码错误
                info.setFlag(false);
                info.setErrorMsg("用户名或密码错误");
            }

            if ( u != null && "N".equals(u.getStatus())){
                //用户尚未激活
                info.setFlag(false);
                info.setErrorMsg("您尚未激活");
            }
            if (u != null && "Y".equals(u.getStatus())){
                request.getSession().setAttribute("user",u);//登录成功标记
                //登陆成功
                info.setFlag(true);
            }
            //将info序列化为json数据
            ObjectMapper mapper =  new ObjectMapper();
            String json = mapper.writeValueAsString(info);

            //将json数据写回客户端
            //设置编码格式
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
