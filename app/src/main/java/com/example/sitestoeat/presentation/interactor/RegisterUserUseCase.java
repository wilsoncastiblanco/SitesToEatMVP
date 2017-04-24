package com.example.sitestoeat.presentation.interactor;

import com.example.sitestoeat.presentation.model.User;
import com.example.sitestoeat.presentation.presenter.RegisterUserPresenter;

import android.content.Context;

import com.example.sitestoeat.data.repository.UserDataRepository;

/**
 * Created by w.castiblanco on 19/04/2017.
 */
public class RegisterUserUseCase {
    private UserDataRepository userDataRepository;

    public void execute(User user, final RegisterUserPresenter.Listener listener) {
        this.userDataRepository = new UserDataRepository(new UserInteractorListener() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
            }

            @Override
            public void onError() {
                listener.onError();
            }
        });
        this.userDataRepository.registerUser(user);
    }
}
