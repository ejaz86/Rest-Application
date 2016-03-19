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

import com.auto.dto.DistributorDetailDTO;
import com.auto.dto.PartsDetailDTO;
import com.auto.dto.VendorDetailDTO;
import com.auto.exceptions.MyApplicationException;
import com.auto.service.AdminServiceImpl;

@Path("admin")
public class AdminResource {

	AdminServiceImpl adminServiceImpl;

	public AdminResource() {
		adminServiceImpl = new AdminServiceImpl();
	}

	@RolesAllowed({ "ADMIN" })
	@POST
	@Path("distributor")
	@Consumes(MediaType.APPLICATION_JSON)
	public int createDistributor(DistributorDetailDTO distributorDetailDTO) throws MyApplicationException {
		return adminServiceImpl.createDistributor(distributorDetailDTO);
	}

	@RolesAllowed({ "ADMIN" })
	@PUT
	@Path("distributor")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateDistributor(DistributorDetailDTO distributorDetailDTO) throws MyApplicationException {
		adminServiceImpl.updateDistributor(distributorDetailDTO);
	}

	@RolesAllowed({ "ADMIN" })
	@DELETE
	@Path("distributor")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteDistributor(DistributorDetailDTO distributorDetailDTO) throws MyApplicationException {
		adminServiceImpl.deleteDistributor(distributorDetailDTO);
	}

	@RolesAllowed({ "ADMIN" })
	@GET
	@Path("distributor/{did}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<DistributorDetailDTO> getDistributor(@PathParam("did") long did) throws Exception {
		return adminServiceImpl.getDistributor(did);
	}

	@RolesAllowed({ "ADMIN" })
	@GET
	@Path("distributor")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<DistributorDetailDTO> getDistributors() throws Exception {
		return adminServiceImpl.getDistributor(0);
	}

	@RolesAllowed({ "ADMIN" })
	@POST
	@Path("parts")
	@Consumes(MediaType.APPLICATION_JSON)
	public int createParts(PartsDetailDTO partsDetailDTO) throws MyApplicationException {
		return adminServiceImpl.createParts(partsDetailDTO);
	}

	@RolesAllowed({ "ADMIN" })
	@PUT
	@Path("parts")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateParts(PartsDetailDTO partsDetailDTO) throws MyApplicationException {
		adminServiceImpl.updateParts(partsDetailDTO);
	}

	@RolesAllowed({ "ADMIN" })
	@DELETE
	@Path("parts")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteParts(PartsDetailDTO partsDetailDTO) throws MyApplicationException {
		adminServiceImpl.deleteParts(partsDetailDTO);
	}

	@RolesAllowed({ "ADMIN" })
	@GET
	@Path("parts/{pid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<PartsDetailDTO> getParts(@PathParam("pid") long pid) throws MyApplicationException {
		return adminServiceImpl.getParts(pid);
	}

	@RolesAllowed({ "ADMIN" })
	@GET
	@Path("parts")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<PartsDetailDTO> getParts() throws MyApplicationException {
		return adminServiceImpl.getParts(0);
	}

	@RolesAllowed({ "ADMIN" })
	@POST
	@Path("vendor")
	@Consumes(MediaType.APPLICATION_JSON)
	public int createVendor(VendorDetailDTO vendorDetailDTO) throws MyApplicationException {
		return adminServiceImpl.createVendor(vendorDetailDTO);
	}

	@RolesAllowed({ "ADMIN" })
	@PUT
	@Path("vendor")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateVendor(VendorDetailDTO vendorDetailDTO) throws MyApplicationException {
		adminServiceImpl.updateVendor(vendorDetailDTO);
	}

	@RolesAllowed({ "ADMIN" })
	@DELETE
	@Path("vendor")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteVendor(VendorDetailDTO vendorDetailDTO) throws MyApplicationException {
		adminServiceImpl.deleteVendor(vendorDetailDTO);
	}

	@RolesAllowed({ "ADMIN" })
	@GET
	@Path("vendor/{vid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<VendorDetailDTO> getVendor(@PathParam("vid") long vid) throws MyApplicationException {
		return adminServiceImpl.getVendor(vid);
	}

	@RolesAllowed({ "ADMIN" })
	@GET
	@Path("vendor")
	@Produces(MediaType.APPLICATION_JSON)
	public List<VendorDetailDTO> getVendor() throws MyApplicationException {
		return adminServiceImpl.getVendor(0);
	}
}
