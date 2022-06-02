package ajbc.webservice.rest.api_demo.models;

public enum InternalErrorCode {
	
	INVALID_PARAMETER(1);
	
	private int code;
	
	private InternalErrorCode(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}

}
