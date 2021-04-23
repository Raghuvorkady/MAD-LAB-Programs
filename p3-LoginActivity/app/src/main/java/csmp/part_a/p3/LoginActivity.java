package csmp.part_a.p3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button signInButton;
    EditText signInUsernameField, signInPasswordField;
    int attempts;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        attempts = 0;
        signInUsernameField = (EditText) findViewById(R.id.signInUsernameEditText);
        signInPasswordField = (EditText) findViewById(R.id.signInPasswordEditText);
        signInButton = (Button) findViewById(R.id.signInButton);

        getValueFromUserBundle();

        signInButton.setOnClickListener(this);
    }

    private void getValueFromUserBundle() { //getting values from bundles
        username = getIntent().getExtras().getString(Constants.USERNAME_KEY);
        password = getIntent().getExtras().getString(Constants.PASSWORD_KEY);
    }


    @Override
    public void onClick(View view) {
        attempts++;
        if (attempts > 2) {
            makeToast("Failed Login Attempts");
            signInButton.setEnabled(false);
        } else {
            String usernameLocal, passwordLocal;
            usernameLocal = signInUsernameField.getText().toString();
            passwordLocal = signInPasswordField.getText().toString();

            if (usernameLocal.equals(username) && passwordLocal.equals(password))
                makeToast("Successful Login");
            else makeToast("Login Failed");
        }
    }

    public void makeToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }
}