package com.saneforce.contact_rest.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.saneforce.contact_rest.entity.Contact;
import com.saneforce.contact_rest.service.ContactService;
import com.saneforce.contact_rest.dao.ContactDAO;

public class ContactServiceImpl implements ContactService {

	private ContactDAO dao= new ContactDAO();
	
	@Override
	public List<Contact> getList() {
		
		List<Contact> contactList = dao.getList();
		
		return contactList;
	}

	@Path("/save")
	@Consumes("application/json")
	@POST
	@Produces("application/json")
	public HashMap<String,Object> saveContact(Contact contact){
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		int insertedCount = this.save(contact);
		if(insertedCount > 0) {
			result.put("success", true);
			result.put("message", "Contact saved");
		}
		else {
			result.put("success", false);
			result.put("message", "Contact not saved");
		}
		
		return result;
	}
	
	@Path("/{id}")
	@GET
	@Produces("application/json")
	public HashMap<String,Object> getContactDetail(@PathParam("id") int id){
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		Contact contact = this.getContact(id);
		if(contact != null) {
			result.put("success", true);
			result.put("detail", contact);
		}
		else {
			result.put("success", false);
			result.put("message", "Contact not found");
		}
		
		return result;
	}
	
	@Path("/list")
	@GET
	@Produces("application/json")
	public HashMap<String,Object> listAll(){
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		List<Contact> contactList = this.getList();
		if(contactList.size() > 0) {
			result.put("success", true);
			result.put("list", contactList);
		}
		else {
			result.put("success", false);
			result.put("message", "No data found");
		}
		
		return result;
	}
	
	
	@Path("/update/{id}")
	@Consumes("application/json")
	@PUT
	@Produces("application/json")
	public HashMap<String,Object> updateContact(Contact contact,@PathParam("id") int id){
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		int insertedCount = this.update(contact,id);
		if(insertedCount > 0) {
			result.put("success", true);
			result.put("message", "Contact updated");
		}
		else {
			result.put("success", false);
			result.put("message", "Contact not updated");
		}
		
		return result;
	}
	
	@Path("/delete/{id}")
	@DELETE
	@Produces("application/json")
	public HashMap<String,Object> deleteContact(@PathParam("id") int id){
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		this.delete(id);
		result.put("success", true);
		result.put("message", "Contact Deleted");
		
		return result;
	}
	
	@Override
	public Contact getContact(@PathParam("id") int id) {
		
		return dao.getContact(id);
	}

	@Override
	public int save(Contact contact) {

		return dao.save(contact);
	}

	@Override
	public int update(Contact contact,int id) {
		return dao.update(contact,id);
	}

	@Override
	public void delete(int id) {
		dao.delete(id);
	}

}
