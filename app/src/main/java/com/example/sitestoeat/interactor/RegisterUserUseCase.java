package com.example.sitestoeat.interactor;

import com.example.sitestoeat.model.User;
import com.example.sitestoeat.presenter.RegisterUserPresenter;

import android.content.Context;

import data.repository.UserDataRepository;

/**
 * Created by w.castiblanco on 19/04/2017.
 */
public class RegisterUserUseCase {
    private UserDataRepository userDataRepository;

    public void execute(Context context, User user, final RegisterUserPresenter.Listener listener) {
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
        this.userDataRepository.registerUser(user, context);
    }
}
