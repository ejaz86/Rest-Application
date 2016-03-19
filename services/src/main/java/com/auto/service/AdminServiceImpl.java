package com.auto.service;

import java.util.List;

import com.auto.dao.DistributorDAO;
import com.auto.dao.PartsDAO;
import com.auto.dao.VendorDAO;
import com.auto.dto.DistributorDetailDTO;
import com.auto.dto.PartsDetailDTO;
import com.auto.dto.VendorDetailDTO;
import com.auto.exceptions.MyApplicationException;

public class AdminServiceImpl {
	DistributorDAO distributorDAO;
	PartsDAO partsDAO;
	VendorDAO vendorDAO;

	public AdminServiceImpl() {
		distributorDAO = new DistributorDAO();
		partsDAO = new PartsDAO();
		vendorDAO = new VendorDAO();
	}

	public int createDistributor(DistributorDetailDTO distributorDetailDTO)
			throws MyApplicationException {
		return distributorDAO.createDistributor(distributorDetailDTO);
	}

	public void updateDistributor(DistributorDetailDTO distributorDetailDTO)
			throws MyApplicationException {
		distributorDAO.updateDistributor(distributorDetailDTO);
	}

	public void deleteDistributor(DistributorDetailDTO distributorDetailDTO)
			throws MyApplicationException {
		distributorDAO.deleteDistributor(distributorDetailDTO);
	}

	public List<DistributorDetailDTO> getDistributor(long did)
 throws Exception {
		return distributorDAO.getDistributorDetails(did);
	}

	public int createParts(PartsDetailDTO partsDetailDTO)
			throws MyApplicationException {
		return partsDAO.createParts(partsDetailDTO);
	}

	public void updateParts(PartsDetailDTO partsDetailDTO)
			throws MyApplicationException {
		partsDAO.updateParts(partsDetailDTO);
	}

	public void deleteParts(PartsDetailDTO partsDetailDTO)
			throws MyApplicationException {
		partsDAO.deleteParts(partsDetailDTO);
	}

	public List<PartsDetailDTO> getParts(long pid)
			throws MyApplicationException {
		return partsDAO.getPartsDetails(pid);
	}

	public int createVendor(VendorDetailDTO vendorDetailDTO)
			throws MyApplicationException {
		return vendorDAO.createVendor(vendorDetailDTO);
	}

	public void updateVendor(VendorDetailDTO vendorDetailDTO)
			throws MyApplicationException {
		vendorDAO.updateVendor(vendorDetailDTO);
	}

	public void deleteVendor(VendorDetailDTO vendorDetailDTO)
			throws MyApplicationException {
		vendorDAO.deleteVendor(vendorDetailDTO);
	}

	public List<VendorDetailDTO> getVendor(long vid)
			throws MyApplicationException {
		return vendorDAO.getVendorDetails(vid);
	}

}
