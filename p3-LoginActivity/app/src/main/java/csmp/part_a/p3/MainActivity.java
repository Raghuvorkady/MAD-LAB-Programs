package csmp.part_a.p3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    EditText signUpUsernameField, signUpPasswordField;
    Button signUpButton;
    SwitchCompat pwdRuleSwitch;
    TextView pwdRuleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpUsernameField = findViewById(R.id.signUpUsernameEditText);
        signUpPasswordField = findViewById(R.id.signUpPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        pwdRuleSwitch = findViewById(R.id.pwdRuleSwitch);
        pwdRuleTextView = findViewById(R.id.pwdRuleTextView);

        pwdRuleSwitch.setOnCheckedChangeListener(this);
        signUpButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        // no need of switch because, we are listening for a single button click
        String username, password;
        username = signUpUsernameField.getText().toString();
        password = signUpPasswordField.getText().toString();

        String pwdValidationRegex = "^(?=.*[0-9])"
                + "(?=.*[a-z])"
                + "(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,}$";
        //For ref: https://www.geeksforgeeks.org/how-to-validate-a-password-using-regular-expressions-in-java/

        if (username.isEmpty())
            makeToast("Please don't forget to fill up username...");
        if (!password.matches(pwdValidationRegex))
            makeToast("Password validation failed!");
        else {
            Bundle userBundle = new Bundle();
            userBundle.putString(Constants.USERNAME_KEY, username);
            userBundle.putString(Constants.PASSWORD_KEY, password);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtras(userBundle);
            startActivity(intent);
        }
    }

    public void makeToast(String toastMessage) {
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked)
            pwdRuleTextView.setVisibility(View.VISIBLE);
        else pwdRuleTextView.setVisibility(View.GONE);
    }
}