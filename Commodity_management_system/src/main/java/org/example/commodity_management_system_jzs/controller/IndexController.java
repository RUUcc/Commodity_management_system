package org.example.commodity_management_system_jzs.controller;

import jakarta.servlet.http.HttpSession;
import org.example.commodity_management_system_jzs.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主页面控制器
 */
@Controller
public class IndexController {

    @Value("${app.name}")
    private String appName;

    /**
     * 后台管理首页
     */
    @GetMapping({"/", "/index"})
    public String index(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("appName", appName);
        model.addAttribute("loginUser", loginUser);
        return "index";
    }
}
