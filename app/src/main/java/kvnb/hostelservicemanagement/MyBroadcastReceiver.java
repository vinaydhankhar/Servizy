package kvnb.hostelservicemanagement;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context aContext, Intent aIntent) {

        // This is where you start your service
        SharedPreferences pref = aContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String key=pref.getString("user","");
        if(!key.equals("")) {
            DatabaseReference mydb = FirebaseDatabase.getInstance().getReference().child("notices");
            mydb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Intent intentNotification = new Intent();

                    PendingIntent pendingintent = PendingIntent.getActivity(aContext, 0, intentNotification, 0);
                    Notification noti = new Notification.Builder(aContext).setTicker("Ticker")
                            .setContentTitle("New Notice")
                            .setContentText("A new Notice is there")
                            .setSmallIcon(R.drawable.logo)
                            .setContentIntent(pendingintent).getNotification();
                    noti.flags = Notification.FLAG_AUTO_CANCEL;
                    NotificationManager notificationManager = (NotificationManager) aContext.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, noti);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }
}