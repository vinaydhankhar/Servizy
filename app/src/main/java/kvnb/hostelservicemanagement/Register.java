package kvnb.hostelservicemanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    Button b3;
    TextView name;
    TextView username;
    TextView password;
    TextView mail;
    TextView phno;
    TextView rollno;
    TextView roomno;
    TextView hno;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        b3=(Button)findViewById(R.id.button4);
        b3.setOnClickListener(new OnClickListener(){
            public void onClick(final View view){

                try {
                    name = (TextView) findViewById(R.id.name);

                    username = (TextView) findViewById(R.id.username);
                    password = (TextView) findViewById(R.id.password);
                    mail = (TextView) findViewById(R.id.email);
                    phno = (TextView) findViewById(R.id.phnumber);
                    rollno = (TextView) findViewById(R.id.rollno);
                    roomno = (TextView) findViewById(R.id.roomno);
                    hno = (TextView) findViewById(R.id.hostelno);
                    final String name1 = name.getText().toString();
                    final String u = username.getText().toString();
                    final String pass = password.getText().toString();
                    final String mail1 = mail.getText().toString();
                    final String phno1 = phno.getText().toString();
                    final String rollno1 = rollno.getText().toString();
                    final String roomno1 =roomno.getText().toString();
                    final String hno1 = hno.getText().toString();


                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(u);
                    final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("registeruser");
                    RegisterUser ruser = new RegisterUser(name1, mail1, phno1, rollno1, roomno1, hno1);

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Snackbar.make(view, "Username Already Exists Choose Other username", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            } else {
                                RegisterUser ruser = new RegisterUser(name1, mail1, phno1, rollno1, roomno1, hno1);
                                User user = new User(pass);
                                reference.setValue(user);
                                reference1.child(u).setValue(ruser);
                                Snackbar.make(view, "Succesfully Registered press back", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value

                        }
                    });
                }
                catch (Exception e){
                    Snackbar.make(view, "Please Fill these values properly or there maybe connection error", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }


}
