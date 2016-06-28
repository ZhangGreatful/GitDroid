package com.example.administrator.gitdroid.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.gitdroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.btnLogin)
    Button btn_login;
    @Bind(R.id.btnEnter)
    Button btn_Entere;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnEnter)
    public void enter() {
        Toast.makeText(this, "enter", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
    }
}
