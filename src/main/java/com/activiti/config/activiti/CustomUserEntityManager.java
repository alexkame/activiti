package com.activiti.config.activiti;

import org.activiti.engine.impl.persistence.entity.UserEntityManager;

public class CustomUserEntityManager extends UserEntityManager{
	// 这个Bean就是公司提供的统一身份访问接口，可以覆盖UserEntityManager的任何方法用公司内部的统一接口提供服务
	private CustomUserManager customUserManager;
 
    @Override
    public Boolean checkPassword(String userId, String password) {
//        CustomUser customUser = customUserManager.get(new Long(userId));
//        return CustomUser.getPassword().equals(password);
    	return true;
    }
 
    public void setCustomUserManager(CustomUserManager customUserManager) {
        this.customUserManager = customUserManager;
    }
}
