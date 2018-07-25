import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class BatchInsertEx {

	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();			
		}
	}
	
	public static void main(String[] args) {

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		Properties pro = null;
		FileInputStream fis = null;
		boolean commit = false;
		
		try {
			
			pro = new Properties() ;
			fis = new FileInputStream("D:\\jsj\\study\\Java\\jdbc.properties");
			pro.load(fis);
			String url = pro.getProperty("url");
			String user = pro.getProperty("user");
			String pswd = pro.getProperty("password");
			
			con = DriverManager.getConnection(url, user, pswd);
			stmt = con.createStatement();
			con.setAutoCommit(false);
			
			sql = "create table test4(id varchar2(10),password varchar2(10))";		
			stmt.executeUpdate(sql);		
			
			stmt.addBatch("insert into test4 values('홍길동', '1111')");
			stmt.addBatch("insert into test4 values('전우치', '2222')");
			stmt.addBatch("insert into test4 values('손오공', '3333')");
			stmt.addBatch("insert into test4 values('이지힘', '4444')");

			@SuppressWarnings("unused")
			int[] updCounts = stmt.executeBatch();
			commit = true;
			con.commit();

			rs = stmt.executeQuery("select * from test4");	
			while(rs.next()) 
				System.out.println(	"id: " + rs.getString(1) + ", "
							+ "password: " + rs.getString(2));
						
			sql = "drop table test4";
			stmt.executeUpdate(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(!commit)	con.rollback();
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null) con.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}	
	}

}
