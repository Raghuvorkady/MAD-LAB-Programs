package csmp.part_a.p3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button signUpButton;
    SwitchCompat pwdRuleSwitch;
    EditText signUpUsernameField, signUpPasswordField;
    TextView pwdRuleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpUsernameField = (EditText) findViewById(R.id.signUpUsernameEditText);
        signUpPasswordField = (EditText) findViewById(R.id.signUpPasswordEditText);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        pwdRuleSwitch = (SwitchCompat) findViewById(R.id.pwdRuleSwitch);
        pwdRuleTextView = (TextView) findViewById(R.id.pwdRuleTextView);

        pwdRuleSwitch.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pwdRuleSwitch:
                if (pwdRuleSwitch.isChecked())
                    pwdRuleTextView.setVisibility(View.VISIBLE);
                else
                    pwdRuleTextView.setVisibility(View.GONE);
                break;
            case R.id.signUpButton:
                String username, password;
                username = signUpUsernameField.getText().toString();
                password = signUpPasswordField.getText().toString();

                String pwdValidationRegex = "^(?=.*[0-9])"
                        + "(?=.*[a-z])(?=.*[A-Z])"
                        + "(?=.*[@#$%^&+=])"
                        + "(?=\\S+$).{8,}$";
                //For ref: https://www.geeksforgeeks.org/how-to-validate-a-password-using-regular-expressions-in-java/

                if (username.isEmpty()) {
                    makeToast("Please don't forget to fill up username...");
                }
                if (!password.matches(pwdValidationRegex)) {
                    makeToast("Password validation failed!");
                } else {
                    Bundle userBundle = new Bundle();
                    userBundle.putString("usernameKey", username);
                    userBundle.putString("passwordKey", password);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtras(userBundle);
                    startActivity(intent);
                }
                break;
        }
    }

    public void makeToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }
}