package com.developer.testtask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.testtask.R;
import com.developer.testtask.databases.UserRepository;
import com.developer.testtask.local.UserDataSource;
import com.developer.testtask.local.UserDatabase;
import com.developer.testtask.models.User;


import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RegistrationActivity extends AppCompatActivity {

    EditText name, email, password, c_password;
    Button btnRegister;
    //Database
    private CompositeDisposable compositeDisposable;
    private UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        btnRegister = findViewById(R.id.btn_regist);

        //Init
        compositeDisposable = new CompositeDisposable();
        //Database
        UserDatabase userDatabase = UserDatabase.getInstance(this); //Create database
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDAO()));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() &&
                        !password.getText().toString().isEmpty() && !c_password.getText().toString().isEmpty()){
                    if(password.getText().toString().trim().equals(c_password.getText().toString().trim())){
                        addUser();
                    }else{
                        password.setError("Wrong password");
                        c_password.setError("Wrong password");
                    }
                }else {
                    name.setError("Enter name");
                    email.setError("Enter email");
                    password.setError("Enter password");
                    c_password.setError("Enter confirm password");
                }
            }
        });

    }

    private void addUser(){
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                User user = new User(name.getText().toString().trim(), email.getText().toString().trim(), password.getText().toString().trim());
                userRepository.insertUser(user);
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object o) throws Exception {
                                   Toast.makeText(RegistrationActivity.this, "User added !", Toast.LENGTH_SHORT).show();

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(RegistrationActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                Toast.makeText(RegistrationActivity.this, "User added!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            }
                        }
                );
    }

}