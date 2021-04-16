package com.service;


import com.pojo.MeunBean;
import com.pojo.UserBean;
import com.utils.Page;

import java.util.List;
import java.util.Set;

public interface UserService {
     List<UserBean> getUserList();

    Page<UserBean> getUserListConn(UserBean ub, Integer pageNum, Integer pageSize);

    List<MeunBean> getMeunList(UserBean ub);

    UserBean getUserById(Long id);

    void saveUserDept(Long id, Long[] deptids);

    UserBean getUserInfo(Long id);

    void saveUserPost(UserBean userBean);

    UserBean getLogin(UserBean ub);

    Set<String> getUserMeunUrlsById(UserBean ub);
}
