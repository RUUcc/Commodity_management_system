package org.example.commodity_management_system_jzs.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.commodity_management_system_jzs.entity.User;
import org.example.commodity_management_system_jzs.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录控制器
 */
@Slf4j
@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * 处理登录请求
     */
    @PostMapping("/doLogin")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        log.info("登录请求：username={}", username);
        try {
            User user = userService.login(username, password);
            if (user != null) {
                // 登录成功
                log.info("登录成功：username={}, id={}", username, user.getId());
                user.setPassword(null);
                session.setAttribute("loginUser", user);
                return "redirect:/index";
            } else {
                // 登录失败
                log.warn("登录失败：username={}，数据库未匹配到用户", username);
                model.addAttribute("error", "用户名或密码错误");
                model.addAttribute("username", username);
                return "login";
            }
        } catch (Exception e) {
            log.error("登录异常：{}", e.getMessage(), e);
            model.addAttribute("error", "系统异常：" + e.getMessage());
            model.addAttribute("username", username);
            return "login";
        }
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
