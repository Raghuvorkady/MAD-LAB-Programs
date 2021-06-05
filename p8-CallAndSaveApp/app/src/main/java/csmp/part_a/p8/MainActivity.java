package csmp.part_a.p8;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private TextView expressionTextView;

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expressionTextView = findViewById(R.id.expression);

        Button zeroBtn = findViewById(R.id.zero);
        Button oneBtn = findViewById(R.id.one);
        Button twoBtn = findViewById(R.id.two);
        Button threeBtn = findViewById(R.id.three);
        Button fourBtn = findViewById(R.id.four);
        Button fiveBtn = findViewById(R.id.five);
        Button sixBtn = findViewById(R.id.six);
        Button sevenBtn = findViewById(R.id.seven);
        Button eightBtn = findViewById(R.id.eight);
        Button nineBtn = findViewById(R.id.nine);

        Button dotBtn = findViewById(R.id.asterisk);
        Button hashBtn = findViewById(R.id.hash);
        TextView backspaceBtn = findViewById(R.id.backspace);

        Button callBtn = findViewById(R.id.call);
        Button saveBtn = findViewById(R.id.save);

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

    @SuppressLint("NonConstantResourceId")
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
                expressionTextView.append("0");
                break;
            case R.id.one:
                expressionTextView.append("1");
                break;
            case R.id.two:
                expressionTextView.append("2");
                break;
            case R.id.three:
                expressionTextView.append("3");
                break;
            case R.id.four:
                expressionTextView.append("4");
                break;
            case R.id.five:
                expressionTextView.append("5");
                break;
            case R.id.six:
                expressionTextView.append("6");
                break;
            case R.id.seven:
                expressionTextView.append("7");
                break;
            case R.id.eight:
                expressionTextView.append("8");
                break;
            case R.id.nine:
                expressionTextView.append("9");
                break;
            case R.id.asterisk:
                expressionTextView.append("*");
                break;
            case R.id.hash:
                expressionTextView.append("#");
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

    private void callMethod(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        if (number.contains("#")) // need to replace # with it's encoded value if using USSD codes
            number = number.replaceAll("#", Uri.encode("#"));
        Uri uri = Uri.parse("tel:" + number);
        intent.setData(uri);
        startActivity(intent);
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