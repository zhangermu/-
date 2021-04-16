package com.controller;

import com.pojo.DeptBean;
import com.service.DeptService;
import com.utils.Page;
import com.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService deptService;
    @RequestMapping("/getDeptListConn")
    public Page<DeptBean> getDeptListConn(@RequestBody DeptBean deptBean,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "3") Integer pageSize){
        return deptService.getDeptListConn(deptBean,pageNum,pageSize);

    }
    @RequestMapping("/saveDeptPost")
    public ResultInfo saveDeptPost(Long deptid,@RequestBody Long[] postids){
        try {
            deptService.saveDeptPost(deptid,postids);
            System.out.println("controller===:"+deptid+postids);
            return new ResultInfo(true,"编辑成功");
        }catch (Exception e){
            return new ResultInfo(false,"编辑失败");
        }
    }
    @RequestMapping("/getDeptByDeptId")
    public DeptBean getDeptByDeptId(Long deptid){
        return deptService.getDeptByDeptId(deptid);
    }

}
