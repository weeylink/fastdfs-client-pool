package me.windpace.fdfs.pool;

import java.io.IOException;
import java.io.InputStream;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;
import org.csource.fastdfs.UploadStream;

/**
 * @project: fastdfs-client-pool
 * @Title: FastdfsClient
 * @Package: me.windpace.fdfs.pool
 * @author: liuwei
 * @email: waylink@163.com	
 * @date: 2016年1月18日
 * @description: //TODO
 * @version:
 */
public class FastdfsClient extends StorageClient1 {

	public FastdfsClient(TrackerServer trackerServer, StorageServer storageServer) {
		super(trackerServer, storageServer);
	}

	public TrackerServer getTrackerServer() {
		return this.trackerServer;
	}

	public StorageServer getStorageServer() {
		return this.storageServer;
	}

	/**
	 * tip：重新获取storageServer trackerServer
	 */
	public void reset() {
		this.storageServer = null;
		this.trackerServer = null;
	}

	/**
	 * tip：重新获取storage
	 */
	public void reset0() {
		this.storageServer = null;
	}
	/**
	 * 
	 * @param group_name can be empty
	 * @param file_name 
	 * @param file_ext_name can be empty
	 * @param inputStream
	 * @param file_size
	 * @param meta_list
	 * @return
	 */
	public String upload_file(String group_name, String file_name, String file_ext_name, InputStream inputStream, Long file_size, NameValuePair[] meta_list) {
		final byte cmd = ProtoCommon.STORAGE_PROTO_CMD_UPLOAD_FILE;
		if (file_ext_name == null) {
			int nPos = file_name.lastIndexOf('.');
			if (nPos > 0 && file_name.length() - nPos <= ProtoCommon.FDFS_FILE_EXT_NAME_MAX_LEN + 1) {
				file_ext_name = file_name.substring(nPos + 1);
			}
		}

		try {
			String[] parts = this.do_upload_file(cmd, group_name, null, null, file_ext_name, file_size,
					new UploadStream(inputStream, file_size), meta_list);
			if (parts != null) {
				return parts[0] + SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + parts[1];
			}

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
