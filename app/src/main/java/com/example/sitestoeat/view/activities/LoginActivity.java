package com.example.sitestoeat.view.activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sitestoeat.R;
import com.example.sitestoeat.presenter.LoginPresenter;
import com.example.sitestoeat.view.LoginView;

import data.bd.DataHelper;
import data.contentProviders.RestaurantsContentProvider;

public class LoginActivity extends AppCompatActivity implements LoginView{
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
                if(editTextPassword.getText().toString().trim().isEmpty() ||
                        editTextUser.getText().toString().trim().isEmpty()){
                    throwErrorEmptyFields();
                }else{
                    validateIfUserExistsInDatabase();
                }
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegisterUsers = new Intent(getApplicationContext(), RegisterUsersActivity.class);
                startActivity(intentRegisterUsers);
            }
        });
    }

    private void throwErrorEmptyFields() {
        Toast.makeText(getApplicationContext(), R.string.login_error_empty_fields, Toast.LENGTH_LONG).show();
    }

    private void validateIfUserExistsInDatabase() {
        DataHelper dataHelper = new DataHelper(getApplicationContext());
        SQLiteDatabase sqliteDatabase = dataHelper.getReadableDatabase();

        String userName = editTextUser.getText().toString();
        String password = editTextPassword.getText().toString();

        //Columnas a traer
        String[] columnsToBring = new String[]{DataHelper.USER_NAMES_COLUMN};
        //Argumentos del WHERE
        String[] whereArgs = new String[]{userName, password};
        //Where
        String where = DataHelper.USER_LOGIN_COLUMN + " = ? AND " + DataHelper.USER_PASSWORD_COLUMN + " = ?";
        //Cursor que guarda el query
        Cursor cursor = sqliteDatabase.query(DataHelper.USERS_TABLE, columnsToBring , where, whereArgs, null, null, null);

        if (cursor.moveToFirst()) {
            String completeName = cursor.getString(cursor.getColumnIndex(DataHelper.USER_NAMES_COLUMN));
            String welcome = String.format(getString(R.string.login_welcome), completeName);
            Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
            //insertRestaurantsDummy();
            Intent intent = new Intent(LoginActivity.this, RestaurantsActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(), getString(R.string.login_error), Toast.LENGTH_LONG).show();
        }

        //Se cierra el cursor de los datos
        cursor.close();
        //Se cierra la conexión de la BD
        sqliteDatabase.close();
    }

    private void insertRestaurantsDummy() {
        ContentResolver contentResolver = getContentResolver();
        for(int i = 0; i < 10; i++){
            ContentValues contentValues = new ContentValues();
            contentValues.put(RestaurantsContentProvider.Restaurants.NAME, "Restaurante "+i);
            contentValues.put(RestaurantsContentProvider.Restaurants.SPECIALITY, "Sushi "+i);
            contentValues.put(RestaurantsContentProvider.Restaurants.LATITUDE, Math.random() * 10);
            contentValues.put(RestaurantsContentProvider.Restaurants.LONGITUDE, Math.random() * -74);
            Uri newRestaurantUri = contentResolver.insert(RestaurantsContentProvider.CONTENT_URI, contentValues);
            if(newRestaurantUri != null){
                Log.i("RESTAURANTS PROVIDER", newRestaurantUri.toString());
            }
        }
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
    public Context context() {
        return getApplicationContext();
    }
}
