package com.jvm.service;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.jvm.dao.AppRoleRepository;
import com.jvm.dao.AppUserRepository;
import com.jvm.entities.AppRole;
import com.jvm.entities.AppUser;
@Service
@Transactional
public class AccountServiceImpl implements AccountService{
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AppRoleRepository appRoleRepository;
	@Autowired
	private BCryptPasswordEncoder bpe;
	
	
	@Override
	public AppUser saveUser(String username, String password, String confirmedPassWord) {
		AppUser u = appUserRepository.findByUsername(username);
		if(u != null) throw new RuntimeException("User Already exists");
		if(!password.equals(confirmedPassWord)) throw new RuntimeException("Please Confirm your password");
		AppUser user = new AppUser();
		user.setUsername(username);
		user.setActived(false);
		user.setPassword(bpe.encode(password));
		appUserRepository.save(user);
		addRoleToUser(username , "GUEST");
		return user;
	}
	
	
	@Override
	public AppRole saveRole(AppRole role) {
		AppRole r = appRoleRepository.findByRoleName(role.getRoleName());
		if(r != null) throw new RuntimeException("Role Already exists");
		return appRoleRepository.save(role);
	}
	
	
	@Override
	public AppUser loadUserByUsername(String username) {
		return appUserRepository.findByUsername(username);
	}
	
	
	@Override
	public void addRoleToUser(String username, String rolename) {
		AppUser user = appUserRepository.findByUsername(username);
		AppRole role = appRoleRepository.findByRoleName(rolename);
		if(user.getRoles().contains(role)) throw new RuntimeException("ce role est déja existe !!");
		user.getRoles().add(role);
	}
	
	
	@Override
	public AppUser saveUserAdmin(String username, String password, String confirmedPassWord) {
		AppUser u = appUserRepository.findByUsername(username);
		if(u != null) throw new RuntimeException("User Already exists");
		if(!password.equals(confirmedPassWord)) throw new RuntimeException("Please Confirm your password");
		AppUser user = new AppUser();
		user.setUsername(username);
		user.setActived(true);
		user.setPassword(bpe.encode(password));
		appUserRepository.save(user);
		addRoleToUser(username , "ADMIN");
		return user;
		
	}
	
	
	@Override
	public AppUser updateUser(String nom ,String us , String p , String pc , String a , boolean ac) {
		AppUser u = appUserRepository.findByUsername(nom);
		if(u == null) throw new RuntimeException("User doesn't exists");
		if(!p.equals(pc)) throw new RuntimeException("Please Confirm your password");
		
		u.setUsername(us);
		u.setPassword(bpe.encode(p));
		u.setApplication(a);
		u.setActived(ac) ;
		appUserRepository.save(u);
		return u;
	}
	
	@Override
	public Collection<AppRole> deleteRoleParUser(@RequestParam(name="role") String role , @RequestParam(name="user") String user){
		AppUser u = appUserRepository.findByUsername(user);
		AppRole r = appRoleRepository.findByRoleName(role);
		if(r != null && u != null)
		u.getRoles().remove(r);
		u = appUserRepository.save(u);
		//appRoleRepository.save(r);
		return u.getRoles();
	}
	
}
