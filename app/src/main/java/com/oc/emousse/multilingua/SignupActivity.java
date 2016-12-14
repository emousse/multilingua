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

import com.oc.emousse.multilingua.database.Lesson;
import com.oc.emousse.multilingua.database.Quizz;
import com.oc.emousse.multilingua.database.User;

import io.realm.Realm;

public class SignupActivity extends AppCompatActivity {

    private EditText _name;
    private EditText _email;
    private EditText _password;
    private Button _signupButton;
    private TextView _loginLink;

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
        setContentView(R.layout.activity_signup);

        //Retrive realm instance
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

        final String email = _email.getText().toString();
        final String password = _password.getText().toString();
        final String name = _name.getText().toString();

        //Load lesson
        _realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User u = _realm.createObject(User.class);
                u.name = name;
                u.password = password;
                u.email = email;

                //load all lessons to user
                Lesson l = _realm.createObject(Lesson.class);
                l.id = 0;
                l.title = "Présent simple";
                l.category = "Grammaire";
                l.description = "<ul><li>\n" +
                        "\t\tLe présent simple est utilisé pour exprimer des habitudes, des vérités générales, des actions répétées ou des situations immuables, des émotions et des désirs :<br><b>I smoke</b> (habit); <b>I work in London</b> (unchanging situation); <b>London is a large city</b> (general truth)</li>\n" +
                        "<li>\n" +
                        "\t\tpour donner des instructions ou des directives :<br><b>You walk</b> for two hundred meters, then <b>you turn</b> left.</li>\n" +
                        "<li>\n" +
                        "\t\tpour exprimer des dispositions fixes, présentes ou futures :<br>\n" +
                        "\t\tYour exam <b>starts</b> at 09.00</li>\n" +
                        "<li>\n" +
                        "\t\tpour exprimer le futur, après certaines conjonctions : <b><em>after, when, before, as soon as, until</em>:<br>\n" +
                        "\t\tHe'll give it to you when <b>you come</b> next Saturday.</b></li>\n" +
                        "</ul>";
                l.enable = false;
                l.isCompleted = false;

                Quizz q = _realm.createObject(Quizz.class);
                q.question = "Le présent simple est le <strong>temps de la vérité générale</strong>. Il désigne donc une action qui se répète dans le temps (<em>I go there every Sunday</em>) ou qui est toujours vraie (<em>water boils at 100°C</em>).</p>";
                q.description = "<strong>Quizz n°1</strong> sur le présent simple.";
                q.answer = true;

                l.quizz = q;
                u.lessons.add(l);

                //load all lessons to user
                Lesson l1 = _realm.createObject(Lesson.class);
                l1.id = 1;
                l1.title = "Passé progressif";
                l1.category = "Grammaire";
                l1.description = "<p>Le passé progressif décrit des actions ou des événements à un moment <strong>antérieur à l'instant présent</strong>, qui ont commencé dans le passé et sont <strong>toujours en cours</strong> au moment où nous parlons. Autrement dit, il exprime une action <strong>inachevée ou incomplète</strong> dans le passé.</p>\n" +
                        "<p><strong>Il est utilisé:</strong></p>\n" +
                        "<ul><li>\n" +
                        "\t\tSouvent, pour décrire le contexte d'une histoire écrite dans le passé, par exemple: \"The sun <strong>was shining</strong> and the birds <strong>were singing</strong> as the elephant came out of the jungle. The other animals <strong>were relaxing</strong> in the shade of the trees, but the elephant moved very quickly. She <strong>was looking</strong> for her baby, and she didn't notice the hunter who <strong>was watching</strong> her through his binoculars. When the shot rang out, she <strong>was running</strong> towards the river...\"</li>\n" +
                        "<li>\n" +
                        "</ul>";
                l1.enable = false;
                l1.isCompleted = false;

                Quizz q1 = _realm.createObject(Quizz.class);
                q1.question = "<p>Le passé progressif de n'importe quel verbe est composé de deux éléments : le verbe <em>\"to be\" (was/were)</em>au passé et la base du verbe principal <em>+ing</em>.</p>";
                q1.description = "<strong>Quizz n°2</strong> sur le passé progressif.";
                q1.answer = true;

                l1.quizz = q1;
                u.lessons.add(l1);
            }
        });

        onSignupSucces("Utilisateur créé");
    }

    public void onSignupSucces(String email){
        Toast.makeText(getBaseContext(), email, Toast.LENGTH_LONG).show();
        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
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
