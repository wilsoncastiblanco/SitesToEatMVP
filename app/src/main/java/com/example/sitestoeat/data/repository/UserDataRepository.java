package com.example.sitestoeat.data.repository;

import android.net.Uri;

import com.example.sitestoeat.data.net.Requests;
import com.example.sitestoeat.data.net.RestConstants;
import com.example.sitestoeat.data.storage.UsersDataAccess;
import com.example.sitestoeat.domain.repository.UserRepository;
import com.example.sitestoeat.presentation.interactor.UserInteractorListener;
import com.example.sitestoeat.presentation.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by w.castiblanco on 19/04/2017.
 */
public class UserDataRepository implements UserRepository {

    private UserInteractorListener userInteractorListener;

    public UserDataRepository(UserInteractorListener userInteractorListener) {
        this.userInteractorListener = userInteractorListener;
    }

    @Override
    public void loginUser(User user) {
        if (UsersDataAccess.getInstance().userExists(user.getUserName())) {
            User userDB = UsersDataAccess.getInstance().checkLogin(user.getUserName(),
                    user.getPassword());
            if (userDB != null) {
                userInteractorListener.onSuccess();
            } else {
                userInteractorListener.onError();
            }
        } else {
            Requests baseRequest = new Requests();
            baseRequest.setOnRequestSuccess(new Requests.Listener() {
                @Override
                public void OnRequestSuccess(JSONObject jsonObject) {
                    validateResponse(jsonObject);
                }
            });
            baseRequest.execute(RestConstants.LOGIN_ENDPOINT, RestConstants.POST, getParams(user));
        }
    }

    private String getParams(User user) {
        Uri.Builder paramsBuilder = new Uri.Builder().appendQueryParameter(RestConstants.USER, user.getUserName())
                .appendQueryParameter(RestConstants.PASSWORD, user.getPassword());
        return paramsBuilder.build().getEncodedQuery();
    }

    private void validateResponse(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                if (jsonObject.getString(RestConstants.STATUS).equals(RestConstants.SUCCESS)) {
                    userInteractorListener.onSuccess();
                } else {
                    userInteractorListener.onError();
                }
            } else {
                userInteractorListener.onError();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerUser(User user) {
        if (UsersDataAccess.getInstance().newUser(user)) {
            userInteractorListener.onSuccess();
        } else {
            userInteractorListener.onError();
        }
    }

}
