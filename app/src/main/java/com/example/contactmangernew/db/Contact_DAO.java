package com.example.contactmangernew.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contactmangernew.db.entity.Contact;

import java.util.List;

@Dao
public interface Contact_DAO {
    @Insert
    public long  insertContact(Contact contact);

    @Update
    public int updateContact(Contact contact);

    @Delete
    public void deleteContact(Contact contact);

    @Query("select * from contacts ")
    public List<Contact> getContacts();

    @Query("select * from contacts where contact_id==:id")
    public Contact getContact(long id);




}
