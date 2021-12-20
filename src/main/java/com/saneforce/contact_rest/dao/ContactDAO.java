package com.saneforce.contact_rest.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.postgresql.ds.PGConnectionPoolDataSource;

import com.saneforce.contact_rest.entity.Contact;

public class ContactDAO {

	PGConnectionPoolDataSource source;

	public ContactDAO() {
		
		Properties properties =new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream("/application.properties"));
			
			source = new PGConnectionPoolDataSource();
			
			source.setDatabaseName(properties.getProperty("database.name"));
			source.setServerNames(new String[] {properties.getProperty("database.server")});
			source.setUser(properties.getProperty("database.username"));
			source.setPassword(properties.getProperty("database.password"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int save(Contact contact) {
		
		int insertedCount =0;
		
		try(Connection connection = source.getConnection()) {
			PreparedStatement insert = connection.prepareStatement("Insert Into contact(name,mobile_number,email) values(?,?,?)");
			
			insert.setString(1, contact.getName());
			insert.setString(2, contact.getMobileNumber());
			insert.setString(3, contact.getEmail());
			
			insertedCount = insert.executeUpdate();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		return insertedCount;
	}
	
	public int update(Contact contact, int id) {
		
		int updatedCount =0;
		
		try(Connection connection = source.getConnection()) {
			String query = "UPDATE contact SET ";
			
			if(contact.getId() != null) {
				query +="id ="+contact.getId()+",";
			}
			
			if(contact.getName() != null) {
				query +="name ='"+contact.getName()+"',";
			}
			
			if(contact.getMobileNumber() != null) {
				query +="mobile_number ='"+contact.getMobileNumber()+"',";
			}
			
			if(contact.getEmail() != null) {
				query +="email ='"+contact.getEmail()+"'";
			}
			
			
			if(id > 0) {
				query += " WHERE id = "+id;
				
				PreparedStatement update = connection.prepareStatement(query);
				
				updatedCount = update.executeUpdate();
			}
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		return updatedCount;
	}

	public List<Contact> getList(){
		
		List<Contact> contactList = new ArrayList<Contact>();
		
		try(Connection connection = source.getConnection()) {
			
			PreparedStatement select = connection.prepareStatement("Select id,name,email,mobile_number from contact");
			ResultSet result = select.executeQuery();
			
			while(result.next()) {
				Contact contact = new Contact();
				
				contact.setId(result.getInt(1));
				contact.setName(result.getString(2));
				contact.setEmail(result.getString(3));
				contact.setMobileNumber(result.getString(4));
				
				contactList.add(contact);
			}
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		return contactList;
	}
	
	public Contact getContact(int id) {
	
		Contact contact=null;
		
		try(Connection connection = source.getConnection()) {
			
			PreparedStatement select = connection.prepareStatement("Select id,name,email,mobile_number from contact where id="+id);
			ResultSet result = select.executeQuery();
			
			
			while(result.next()) {
				contact = new Contact();
				
				contact.setId(result.getInt(1));
				contact.setName(result.getString(2));
				contact.setMobileNumber(result.getString(3));
				contact.setEmail(result.getString(4));
				
			}
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		return contact;
	}
	
	public void delete(int id) {
		
		try(Connection connection = source.getConnection()) {
			
			PreparedStatement select = connection.prepareStatement("Delete from contact where id="+id);
			select.executeUpdate();
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
	}
	
}
