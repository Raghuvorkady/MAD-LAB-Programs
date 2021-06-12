package csmp.part_a.p3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int MAX_ATTEMPTS = 2;
    private int attempts = 0;

    private String username;
    private String password;

    Button signInButton;
    EditText signInUsernameField, signInPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        TextView heading = findViewById(R.id.heading);
        signInUsernameField = findViewById(R.id.usernameEditText);
        signInPasswordField = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.button);

        LinearLayout passwordRulesView = findViewById(R.id.password_rules_view);

        heading.setText(getString(R.string.signin_activity));
        signInButton.setText(getString(R.string.sign_in));
        passwordRulesView.setVisibility(View.GONE);

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
                makeToast("Incorrect login credentials\nLogin attempts remaining: " + (MAX_ATTEMPTS - attempts));
            else {
                makeToast("Failed Login Attempts");
                signInButton.setEnabled(false);
            }
            attempts++;
        }
    }

    private void makeToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }
}