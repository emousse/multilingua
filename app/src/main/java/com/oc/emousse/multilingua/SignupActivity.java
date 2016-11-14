package com.oc.emousse.multilingua;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oc.emousse.multilingua.database.User;

import io.realm.Realm;

public class SignupActivity extends AppCompatActivity {

    private EditText _name;
    private EditText _email;
    private EditText _password;
    private Button _signupButton;
    private TextView _loginLink;

    private Realm _realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _realm = Realm.getDefaultInstance();

        _name = (EditText) findViewById(R.id.input_name);
        _email = (EditText) findViewById(R.id.input_email);
        _password = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
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

    public void signup(){
        if(!validate()){
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        String email = _email.getText().toString();
        String password = _password.getText().toString();
        String name = _name.getText().toString();

        _realm.beginTransaction();
        User u = _realm.createObject(User.class);
        u.name = name;
        u.password = password;
        u.email = email;
        _realm.commitTransaction();

        onSignupSucces(u.email);
    }

    public void onSignupSucces(String email){
        Toast.makeText(getBaseContext(), email, Toast.LENGTH_LONG).show();

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Impossible de créer cet utilisateur.", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _name.getText().toString();
        String email = _email.getText().toString();
        String password = _password.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _name.setError("Le nom doit comprendre plus de 3 caractères");
            valid = false;
        } else {
            _name.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _email.setError("Entrez un email valide.");
            valid = false;
        } else {
            User user = _realm.where(User.class).equalTo("email",email).findFirst();
            if(user != null && user.email.equals(email) ){
                valid =false;
                _email.setError("Adresse email déjà prise.");
            } else{
                _email.setError(null);
            }
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _password.setError("Le mot de passe doit comprendre entre 4 et 10 caractères");
            valid = false;
        } else {
            _password.setError(null);
        }

        return valid;
    }
}
