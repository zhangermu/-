package com.pojo;

import java.util.List;

public class DeptBean {
    private Long id;

    private String deptname;

    private Long[] postids;
    private List<PostBean> plist;

    public Long[] getPostids() {
        return postids;
    }

    public void setPostids(Long[] postids) {
        this.postids = postids;
    }

    public List<PostBean> getPlist() {
        return plist;
    }

    public void setPlist(List<PostBean> plist) {
        this.plist = plist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }
}