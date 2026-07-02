package org.example.commodity_management_system_jzs.config;

import lombok.extern.slf4j.Slf4j;
import org.example.commodity_management_system_jzs.entity.User;
import org.example.commodity_management_system_jzs.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 数据初始化器（应用启动时自动执行）
 * 确保默认管理员账号存在且密码已 BCrypt 加密
 */
@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        // 查找是否存在 admin 用户
        User user = userService.lambdaQuery()
                .eq(User::getUsername, "admin")
                .one();

        if (user == null) {
            // 不存在：新建 admin 账号
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123456");  // save() 中会自动 BCrypt 加密
            userService.save(admin);
            log.info("✅ 默认管理员账号已创建：admin / 123456");
        } else {
            // 已存在：检查密码是否是明文（BCrypt 密文以 $2a$ 开头）
            if (!user.getPassword().startsWith("$2a$")) {
                user.setPassword("123456");
                // updateById 中会自动 BCrypt 加密
                userService.updateById(user);
                log.info("⚠️ 管理员密码已从明文升级为 BCrypt 加密");
            } else {
                log.info("✅ 管理员账号正常（已加密）");
            }
        }
    }
}
