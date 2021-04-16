package com.service;

import com.pojo.MeunBean;
import com.pojo.PostBean;
import com.utils.Page;

import java.util.List;

public interface PostService {
    Page<PostBean> getPostListConn(PostBean postBean, Integer pageNum, Integer pageSize);

    List<MeunBean> getMeunListById(Long id);

    void savePostMeun(Long postid, Long[] ids);
}
