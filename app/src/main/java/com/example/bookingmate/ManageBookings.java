package com.example.bookingmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingmate.Models.BookingDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageBookings extends AppCompatActivity {

    Button button;
    ListView listView;
    List<BookingDetails> user;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bookings);

        button = (Button)findViewById(R.id.addDetails);
        listView = (ListView)findViewById(R.id.listview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageBookings.this, AddBooking.class);
                startActivity(intent);
            }
        });

        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("BookingDetails");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    BookingDetails bookingDetails = studentDatasnap.getValue(BookingDetails.class);
                    user.add(bookingDetails);
                }

                MyAdapter adapter = new MyAdapter(ManageBookings.this, R.layout.custom_booking_details, (ArrayList<BookingDetails>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        Button button1;
        Button button2;
    }

    class MyAdapter extends ArrayAdapter<BookingDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<BookingDetails> user;


        public MyAdapter(Context context, int resource, ArrayList<BookingDetails> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_booking_details, null);

                holder.COL1 = (TextView) view.findViewById(R.id.id);
                holder.COL2 = (TextView) view.findViewById(R.id.name);
                holder.COL3 = (TextView) view.findViewById(R.id.purpose);
                holder.COL4 = (TextView) view.findViewById(R.id.arrive);
                holder.button1 = (Button) view.findViewById(R.id.deledit);
                holder.button2 = (Button) view.findViewById(R.id.deldelete);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("ID No:- "+user.get(position).getId());
            holder.COL2.setText("Name:- "+user.get(position).getName());
            holder.COL3.setText("Purpose:- "+user.get(position).getPurpose());
            holder.COL4.setText("Arrive:- "+user.get(position).getArrive());
            System.out.println(holder);

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final String idd = user.get(position).getId();
                                    FirebaseDatabase.getInstance().getReference("BookingDetails").child(idd).removeValue();
                                    //remove function not written
                                    Toast.makeText(myContext, "Item deleted successfully", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.custom_update_booking_details, null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText) view1.findViewById(R.id.updateTname);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.updateTphone);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.updateTpurpose);
                    final EditText editText4 = (EditText) view1.findViewById(R.id.updateTarrive);
                    final EditText editText5 = (EditText) view1.findViewById(R.id.updateTdestination);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.update);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("BookingDetails").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = (String) snapshot.child("name").getValue();
                            String phone = (String) snapshot.child("phone").getValue();
                            String purpose = (String) snapshot.child("purpose").getValue();
                            String arrive = (String) snapshot.child("arrive").getValue();
                            String destination = (String) snapshot.child("destination").getValue();

                            editText1.setText(name);
                            editText2.setText(phone);
                            editText3.setText(purpose);
                            editText4.setText(arrive);
                            editText5.setText(destination);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String name = editText1.getText().toString();
                            String phone = editText2.getText().toString();
                            String purpose = editText3.getText().toString();
                            String arrive = editText4.getText().toString();
                            String destination = editText5.getText().toString();

                            if (name.isEmpty()) {
                                editText1.setError("Name is required");
                            }else if (phone.isEmpty()) {
                                editText2.setError("Contact number is required");
                            }else if (purpose.isEmpty()) {
                                editText3.setError("Purpose is required");
                            }else if (arrive.isEmpty()) {
                                editText4.setError("Arrive time is required");
                            }else if (destination.isEmpty()) {
                                editText5.setError("Arrive Destination is required");
                            }else {

                                HashMap map = new HashMap();
                                map.put("name", name);
                                map.put("phone", phone);
                                map.put("purpose", purpose);
                                map.put("arrive", arrive);
                                map.put("driverName", destination);
                                reference.updateChildren(map);

                                Toast.makeText(ManageBookings.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                                alertDialog.dismiss();
                            }
                        }
                    });
                }
            });

            return view;

        }
    }
}