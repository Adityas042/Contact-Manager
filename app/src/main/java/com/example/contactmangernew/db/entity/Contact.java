package com.example.contactmangernew.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import kotlin.experimental.ExperimentalTypeInference;

@Entity(tableName = "CONTACTS")
public class Contact {
//    public static final String TABLE_NAME="CONTACTS";
//    public  static final String COLUMN_ID="contact_id";
//    public static final String COLUMN_NAME="contact_name";
//    public static final String COLUMN_EMAIL="contact_email";
    @ColumnInfo(name="contact_name")
    public static String name;
    @ColumnInfo(name="contact_email")
    public static String email;
    @ColumnInfo(name = "contact_id")
    @PrimaryKey(autoGenerate = true)
    public static int id;

    @Ignore
    public  Contact(){

    }

    @Ignore
    public Contact(String name, int id,String email) {
        this.name = name;
        this.email = email;
        this.id = id;
    }
@Ignore
    public Contact(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//    SQL query
//    public static final String CREATE_TABLE=
//        "CREATE_TABLE"+TABLE_NAME+"("
//        +COLUMN_ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"
//        +COLUMN_NAME+"TEXT,"
//        +COLUMN_EMAIL+"DATETIME DEFAULT CURRENT_TIMESTAMP"
//        +")";
//

}

