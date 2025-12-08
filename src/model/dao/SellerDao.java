package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {
	void Insert(Seller sel);
	void Update(Seller sel);
	void DeleteById(Integer id);
	Seller FindById(Integer id);
	List<Seller> findAll();
}
