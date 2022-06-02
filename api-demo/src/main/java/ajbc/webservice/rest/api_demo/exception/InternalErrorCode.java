package ajbc.webservice.rest.api_demo.exception;

public enum InternalErrorCode {
	
	INVALID_ID(1);
	
	private int code;
	
	private InternalErrorCode(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}

}
