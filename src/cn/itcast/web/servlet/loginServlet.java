package cn.itcast.web.servlet;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
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

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        //获取数据
        //对验证码核对操作
        //封装User对象
        //调用Service查询
        //判断是否登陆成功
        String verifycode = request.getParameter("verifycode"); //输入框验证码
        HttpSession session1 = request.getSession();
        String checkCode_session = (String) session1.getAttribute("checkCode_session");//从Session里面拿到生成的验证码
        session1.removeAttribute("checkCode_session");//保证验证码一次性，拿完就删除
        if (!verifycode.equalsIgnoreCase(checkCode_session)) {
            //验证码不正确
            request.setAttribute("login_msg", "验证码错误!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;//下面别执行了，直接返回
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);//封装了User对象
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service = new UserServiceImpl();
        User loginUser = service.login(user);
        if (loginUser == null) {
            //登陆失败
            request.setAttribute("login_msg", "用户名或密码错误！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else {
            //登陆成功，将用户存入Session
            HttpSession session = request.getSession();
            session.setAttribute("user", loginUser);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}
