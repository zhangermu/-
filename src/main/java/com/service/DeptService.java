package com.service;

import com.pojo.DeptBean;
import com.utils.Page;

public interface DeptService {
    Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize);

    void saveDeptPost(Long deptid, Long[] postids);

    DeptBean getDeptByDeptId(Long deptid);
}
