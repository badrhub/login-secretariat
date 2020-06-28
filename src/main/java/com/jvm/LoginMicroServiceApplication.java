package com.jvm;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LoginMicroServiceApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(LoginMicroServiceApplication.class, args);
	}

//	@Bean
//	CommandLineRunner start(AccountService ac) {
//		return args->{
//			ac.saveRole(new AppRole(null,"USER"));
//			ac.saveRole(new AppRole(null , "ADMIN"));
//			ac.saveRole(new AppRole(null , "GUEST"));
//			ac.saveRole(new AppRole(null , "SECretariat"));
//			Stream.of("badr","dalila" ,"imane" ,"ali" , "hakima").forEach(u->{
//				ac.saveUserAdmin(u, "1234", "1234");
//			});
//			
//			/*	
//			ac.addRoleToUser("nabil", "USER");
//			ac.addRoleToUser("dalila", "ADMIN");
//			ac.addRoleToUser("imane", "ADMIN");
//			ac.addRoleToUser("ali", "ADMIN");
//			ac.addRoleToUser("hakima", "ADMIN");
//			
//			*/
//			
//			
//			ac.addRoleToUser("badr", "SECretariat");
//		};
//	}
	
	@Bean
	BCryptPasswordEncoder getBPE() {
		return new BCryptPasswordEncoder();
	}
}
