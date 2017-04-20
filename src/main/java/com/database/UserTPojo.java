/**   
* @Title: UserTPojo.java 
* @Package com.database 
* @Description: TODO
* @author Guzman liu
* @date 2017年4月19日 下午2:03:09 
* @version V1.0   
*/ 

package com.database;

/** 
 * @ClassName: UserTPojo 
 * @Description: TODO
 * @author Guzman liu
 * @date 2017年4月19日 下午2:03:09 
 *  
 */
public class UserTPojo {
	private String id;
	private String name;
	private String password;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString() {
		return "UserTPojo [id=" + id + ", name=" + name + ", password="
				+ password + "]";
	}
	
}
