package hr.fer.oprpp1.hw05.shell;

public class ShellIOException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ShellIOException() { }
	
	public ShellIOException(String msg) {
		super(msg);
	}
	
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

}

