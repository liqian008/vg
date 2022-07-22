package com.example.demo.service.entity.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.UserEntityMapper;
import com.example.demo.model.UserEntity;
import com.example.demo.service.entity.IUserEntityService;
import org.springframework.stereotype.Service;

/**
 * 用户EntityService
 * @author bruce
 */
@Service
public class UserEntityServiceImpl extends ServiceImpl<UserEntityMapper, UserEntity>
		implements IUserEntityService {

}
