package com.classy.kidspy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    //private FirebaseAuth mAuth ;
    //change email, password to check on your phone else you might see my app usage :D
    //public String email ="test@email.com", password = "123455";
    // uid 1234 12345 123456 used
    public String uid;
    private TextView facebook_view;
    private TextView whatsapp_view;
    private TextView netflix_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        uid = "1234567";
        facebook_view = findViewById(R.id.facebook_time);
        whatsapp_view = findViewById(R.id.whatsapp_time);
        netflix_view = findViewById(R.id.netflix_time);
        TimerTask updateView = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    final DocumentReference documentReference = db.collection(uid).document("AppUsage");
                    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (value != null){
                            facebook_view.setText(value.getString("Facebook"));
                            whatsapp_view.setText(value.getString("Whatsapp"));
                            netflix_view.setText(value.getString("Netflix"));
                        }}
                    });
                });
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(updateView, 0, 1000);
    }
}