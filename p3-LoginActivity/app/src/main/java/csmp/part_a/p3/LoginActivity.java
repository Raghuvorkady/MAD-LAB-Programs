package csmp.part_a.p3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MAX_ATTEMPTS = 2;
    int attempts;

    private String username;
    private String password;

    Button signInButton;
    EditText signInUsernameField, signInPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInUsernameField = findViewById(R.id.signInUsernameEditText);
        signInPasswordField = findViewById(R.id.signInPasswordEditText);
        signInButton = findViewById(R.id.signInButton);

        attempts = 0;

        getValueFromUserBundle();

        signInButton.setOnClickListener(this);
    }

    // a method for getting values from bundles
    private void getValueFromUserBundle() {
        username = getIntent().getExtras().getString(Constants.USERNAME_KEY);
        password = getIntent().getExtras().getString(Constants.PASSWORD_KEY);
    }


    @Override
    public void onClick(View view) {
        String usernameLocal = signInUsernameField.getText().toString();
        String passwordLocal = signInPasswordField.getText().toString();

        if (usernameLocal.equals(username) && passwordLocal.equals(password))
            makeToast("Successful Login!");
        else {
            if (attempts < MAX_ATTEMPTS)
                makeToast("Login attempts remaining: " + (MAX_ATTEMPTS - attempts));
            else {
                makeToast("Failed Login Attempts");
                signInButton.setEnabled(false);
            }
            attempts++;
        }
    }

    public void makeToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }
}