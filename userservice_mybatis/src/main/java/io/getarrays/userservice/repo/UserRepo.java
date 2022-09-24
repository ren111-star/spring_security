package io.getarrays.userservice.repo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.getarrays.userservice.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRepo extends BaseMapper<User> {
    User findByUsername(@Param("username") String username);

    int insertUser(User user);

    int addRoleToUserById(@Param("user_id") Long userId, @Param("roles_id") Long rolesId);

    List<User> findAll();
}
