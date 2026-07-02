package org.example.commodity_management_system_jzs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.commodity_management_system_jzs.common.Result;
import org.example.commodity_management_system_jzs.entity.User;
import org.example.commodity_management_system_jzs.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户管理控制器
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "5") Integer pageSize,
                           Model model) {
        Page<User> page = new Page<>(pageNum, pageSize);
        Page<User> result = userService.page(page);
        model.addAttribute("page", result);
        return "user-list";
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<?> add(@Valid User user,
                         @RequestParam(required = false) MultipartFile avatarFile) {
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String avatarPath = ProductController.saveStaticFile(avatarFile);
            user.setAvatar(avatarPath);
        }
        userService.save(user);
        return Result.ok("新增成功");
    }

    @PostMapping("/edit")
    @ResponseBody
    public Result<?> edit(@Valid User user,
                          @RequestParam(required = false) MultipartFile avatarFile) {
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String avatarPath = ProductController.saveStaticFile(avatarFile);
            user.setAvatar(avatarPath);
        }
        userService.updateById(user);
        return Result.ok("修改成功");
    }

    @GetMapping("/delete")
    @ResponseBody
    public Result<?> delete(@RequestParam Integer id) {
        userService.removeById(id);
        return Result.ok("删除成功");
    }

    @GetMapping("/get")
    @ResponseBody
    public Result<User> getById(@RequestParam Integer id) {
        User user = userService.getById(id);
        if (user != null) user.setPassword(null);
        return user != null ? Result.ok(user) : Result.fail("用户不存在");
    }
}
