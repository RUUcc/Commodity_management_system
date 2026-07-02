package org.example.commodity_management_system_jzs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.commodity_management_system_jzs.entity.User;
import org.example.commodity_management_system_jzs.mapper.UserMapper;
import org.example.commodity_management_system_jzs.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户 Service 实现
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String username, String password) {
        // 先按用户名查用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = this.getOne(wrapper);
        if (user == null) {
            return null;
        }
        // BCrypt 密码比对
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * 新增用户 —— 密码加密 + 事务
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(User user) {
        // BCrypt 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.save(user);
    }

    /**
     * 修改用户 —— 如果传了新密码则加密
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            // 没传密码，保留原密码
            user.setPassword(null);
        }
        return super.updateById(user);
    }
}
