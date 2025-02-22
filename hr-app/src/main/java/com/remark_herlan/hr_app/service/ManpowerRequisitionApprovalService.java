package com.remark_herlan.hr_app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.remark_herlan.hr_app.dao.ManpowerRequisitionApprovalDao;
import com.remark_herlan.hr_app.exceptions.AuthorizationException;
import com.remark_herlan.hr_app.exceptions.DataNotFoundException;
import com.remark_herlan.hr_app.exceptions.InternalServerException;
import com.remark_herlan.hr_app.exceptions.InvalidRequestException;
import com.remark_herlan.hr_app.model.ManpowerRequisition;
import com.remark_herlan.hr_app.model.ManpowerRequisitionApproval;
import com.remark_herlan.hr_app.model.ManpowerRequisitionApprovalUniqueKey;
import com.remark_herlan.hr_app.model.ResponseInfo;
import com.remark_herlan.hr_app.model.Users;

/**
 * author: Naimul Hassan
 * 
 * date: 2/8/2025
 */

@Service
public class ManpowerRequisitionApprovalService {

	@Autowired
	ManpowerRequisitionApprovalDao dao;

	@Autowired
	GetSequenceService sequenceService;

	public ResponseInfo<List<ManpowerRequisitionApproval>> getAllInfos()
			throws InternalServerException, DataNotFoundException {
		ResponseInfo<List<ManpowerRequisitionApproval>> responseInfo = new ResponseInfo<>();

		try {
			List<ManpowerRequisitionApproval> response = dao.findAll();

			if (response.isEmpty()) {
				throw new DataNotFoundException("No data found!");
			}

			responseInfo.setStatusCode(HttpStatus.OK.value());
			responseInfo.setMessage("Successfully fetched!");
			responseInfo.setData(response);

			return responseInfo;
		} catch (DataNotFoundException e) {
			// Explicitly handle known exception
			throw e; // Re-throw to let a higher-level handler manage it
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}

	}

	public ResponseInfo<Optional<ManpowerRequisitionApproval>> getInfo(ManpowerRequisitionApprovalUniqueKey key)
			throws InternalServerException, DataNotFoundException {
		ResponseInfo<Optional<ManpowerRequisitionApproval>> responseInfo = new ResponseInfo<>();

		try {
			Optional<ManpowerRequisitionApproval> response = dao.findById(key);

			if (response.isEmpty()) {
				throw new DataNotFoundException("No data found!");
			}

			responseInfo.setStatusCode(HttpStatus.OK.value());
			responseInfo.setMessage("Successfully fetched!");
			responseInfo.setData(response);

			return responseInfo;
		} catch (DataNotFoundException e) {
			// Explicitly handle known exception
			throw e; // Re-throw to let a higher-level handler manage it
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}

	}

	public ResponseInfo<List<ManpowerRequisitionApproval>> getInfoByStatus(String status)
			throws InternalServerException, DataNotFoundException {
		ResponseInfo<List<ManpowerRequisitionApproval>> responseInfo = new ResponseInfo<>();

		try {
			List<ManpowerRequisitionApproval> response = dao.findByStatus(status);

			if (response.isEmpty()) {
				throw new DataNotFoundException("No data found!");
			}

			responseInfo.setStatusCode(HttpStatus.OK.value());
			responseInfo.setMessage("Successfully fetched!");
			responseInfo.setData(response);

			return responseInfo;
		} catch (DataNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}

	}

	public ResponseInfo<List<ManpowerRequisitionApproval>> getInfoByApprover(Users approver, String status)
			throws InternalServerException, DataNotFoundException {
		ResponseInfo<List<ManpowerRequisitionApproval>> responseInfo = new ResponseInfo<>();

		try {
			List<ManpowerRequisitionApproval> response = dao.findByApprovedBy(approver);

			if (response.isEmpty()) {
				throw new DataNotFoundException("No data found!");
			}

			List<ManpowerRequisitionApproval> filteredList = response.stream().filter(c -> status.equals(c.getStatus())) // Corrected
																															// string
																															// comparison
					.collect(Collectors.toList());

			if (filteredList.isEmpty()) {
				throw new DataNotFoundException("No data found!");
			}

			responseInfo.setStatusCode(HttpStatus.OK.value());
			responseInfo.setMessage("Successfully fetched!");
			responseInfo.setData(filteredList);

			return responseInfo;
		} catch (DataNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
	}

	public ResponseInfo<List<ManpowerRequisitionApproval>> getInfoByRequisition(ManpowerRequisition manpowerRequisition)
			throws InternalServerException, DataNotFoundException {
		ResponseInfo<List<ManpowerRequisitionApproval>> responseInfo = new ResponseInfo<>();

		try {
			List<ManpowerRequisitionApproval> response = dao.findByApprovalOf(manpowerRequisition);

			if (response.isEmpty()) {
				throw new DataNotFoundException("No data found!");
			}

			responseInfo.setStatusCode(HttpStatus.OK.value());
			responseInfo.setMessage("Successfully fetched!");
			responseInfo.setData(response);

			return responseInfo;
		} catch (DataNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
	}

	public ResponseInfo<Integer> updateApprovalStatus(String status, ManpowerRequisitionApprovalUniqueKey key,
			String loggedUser, String remarks)
			throws InternalServerException, DataNotFoundException, InvalidRequestException, AuthorizationException {
		ResponseInfo<Integer> responseInfo = new ResponseInfo<>();

		try {
			ManpowerRequisitionApproval requisitionApprovalResponse = dao.findById(key)
					.orElseThrow(() -> new DataNotFoundException("No data found!"));

			if (!"PENDING".equals(requisitionApprovalResponse.getStatus())) {
				throw new InvalidRequestException("This approval is already completed!");
			}

			if (!loggedUser.equals(requisitionApprovalResponse.getApprovedBy().getUsername())) {
				throw new AuthorizationException("Access denied: You are not authorized for this approval!");
			}

			LocalDateTime currentDate = LocalDateTime.now();

			int response = dao.updateStatusByKey(status, currentDate, remarks, key);

			responseInfo.setStatusCode(HttpStatus.OK.value());
			responseInfo.setMessage("Status updated!");
			responseInfo.setData(response);

			return responseInfo;
		} catch (DataNotFoundException e) {
			throw e;
		} catch (InvalidRequestException e) {
			throw e;
		} catch (AuthorizationException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
	}

	public ResponseInfo<String> saveInfo(ManpowerRequisitionApproval manpowerRequisitionApproval, String username)
			throws InternalServerException {
		ResponseInfo<String> responseInfo = new ResponseInfo<>();

		try {
			manpowerRequisitionApproval.setCreatedBy(username);
			dao.save(manpowerRequisitionApproval);

			responseInfo.setStatusCode(HttpStatus.OK.value());
			responseInfo.setMessage("Successfully added!");
			responseInfo.setData(HttpStatus.OK.name());

			return responseInfo;
		} catch (Exception e) {
			throw new InternalServerException(e.getMessage());
		}
	}

}
