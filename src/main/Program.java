package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;
import db.DbIntegrityException;

public class Program {

	public static void main(String[] args) {
		
		Connection connMain = null;
		Statement state = null;
		try {
			connMain = DB.getConnection();
			connMain.setAutoCommit(false);
			state = connMain.createStatement();
			
			int row1 = state.executeUpdate("UPDATE seller set BaseSalary = 2090 WHERE DepartmentId = 1 ");
			
			int x = 1;
			/*if(x<2) {
				throw new SQLException("Fake error");
			}*/
			
			int row2 = state.executeUpdate("UPDATE seller set BaseSalary = 1000 WHERE DepartmentId = 2 ");
			
			connMain.commit();
			
			System.out.println("row1 " + row1);
			
			System.out.println("row2 " + row2);
					
		} catch(SQLException e) {
			try {
				connMain.rollback();
				throw new DbException("Transaction rolled back: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Error trying rollback");
			}
		}
		
		finally {
			DB.closeStatment(state);
			DB.closeConnection();
		}
		
		
	}

}
