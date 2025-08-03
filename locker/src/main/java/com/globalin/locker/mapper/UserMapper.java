package com.globalin.locker.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<Map<String, Object>> getAllUsers();
    int getUserCount();
    void insertUser(@Param("id") int id, @Param("name") String name, @Param("email") String email);
}
