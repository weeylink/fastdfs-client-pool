package me.windpace.fdfs.pool;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.xml.DOMConfigurator;
import org.csource.common.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
/**
 * @project: fastdfs-client-pool
 * @Title: TestClient
 * @Package: me.windpace.fdfs.pool
 * @author: liuwei
 * @email: waylink@163.com	
 * @date: 2016年1月18日
 * @description: //TODO
 * @version:
 */
public class TestClient {
	private static final Logger logger = LoggerFactory.getLogger(TestClient.class);

	@SuppressWarnings({ "resource" })
	public static void main(String[] args) throws Throwable {
		DOMConfigurator.configureAndWatch("config/log4j.xml");
		ApplicationContext factory =  new FileSystemXmlApplicationContext("config/propholder.xml");
		FastdfsPool pool =	(FastdfsPool) factory.getBean("fdfsPool");
		
		for(int i = 0; i<1000; i++){
			FastdfsClient fastdfsClient =  pool.getResource();
			String local_filename = "D:/demo2.png";
			
			File file = new File(local_filename);
			FileInputStream fis = new FileInputStream(file);
			
	        NameValuePair[] meta_list = new NameValuePair[1];
	        meta_list[0] = new NameValuePair("fileName", local_filename);
				
	        String fileId = fastdfsClient.upload_file(null, local_filename, null, fis, file.length(), meta_list);
	        pool.returnResource(fastdfsClient);
			System.out.println(fastdfsClient + " upload success. file id is: " + fileId);

		}
		

	}
}
