package me.windpace.fdfs.pool;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
	
	private final Integer objMaxActive;

	public FastdfsPooledObjectFactory(String confPath, Integer objMaxActive) throws FileNotFoundException, IOException, MyException {
		super();
		Resource resource = null;
		if(confPath.startsWith("classpath:")){
			resource = new ClassPathResource(confPath.substring(confPath.indexOf(":") + 1));
		}else{
			resource = new FileSystemResource(confPath);
		}
		this.objMaxActive = objMaxActive;
		ClientGlobal.init(confPath, resource.getFile());
	}

	@Override
	public PooledObject<FastdfsClient> makeObject() throws Exception {
		
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		FastdfsClient client = new FastdfsClient(trackerServer, storageServer, objMaxActive);
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
		client.reset();

	}

	@Override
	public boolean validateObject(PooledObject<FastdfsClient> p) {
		try {
			return ProtoCommon.activeTest(p.getObject().getStorageServer().getSocket());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public void activateObject(PooledObject<FastdfsClient> p) throws Exception {
		if (p == null) {
			return;
		}
		if (p.getObject() == null) {
			return;
		}
		FastdfsClient client = p.getObject();
		client.tryReset();
	}

	@Override
	public void passivateObject(PooledObject<FastdfsClient> p) throws Exception {
	}

}
