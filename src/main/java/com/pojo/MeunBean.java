package com.pojo;

import java.io.Serializable;

public class MeunBean implements Serializable {
    private Long id;

    private Long pid;

    private String mname;

    private String url;

    private String target;

    private String icon;

    private Integer isbutton;

    //回显使用的，在后台回显，比较方便，数据库不需要
    private Boolean checked = false;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getIsbutton() {
        return isbutton;
    }

    public void setIsbutton(Integer isbutton) {
        this.isbutton = isbutton;
    }
}