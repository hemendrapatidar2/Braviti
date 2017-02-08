package com.tm.braveti.controller;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.tm.braveti.model.UserDTO;
import com.tm.braveti.model.UserPrefResponse;
import com.tm.braveti.service.UserServiceI;
import com.tm.braveti.service.UserServiceImpl;

import parquet.org.codehaus.jackson.JsonGenerationException;
import parquet.org.codehaus.jackson.map.JsonMappingException;
import parquet.org.codehaus.jackson.map.ObjectMapper;

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
	
	/**
	 * Sends addUser request to {@link UserServiceImpl} dao class.
	 * @param all required user details in JSON format.
	 * @return JSON string containing result of operation. ex: {"status":true/false,"statusMsg:success/error message" }
	 */
	@POST
	@Path("/addUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addUser(String userString) {
		try {
			UserServiceI userService = new UserServiceImpl();
			ObjectMapper mapper = new ObjectMapper();
			UserDTO dto=mapper.readValue(userString, UserDTO.class); 
			//userService.save(dto);
			return buildResponse(true,"Successfully added user");
		} catch (Exception e) {
			System.err.println("ERROR while adding user " + e.getMessage());
			e.printStackTrace();
		}
		return buildResponse(false,"Failed to add user");
	}
	
	/**
	 * Builds JSON string containing result of operation. Ex: addUser operation
	 * @param result {true,false}
	 * @param msg {success/error message}
	 * @return JSON string in format {"status":true/false,"statusMsg:success/error message" }
	 */
	private String buildResponse(boolean result, String msg) {
		UserPrefResponse userPrefRes = new UserPrefResponse();
		ObjectMapper mapper = new ObjectMapper();
		userPrefRes.setStatus(result);
		userPrefRes.setStatusMsg(msg);
		try {
			return mapper.writeValueAsString(userPrefRes);
		} catch (JsonGenerationException e) {
			System.err.println("JSON exception while building response");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			System.err.println("JSON mapping exception while building response");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO exception while building response");
			e.printStackTrace();
		}
		return "";

	}

}
