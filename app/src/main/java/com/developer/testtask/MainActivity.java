package com.developer.testtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import com.developer.testtask.databases.UserRepository;
import com.developer.testtask.local.UserDataSource;
import com.developer.testtask.local.UserDatabase;
import com.developer.testtask.models.Model;
import com.developer.testtask.others.NetworkService;
import com.developer.testtask.others.Utils;
import com.developer.testtask.ui.LoginActivity;
import com.developer.testtask.ui.WebActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Utils.isOnline(this)){
            NetworkService.getInstance()
                    .getJSONApi()
                    .getData()
                    .enqueue(new Callback<Model>() {
                        @Override
                        public void onResponse(@NonNull Call<Model> call, @NonNull Response<Model> response) {
                            Log.d("ress", String.valueOf(response.body().toString()));
                            Model model = response.body();
                            try {
                                JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                                Log.d("Json ", jsonObject.toString());
                                if(jsonObject.has("url")){
                                    Intent intent = new Intent(MainActivity.this, WebActivity.class);
                                    intent.putExtra("url", model.getUrl().toString());
                                    startActivity(intent);
                                    Log.d("url = ", model.getUrl());
                                }else {
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Model> call, @NonNull Throwable t) {

                            Log.d("Logg", "Error occurred while getting request!");
                            t.printStackTrace();
                        }
                    });
        }else {
            viewInet();
        }
    }


    private void viewInet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Please, check your Internet connection status and restart app");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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