package com.controller;

import com.pojo.MeunBean;
import com.pojo.PostBean;
import com.service.PostService;
import com.utils.Page;
import com.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Resource
    private PostService postService;

    @RequestMapping("/getPostListConn")
    public Page<PostBean> getPostListConn(@RequestBody PostBean postBean,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "3") Integer pageSize){
        return postService.getPostListConn(postBean,pageNum,pageSize);

    }
    @RequestMapping("/savePostMeun")
    public ResultInfo savePostMeun(Long postid,@RequestBody Long[] ids){
        try {
            postService.savePostMeun(postid,ids);
            return new ResultInfo(true, "保存成功");
        }catch (Exception e){
            return new ResultInfo(false, "保存失败");
        }
    }

    @RequestMapping("/getMeunListById")
    public List<MeunBean>getMeunListById(Long id){
        return postService.getMeunListById(id);
    }
}
