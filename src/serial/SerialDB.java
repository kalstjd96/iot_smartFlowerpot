package serial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;




public class SerialDB {
	Connection conn = null;
	PreparedStatement pstmt = null;
	String jdbc_driver = "oracle.jdbc.driver.OracleDriver";
	String jdbc_url = "jdbc:oracle:thin:@168.126.146.45:1521:orcl";
	
	void connect() {
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(jdbc_url,"20152468","960209");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void disconnect () {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<SerialBean> getDBList() {
		connect();
		ArrayList<SerialBean> datas = new ArrayList<SerialBean>();
		String sql = "SELECT * FROM Ard_TB ORDER BY NO DESC";
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				SerialBean bean = new SerialBean();
				bean.setNo(rs.getInt("NO"));
				bean.setTime(rs.getString("TIME"));
				bean.setTemperature(rs.getString("TEMPERATURE"));
				bean.setHumidity(rs.getString("HUMIDITY"));
				datas.add(bean);
			}
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
	
		return datas;
	}
	
	
	
	public boolean insertDB(SerialBean bean) {
		connect();
		String sql = "insert into Ard_TB values(ard_seq.nextval,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getTime());
			pstmt.setString(2, bean.getTemperature());
			pstmt.setString(3, bean.getHumidity());
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			disconnect();
		}
		return true;
	}
	
	
}
