package kvnb.hostelservicemanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.google.firebase.appindexing.builders.PersonBuilder;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserComplaint extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String str="Message Checking";
    private String ausername;
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        CardView cardview;
        TextView messengerTextView;
        CircleImageView messengerImageView;
        TextView resolved;

        public MessageViewHolder(View v) {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.cardtextcomp1);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageViewcomp);
            cardview=(CardView)itemView.findViewById(R.id.cardviewcomp);
            messengerTextView = (TextView) itemView.findViewById(R.id.cardtextcomp2);

        }
    }

    private static final String TAG = "UserComplaint";
    public static final String MESSAGES_CHILD = "complaint";
    private static final int REQUEST_INVITE = 1;
    private static final int REQUEST_IMAGE = 2;
    private static final String LOADING_IMAGE_URL = "https://www.google.com/images/spin-32.gif";
    public static final int DEFAULT_MSG_LENGTH_LIMIT =100;
    public static final String ANONYMOUS = "anonymous";
    private String mUsername;
    private SharedPreferences mSharedPreferences;
    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<Complaint, UserComplaint.MessageViewHolder>
            mFirebaseAdapter;
    public void sendComplaint(){
        Intent in = new Intent(this,complaintreg.class);
        in.putExtra(str,ausername);
        startActivity(in);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_complaint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent in =getIntent();
        ausername=in.getStringExtra(str);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              sendComplaint();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //firebase cardview implementation
        Log.v("ErrorMessage","Checking1");
        // Set default username is anonymous.
        mUsername = ausername;
        Log.v("ErrorMessage","Checking2");
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView2);
        mLinearLayoutManager = new LinearLayoutManager(this);
        Log.v("ErrorMessage","Checking3");
        mLinearLayoutManager.setStackFromEnd(false);
        Log.v("ErrorMessage","Checking4");
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        //mMessageRecyclerView.addItemDecoration(new LineDividerItemDecoration(this, R.drawable.line_divider));
        Log.v("ErrorMessage","Checking5");

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference().child(MESSAGES_CHILD);
        Log.v("ErrorMessage","Checking2");
        SnapshotParser<Complaint> parser = new SnapshotParser<Complaint>() {
            @Override
            public Complaint parseSnapshot(DataSnapshot dataSnapshot) {
                Complaint friendlyMessage = dataSnapshot.getValue(Complaint.class);
                if (friendlyMessage != null) {
                    friendlyMessage.setId(dataSnapshot.getKey());
                }
                return friendlyMessage;
            }
        };
        Log.v("Error","Checking3");
        DatabaseReference messagesRef = mFirebaseDatabaseReference;
        FirebaseRecyclerOptions<Complaint> options =
                new FirebaseRecyclerOptions.Builder<Complaint>()
                        .setQuery(messagesRef, parser)
                        .build();
        Log.v("Error","Checking4");
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Complaint, UserComplaint.MessageViewHolder>(options) {
            @Override
            public UserComplaint.MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new UserComplaint.MessageViewHolder(inflater.inflate(R.layout.item_complaint, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(final UserComplaint.MessageViewHolder viewHolder,
                                            int position,
                                            Complaint friendlyMessage) {

                Log.v("Error", "Checking5");
                if (friendlyMessage.getComplaintType() != null) {
                    Log.v("Error", "Checking7");
                    if (friendlyMessage.getName().equals(ausername)){
                        Log.v("Error", "Checking5");
                    viewHolder.messageTextView.setText(friendlyMessage.getComplaintType());
                    viewHolder.cardview.setVisibility(CardView.VISIBLE);
                    viewHolder.cardview.setCardBackgroundColor(Color.parseColor(generateColor(new SecureRandom())));
                    viewHolder.messageTextView.setVisibility(TextView.VISIBLE);

                    viewHolder.messengerTextView.setVisibility(TextView.VISIBLE);
                    viewHolder.messengerTextView.setText(friendlyMessage.getComplaintDescription());
                    if (friendlyMessage.getRusolved().equals("resolved")) {
                        viewHolder.messengerImageView.setImageResource(R.drawable.ic_done);
                        viewHolder.messengerImageView.setVisibility(CircleImageView.VISIBLE);
                    } else {
                        viewHolder.messengerImageView.setImageResource(R.drawable.ic_notdone);
                        viewHolder.messengerImageView.setVisibility(CircleImageView.VISIBLE);

                    }
                }
                    else{
                        viewHolder.cardview.setVisibility(CardView.GONE);
                    }
                }


                Log.v("Error","Checking6");



            }
        };
        Log.v("Error","Checking6");
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_complaint, menu);
        return true;
    }
    public void onPause() {
        super.onPause();
        mFirebaseAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseAdapter.startListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent =new Intent(this,AppSettings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent in =new Intent(this,ParentActivity.class);
            in.putExtra(str,ausername);
            startActivity(in);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            final SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            Intent in =new Intent(this,MainActivity.class);

            startActivity(in);
        } else if (id == R.id.nav_manage) {
            Intent in = new Intent(this, ProfileSetting.class);
            in.putExtra(str, ausername);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private static String generateColor(SecureRandom r) {
        StringBuilder color[] = new StringBuilder[5];
        color[0]=new StringBuilder("#ffffff");
        color[1]=new StringBuilder("#ffffff");
        color[2]=new StringBuilder("#ffffff");
        color[3]=new StringBuilder("#ffffff");
        color[4]=new StringBuilder("#ffffff");

        int i=(int)r.nextInt(4);
        return color[i].toString();
    }


}
