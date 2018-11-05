package com.sym.myboot.mapper;

import com.sym.myboot.entity.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    List<Map> getSysRoleByUserName(String userName);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}