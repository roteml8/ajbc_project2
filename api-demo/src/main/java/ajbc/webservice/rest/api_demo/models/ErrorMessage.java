package ajbc.webservice.rest.api_demo.models;

public class ErrorMessage {
	
	private String errorMessage;
	private InternalErrorCode errorCode;
	private String docsLink;
	
	public ErrorMessage() {
		
	}
	
	
	public ErrorMessage(String errorMessage, InternalErrorCode errorCode, String docsLink) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.docsLink = docsLink;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public InternalErrorCode getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(InternalErrorCode errorCode) {
		this.errorCode = errorCode;
	}


	public String getDocsLink() {
		return docsLink;
	}


	public void setDocsLink(String docsLink) {
		this.docsLink = docsLink;
	}
	

}
