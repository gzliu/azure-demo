/**   
* @Title: MysqlLink.java 
* @Package com.database 
* @Description: TODO
* @author Guzman liu
* @date 2017年4月19日 上午11:50:45 
* @version V1.0   
*/ 

package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;


/** 
 * @ClassName: MysqlLink 
 * @Description: 数据库连接操作
 * @author Guzman liu
 * @date 2017年4月19日 上午11:50:45 
 *  
 */
public class MysqlLink {
   static Connection conn = null;
   
   private static Logger logger = Logger.getLogger(MysqlLink.class);
	static {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://myappmysql.mysqldb.chinacloudapi.cn:3306/test",
					"myappmysql%myappsql", "Pass9876");
		} catch (ClassNotFoundException | SQLException cnfe) {
			cnfe.printStackTrace();
		}
	}
	public static void main(String[] args) {
		/*try {
			conn.prepareStatement("select 1");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			ps = conn.prepareStatement("select * from t_user");
			rs = ps.executeQuery();
			while(rs.next()){
				String id = rs.getString(1);
				String name = rs.getString(2);
				String password= rs.getString(3);
				UserTPojo p = new UserTPojo();
				p.setId(id);
				p.setName(name);
				p.setPassword(password);
				logger.info(p.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
