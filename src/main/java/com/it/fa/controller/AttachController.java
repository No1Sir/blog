package com.it.fa.controller;

import com.github.pagehelper.PageInfo;
import com.it.fa.constant.ErrorConstant;
import com.it.fa.constant.Types;
import com.it.fa.model.Articles;
import com.it.fa.model.Attach;
import com.it.fa.model.User;
import com.it.fa.service.attach.IAttachService;
import com.it.fa.service.service.QiNiuService;
import com.it.fa.utils.APIResponse;
import com.it.fa.utils.Tales;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.it.fa.constant.Types.IMAGE;

@Controller
@RequestMapping("/admin/attach")
public class AttachController {
    @Resource
    private QiNiuService qiNiuService;
    @Resource
    private IAttachService attachService;

    @GetMapping("")
    public String toUploadPage(@RequestParam(name = "page",required = false,defaultValue = "1")int page,
                               @RequestParam(name = "limit",required = false,defaultValue = "5")int limit,
                               HttpServletRequest request) {
        PageInfo<Attach> attaches = attachService.findAll(page,limit);
        request.setAttribute("attachs", attaches);
        return "admin/attach";
    }
    @ResponseBody
    @PostMapping(value = "upload")
    public APIResponse upLoadFile(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam(name = "file") MultipartFile[] files) {
        //文件上传
        try {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            for (MultipartFile file : files) {
                String filename = Tales.getFileKey(file.getOriginalFilename()).replaceFirst("/", "");
                qiNiuService.upload(file, filename);
                Attach attach = new Attach();
                attach.setFtype(Tales.isImage(file.getInputStream()) ? Types.IMAGE.getType() : Types.FILE.getType());
                attach.setFname(filename);
                User u = (User) request.getSession().getAttribute("userInfo");
                attach.setAuthorId(u.getUid());
                //外链访问
                attach.setFkey(qiNiuService.cdn + "/" + filename);
                attachService.add(attach);
            }
            return APIResponse.success();
        } catch (IOException e) {
            e.printStackTrace();
            return APIResponse.fail(ErrorConstant.Att.ADD_NEW_ATT_FAIL);
        }
    }
}

