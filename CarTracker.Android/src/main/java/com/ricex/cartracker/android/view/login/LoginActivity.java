package com.ricex.cartracker.android.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ricex.cartracker.android.R;
import com.ricex.cartracker.android.settings.CarTrackerSettings;
import com.ricex.cartracker.androidrequester.request.AbstractRequestCallback;
import com.ricex.cartracker.androidrequester.request.exception.InvalidCredentialsException;
import com.ricex.cartracker.androidrequester.request.tracker.CarTrackerRequestFactory;

/**
 * Created by Mitchell on 2016-11-17.
 */

public class LoginActivity extends Activity {

    private static final String LOG_TAG = "CT_LoginActivity";

    /** The key for retreiving the account name result */
    public static final String RES_ACCOUNT_NAME = "com.ricex.cartracker.android.view.login.account_name";

    /** The key for retreiving the auth token result */
    public static final String RES_AUTH_TOKEN = "com.ricex.cartracker.android.view.login.auth_token";

    private EditText textUsername;

    private EditText textPassword;

    private Button butLogin;

    private CarTrackerRequestFactory requestFactory;

    private CarTrackerSettings settings;

    public LoginActivity() {

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create Car Tracker Settings
        settings = new CarTrackerSettings(this);
        requestFactory = new CarTrackerRequestFactory(settings);

        setContentView(R.layout.activity_login);

        textUsername = (EditText) findViewById(R.id.loginUsernameText);
        textPassword = (EditText) findViewById(R.id.loginPasswordText);
        butLogin = (Button) findViewById(R.id.loginButton);

        butLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

        String accountName = settings.getUsername();
        if (!TextUtils.isEmpty(accountName)) {
            textUsername.setText(accountName);
        }

    }

    protected void login() {
        final String username = textUsername.getText().toString();
        final String password = textPassword.getText().toString();

        loginRequest(username, password);

        textPassword.getText().clear();
    }

    protected void loginRequest(final String username, final String password) {
        requestFactory.createLoginPasswordRequest(username, password).executeAsync(
                new AbstractRequestCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean results) {
                        if (results) {
                            requestAuthenticationToken(username);
                        }
                    }
                    @Override
                    public void onError(Exception e) {
                        if (null == Looper.myLooper()) {
                            Looper.prepare();
                        }
                        if (e instanceof InvalidCredentialsException) {
                            Toast.makeText(getApplicationContext(), "Invalid username / password", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Error communicating with the server. Please try again later.", Toast.LENGTH_LONG).show();
                        }

                        Log.w(LOG_TAG, "Error Logging into to server: " + e.getMessage(), e);

                        Looper.loop();
                    }
                });
    }

    protected void requestAuthenticationToken(final String username) {
        requestFactory.createAuthenticationTokenRequest().executeAsync(
                new AbstractRequestCallback<String>() {
                    @Override
                    public void onSuccess(String token) {
                        finishLogin(username, token);
                    }

                    @Override
                    public void onError(Exception e) {
                        if (null == Looper.myLooper()) {
                            Looper.prepare();
                        }
                        Toast.makeText(getApplicationContext(), "Couldn't get authentication Token!", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                });
    }

    protected void finishLogin(String username, String authenticationToken) {
        //put the username and auth token in settings
        settings.setUsername(username);
        settings.setAuthenticationToken(authenticationToken);

        Intent result = new Intent();

        result.putExtra(RES_ACCOUNT_NAME, username);
        result.putExtra(RES_AUTH_TOKEN, authenticationToken);

        setResult(RESULT_OK, result);

        finish();
    }

}
