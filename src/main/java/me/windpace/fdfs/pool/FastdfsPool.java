package me.windpace.fdfs.pool;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.common.MyException;
/**
 * @project: fastdfs-client-pool
 * @Title: FastdfsPool
 * @Package: me.windpace.fdfs.pool
 * @author: liuwei
 * @email: waylink@163.com	
 * @date: 2016年1月18日
 * @description: //TODO
 * @version:
 */
public class FastdfsPool extends Pool<FastdfsClient> {

	public FastdfsPool(GenericObjectPoolConfig poolConfig, String confPath) throws FileNotFoundException, IOException, MyException {
		super(poolConfig, new FastdfsPooledObjectFactory(confPath));
		
	}

	public void returnBrokenResource(final FastdfsClient resource) {
		if (resource != null) {
			returnBrokenResourceObject(resource);
		}
	}

	public void returnResource(final FastdfsClient resource) {
		if (resource != null) {
			returnResourceObject(resource);
		}
	}

}
