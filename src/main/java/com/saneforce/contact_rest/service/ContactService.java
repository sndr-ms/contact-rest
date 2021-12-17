package com.saneforce.contact_rest.service;

import java.util.List;

import com.saneforce.contact_rest.entity.Contact;
public interface ContactService {

	List<Contact> getList();
	Contact getContact(int id);
	int save(Contact contact);
	int update(Contact contact, int id);
	void delete(int id);
}
