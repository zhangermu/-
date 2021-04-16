package com.service;

import com.pojo.MeunBean;

import java.util.List;

public interface MeunService {
    List<MeunBean> getMeunListByPid(Long pid);

    void saveMeun(MeunBean meunBean);

    void deleteMeunById(Long id);
}
