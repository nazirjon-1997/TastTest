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

    private ListView firstUser;

    //Adapter
    List<User> userList = new ArrayList<>();
    UsersAdapter adapter;

    RecyclerView recyclerView;



    //Database
    private CompositeDisposable compositeDisposable;
    private UserRepository userRepository;
    TextView nameView, emailView;
    String email, name;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        nameView = findViewById(R.id.nameView);
        emailView = findViewById(R.id.emailView);
        //Init
        compositeDisposable = new CompositeDisposable();

        //Init View
        recyclerView = findViewById(R.id.firstUsers);

        //Database
        UserDatabase userDatabase = UserDatabase.getInstance(this); //Create database
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDAO()));

        // Load all data Database
//        loadData();

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

    private void loadData() {
        //Use RxJava
        Disposable disposable = userRepository.getAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        onGetAllUserSuccess(users);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(ViewActivity.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void onGetAllUserSuccess(List<User> users) {

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new UsersAdapter(this, users);
        recyclerView.setAdapter(adapter);
    }
}