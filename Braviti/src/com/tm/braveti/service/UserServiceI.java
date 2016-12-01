package com.tm.braveti.service;

import java.util.List;

import com.tm.braveti.model.UserDTO;

public interface UserServiceI {

	public void save(UserDTO dto) throws Exception;

	public void delete(UserDTO dto) throws Exception;

	public List search(UserDTO dto) throws Exception;

	public UserDTO get(UserDTO dto) throws Exception;
}
