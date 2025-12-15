package aplication;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller seller = sellerDao.FindById(3);
		
		System.out.println(seller);
		
		System.out.println("========== teste seller Find by department = ==========");
		System.out.println("");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for(Seller sel: list) {
			System.out.println(sel);
		}
		
		System.out.println("========== teste seller FindAll = ==========");
		System.out.println("");
		
		list = sellerDao.findAll();
		for(Seller sel: list) {
			System.out.println(sel);
		}
		
		System.out.println("========== teste seller FindAll = ==========");
		System.out.println();
		
		Seller newSeller = new Seller(null, "Bruno", "Bruno@mail", new Date(), 4000.0, department);
		sellerDao.Insert(newSeller);
		System.out.println(newSeller.getId());
		
		System.out.println("========== teste seller Update = ==========");
		System.out.println();
		
		seller = sellerDao.FindById(1);
		seller.setName("Ada");
		sellerDao.Update(seller);
		System.out.println("update complete");
		
	}
}
