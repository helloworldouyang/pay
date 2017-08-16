package com.dao;

import com.entity.User;

public interface IUserDao {

	
	User selectByPrimaryKey(Integer id);
}
