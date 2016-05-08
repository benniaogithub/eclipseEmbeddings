package wordEmbeddings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.*;

import org.apache.log4j.Logger;

import com.mysql.jdbc.CallableStatement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.jmx.LoggerDynamicMBean;

public class InsertRelations  {
	public static final String url = "jdbc:mysql://127.0.0.1/wordembeding";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "su";
	public static Connection conn = null;
	static CallableStatement cstmt = null;
	private static Logger logger = Logger.getLogger(InsertRelations.class);
	public static String filePath = "I:\\数据\\relations\\data\\results_part1";
	public static InputStreamReader read = null;
	public static BufferedReader bufferedReader = null;

	public static void main(String args[]) throws Exception {
		PropertyConfigurator.configure("config/log4j.properties");
		// 加载驱动
		try {
			Class.forName(name);// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		cstmt = (CallableStatement)conn.prepareCall("{call pro_insertRelation(?,?,?)}");
		proFiles(filePath);
		// 获得连接
		// 创建存储过程的对象
		
		// // 给存储过程的第一个参数设置值
		// cstmt.setString(1, "3333");
		// cstmt.setString(2, "3333");
		// cstmt.setString(3, "3333");
		// 注册存储过程的第二个参数
		// cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
		// 执行存储过程
		
		try {
			cstmt.close();
			conn.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	static void proFiles(String filePath) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				proFiles(file.getAbsolutePath());

			} else {
				// file.getAbsolutePath();
				System.out.println(file.getAbsolutePath());
				logger.debug(file.getAbsolutePath());
				try {
					read = new InputStreamReader(new FileInputStream(file));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// 考虑到编码格式
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				String word = null;
				String relation = null;
				String reference = null;
				try {
					while ((lineTxt = bufferedReader.readLine()) != null) {
						
						String[] item = lineTxt.split("\t");
						if(item.length<4){
							//System.out.println("not 4");
							continue;	
						}
					
						relation = item[1].trim().split(":")[0];
						word = item[2].trim().toLowerCase();
						reference = item[3].trim().toLowerCase();

						try {
							cstmt.setString(1, word);
							cstmt.setString(2, reference);
							cstmt.setString(3, relation);
							cstmt.execute();
						} catch (SQLException e) {
							logger.error(e.getMessage());
							logger.error(file.getAbsolutePath());
						}
					}
					read.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}