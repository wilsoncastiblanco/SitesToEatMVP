package com.example.sitestoeat.view.activities;

import com.example.sitestoeat.R;
import com.example.sitestoeat.presenter.LoginPresenter;
import com.example.sitestoeat.view.LoginView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private EditText editTextUser;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter();
        loginPresenter.setView(this);
        initializeVisualComponents();
        initializeEvents();
    }

    private void initializeEvents() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loginPresenter.onLoginClicked(editTextUser.getText().toString(),
                        editTextPassword.getText().toString());
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegisterUsers = RegisterUsersActivity.getCallingIntent(getApplicationContext());
                startActivity(intentRegisterUsers);
            }
        });
    }

    @Override
    public void throwErrorEmptyFields() {
        Toast.makeText(getApplicationContext(), R.string.login_error_empty_fields, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, RestaurantsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showWrongCredentialsMessage() {
        Toast.makeText(getApplicationContext(), getString(R.string.login_error), Toast.LENGTH_LONG).show();
    }

    private void initializeVisualComponents() {
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUser = (EditText) findViewById(R.id.editTextUser);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
