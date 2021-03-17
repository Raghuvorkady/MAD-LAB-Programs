package csmp.part_a.p8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    Button zeroBtn, oneBtn, twoBtn, threeBtn, fourBtn, fiveBtn, sixBtn, sevenBtn, eightBtn, nineBtn, dotBtn, hashBtn, callBtn, saveBtn;
    ImageButton backspaceBtn;
    private TextView expressionTextView;

    Vibrator vibrator;

    String inputPhoneString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expressionTextView = findViewById(R.id.expression);

        zeroBtn = findViewById(R.id.zero);
        oneBtn = findViewById(R.id.one);
        twoBtn = findViewById(R.id.two);
        threeBtn = findViewById(R.id.three);
        fourBtn = findViewById(R.id.four);
        fiveBtn = findViewById(R.id.five);
        sixBtn = findViewById(R.id.six);
        sevenBtn = findViewById(R.id.seven);
        eightBtn = findViewById(R.id.eight);
        nineBtn = findViewById(R.id.nine);

        dotBtn = findViewById(R.id.dot);
        hashBtn = findViewById(R.id.hash);
        backspaceBtn = findViewById(R.id.backspace);

        callBtn = findViewById(R.id.call);
        saveBtn = findViewById(R.id.save);

        zeroBtn.setOnClickListener(this);
        oneBtn.setOnClickListener(this);
        twoBtn.setOnClickListener(this);
        threeBtn.setOnClickListener(this);
        fourBtn.setOnClickListener(this);
        fiveBtn.setOnClickListener(this);
        sixBtn.setOnClickListener(this);
        sevenBtn.setOnClickListener(this);
        eightBtn.setOnClickListener(this);
        nineBtn.setOnClickListener(this);

        dotBtn.setOnClickListener(this);
        backspaceBtn.setOnClickListener(this);
        backspaceBtn.setOnLongClickListener(this);
        hashBtn.setOnClickListener(this);

        callBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        requestPermission();
    }

    @Override
    public void onClick(View view) {
        vibrator.vibrate(10); //used to get haptic feedback for every button click
        int id = view.getId();
        String inputPhoneNo = expressionTextView.getText().toString();

        switch (id) {
            case R.id.call:
                callMethod(inputPhoneNo);
                break;
            case R.id.save:
                saveMethod(inputPhoneNo);
                break;
            case R.id.zero:
                generateExpression("0");
                break;
            case R.id.one:
                generateExpression("1");
                break;
            case R.id.two:
                generateExpression("2");
                break;
            case R.id.three:
                generateExpression("3");
                break;
            case R.id.four:
                generateExpression("4");
                break;
            case R.id.five:
                generateExpression("5");
                break;
            case R.id.six:
                generateExpression("6");
                break;
            case R.id.seven:
                generateExpression("7");
                break;
            case R.id.eight:
                generateExpression("8");
                break;
            case R.id.nine:
                generateExpression("9");
                break;
            case R.id.dot:
//                String dotExpression = "[0-9]+\\.[0-9]+";
//                generateExpression(".");
                break;
            case R.id.hash:
//                generateExpression("-");
                break;
            case R.id.backspace:
                int inputLength = inputPhoneNo.length();
                if (inputLength > 0)
                    expressionTextView.setText(inputPhoneNo.substring(0, inputLength - 1));
                break;
        }
    }

    private void saveMethod(String inputPhoneNo) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, inputPhoneNo);
        startActivity(intent);
    }

    private void callMethod(String inputPhoneNo) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri number = Uri.parse("tel:" + inputPhoneNo);
        intent.setData(number);
        startActivity(intent);
    }

    public void generateExpression(String val) {
        inputPhoneString = expressionTextView.getText().toString();
        String phoneExpression = "[0-9]{0,9}";

        if (inputPhoneString.matches(phoneExpression))
            expressionTextView.setText(String.format("%s%s", inputPhoneString, val));
    }

    private void requestPermission() {
        String permissionString = Manifest.permission.CALL_PHONE;
        String[] permissionStringArray = new String[]{permissionString};
        if (ContextCompat.checkSelfPermission(this, permissionString) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, permissionStringArray, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {// Permission Granted
                makeToast("Permission Granted");
            } else { // Permission Denied
                makeToast("Permission Denied");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void makeToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View view) {
        expressionTextView.setText("");
        return true;
    }
}