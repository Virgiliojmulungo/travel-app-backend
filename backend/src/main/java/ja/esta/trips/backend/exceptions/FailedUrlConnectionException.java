package ja.esta.trips.backend.exceptions;

public class FailedUrlConnectionException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public FailedUrlConnectionException(String message){
		super(message);
	}
}
