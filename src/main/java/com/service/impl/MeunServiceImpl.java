package com.service.impl;

import com.mapper.MeunMapper;
import com.pojo.MeunBean;
import com.pojo.MeunBeanExample;
import com.service.MeunService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class MeunServiceImpl implements MeunService {
    @Resource
    private MeunMapper meunMapper;

    //查看下一级
    @Override
    public List<MeunBean> getMeunListByPid(Long pid) {
        //因为pid不是主键所以不能主键查询，需要条件查询
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        return meunMapper.selectByExample(example);
    }
//增删
    @Override
    public void saveMeun(MeunBean meunBean) {
        if(meunBean!=null){
            if(meunBean.getId()!=null){
                /**
                 * 这个修改是遇到实体类中字段值为空的时候，不修改，一般密码等
                 */
                meunMapper.updateByPrimaryKeySelective(meunBean);
                /**
                 * 实体类传递过来是什么，把数据库中全部修改为实体类中的值，即便实体类中为空，也把数据库中修改为空
                 * meunMapper.updateByPrimaryKey(meunBean);
                 */

            }else{
                meunMapper.insertSelective(meunBean);
            }
        }
    }

    //删除树形菜单
    @Override
    public void deleteMeunById(Long id) {
        /**
         * 按照id等于pid去查询的方法，在service中已经有了，我们可以直接使用吗，当然使用了也没什么大问题，
         * 但是不符合面向对象的设计原则：单一职责原则，开闭原则，接口隔离原则，依赖倒置原则，里氏替换原则
         */
        getMeunListByPidToDelete(id);
        for (Long id1 : ids) {
            meunMapper.deleteByPrimaryKey(id1);
        }
    }
    List<Long> ids=new ArrayList<>();
    private void getMeunListByPidToDelete(Long pid) {
        ids.add(pid);
        //因为pid不是主键所以不能主键查询，需要条件查询
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MeunBean> list = meunMapper.selectByExample(example);
        /**
         * 和我们阶乘的思想有点不一样，阶乘是到了最后满足条件了结束
         * 不组满条件的时候，一直执行
         *
         * 只要满足条件就执行，不满足条件了结束。
         *
         */
        if(list!=null&&list.size()>=1){
            for (MeunBean bean : list) {
                getMeunListByPidToDelete(bean.getId());
            }
        }

    }
}
