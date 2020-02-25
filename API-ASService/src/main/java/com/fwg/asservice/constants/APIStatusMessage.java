package com.fwg.asservice.constants;

public class APIStatusMessage {
	
	public static final String SUCCESS = "Success";
	public static final String FAILED = "Failed";

	// Error code from stored procedure ==> Example ::: NotFound[PersonID] , Duplicated[PersonID]
	public static final String[] ERROR_CODE_STORED_PROCEDURE = {"NotFound", "Duplicated","Incorrect","Expired"};
}
