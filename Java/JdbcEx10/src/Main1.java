import java.sql.*;

class ConnTest1{
	
	ConnectionPool1 cp = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public void select() {
		try {
			
			cp = ConnectionPool1.getInstance();
			con = cp.getConnection();
			pstmt = con.prepareStatement("select count(*) from department");
			rs = pstmt.executeQuery();	
			while(rs.next()) 
				System.out.println(	"count: " + rs.getInt(1));
									
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) cp.releaseConnection(con);
			}catch(SQLException _sqle){
				_sqle.printStackTrace();
			}
		}
	}
}

class ConnTest2 extends Thread{
	ConnectionPool1 cp = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	@Override
	public void run() {
		try {
			String name = Thread.currentThread().getName();
			cp = ConnectionPool1.getInstance();
			con = cp.getConnection();
			pstmt = con.prepareStatement("select count(*) from department");
			rs = pstmt.executeQuery();	
			while(rs.next()) 
				System.out.println(name + ":" + rs.getInt(1) + ":" + con );
									
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) cp.releaseConnection(con);
			}catch(SQLException _sqle){
				_sqle.printStackTrace();
			}
		}
	}
}

public class Main1 {

	public static void main(String[] args) throws InterruptedException {

		ConnTest1 ct = new ConnTest1();
		ct.select();
		
		for (int i=0; i<80; i++) {
			Thread test = new ConnTest2();
			test.start();
			Thread.sleep(1);
		}
	}

}
