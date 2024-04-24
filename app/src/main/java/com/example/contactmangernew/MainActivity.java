package com.example.contactmangernew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.contactmangernew.adapter.ContactAdapter;
import com.example.contactmangernew.db.ContactAppDatabase;
//import com.example.contactmangernew.db.DataBaseHelper;
import com.example.contactmangernew.db.entity.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
//    variables
    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contactArrayList;
    private RecyclerView recyclerView;
//    private DataBaseHelper db;
    private ContactAppDatabase contactAppDatabase;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar= findViewById(R.id.toolbar);


        recyclerView=findViewById(R.id.r1);
//        callbacks
        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);



//                These are four contacts already created in the app when installed (Build in contacts)
                createContact("pranav","prabnav@gmail.com");
                createContact("pranav 1","prabna1v@gmail.com");
                createContact("pranav 2","prabna2v@gmail.com");
                createContact("pranav 3","prabna3v@gmail.com");

                Log.i("TAG","DATABASE HAS BEEN CREATED");

            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);

                Log.i("TAG","DATABASE HAS BEEN OPENED");
            }
        };




//        database
        contactAppDatabase= Room.databaseBuilder(
                getApplicationContext(),
                ContactAppDatabase.class,
                "ContactDB"
        ).addCallback(myCallBack).build();

//        db=new DataBaseHelper(this);
//        Contact List
//        contactArrayList.addAll(db.getAllContacts());
//        displaying all contact list
//        contactArrayList.addAll(contactAppDatabase.getContactDao().getContacts());
                 DisplayAllContactsInBackground();







        contactAdapter=new ContactAdapter(this,contactArrayList,MainActivity.this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);
        FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAndEditContacts(false,null,-1);
            }
        });



    }

    public void addAndEditContacts(final boolean isUpdated, Contact contact, int position) {
        LayoutInflater layoutInflater=LayoutInflater.from(getApplicationContext());
        View view=layoutInflater.inflate(R.layout.add_contact,null,false);
        AlertDialog.Builder alerDialog=new AlertDialog.Builder(MainActivity.this);
        alerDialog.setView(view);


        TextView contact_title=view.findViewById(R.id.new_contact);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final EditText newcontactname=view.findViewById(R.id.name);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final  EditText newcontactemail=view.findViewById(R.id.email);
        contact_title.setText(!isUpdated?"Add New Contact":"Edit Contact");

        if(isUpdated && contact !=null)
        {
            newcontactname.setText(Contact.getName());
            newcontactemail.setText(Contact.getEmail());
        }
        alerDialog.setCancelable(false).setPositiveButton(
                isUpdated ? "Update" : "save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }
        )
                .setNegativeButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ((isUpdated))
                        {
                            DeleteContact(contact,position);
                        }
                        else {
                            dialog.cancel();
                        }
                    }
                });
        final AlertDialog alertDialog=alerDialog.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(newcontactname.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "please Enter a name", Toast.LENGTH_SHORT).show();
                    return;

                }
                else {
                    alertDialog.dismiss();
                }
                if (isUpdated && contact !=null)
                {
                    UpdateContact(newcontactname.getText().toString(),newcontactemail.getText().toString(),position);
                }
                else {
                    createContact(newcontactname.getText().toString(),newcontactemail.getText().toString());
                }
            }
        });


    }

    private void DeleteContact(Contact contact, int position) {
        contactArrayList.remove(position);
        contactAppDatabase.getContactDao().deleteContact(contact);
        contactAdapter.notifyDataSetChanged();

    }

    private void createContact(String name, String email) {
        long id =contactAppDatabase.getContactDao()
                .insertContact(new Contact(name, email,0));
        Contact contact=contactAppDatabase.getContactDao().getContact(id);
        if(contact!=null)
        {
            contactArrayList.add(0,contact);
            contactAdapter.notifyDataSetChanged();
        }

    }

    private void UpdateContact(String name, String email, int position) {
        Contact contact=contactArrayList.get(position);
        contact.setName(name);
        contact.setEmail(email);
        contactAppDatabase.getContactDao().updateContact(contact);
        contactArrayList.set(position,contact);
        contactAdapter.notifyDataSetChanged();
    }

    private void DisplayAllContactsInBackground()
    {
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        Handler handler=new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
//                background work
                       contactArrayList.addAll(contactAppDatabase.getContactDao().getContacts());


//                executed after the background work has finished
                handler.post(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {


                        contactAdapter.notifyDataSetChanged();
                    }
                });


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return  true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id ==R.id.i1)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}