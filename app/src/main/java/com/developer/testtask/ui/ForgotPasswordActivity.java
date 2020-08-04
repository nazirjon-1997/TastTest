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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordActivity extends AppCompatActivity {


    //Database
    private CompositeDisposable compositeDisposable;
    private UserRepository userRepository;

    EditText emailInput;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forgot);
        emailInput = findViewById(R.id.email);
        send = findViewById(R.id.btn_send);

        //Init
        compositeDisposable = new CompositeDisposable();
        //Database
        UserDatabase userDatabase = UserDatabase.getInstance(this); //Create database
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDAO()));


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailInput.getText().toString().isEmpty()){
                    emailInput.setError("Enter mail");
                }else {
                    loadData(emailInput.getText().toString().trim());
                }
            }
        });
    }

    private void loadData(final String email) {
        //Use RxJava
        Disposable disposable = userRepository.getUserByEmail(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User users) throws Exception {
                        if (users != null){
                            Log.d("--------------", users.getName());
                            Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                            intent.putExtra("id", users.getId());
                            intent.putExtra("name", users.getName());
                            intent.putExtra("email", users.getEmail());
                            startActivity(intent);
                        }else {
                            emailInput.setError("Error not found email");
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(ForgotPasswordActivity.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }
}