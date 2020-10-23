package com.ylqq.document.controller;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.pojo.Role;
import com.ylqq.document.pojo.User;
import com.ylqq.document.service.UserRepository;
import com.ylqq.document.service.impl.DocumentServiceImpl;
import com.ylqq.document.service.impl.FunctionServiceImpl;
import com.ylqq.document.service.impl.RoleServiceImpl;
import com.ylqq.document.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author ylqq
 */
@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private DocumentServiceImpl documentService;
    @Autowired
    private FunctionServiceImpl functionService;

    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @GetMapping("/register")
    public String toRegister(){
        return "register";
    }

    @GetMapping({"/","/index"})
    public String index(){
        return "index";
    }

    /**
     * 用户注销
     *
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {

        //移除user属性
        session.removeAttribute("user");

        //注销Session
        session.invalidate();

        //返回登录界面
        return "redirect:/toLogin";
    }


    /**
     * 用户登陆
     *
     * @param modelAndView       保存结果集
     * @param session   存取用户信息
     * @param loginName 提交的登录名
     * @param password  提交的密码
     * @return
     */
    @RequestMapping("login")
    public String userLogin(HttpSession session,String loginName, String password, ModelAndView modelAndView) {
        //1.首先检查登录名、密码和验证码用户是否都填写了，如果有一样没填写就直接打回

        if (!StringUtils.hasText(loginName) || !StringUtils.hasText(password)) {

            //1.1 告诉用户登陆失败，这三个字段都是必填项
            modelAndView.addObject("msg", "登录名、密码都是必填项！");
            modelAndView.addObject("result", false);

            //1.2 直接跳回登录界面
            return "index";
        }

        //检查用户输入的账号是否正确

        //去数据库查询用户名和密码
        //先来加密一下
        String md5pass = DigestUtils.md5DigestAsHex(password.getBytes());
        User user=userRepository.findByLoginName(loginName);

        //检查能不能找到
        if (user!=null&&user.getPassword().equals(md5pass)) {
            session.setAttribute("user", user);
            return "redirect:/toWelcome";
        } else {
            modelAndView.addObject("mas", "登录名或密码错误！");
            modelAndView.addObject("result", false);
            return "login";
        }
    }

    @RequestMapping("/toIndex")
    public String toIndex(Map<String, Object> map, HttpSession session) {
        //1.从Session中加载出用户的信息
        User user = (User) session.getAttribute("user");

        //2.通过用户信息找到用户的角色信息
        Role role = roleService.selectByPrimaryKey(user.getRoleId());

        //3.通过角色信息查出角色下面的功能
        List<Function> functions = functionService.selectByKeyRoleId(role.getRoleId());
        map.put("functionList", functions);

        return "index";
    }

    /**
     * 访问欢迎页
     */
    @RequestMapping("/toWelcome")
    public String toWelcome(Map<String, Object> map, HttpSession session) {

        //1.从Session中取出用户信息，并得到用户id和角色id
        User user = (User) session.getAttribute("user");
        Integer userId = user.getUserid();
        Integer roleid = user.getRoleId();

        //2.找出要统计的4个数字

        //2.1 找出待处理公文数量
        Long dealcount = null;
        if (roleid == 1 || roleid == 2) {
            dealcount = documentService.selectMyDealCount(userId);
        }

        //2.2 找出审核驳回公文数量
        Long failcount = documentService.selectMyFailCount(userId);

        //2.3 找出待接收公文数量
        Long receivecount = documentService.selectMyCountReceiver(userId);

        //2.4 找出等待审核通过公文数量
        Long waitcount = documentService.selectMyWaitingCount(userId);

        //3 保存查询结果
        map.put("dealcount", dealcount);
        map.put("failcount", failcount);
        map.put("receivecount", receivecount);
        map.put("waitcount", waitcount);

        //4.返回首页
        return "/user/home";
    }


}
