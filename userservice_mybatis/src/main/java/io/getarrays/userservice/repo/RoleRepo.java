package io.getarrays.userservice.repo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.getarrays.userservice.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleRepo extends BaseMapper<Role> {
    Role findByName(@Param("name") String name);
}
