package com.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 创作时间：2021/4/8 14:28
 * 作者：李增强
 */
@Data
public class ResultInfo implements Serializable {
    private boolean flag;
    private String msg;

    public ResultInfo() {
    }

    public ResultInfo(boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }
}
