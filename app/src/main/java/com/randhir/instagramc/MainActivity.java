package com.randhir.instagramc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , View.OnKeyListener{


    Boolean sigupModeActive = true ;

    TextView textView;
    EditText usernameEditText;
    EditText passwordEditText;

    ImageView logoImageView;
    RelativeLayout background ;

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN ){
            signupClicked(v);
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.textView){

            Button signupButton = findViewById(R.id.button);

            if(sigupModeActive){
                sigupModeActive = false;
                signupButton.setText("LOGIN");
                textView.setText("or, SIGN UP");
            }else{
                sigupModeActive = true;
                signupButton.setText("SIGN UP");
                textView.setText("or, LOGIN");
            }
        }else if(v.getId() == R.id.logoImageView || v.getId() == R.id.backgroundLayout){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0); // Keyboard will disappear
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoImageView = findViewById(R.id.logoImageView);
        background = findViewById(R.id.backgroundLayout);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        textView = findViewById(R.id.textView);
        textView.setOnClickListener(this);
        passwordEditText.setOnKeyListener(this);
        logoImageView.setOnClickListener(this);
        background.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null){
            showUserList(); // If user is already logined
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void showUserList(){
        Intent intent = new Intent(getApplicationContext(),UserActivity.class);
        startActivity(intent);
    }

    public void signupClicked(View view) {

        if (usernameEditText.toString().matches("") || passwordEditText.toString().matches("")) {
            Toast.makeText(this, "Fill the information first", Toast.LENGTH_LONG).show();
        } else {

            if (sigupModeActive) {

                ParseUser user = new ParseUser();
                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Signup", "Success");
                            showUserList();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else{
                //LOGIN
                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user != null){
                            Log.i("LOGIN", "ok!");
                            showUserList();
                        }else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }



}