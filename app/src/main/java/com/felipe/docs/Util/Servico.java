package com.felipe.docs.Util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.felipe.docs.Adapter.FinancasAdapter;
import com.felipe.docs.Banco.DBConfig;
import com.felipe.docs.Model.CriaContaFinaceira;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class Servico extends Service {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DBConfig dbConfig;
    public Timer mTimer1;
    public TimerTask mTt1;
    public Handler mTimerHandler1 = new Handler();
    private Util util;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        startForeground();

        util = new Util(this);
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        dbConfig = new DBConfig(this);
        Timer();
    }

    private void startForeground(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String NOTIFICATION_CHANNEL_ID = "com.bitbar.bitcardapio";
            String channelName = "My Background Service";
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setContentTitle("")
                    .setPriority(NotificationManager.IMPORTANCE_NONE)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            startForeground(2, notification);
        }
    }

    private void Timer() {
        mTimer1 = new Timer();
        mTt1 = new TimerTask() {
            public void run() {
                mTimerHandler1.post(new Runnable() {
                    public void run() {
                        //Dados();
                    }
                });
            }
        };
        mTimer1.schedule(mTt1, 0, 10000);
    }

    /*private void Dados() {
        dbConfig.open();
        databaseReference.child(dbConfig.Fields.Nome + " (" + atual.replace("/", "-") + ")").child(util.key()).setValue(c);


        databaseReference.child(dbConfig.Fields.Nome).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot obj : dataSnapshot.getChildren()) {
                    CriaContaFinaceira c = obj.getValue(CriaContaFinaceira.class);

                    String data = c.getData().substring(3, 10);
                    String atual = util.data();
                    atual = atual.substring(3, 10);

                    if (!atual.equals(data)) {

                        databaseReference.child(dbConfig.Fields.Nome + " (" + atual.replace("/", "-") + ")").child(util.key()).setValue(c);

                        databaseReference.child(dbConfig.Fields.Nome).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbConfig.close();
    }*/
}
