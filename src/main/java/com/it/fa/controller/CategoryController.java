package com.it.fa.controller;

import com.it.fa.model.Label;
import com.it.fa.service.label.ILabelService;
import com.it.fa.utils.APIResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Resource
    private ILabelService labelService;
    @GetMapping(value = {"","/"})
    public String toCategoryPage(HttpServletRequest request){
        List<Label> categories = labelService.findAllByCategory();
        List<Label> tags = labelService.findAllByTag();
        request.setAttribute("categories",categories);
        request.setAttribute("tags",tags);
        return "admin/category";
    }
    @ResponseBody
    @PostMapping("/save")
    public APIResponse save(String cname){
        int i = labelService.addCategory(cname);
        if(i>0){
            return APIResponse.success();
        }
        return APIResponse.fail("添加分类失败!");
    }
    @ResponseBody
    @PostMapping("/delete")
    public APIResponse save(Integer lid){
        int i = labelService.deleteLabelByLid(lid);
        if(i>0){
            return APIResponse.success();
        }
        return APIResponse.fail("删除分类失败!");
    }
}
