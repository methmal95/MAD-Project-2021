package com.example.bookingmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookingmate.Models.BookingDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBooking extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    Button button;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);

        ref = FirebaseDatabase.getInstance().getReference("BookingDetails");

        editText1 = (EditText)findViewById(R.id.Tid);
        editText2 = (EditText)findViewById(R.id.Tname);
        editText3 = (EditText)findViewById(R.id.Tphone);
        editText4 = (EditText)findViewById(R.id.Tpurpose);
        editText5 = (EditText)findViewById(R.id.Tarrive);
        editText6 = (EditText)findViewById(R.id.Tdestination);
        button = (Button)findViewById(R.id.addtime);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = editText1.getText().toString();
                String Name = editText2.getText().toString();
                String Contact = editText3.getText().toString();
                String Purpose = editText4.getText().toString();
                String Arrive = editText5.getText().toString();
                String Destination = editText6.getText().toString();

                if (Id.isEmpty()) {
                    editText1.setError("Id is required");
                }else if (Name.isEmpty()) {
                    editText2.setError("Name no is required");
                }else if (Contact.isEmpty()) {
                    editText3.setError("Contact number is required");
                }else if (Purpose.isEmpty()) {
                    editText4.setError("Purpose is required");
                }else if (Arrive.isEmpty()) {
                    editText5.setError("Arrive is required");
                }else if (Destination.isEmpty()) {
                    editText6.setError("Destination is required");
                }else {

                    BookingDetails bookingDetails = new BookingDetails(Id,Name,Contact,Purpose,Arrive,Destination);
                    ref.child(Id).setValue(bookingDetails);

                    Toast.makeText(AddBooking.this, "Successfully added", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddBooking.this, ManageBookings.class);
                    startActivity(intent);
                }
            }
        });
    }
}