package com;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;


/** 
* @ClassName: StorageQueueListener 
* @Description: 已开启存储账户  接收消息
* @author Guzman liu
* @date 2017年4月18日 下午2:24:38 
*  
*/ 
public class StorageQueueListener implements ServletContextListener {
	private static Logger logger = Logger.getLogger(StorageQueueListener.class);
	public static final String storageConnectionString = 
			"DefaultEndpointsProtocol=http;"
			+ "AccountName=keystore;"
			+ "AccountKey=8wl1t8XFPgF0r1jPeInimvjU6KWxA3B9FVDno5MBnnLzWtgh1zp4xshdT9FK+erzr3feH4HhD7DRQ+5Qw56cyA==;"
			+ "EndpointSuffix=core.chinacloudapi.cn";

	static CloudQueueClient queueClient = null;
	static {
		// Retrieve storage account from connection-string.
		CloudStorageAccount storageAccount = null;
		try {
			storageAccount = CloudStorageAccount
					.parse(storageConnectionString);
			// Create the queue client.
			queueClient = storageAccount
					.createCloudQueueClient();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		
		
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("===start execute queue===");
//		arg0.getServletContext().setAttribute("queue", queue);
		Runnable run = new Runnable() {
			public void run() {
				try {
					
					while(true){
						
						for (CloudQueue queue : queueClient.listQueues()) {
							executeQueue(queue);
						}
						
					}
					
				} catch (Exception e) {
					logger.error(e);
				}
			}
		};
		new Thread(run).start();
		
	}
	
	private void executeQueue(CloudQueue queue) {
		try {

			// Download the approximate message count from the server.
			queue.downloadAttributes();

			// Retrieve the newly cached approximate message count.
			long cachedMessageCount = queue.getApproximateMessageCount();

			// Display the queue length.
			if(cachedMessageCount > 0){
				
				logger.info(String.format("Queue length: %d", cachedMessageCount));
			}

			// Retrieve the messages in the queue with a visibility timeout of
			// 30 seconds and delete them
			CloudQueueMessage retrievedMessage;
			while ((retrievedMessage = queue.retrieveMessage(30,null /* options */, null /* opContext */)) != null) {
				// Process the message in less than 30 seconds, and then delete
				// the message.
				logger.info("received message content :"+retrievedMessage.getMessageContentAsString());
				
				queue.deleteMessage(retrievedMessage);
			}

		} catch (Exception e) {
			// Output the stack trace.
			logger.error(e);
		}
	}

}
