package com.mapper;

import com.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserMapper {
    long countByExample(UserBeanExample example);

    int deleteByExample(UserBeanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserBean record);

    int insertSelective(UserBean record);

    List<UserBean> selectByExample(UserBeanExample example);

    UserBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByExample(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByPrimaryKeySelective(UserBean record);

    int updateByPrimaryKey(UserBean record);

    Long[] getUserDeptidsById(@Param("id") Long id);

    void deleteUserPostById(@Param("id") Long id);

    void deleteUserDeptById(@Param("id") Long id);

    void insertUserDept(@Param("userid") Long userid,@Param("deptid") Long deptid);

    List<DeptBean> getUserDeptById(@Param("id") Long id);

    void saveUserPost(@Param("userid") Long userid,@Param("postid") Long postid);

    List<UserBean> getLongin(UserBean ub);

    List<MeunBean> getUserMeunListById(@Param("userid") Long userid);

    Set<String> getUserMeunUrlsById(@Param("userid") Long userid);
}