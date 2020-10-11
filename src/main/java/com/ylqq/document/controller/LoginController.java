package com.ylqq.document.controller;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.pojo.Role;
import com.ylqq.document.pojo.User;
import com.ylqq.document.service.DocumentService;
import com.ylqq.document.service.FunctionService;
import com.ylqq.document.service.RoleService;
import com.ylqq.document.service.UserService;
import com.ylqq.document.service.impl.DocumentServiceImpl;
import com.ylqq.document.service.impl.FunctionServiceImpl;
import com.ylqq.document.service.impl.RoleServiceImpl;
import com.ylqq.document.service.impl.UserServiceImpl;
import com.ylqq.document.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author ylqq
 */
@RestController
public class LoginController {
    /**
     * 用户业务
     */
    @Autowired
    private UserServiceImpl userService;

    /**
     * 角色业务
     */
    @Autowired
    private RoleServiceImpl roleService;

    /**
     * 公文Service
     */
    @Autowired
    private DocumentServiceImpl documentService;

    /**
     * 功能service
     * */
    @Autowired
    private FunctionServiceImpl functionService;
    /**
     * 用户注销
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
        return "redirect:/login.jsp";
    }


    /**
     * 用户登陆
     * @param map 保存结果集
     * @param session 存取用户信息
     * @param loginname 提交的登录名
     * @param password 提交的密码
     * @param code 提交的验证码
     * @return
     * */
    @RequestMapping("/login")
    public String userLogin(Map<String, Object> map, HttpSession session,
                            String loginname, String password, String code){
        //1.首先检查登录名、密码和验证码用户是否都填写了，如果有一样没填写就直接打回去

        if (!StringUtils.hasText(loginname) || !StringUtils.hasText(password)
                || !StringUtils.hasText(code)) {

            //1.1 告诉用户登陆失败，这三个字段都是必填项
            map.put("msg", "登录名、密码和验证码都是必填项！");
            map.put("result", false);

            //1.2 直接跳回登录界面
            return "forward:/login.jsp";
        }

        //检查用户输入的账号是否正确

        //去数据库查询用户名和密码
        //先来加密一下
        String md5pass= DigestUtils.md5DigestAsHex(password.getBytes());
        User user=userService.loginValidate(loginname,md5pass);

        //检查能不能找到
        if (user!=null){
            session.setAttribute("user",user);
            return "forward:/toIndex";
        }else {
            map.put("mas","登录名或密码错误！");
            map.put("resulet",false);
            return "forward:/login.jsp";
        }
    }

    @RequestMapping("/toIndex")
    public String toIndex(Map<String, Object> map, HttpSession session){
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
     *访问欢迎页
     *
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
        return "home";
    }


}
