package com.it.fa.controller;

import com.it.fa.model.computer.Server;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/admin/server")
public class BaseController {
    @RequestMapping(value = {"","/"})
    public String getSystemInfo(HttpServletRequest request){
        Server server = new Server();
        server.copyTo();
        request.setAttribute("server",server);
        return "admin/server";
    }
}
