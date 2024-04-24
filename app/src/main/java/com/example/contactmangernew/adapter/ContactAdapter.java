package com.example.contactmangernew.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactmangernew.MainActivity;
import com.example.contactmangernew.R;
//import com.example.contactmangernew.db.DataBaseHelper;
import com.example.contactmangernew.db.entity.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
//1-variable
private Context context;
private ArrayList<Contact> contactArrayList;
//private DataBaseHelper dataBaseHelper;
private MainActivity mainActivity;
//2-viewHolder
public class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView name;
    public TextView Email;

    public MyViewHolder(@NonNull View itemView,TextView name,TextView Email) {
        super(itemView);
        this.name=itemView.findViewById(R.id.name);
        this.Email=itemView.findViewById(R.id.email);
    }


        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
    public ContactAdapter(Context context,ArrayList<Contact> contacts,MainActivity mainActivity)
    {
        this.context=context;
        this.contactArrayList=contacts;
        this.mainActivity=mainActivity
;

    }
    @NonNull
    @Override
    public ContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int positions) {
 final  Contact contact=contactArrayList.get(positions);
 holder.name.setText(Contact.getName());
 holder.Email.setText(Contact.getEmail());
 holder.itemView.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         mainActivity.addAndEditContacts(true,contact,positions);
     }
 });

    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }
}
