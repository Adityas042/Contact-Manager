package com.example.contactmangernew.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.contactmangernew.db.entity.Contact;

@Database(entities ={ Contact.class},version = 1)
public abstract class ContactAppDatabase extends RoomDatabase {
//    linking the dao with our database
    public abstract Contact_DAO getContactDao();







}
