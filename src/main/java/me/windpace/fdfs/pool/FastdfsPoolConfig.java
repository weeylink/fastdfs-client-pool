package me.windpace.fdfs.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
/**
 * @project: fastdfs-client-pool
 * @Title: FastdfsPoolConfig
 * @Package: me.windpace.fdfs.pool
 * @author: liuwei
 * @email: waylink@163.com	
 * @date: 2016年1月18日
 * @description: //TODO
 * @version:
 */
public class FastdfsPoolConfig extends GenericObjectPoolConfig {

	public FastdfsPoolConfig() {
		super();
		// defaults to make your life with connection pool easier :)
		setTestWhileIdle(true);
		setMinEvictableIdleTimeMillis(60000);
		setTimeBetweenEvictionRunsMillis(30000);
		setNumTestsPerEvictionRun(-1);
	}

}
