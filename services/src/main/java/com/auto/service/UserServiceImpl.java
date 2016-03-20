package com.auto.service;

import java.util.List;

import com.auto.dao.OrderDAO;
import com.auto.dao.UserDAO;
import com.auto.dto.OrderDTO;
import com.auto.dto.UserDTO;
import com.auto.exceptions.MyApplicationException;
import com.auto.resource.security.AuthToken;

public class UserServiceImpl {
	UserDAO userDAO;
	OrderDAO orderDAO;

	public UserServiceImpl() {
		userDAO = new UserDAO();
		orderDAO = new OrderDAO();
	}

	public int createUser(UserDTO userDTO) throws MyApplicationException {
		return userDAO.createUser(userDTO);
	}

	public void updateUser(UserDTO userDTO) throws MyApplicationException {
		userDAO.updateUser(userDTO);
	}

	public List<UserDTO> getUser(long uid) throws MyApplicationException {
		return userDAO.getUserDetails(uid);
	}

	public void deleteUser(long uid) throws MyApplicationException {
		userDAO.deleteUser(uid);
	}

	public int placeOrder(OrderDTO orderDTO) throws MyApplicationException {
		return orderDAO.placeOrder(orderDTO);
	}

	public String getUserToken(int uid) throws Exception {
		List<UserDTO> list = userDAO.getUserDetails(uid);
		if (list == null) {
			throw new MyApplicationException("invalid user");
		} else {
			UserDTO userDto = list.get(0);
			String base = userDto.getfName() + userDto.getEmail() + userDto.getUid();
			String key = String.valueOf(userDto.getUid()) + userDto.getfName();
			return AuthToken.computeSignature(base, key);
		}
	}

}
