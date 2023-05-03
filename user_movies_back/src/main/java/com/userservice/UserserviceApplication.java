package com.userservice;

import com.userservice.domain.Movie;
import com.userservice.domain.Role;
import com.userservice.domain.User;
import com.userservice.service.MovieService;
import com.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, MovieService movieService){
		return args -> {
			movieService.saveMovie(new Movie(1, "Creed 3", 1.33,"Michael B. Jordan"));
			movieService.saveMovie(new Movie(2, "Super Mario Bros", 1.32,"Nintendo"));
        movieService.saveMovie(new Movie(3, "Avatar", 3.12,"James Cameron"));
        movieService.saveMovie(new Movie(4, "John Wick", 2.50,"Chad. S"));

			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(1, "Luis Soto","luis","123456",new ArrayList<>()));
			userService.saveUser(new User(2, "Vicky Delgadillo","vicky","123456",new ArrayList<>()));
			userService.saveUser(new User(3, "User_xyz","user_xyz","123456",new ArrayList<>()));
			userService.saveUser(new User(4, "User_abc","user_abc","123456",new ArrayList<>()));

			userService.addRoleToUser("luis","ROLE_SUPER_ADMIN");
        userService.addRoleToUser("luis","ROLE_ADMIN");
        userService.addRoleToUser("luis","ROLE_MANAGER");
        userService.addRoleToUser("luis","ROLE_USER");

			userService.addRoleToUser("vicky","ROLE_MANAGER");
        userService.addRoleToUser("vicky","ROLE_ADMIN");
        userService.addRoleToUser("vicky","ROLE_USER");

			userService.addRoleToUser("user_xyz","ROLE_ADMIN");
        userService.addRoleToUser("user_xyz","ROLE_USER");

			userService.addRoleToUser("user_abc","ROLE_USER");
		};
	}

}
