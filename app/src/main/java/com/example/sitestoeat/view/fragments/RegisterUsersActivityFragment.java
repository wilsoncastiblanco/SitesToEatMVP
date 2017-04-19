package com.example.sitestoeat.view.fragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sitestoeat.R;
import data.bd.DataHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterUsersActivityFragment extends Fragment {
    EditText editTextLogin;
    EditText editTextPassword;
    EditText editTextNames;
    FloatingActionButton fabSaveUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_users, container, false);
        initializeVisualComponents(rootView);
        initializeEvents();
        return rootView;
    }

    private void initializeEvents() {
        fabSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserIntoDatabase();
            }
        });
    }

    private void saveUserIntoDatabase() {
        //Se crea una instancia de la clase DataHelper, que es la gestora de la base de datos
        DataHelper dataHelper = new DataHelper(getContext());
        //Se crea una instancia de escritura de SQLiteDatabase que se encarga de los procesos CRUD
        SQLiteDatabase sqLiteDatabase = dataHelper.getWritableDatabase();
        //Contenedor de datos de SQLite para guardar datos en columnas
        ContentValues newUser = new ContentValues();
        newUser.put(DataHelper.USER_LOGIN_COLUMN, editTextLogin.getText().toString());
        newUser.put(DataHelper.USER_PASSWORD_COLUMN, editTextPassword.getText().toString());
        newUser.put(DataHelper.USER_NAMES_COLUMN, editTextNames.getText().toString());
        //Uso del método insert del API de android para crear un nuevo usuario basado en los ContentValues
        long  userId = sqLiteDatabase.insert(DataHelper.USERS_TABLE, null, newUser);
        validateUserCreated(userId);
        //Se cierra la instancia que se usó
        sqLiteDatabase.close();
        getActivity().finish();
    }

    private void validateUserCreated(long userId) {
        String message = getString(R.string.login_user_created);
        if(userId == -1){
            message = getString(R.string.login_error_creating_user);
        }
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void initializeVisualComponents(View rootView) {
        editTextLogin = (EditText) rootView.findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) rootView.findViewById(R.id.editTextPassword);
        editTextNames = (EditText) rootView.findViewById(R.id.editTextNames);
        fabSaveUser = (FloatingActionButton) rootView.findViewById(R.id.fabSaveUser);
    }


}
