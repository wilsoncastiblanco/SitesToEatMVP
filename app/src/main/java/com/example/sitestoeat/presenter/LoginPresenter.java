package com.example.sitestoeat.presenter;

import android.support.annotation.NonNull;

import com.example.sitestoeat.view.LoginView;

/**
 * Created by wilsoncastiblanco on 4/19/17.
 */

public class LoginPresenter {

    private LoginView loginView;

    public void setView(@NonNull LoginView view) {
        this.loginView = view;
    }

}
