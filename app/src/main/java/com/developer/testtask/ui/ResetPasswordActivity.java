package com.developer.testtask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
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


import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText password, c_password;
    Button resetPassword;

    //Database
    private CompositeDisposable compositeDisposable;
    private UserRepository userRepository;

    String email, name;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        resetPassword = findViewById(R.id.btn_reset);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString("email");
            name = extras.getString("name");
            id = extras.getInt("id");
            Log.d("email id", "" + id);
        }

        //Init
        compositeDisposable = new CompositeDisposable();
        //Database
        UserDatabase userDatabase = UserDatabase.getInstance(this); //Create database
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDAO()));


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().isEmpty() && c_password.getText().toString().isEmpty()){
                    password.setError("Enter new password");
                    c_password.setError("Enter confirm password");
                }else if (password.getText().toString().trim().equals(c_password.getText().toString().trim())) {

                    User user = new User();
                    user.setId(id);
                    user.setName(name.trim());
                    user.setEmail(email.trim());
                    user.setPassword(password.getText().toString().trim());
                    resetPass(user);

                }else {
                    password.setError("Wrong password");
                    c_password.setError("Wrong password");
                }
            }
        });
    }



    private void resetPass(final  User user){
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                userRepository.updateUser(user);
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(ResetPasswordActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {

                    @Override
                    public void run() throws Exception {
                        Toast.makeText(ResetPasswordActivity.this, "Update", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                    }
                });

        compositeDisposable.add(disposable);
    }

}