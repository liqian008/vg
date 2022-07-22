package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户EntityMapper
 * @author bruce
 */
@Mapper
public interface UserEntityMapper extends BaseMapper<UserEntity> {

}
