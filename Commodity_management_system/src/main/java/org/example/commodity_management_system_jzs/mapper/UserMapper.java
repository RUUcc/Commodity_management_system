package org.example.commodity_management_system_jzs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.commodity_management_system_jzs.entity.User;

/**
 * 用户 Mapper
 * 继承 BaseMapper，自带 CRUD
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
