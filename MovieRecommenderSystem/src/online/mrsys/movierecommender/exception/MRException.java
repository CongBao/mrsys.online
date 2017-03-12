package online.mrsys.movierecommender.exception;

public class MRException extends RuntimeException {
	
	private static final long serialVersionUID = -3862536228064384031L;

	public MRException() {
	}
	
	public MRException(String msg) {
		super(msg);
	}

}
