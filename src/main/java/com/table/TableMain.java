/**   
* @Title: Table.java 
* @Package com 
* @Description: TODO
* @author Guzman liu
* @date 2017年4月18日 下午4:02:40 
* @version V1.0   
*/ 

package com.table;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;

/** 
 * @ClassName: Table 
 * @Description: 存储账户 > table
 * @author Guzman liu
 * @date 2017年4月18日 下午4:02:40 
 *  
 */
public class TableMain {
	public static final String storageConnectionString = 
			"DefaultEndpointsProtocol=http;"
			+ "AccountName=keystore;"
			+ "AccountKey=8wl1t8XFPgF0r1jPeInimvjU6KWxA3B9FVDno5MBnnLzWtgh1zp4xshdT9FK+erzr3feH4HhD7DRQ+5Qw56cyA==;"
			+ "EndpointSuffix=core.chinacloudapi.cn";// 存储账户的连接字符串
	
	private static void createTable(){
		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		       CloudStorageAccount.parse(storageConnectionString);

		   // Create the table client.
		   CloudTableClient tableClient = storageAccount.createCloudTableClient();

		   // Create the table if it doesn't exist.
		   String tableName = "people";
		   CloudTable cloudTable = tableClient.getTableReference(tableName);
		   cloudTable.createIfNotExists();
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		insertEntity();
		
	}
	
	
	private static void insertEntity (){
		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		       CloudStorageAccount.parse(storageConnectionString);

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    // Create a cloud table object for the table.
		    CloudTable cloudTable = tableClient.getTableReference("people");

		    // Create a new customer entity.
		    CustomerEntity customer1 = new CustomerEntity("Harp", "Walter");
		    customer1.setEmail("Walter@contoso.com");
		    customer1.setPhoneNumber("425-555-0101");

		    // Create an operation to add the new customer to the people table.
		    TableOperation insertCustomer1 = TableOperation.insertOrReplace(customer1);

		    // Submit the operation to the table service.
		    cloudTable.execute(insertCustomer1);
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
	}
}
