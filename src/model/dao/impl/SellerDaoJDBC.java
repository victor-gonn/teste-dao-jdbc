package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	
	private Connection conn;
	
	//esse construtor permite instanciar Connection em qual ponto
	//da aplicação, bastando chamar apenas o SellerDaoJDBC.conn
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	@Override
	public void Insert(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ " VALUES "
					+ "(?, ?, ?, ?, ?) ",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(0));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Error inexperado");
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePrepareStatement(st);
			
		}
		
	}

	@Override
	public void Update(Seller obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ?");
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(0));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
			
			
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePrepareStatement(st);
			
		}
		
	}

	@Override
	public void DeleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller FindById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					 + "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ? ");
					
			st.setInt(1, id);
			rs  = st.executeQuery();
			if(rs.next()) {
				Department dep = instanceDepartment(rs);
				Seller sel = instanceSeller(rs, dep);
				return sel;
			} return null;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePrepareStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	private Seller instanceSeller(ResultSet rs, Department dep) throws SQLException {
		Seller sel = new Seller();
		sel.setId(rs.getInt("Id"));
		sel.setName(rs.getString("Name"));
		sel.setEmail(rs.getString("Email"));
		sel.setBirthdate(rs.getDate("Birthdate"));
		sel.setBaseSalary(rs.getDouble("BaseSalary"));
		sel.setDepartment(dep);
		return sel;
	}
	private Department instanceDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}
	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					 + "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					
					+ "Order by name");
					
			
			rs  = st.executeQuery();
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instanceDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller sel = instanceSeller(rs, dep);
				list.add(sel);
			} return list;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePrepareStatement(st);
			DB.closeResultSet(rs);
		}
	}
	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					 + "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "Order by name");
					
			st.setInt(1, department.getId());
			rs  = st.executeQuery();
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if(dep == null) {
					dep = instanceDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller sel = instanceSeller(rs, dep);
				list.add(sel);
			} return list;
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closePrepareStatement(st);
			DB.closeResultSet(rs);
		}
		
	
	}

}
