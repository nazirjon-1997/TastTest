package com.developer.testtask.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.testtask.R;
import com.developer.testtask.adapters.UsersAdapter;
import com.developer.testtask.databases.UserRepository;
import com.developer.testtask.local.UserDataSource;
import com.developer.testtask.local.UserDatabase;
import com.developer.testtask.models.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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