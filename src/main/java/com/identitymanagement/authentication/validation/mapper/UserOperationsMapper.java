package com.identitymanagement.authentication.validation.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.identitymanagement.authentication.model.UserSecrets;

@Mapper
public interface UserOperationsMapper {
	
	@Select("select count(*) from user_details where user_name=#{userName}")
	public int getUserbyUserName(String userName);
	
	@Select("select count(*) from user_details where mail_id=#{mailId}")
	public int getUserbyMail(String mailId);
	
	@Select("select count(*) from user_details where user_name=#{userName} and password=#{password}")
	public int validateUser(UserSecrets user);
}
