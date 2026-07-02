package org.example.commodity_management_system_jzs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.commodity_management_system_jzs.entity.User;

/**
 * 用户 Service 接口
 */
public interface UserService extends IService<User> {

    /**
     * 登录校验
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户对象，失败返回 null
     */
    User login(String username, String password);
}
