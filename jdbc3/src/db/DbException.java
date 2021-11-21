package db;

public class DbException extends  RuntimeException{

	/**
	 * versao do Runtimeexception
	 */
	private static final long serialVersionUID = 1L;
	
	public DbException(String msg) {
		super(msg);
	}
	

}
