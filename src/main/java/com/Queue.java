package com;

import org.apache.commons.lang.StringUtils;

import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.services.servicebus.ServiceBusConfiguration;
import com.microsoft.windowsazure.services.servicebus.ServiceBusContract;
import com.microsoft.windowsazure.services.servicebus.ServiceBusService;
import com.microsoft.windowsazure.services.servicebus.models.BrokeredMessage;
import com.microsoft.windowsazure.services.servicebus.models.CreateQueueResult;
import com.microsoft.windowsazure.services.servicebus.models.QueueInfo;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveMessageOptions;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveMode;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveQueueMessageResult;

/** 
* @ClassName: Queue 
* @Description: 
*	服务总线 > queue	
* detail to see : https://www.azure.cn/documentation/articles/service-bus-java-how-to-use-queues/
* @author Guzman liu
* @date 2017年4月18日 下午2:25:35 
*  
*/ 
public class Queue {
	
	private static ServiceBusContract service = null;
	static {
		Configuration config =
	            ServiceBusConfiguration.configureWithSASAuthentication(
	                    "queue1",// 命名空间
	                    "RootManageSharedAccessKey",// hard code
	                    "Ghi/1EGz48phrAZessEDAbD3ZfpO+/OtytA1lUCbD00=",// key string
	                    ".servicebus.chinacloudapi.cn"// hard code,
	                    );
		service = ServiceBusService.create(config);
	}
	

	/**
	 * create queue 
	 */
	private void createQueue(){
		try
		{
			QueueInfo queueInfo = new QueueInfo("TestQueue");// 
		    CreateQueueResult result = service.createQueue(queueInfo);
		}
		catch (ServiceException e)
		{
		    System.out.print("ServiceException encountered: ");
		    System.out.println(e.getMessage());
		    System.exit(-1);
		}
	}

	private static void sendMessage(String msg) {

		try {
			if (StringUtils.isEmpty(msg)) {
				return;
			}
			
//			QueueInfo queueInfo = new QueueInfo("demo");
			// CreateQueueResult result = service.createQueue(queueInfo);
			BrokeredMessage message = new BrokeredMessage(msg);
			service.sendQueueMessage("demo", message);
		} catch (ServiceException e) {
			System.out.print("ServiceException encountered: ");
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}
	
	public static void main(String[] args)  {
		
		sendMessage("Hello World!!");
		new RecievMessage(service).start();
	}
	

}


class RecievMessage extends Thread{
	private ServiceBusContract service = null;
	public RecievMessage(ServiceBusContract service) {
		this.service = service;
	}

	public void run() {

		 try
		 {
		     ReceiveMessageOptions opts = ReceiveMessageOptions.DEFAULT;
		     opts.setReceiveMode(ReceiveMode.PEEK_LOCK);

		     while(true)  { 
		         ReceiveQueueMessageResult resultQM =  service.receiveQueueMessage("demo", opts);
		         BrokeredMessage message = resultQM.getValue();
		         if (message != null && message.getMessageId() != null)
		         {
		             System.out.println("MessageID: " + message.getMessageId());    
		             // Display the queue message.
		             System.out.print("From queue: ");
		             byte[] b = new byte[200];
		             String s = null;
		             // read message
		             int numRead = message.getBody().read(b);
		             while (-1 != numRead)
		             {
		                 s = new String(b);
		                 s = s.trim();
		                 System.out.print(s);
		                 numRead = message.getBody().read(b);
		             }
		             //System.out.println("Custom Property: " + message.getProperty("MyProperty"));
		             // Remove message from queue.
		             System.out.println("Deleting this message.");
		             service.deleteMessage(message);
		         }  
		         else  
		         {        
		             System.out.println("Finishing up - no more messages.");        
		             break; 
		             // Added to handle no more messages.
		             // Could instead wait for more messages to be added.
		         }
		     }
		 }
		 catch (ServiceException e) {
			 e.printStackTrace();
		 }
		 catch (Exception e) {
			 e.printStackTrace();
		 }   
	
	}
	
}
