package com.jvm.web;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jvm.entities.AppRole;
import com.jvm.entities.AppUser;
import com.jvm.service.AccountService;

import lombok.Data;

@RestController
public class UserController {
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/register")
	public AppUser register(@RequestBody UserForm userForm) {
		System.out.println(userForm.getUsername() +" " + userForm.getPassword() +" " + userForm.getConfirmedPassword());
	    return accountService.saveUser(userForm.getUsername() , userForm.getPassword() , userForm.getConfirmedPassword());	
	}
	
	@PostMapping("/saveRole")
	public AppRole saveRole(@RequestBody AppRole role) {
	    return accountService.saveRole(role);	
	}
	
	@PutMapping("/updateUser/{nom}")
	public AppUser updateUser(@RequestBody UserForm u , @PathVariable String nom) {
		System.out.println();
	    return accountService.updateUser(nom ,u.getUsername() , u.getPassword() ,u.getConfirmedPassword() , u.getApplication() , u.isActived());	
	}
	
	@GetMapping("/addRoleToUser")
	public void AddUserToRole(@RequestParam String username, @RequestParam String role) {
			    accountService.addRoleToUser(username, role);	
	}
    
	@GetMapping("/listRoleParUser/{username}")
	public Collection<AppRole> listRoleParUser(@PathVariable String username){
		AppUser u = accountService.loadUserByUsername(username);
		return u.getRoles();
	}
	
	@GetMapping("/deleteRoleParUser" )
	public Collection<AppRole> deleteRoleParUser(@RequestParam(name="role") String role , @RequestParam(name="user") String user){
		return accountService.deleteRoleParUser(role, user);
		
	}
	

}

@Data
class UserForm{
	private String username;
	private String password;
	private String confirmedPassword;
	private boolean actived;
	private String application;
}