package kvnb.hostelservicemanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    Button a;
    TextView textView;
    private String ausername;
    private String password;
    AnimationDrawable animationDrawable;
    RelativeLayout relativeLayout;
    private String str = "Message Checking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout=findViewById(R.id.layout);
        animationDrawable=(AnimationDrawable)relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        a = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.register);
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        String user1 = pref.getString("user", "");
        if (!user1.equals("")) {
            ausername = user1;
            sendMessage();
        } else {


            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    TextView textView1 = (TextView) findViewById(R.id.username1);
                    TextView textView2 = (TextView) findViewById(R.id.password1);
                    final String u = textView1.getText().toString();
                    final String p = textView2.getText().toString();
                    if (!u.equals("")) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(u);


                        reference.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    User user = dataSnapshot.getValue(User.class);
                                    try {
                                        Trippledes Des = new Trippledes();
                                        password = Des.decrypt(user.getPassword());
                                        Log.v("Checkingmsg","11");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (password.equals(p)) {
                                        ausername = u;
                                        editor.putString("user", u);
                                        editor.commit();


                                        sendMessage();
                                    } else {
                                        Snackbar.make(view, "Wrong Password", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }

                                } else {
                                    Snackbar.make(view, "Wrong Username If not Registered please register", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value
                                Snackbar.make(view, "Lost Network Connectivity", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });

                    } else {
                        Snackbar.make(view, "Please Enter a username", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });

    }

    public void sendMessage() {
        Intent intent = new Intent(this, ParentActivity.class);
        intent.putExtra(str, ausername);
        startActivity(intent);
    }

    public void register(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
