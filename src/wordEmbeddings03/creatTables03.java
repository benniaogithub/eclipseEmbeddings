package wordEmbeddings03;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mysql.jdbc.CallableStatement;

public class creatTables03 {
	static final String url = "jdbc:mysql://127.0.0.1/wordembedding";
	static final String name = "com.mysql.jdbc.Driver";
	static final String user = "root";
	static final String password = "root";
	static Connection conn = null;
	static CallableStatement cstmt = null;
	static InputStreamReader read = null;
	static BufferedReader bufferedReader = null;
	static String wordListPath = "res/wordlist_final4.txt";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ArrayList<String> wordList = new ArrayList<String>();
		try {
			Class.forName(name);// 指定连接类型
			conn = DriverManager.getConnection(url, user, password);// 获取连接
			cstmt = (CallableStatement) conn
					.prepareCall("{call pro_createTables(?)}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			read = new InputStreamReader(new FileInputStream(wordListPath));
			bufferedReader = new BufferedReader(read);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String lineTxt = null;
			String word = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				word = "t_"+lineTxt.trim();
				// wordList.add(word);
				try {
					cstmt.setString(1, word);
					cstmt.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			bufferedReader.close();
			read.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			cstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
