package com.oc.emousse.multilingua;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oc.emousse.multilingua.database.User;
import com.oc.emousse.multilingua.pref.UserShared;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {

    private EditText _email;
    private EditText _password;
    private Button _loginButton;
    private TextView _signupLink;

    private Realm _realm;

    //Subscribe to close action, prevent back action
    private final IntentFilter intentFilter = new IntentFilter("close");
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("close".equals(intent.getAction())) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _email = (EditText) findViewById(R.id.input_email);
        _password = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);

        //Retrieve realm instance
        _realm = Realm.getDefaultInstance();

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the Realm instance.
        _realm.close();
    }

    public void login(){
        if(!isFormValid()){
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        String email = _email.getText().toString();
        String password = _password.getText().toString();

        //find email and password in realm
        User user = _realm.where(User.class).equalTo("email",email).findFirst();
        if(user == null){
            onLoginFailed();
            return;
        }

        if(password.equals(user.password)){
            onLoginSuccess(email,password);
        } else {
            onLoginFailed();
        }


    }

    public void onLoginSuccess(String email, String name){
        _loginButton.setEnabled(true);
        Toast.makeText(getBaseContext(), "Connexion OK", Toast.LENGTH_LONG).show();

        UserShared.getInstance(getApplicationContext()).createLoginSession(email, name);
        Intent i = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Impossibe de se connecter", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean isFormValid(){
        boolean valid = true;

        String email = _email.getText().toString();
        String password = _password.getText().toString();

        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _email.setError("Entrez un email valide.");
            valid = false;
        }else {
            _email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10){
            _password.setError("Le mot de passe doit comprendre entre 4 et 10 caractères");
            valid = false;
        } else {
            _password.setError(null);
        }
        return valid;
    }
}
