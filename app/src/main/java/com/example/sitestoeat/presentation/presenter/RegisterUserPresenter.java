package com.example.sitestoeat.presentation.presenter;

import com.example.sitestoeat.presentation.interactor.RegisterUserUseCase;
import com.example.sitestoeat.presentation.model.User;
import com.example.sitestoeat.presentation.view.RegisterUserView;

import android.support.annotation.NonNull;

/**
 * Created by w.castiblanco on 19/04/2017.
 */
public class RegisterUserPresenter {

    public interface Listener{
        void onSuccess();
        void onError();
    }

    private RegisterUserView registerUserView;
    private RegisterUserUseCase registerUserUseCase;

    public void setView(@NonNull RegisterUserView view) {
        this.registerUserView = view;
        this.registerUserUseCase = new RegisterUserUseCase();
    }

    public void registerUser(String username, String password, String names){
        User user = new User(username, password, names);
        registerUserUseCase.execute(user, listener);
    }

    private Listener listener = new Listener() {
        @Override
        public void onSuccess() {
            registerUserView.userCreatedSuccessfully();
        }

        @Override
        public void onError() {
            registerUserView.errorCreatingUser();
        }
    };

}
