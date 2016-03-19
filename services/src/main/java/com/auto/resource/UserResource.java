package com.auto.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.auto.dto.OrderDTO;
import com.auto.dto.UserDTO;
import com.auto.exceptions.MyApplicationException;
import com.auto.service.UserServiceImpl;

@Path("user")
public class UserResource {
	UserServiceImpl serviceImpl;

	public UserResource() {
		serviceImpl = new UserServiceImpl();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public int createUser(UserDTO userDTO) throws MyApplicationException {
		return serviceImpl.createUser(userDTO);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(UserDTO userDTO) throws MyApplicationException {
		serviceImpl.updateUser(userDTO);
	}

	@GET
	@Path("{uid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> getUser(@PathParam("uid") long uid) throws MyApplicationException {
		return serviceImpl.getUser(uid);
	}

	@DELETE
	@Path("{uid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteUser(@PathParam("uid") long uid) throws MyApplicationException {
		serviceImpl.deleteUser(uid);
	}

	@RolesAllowed({ "USER" })
	@POST
	@Path("{uid}/order")
	@Consumes(MediaType.APPLICATION_JSON)
	public int placeOrder(@PathParam("uid") long uid, OrderDTO orderDTO) throws MyApplicationException {
		return serviceImpl.placeOrder(orderDTO);
	}
}
