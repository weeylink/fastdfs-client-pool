package me.windpace.fdfs.pool;
/**
 * @project: fastdfs-client-pool
 * @Title: FastdfsException
 * @Package: me.windpace.fdfs.pool
 * @author: liuwei
 * @email: waylink@163.com	
 * @date: 2016年1月18日
 * @description: //TODO
 * @version:
 */
public class FastdfsException extends RuntimeException {

	private static final long serialVersionUID = -3572357640110891457L;

	public FastdfsException() {
		super();
	}

	public FastdfsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FastdfsException(String message, Throwable cause) {
		super(message, cause);
	}

	public FastdfsException(String message) {
		super(message);
	}

	public FastdfsException(Throwable cause) {
		super(cause);
	}


	
	

}
