package com.developer.testtask.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //Database
    private CompositeDisposable compositeDisposable;
    private UserRepository userRepository;

    TextView forgotPassword, register;
    EditText emailIn, passwordIn;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailIn = findViewById(R.id.email);
        passwordIn = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotPassword);
        register = findViewById(R.id.link_regist);
        login = findViewById(R.id.btn_login);
        forgotPassword.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);


        //Init
        compositeDisposable = new CompositeDisposable();
        //Database
        UserDatabase userDatabase = UserDatabase.getInstance(this); //Create database
        userRepository = UserRepository.getInstance(UserDataSource.getInstance(userDatabase.userDAO()));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.forgotPassword:
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                break;
            case R.id.link_regist:
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                break;
            case R.id.btn_login:
                loadData(emailIn.getText().toString().trim(), passwordIn.getText().toString().trim());
                break;
        }
    }

    private void loadData(final String email, final String password) {
        //Use RxJava
        Disposable disposable = userRepository.getUserEmailPassword(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User users) throws Exception {
                        if (users != null){
                            Log.d("--------------", users.getName());
                            Intent intent = new Intent(LoginActivity.this, ViewActivity.class);
                            intent.putExtra("id", users.getId());
                            intent.putExtra("name", users.getName());
                            intent.putExtra("email", users.getEmail());
                            startActivity(intent);
                        }else {
                            emailIn.setError("Error");
                            passwordIn.setError("Error");
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(LoginActivity.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_more:
                startActivity(new Intent(LoginActivity.this, ViewActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        outDialog();

    }

    private void outDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit the application?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent homeIntent= new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

}