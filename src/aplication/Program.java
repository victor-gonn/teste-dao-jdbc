package aplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.entities.Department;

public class Program {

	public static void main(String[] args) {
		
		Department obj = new Department(1, "books");
		System.out.println(obj);
		
		
		
	}
}
