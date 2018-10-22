package kvnb.hostelservicemanagement;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static String ausername;
    private String str = "Message Checking";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    private static FloatingActionButton fab;
    public void sendComplaint() {
        Intent in = new Intent(this, complaintreg.class);
        in.putExtra(str, ausername);
        startActivity(in);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseReference mydb=FirebaseDatabase.getInstance().getReference().child("notices");
        mydb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Intent intentNotification= new Intent();

                PendingIntent pendingintent=PendingIntent.getActivity(ParentActivity.this,0,intentNotification,0);
                Notification noti=new Notification.Builder(ParentActivity.this).setTicker("Ticker")
                        .setContentTitle("New Noticies")
                        .setContentText("Check your Noticies")
                        .setSmallIcon(R.drawable.logo)
                        .setContentIntent(pendingintent).getNotification();
                noti.flags=Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0,noti);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent in = getIntent();
        ausername = in.getStringExtra(str);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        viewPageAdapter=new ViewPageAdapter(getSupportFragmentManager());
        //Add fragment
        viewPageAdapter.AddFragment(new NoticeFragment(),"");
        viewPageAdapter.AddFragment(new DiscussionFragment(),"");
        viewPageAdapter.AddFragment(new ProfileFragment(),"");
        viewPager.setAdapter(viewPageAdapter);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComplaint();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        fab.show();
                        break;
                    case 1:
                        fab.hide();
                        break;
                    case 2:
                        fab.show();
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_complaint);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_chat_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_account);DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DatabaseReference mydb=FirebaseDatabase.getInstance().getReference().child("notices");
        mydb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Intent intentNotification= new Intent();

                PendingIntent pendingintent=PendingIntent.getActivity(ParentActivity.this,0,intentNotification,0);
                Notification noti=new Notification.Builder(ParentActivity.this).setTicker("Ticker")
                        .setContentTitle("New Noticies")
                        .setContentText("Check your Noticies")
                        .setSmallIcon(R.drawable.logo)
                        .setContentIntent(pendingintent).getNotification();
                noti.flags=Notification.FLAG_AUTO_CANCEL;
                NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0,noti);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
        getMenuInflater().inflate(R.menu.parent, menu);
        return true;
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
            intent.putExtra(str, ausername);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent in = new Intent(this, UserComplaint.class);
            in.putExtra(str, ausername);
            startActivity(in);
        } else if (id == R.id.nav_slideshow) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            final SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            Intent in = new Intent(this, MainActivity.class);
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
    public static FloatingActionButton getmyButton(){
        return fab;
    }
    public static String getMyData() {
        return ausername;
    }
}
