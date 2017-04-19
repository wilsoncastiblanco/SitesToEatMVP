package com.example.sitestoeat.view.fragments;

import com.example.sitestoeat.R;
import com.example.sitestoeat.presenter.RegisterUserPresenter;
import com.example.sitestoeat.view.RegisterUserView;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegisterUsersFragment extends Fragment implements RegisterUserView {
    EditText editTextLogin;
    EditText editTextPassword;
    EditText editTextNames;
    FloatingActionButton fabSaveUser;
    private RegisterUserPresenter registerUserPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_users, container, false);
        this.registerUserPresenter = new RegisterUserPresenter();
        this.registerUserPresenter.setView(this);
        initializeVisualComponents(rootView);
        initializeEvents();
        return rootView;
    }

    private void initializeEvents() {
        fabSaveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUserPresenter.registerUser(editTextLogin.getText().toString(),
                        editTextPassword.getText().toString(), editTextNames.getText().toString());
            }
        });
    }

    private void initializeVisualComponents(View rootView) {
        editTextLogin = (EditText) rootView.findViewById(R.id.editTextLogin);
        editTextPassword = (EditText) rootView.findViewById(R.id.editTextPassword);
        editTextNames = (EditText) rootView.findViewById(R.id.editTextNames);
        fabSaveUser = (FloatingActionButton) rootView.findViewById(R.id.fabSaveUser);
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
        return super.getContext();
    }

    @Override
    public void errorCreatingUser() {
        Toast.makeText(getContext(), getString(R.string.login_error_creating_user), Toast.LENGTH_LONG).show();

    }

    @Override
    public void userCreatedSuccessfully() {
        Toast.makeText(getContext(), getString(R.string.login_user_created), Toast.LENGTH_LONG).show();
        getActivity().finish();
    }
}
