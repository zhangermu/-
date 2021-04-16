package com.controller;

import com.pojo.MeunBean;
import com.pojo.UserBean;
import com.service.UserService;
import com.utils.Page;
import com.utils.ResultInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 给用户分配职位
     * @param
     * @return
     */
    @RequestMapping("/getUserInfo")
    public UserBean getUserInfo(Long id){
        System.out.println("用户分配controller:"+id);
    return userService.getUserInfo(id);
    }

    /**
     * 登錄
     * @param ub
     * @return
     */
    @RequestMapping("/getLogin")
    public ResultInfo getLogin(@RequestBody UserBean ub , HttpServletRequest request){
      UserBean userBean =  userService.getLogin(ub);
      if(userBean==null){
        return new ResultInfo(false,"用户名或密码错误");
      }else{
            request.getSession().setAttribute("ub",userBean);
          return new ResultInfo(true,"登陆成功");
      }

    }

    /**
     * 普通全查
     * @return
     */
    @RequestMapping("/getUserList")
    public List<UserBean> getUserList(){
        return userService.getUserList();
    }

    /**
     * 分页模糊全查
     * @param ub
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/getUserListConn")
    public Page<UserBean> getUserListConn(@RequestBody UserBean ub, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize){
        return userService.getUserListConn(ub,pageNum,pageSize);
    }

    @RequestMapping("/getMeunList")
    public List<MeunBean> getMeunList(HttpServletRequest request){
        //谁登陆，查询出不是按钮的菜单
        UserBean ub = (UserBean) request.getSession().getAttribute("ub");
        //查询这个用户用了哪些url
        Set<String>urls = userService.getUserMeunUrlsById(ub);
        request.getSession().setAttribute("urls",urls);
        return userService.getMeunList(ub);

    }
    @RequestMapping("/getUserById")
    public  UserBean getUserById(Long id){
        return userService.getUserById(id);
    }


    @RequestMapping("/saveUserDept")
    public ResultInfo saveUserDept( Long id ,@RequestBody Long[] deptids){
        try {
            userService.saveUserDept(id,deptids);
            System.out.println("Controller=================:"+id+deptids);
            return new ResultInfo(true,"选择成功");
        }catch (Exception e){
            return new ResultInfo(false,"选择失败");
        }
    }
    @RequestMapping("/saveUserPost")
    public ResultInfo saveUserPost(@RequestBody UserBean userBean){
        try {
            userService.saveUserPost(userBean);
            System.out.println("添加controller:"+userBean);
            return new ResultInfo(true,"编辑成功");
        }catch (Exception e){
            return  new ResultInfo(false,"编辑失败");
        }

    }

}
