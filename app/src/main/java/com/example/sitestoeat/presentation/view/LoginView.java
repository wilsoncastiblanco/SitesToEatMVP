package com.example.sitestoeat.presentation.view;

/**
 * Created by wilsoncastiblanco on 4/19/17.
 */

public interface LoginView extends BaseView {
    void throwErrorEmptyFields();
    void navigateToHome();
    void showWrongCredentialsMessage();
}
