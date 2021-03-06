package com.example.sitestoeat.presentation.presenter;

import android.support.annotation.NonNull;

import com.example.sitestoeat.presentation.interactor.LoginUserUseCase;
import com.example.sitestoeat.presentation.model.User;
import com.example.sitestoeat.presentation.view.LoginView;

/**
 * Created by wilsoncastiblanco on 4/19/17.
 */

public class LoginPresenter {

    public interface Listener{
        void onSuccess();
        void onError();
    }

    private LoginView loginView;
    private LoginUserUseCase loginUserUseCase;

    public void setView(@NonNull LoginView view) {
        this.loginView = view;
        this.loginUserUseCase = new LoginUserUseCase();
    }

    public void onLoginClicked(String user, String password){
        if(user.trim().isEmpty() || password.trim().isEmpty()){
            loginView.throwErrorEmptyFields();
        }else{
            loginUserUseCase.execute(new User(user, password), listener);
        }
    }

    private Listener listener = new Listener() {
        @Override
        public void onSuccess() {
            loginView.navigateToHome();
        }

        @Override
        public void onError() {
            loginView.showWrongCredentialsMessage();
        }
    };


}
