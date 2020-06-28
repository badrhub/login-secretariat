package com.jvm.service;
import java.util.Collection;

import com.jvm.entities.AppRole;
import com.jvm.entities.AppUser;

public interface AccountService {
		
	public AppUser saveUser(String username ,String password ,String confirmedPassWord);
	public AppRole saveRole(AppRole role );
	public AppUser loadUserByUsername(String username);
	public void addRoleToUser(String username , String rolename);
	public AppUser saveUserAdmin(String u, String string, String string2);
	public AppUser updateUser(String username, String password, String confirmedPassword, String application,
			String string, boolean actived);
	Collection<AppRole> deleteRoleParUser(String role, String user);
		
}
