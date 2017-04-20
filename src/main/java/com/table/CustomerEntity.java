/**   
* @Title: CustomerEntity.java 
* @Package com.table 
* @Description: TODO
* @author Guzman liu
* @date 2017年4月18日 下午4:04:48 
* @version V1.0   
*/ 

package com.table;

import com.microsoft.azure.storage.table.TableServiceEntity;

/** 
 * @ClassName: CustomerEntity 
 * @Description: TODO
 * @author Guzman liu
 * @date 2017年4月18日 下午4:04:48 
 *  
 */
public class CustomerEntity extends TableServiceEntity {
	
	public CustomerEntity(String lastName, String firstName) {
        this.partitionKey = lastName;
        this.rowKey = firstName;
    }

    public CustomerEntity() { }

    String email;
    String phoneNumber;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
