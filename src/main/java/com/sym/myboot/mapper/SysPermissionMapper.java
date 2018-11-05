package com.sym.myboot.mapper;

import com.sym.myboot.entity.SysPermission;

import java.util.List;
import java.util.Map;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);

    List<Map> getSysPermissionByRoleId(int id);
}