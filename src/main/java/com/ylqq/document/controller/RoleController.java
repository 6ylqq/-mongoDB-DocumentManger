package com.ylqq.document.controller;

import com.ylqq.document.pojo.Function;
import com.ylqq.document.pojo.Role;
import com.ylqq.document.service.FunctionService;
import com.ylqq.document.service.RoleService;
import com.ylqq.document.service.impl.FunctionServiceImpl;
import com.ylqq.document.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author ylqq
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    /**
     * 角色Service
     */
    @Autowired
    private RoleServiceImpl roleService;

    /**
     * 功能Service
     */
    @Autowired
    private FunctionServiceImpl functionService;

    /**
     * 更新权限
     * @param map
     * @param roleid 角色id
     * @param funids 用户选中的权限id
     * @return
     */
    @RequestMapping("/updateRoleRight")
    public String updateRoleRight(Map<String, Object> map, Integer roleid, Integer funids[]) {

        //查出角色信息
        Role role = roleService.selectByPrimaryKey(roleid);

        //修改权限列表
        boolean result = roleService.updateRoleright(roleid, funids);
        map.put("result", result);

        if (result) {
            map.put("msg", "修改角色[" + role.getRoleName() + "]的权限信息成功！");
        } else {
            map.put("msg", "修改角色[" + role.getRoleName() + "]的权限信息失败！");
        }
        return "forward:/role/roles";
    }

    /**
     * 进入权限页面
     * @return
     */
    @RequestMapping("/toRoleRight")
    public String toRoleRight(Map<String, Object> map, Integer roleid) {

        //得到权限列表
        Role role = roleService.selectByPrimaryKey(roleid);
        map.put("role", role);

        //得到所有功能信息
        List<Function> functions = functionService.selectByKeyRoleId(roleid);
        map.put("functionList", functions);

        return "role/roleright";
    }

    /**
     * 进入修改页面
     * @return
     */
    @RequestMapping("/toModify")
    public String toModify(Map<String, Object> map, Integer roleid) {

        Role role = roleService.selectByPrimaryKey(roleid);
        map.put("role", role);

        return "role/rolemodify";
    }

    /**
     * 进入添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "role/roleadd";
    }

    /**
     * 修改角色
     * @return
     */
    @RequestMapping("/modifyRole")
    public String modifyRole(Map<String, Object> map, @Valid Role role, BindingResult bindingResult) {

        //检查校验是否出错
        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            ObjectError oe = list.get(0);

            //校验失败信息
            map.put("result", false);
            map.put("msg", oe.getDefaultMessage() + "修改角色[" + role.getRoleName() + "]失败！");
        } else {
            //功能名称查重
            boolean hasSame = roleService.hasSameRole(role.getRoleId(), role.getRoleName()).size()>0;

            if (hasSame == false) {
                //功能名称不重复

                //保存功能信息，拿到修改操作的结果
                boolean result = roleService.updateByPrimaryKeySelective(role)>0;
                map.put("result", result);

                //根据操作结果生成提示信息
                if (result == true) {
                    //修改成功且无重复

                    map.put("msg", "修改角色[" + role.getRoleName() + "]成功！");
                } else {

                    map.put("msg", "修改角色[" + role.getRoleName() + "]失败！");
                }

            } else {
                map.put("result", false);
                map.put("msg", "角色名称[" + role.getRoleName() + "]重复，修改角色失败！");
            }
        }

        return "forward:/role/roles";

    }

    /**
     * 添加角色
     * @return
     */
    @RequestMapping("/addRole")
    public String addRole(Map<String, Object> map, @Valid Role role, BindingResult bindingResult) {

        //检查校验是否出错
        if(bindingResult.hasErrors()){
            List<ObjectError> list = bindingResult.getAllErrors();
            ObjectError oe = list.get(0);

            //校验失败信息
            map.put("result", false);
            map.put("msg", oe.getDefaultMessage() + "添加角色[" + role.getRoleName() + "]失败！");
        } else {
            //功能名称查重
            boolean hasSame = roleService.hasSameRole(null, role.getRoleName()).size()>0;

            if (hasSame == false) { //功能名称不重复

                //保存功能信息，拿到添加操作的结果
                boolean result = roleService.insertSelective(role)>0;
                map.put("result", result);

                //根据操作结果生成提示信息
                if (result == true) {  //添加成功且无重复

                    map.put("msg", "添加角色[" + role.getRoleName() + "]成功！");
                } else {

                    map.put("msg", "添加角色[" + role.getRoleName() + "]失败！");
                }

            } else {
                map.put("result", false);
                map.put("msg", "角色名称[" + role.getRoleName() + "]重复，添加角色失败！");
            }
        }

        return "forward:/role/roles";

    }

/*分页函数*/

}
