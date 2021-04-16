package com.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.DeptMapper;
import com.mapper.MeunMapper;
import com.mapper.UserMapper;
import com.pojo.*;
import com.service.UserService;
import com.utils.MD5key;
import com.utils.Page;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MeunMapper meunMapper;
    @Resource
    private DeptMapper deptMapper;
/*    @Resource
    private RedisUtil redisUtil;*/

    @Override
    public List<UserBean> getUserList() {
        return userMapper.selectByExample(null);
    }

    @Override
    public Page<UserBean> getUserListConn(UserBean ub, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        UserBeanExample example = new UserBeanExample();
        UserBeanExample.Criteria criteria = example.createCriteria();
        if(ub!=null){

            if(ub.getUname()!=null&& ub.getUname().length()>=1){
                criteria.andUnameLike("%"+ub.getUname()+"%");
            }
            if(ub.getAge()!=null){
                criteria.andAgeGreaterThanOrEqualTo(ub.getAge());
            }
            if(ub.getAge()!=null){
                criteria.andAgeLessThanOrEqualTo(ub.getEage());
            }
        }

        List<UserBean> list = userMapper.selectByExample(example);
        PageInfo<UserBean> pageInfo = new PageInfo<>(list);
        Long total = pageInfo.getTotal();
        Page page = new Page(pageInfo.getPageNum()+"",total.intValue(),pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }
    @Override
    public List<MeunBean> getMeunList(UserBean ub) {
        if(ub!=null){
           List<MeunBean> list = userMapper.getUserMeunListById(ub.getId());
           /* List<MeunBean> list = null;
            //从缓存中获取用户列表
            Object userListCache = redisUtil.getObject(ub.getId());

            //判断缓存中是否存在
            if (userListCache != null) {//不空，则强转返回
                System.out.println("redis中存在，直接返回");
                list = (List<MeunBean>) userListCache;

            }else{
                System.out.println("redis缓存中不存在，从数据库中取出，并且放入缓存");
                //查询数据库，取出
                list = userMapper.getUserMeunListById(ub.getId());
                //放入redis缓存
                redisUtil.putObject(ub.getId(), list);
            }*/
            return list;
        }
        return null;

    }


    /*@Override
    public List<MeunBean> getMeunList(UserBean ub) {
        if(ub!=null){
            //先展示不是按钮的菜单
            MeunBeanExample example = new MeunBeanExample();
            MeunBeanExample.Criteria criteria = example.createCriteria();
            criteria.andIsbuttonEqualTo(0);
            List<MeunBean> list = meunMapper.selectByExample(example);
            return list;
        }
        return null;

    }*/

    public List<MeunBean> getMeunList2() {
        Long[] ids = {1L,2L,3L};
        List<MeunBean> list = meunMapper.selectByExample(null);
        for (Long id : ids) {
            for (MeunBean bean : list) {
                if(id.equals(bean.getId())){
                    bean.setChecked(true);
                    break;
                }
            }
        }
        return list;
    }

//根据用户id查询部门
    @Override
    public UserBean getUserById(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        /**
         * 去查询这个用户有那些部门，咱们的设计是一对多，所以页面待会儿要不用复选框接收，
         * 要是不用下拉框接收，但是下拉框要支持多选
         * 需要中间表
         */
        //部门回显
        Long[] deptids = userMapper.getUserDeptidsById(id);
        userBean.setDeptids(deptids);
        //感觉是部门全查，然后放入user
        List<DeptBean> deptBeanList = deptMapper.selectByExample(null);
        userBean.setDlist(deptBeanList);
        return userBean;
    }

    @Override
    public void saveUserDept(Long id, Long[] deptids) {
        /**
         * 修改科室，要把该用户原来的科室删掉，再次新增
         *
         * 还需要把原来的职位也删除掉（科室都变了，职位没了）
         */
        userMapper.deleteUserPostById(id);
        userMapper.deleteUserDeptById(id);
        if(deptids!=null&& deptids.length>=1){
            for (Long deptid : deptids) {
                    userMapper.insertUserDept(id,deptid);
            }
        }
    }

    @Override
    public UserBean getUserInfo(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        /**
         * 开始去查询这个用户有没有部门
         */
        List<DeptBean> dlist = userMapper.getUserDeptById(id);
        System.out.println("查询业务:"+dlist);

        /**
         * 开始循环部门，查询部门里面的职位
         */
        if(dlist!=null&&dlist.size()>=1){
            for (DeptBean deptBean : dlist) {
                List<PostBean> plist = deptMapper.getDeptPostList(deptBean.getId());
                System.out.println("查询业务:"+plist);
                Long[] postids = deptMapper.getUserPostByIdAndDeptid(id,deptBean.getId());
                System.out.println("查询业务:"+postids);
                deptBean.setPostids(postids);
                deptBean.setPlist(plist);
            }
        }
        userBean.setDlist(dlist);

        return userBean;
    }

    @Override
    public void saveUserPost(UserBean userBean) {
        /**
         * 先去删除用户的职位
         */
        if(userBean!=null){
            userMapper.deleteUserPostById(userBean.getId());
            System.out.println("删除业务1:"+userBean.getId());
            if(userBean.getDlist()!=null&&userBean.getDlist().size()>=1){
                for (DeptBean  deptBean : userBean.getDlist()) {
                    if(deptBean.getPostids()!=null&&deptBean.getPostids().length>=1){
                        for (Long postid : deptBean.getPostids()) {
                            System.out.println("添加业务2:"+deptBean.getPostids());
                            userMapper.saveUserPost(userBean.getId(),postid);
                            System.out.println("最终添加3:"+userBean.getId()+postid);
                        }
                    }
                }
            }
        }

    }

    @Override
    public UserBean getLogin(UserBean ub) {
        //先用户用户名或者手机号，都是用用户名接收的，有可能用户输入的手机号，都是strint类型，无所谓
        if(ub!=null){
            List<UserBean> list = userMapper.getLongin(ub);
            if(list!=null&&list.size()==1){
                //到了这里表示用用户名或者手机号码查询到了
                //开始比对密码，比对密码之前需要加盐加密
                //加密算法有很多，常用的md5，bscript等
                UserBean userBean = list.get(0);
                //页面用户输入的密码，进行加密加盐后和数据库里面的密码进行比较，相等正确，不相等就错误
                String pwd = ub.getPwd();
                //也就是这里的加盐加密规则和注册的要一致，就好了，都是一个人负责的
                pwd = userBean.getPwdsalt()+pwd+userBean.getPwdsalt();
                MD5key md5key = new MD5key();
                String spwd = md5key.getkeyBeanofStr(pwd);
                if(spwd.equals(userBean.getPwd())){
                    return userBean;
                }
            }
        }
        return null;
    }

    @Override
    public Set<String> getUserMeunUrlsById(UserBean ub) {
        if(ub!=null){
            Set<String> urls = userMapper.getUserMeunUrlsById(ub.getId());
            return  urls;
        }

        return null;
    }

    @Test
    public void test(){
            String pwd = "123";
            pwd = "1234"+pwd+"1234";
        MD5key md5key = new MD5key();
        String spwd = md5key.getkeyBeanofStr(pwd);
        System.out.println(spwd);

    }

}
