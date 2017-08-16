package com.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dao.IUserDao;
import com.entity.User;
import com.service.IUserService;


@Service("userService")
public class IUserServiceImpl implements IUserService{
	
	@Resource
	private IUserDao iUserDao;

	@Override
	public User getUserById(int userId) {
		// TODO Auto-generated method stub
		return iUserDao.selectByPrimaryKey(userId);
	}

}
