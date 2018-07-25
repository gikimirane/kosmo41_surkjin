import java.sql.*;

public class TransactionEx {
	
	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();			
		}
	}
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement pstmt = null;
		Boolean success = false;
		
		try {
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe",
					"scott","tiger");
			con.setAutoCommit(false);

			String sql = "insert into test3 values('홍길동','1111')";
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			System.out.println("111111111");
			
			sql = "insert into test3 values('전우치','2222')";
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			System.out.println("222222222");
			
			sql = "insert into test3 values('손오공','3333')";
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			System.out.println("333333333");
			
			success = true;
			
		}catch(SQLException sqle){
			System.out.println("Connection Error");
			sqle.printStackTrace();
		}finally {
			try {
				if(success) {
					System.out.println("444444444");
					con.commit();
				}else {
					System.out.println("555555555");
					con.rollback();
				}
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(SQLException sqle){}
		}
	}

}
