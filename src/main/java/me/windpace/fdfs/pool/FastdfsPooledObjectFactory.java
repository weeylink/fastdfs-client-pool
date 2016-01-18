package me.windpace.fdfs.pool;

import java.io.File;
import java.io.IOException;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
/**
 * @project: fastdfs-client-pool
 * @Title: FastdfsPooledObjectFactory
 * @Package: me.windpace.fdfs.pool
 * @author: liuwei
 * @email: waylink@163.com	
 * @date: 2016年1月18日
 * @description: //TODO
 * @version:
 */
public class FastdfsPooledObjectFactory implements PooledObjectFactory<FastdfsClient> {

	public FastdfsPooledObjectFactory(String confPath) {
		super();
		try {
			String classPath = new File(super.getClass().getResource("/").getFile()).getCanonicalPath();
			String configFilePath = classPath + File.separator + confPath;
			ClientGlobal.init(configFilePath);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public PooledObject<FastdfsClient> makeObject() throws Exception {
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		FastdfsClient client = new FastdfsClient(trackerServer, storageServer);
		return new DefaultPooledObject<FastdfsClient>(client);
	}

	@Override
	public void destroyObject(PooledObject<FastdfsClient> p) throws Exception {
		if (p == null) {
			return;
		}
		if (p.getObject() == null) {
			return;
		}
		FastdfsClient client = p.getObject();
		TrackerServer trackerServer = client.getTrackerServer();
		StorageServer storageServer = client.getStorageServer();
		if (trackerServer != null) {
			trackerServer.close();
		}
		if (storageServer != null) {
			storageServer.close();
		}

	}

	@Override
	public boolean validateObject(PooledObject<FastdfsClient> p) {
		try {
			return ProtoCommon.activeTest(p.getObject().getTrackerServer().getSocket());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public void activateObject(PooledObject<FastdfsClient> p) throws Exception {
	}

	@Override
	public void passivateObject(PooledObject<FastdfsClient> p) throws Exception {
	}

}
