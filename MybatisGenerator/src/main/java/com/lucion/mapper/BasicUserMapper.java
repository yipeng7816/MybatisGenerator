package com.lucion.mapper;

import com.lucion.pojo.BasicUser;
import com.lucion.pojo.BasicUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BasicUserMapper {
    int countByExample(BasicUserExample example);

    int deleteByExample(BasicUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BasicUser record);

    int insertSelective(BasicUser record);

    List<BasicUser> selectByExample(BasicUserExample example);

    BasicUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BasicUser record, @Param("example") BasicUserExample example);

    int updateByExample(@Param("record") BasicUser record, @Param("example") BasicUserExample example);

    int updateByPrimaryKeySelective(BasicUser record);

    int updateByPrimaryKey(BasicUser record);
}