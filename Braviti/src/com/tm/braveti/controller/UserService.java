package com.tm.braveti.controller;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.tm.braveti.model.UserDTO;
import com.tm.braveti.service.UserServiceI;
import com.tm.braveti.service.UserServiceImpl;

@Path("/user")
public class UserService {

	@POST
	@Path("/authenticate")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean authenticate(@QueryParam("userId") String userId, @QueryParam("password") String password) {

		System.out.println("in user service authenticate method  " + userId + "  " + password);
		if ("admin".equals(userId) && "admin".equals(password)) {
			System.out.println("valid user");
			return true;
		}
		return false;
	}

	@POST
	@Path("/submit")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean authenticate(UserDTO dto) {

		System.out.println("in register user service  method  ");
		
		UserServiceI service = new UserServiceImpl();
		try {
			service.save(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;

	}
	
	@POST
	@Path("/userList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> getRegisterUserList(UserDTO dto) {

		System.out.println("in getRegisterUserList  method");
		UserServiceI service = new UserServiceImpl();
		List<UserDTO> list=null;
		try {
			 list =  service.search(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
	
	
	

}
