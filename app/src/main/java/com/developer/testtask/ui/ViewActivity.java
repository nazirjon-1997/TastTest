package com.developer.testtask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.developer.testtask.R;


public class ViewActivity extends AppCompatActivity {
    TextView nameView, emailView;
    String email, name;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        nameView = findViewById(R.id.nameView);
        emailView = findViewById(R.id.emailView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("email");
            name = extras.getString("name");
            id = extras.getInt("id");
            Log.d("email id", "" + id);
            nameView.setText(name);
            emailView.setText(email);
        }

    }
}