package com;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;

/** 
* @ClassName: StorageQueueServlet 
* @Description: 已开启存储账户  发送消息
* @author Guzman liu
* @date 2017年4月18日 下午2:27:37 
*  
*/ 
public class StorageQueueServlet extends HttpServlet {
	private static final long serialVersionUID = 601876779793474170L;
	private static Logger logger = Logger.getLogger(StorageQueueServlet.class);
	// Define the connection-string with your values.
	public static final String storageConnectionString = 
			"DefaultEndpointsProtocol=http;"// 
			+ "AccountName=keystore;"
			+ "AccountKey=8wl1t8XFPgF0r1jPeInimvjU6KWxA3B9FVDno5MBnnLzWtgh1zp4xshdT9FK+erzr3feH4HhD7DRQ+5Qw56cyA==;"
			+ "EndpointSuffix=core.chinacloudapi.cn";

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp); 
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Retrieve storage account from connection-string.
//		String message  = (String)req.getAttribute("message");
		String m = req.getParameter("message");
		logger.info(m);
		Writer w = resp.getWriter();
		runQueue(m);
		w.append("<h1>SEND MESSGAE Successfully</h1>");
		w.close();
	}
	
	private void runQueue(String msg) throws IOException {
		try {
			CloudStorageAccount storageAccount = CloudStorageAccount
					.parse(storageConnectionString);

			// Create the queue client.
			CloudQueueClient queueClient = storageAccount
					.createCloudQueueClient();

			// Retrieve a reference to a queue.
			CloudQueue queue = queueClient.getQueueReference("demo");
			// Create the queue if it doesn't already exist.
			queue.createIfNotExists();

			sendMessageQueue(queue,msg);

		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	private void sendMessageQueue(CloudQueue queue,String msg) throws StorageException{
		// Create a message and add it to the queue.
	    logger.info("sendding message ...");
		CloudQueueMessage message = new CloudQueueMessage(msg);
	    queue.addMessage(message);
	    logger.info("sendding message successfully.");
	    
	}
	
	
}
