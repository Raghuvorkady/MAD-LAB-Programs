package csmp.part_a.p3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameField = findViewById(R.id.username_edit_text);
        passwordField = findViewById(R.id.password_edit_text);
        Button signUpButton = findViewById(R.id.button);

        signUpButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        // no need of switch case because, we are listening for a single button click
        String username, password;
        username = usernameField.getText().toString();
        password = passwordField.getText().toString();

        String pwdValidationRegex = "^(?=.*[0-9])"
                + "(?=.*[a-z])"
                + "(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,}$";
        //For ref: https://www.geeksforgeeks.org/how-to-validate-a-password-using-regular-expressions-in-java/

        if (username.isEmpty()) {
            makeToast("Please don't forget to fill up username...");
            return;
        }
        if (!password.matches(pwdValidationRegex)) {
            makeToast("Password validation failed!");
        } else {
            Bundle userBundle = new Bundle();
            userBundle.putString("userNameKey", username);
            userBundle.putString("passwordKey", password);
            Intent intent = new Intent(this, SignInActivity.class);
            intent.putExtras(userBundle);
            startActivity(intent);
        }
    }

    private void makeToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }
}