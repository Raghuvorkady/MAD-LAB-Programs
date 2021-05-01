package csmp.part_a.p1.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import csmp.part_a.p1.R;
import csmp.part_a.p1.models.VisitingCard;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String VISITING_CARD_KEY = "visitingCard";
    private static final int REQUEST_CODE = 1;
    public static final int TIME_DELAY = 2000;
    private ImageView selectedLogo;
    private Button createButton, selectImageButton;
    private Uri fullPhotoUri;
    private TextView selectedImageText;
    private EditText companyName, name, jobTitle, phone, address, email, website, fax;
    private VisitingCard visitingCard;
    private boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //for disabling the night/dark mode

        selectedImageText = findViewById(R.id.selectedImageText);
        selectedLogo = findViewById(R.id.imageView);
        companyName = findViewById(R.id.editText1);
        name = findViewById(R.id.editText2);
        jobTitle = findViewById(R.id.editText3);
        phone = findViewById(R.id.editText4);
        address = findViewById(R.id.editText5);
        email = findViewById(R.id.editText6);
        website = findViewById(R.id.editText7);
        fax = findViewById(R.id.editText8);
        createButton = findViewById(R.id.createButton);
        selectImageButton = findViewById(R.id.selectImageButton);

        selectImageButton.setOnClickListener(this);
        createButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectImageButton:
                selectImage();
                break;
            case R.id.createButton:
                if (companyName.getText().toString().equals(""))
                    makeToast("Enter the company name");
                else {
                    visitingCard = new VisitingCard(companyName, name, jobTitle, phone, address,
                            email, website, fax, fullPhotoUri);

                    Intent intent = new Intent(MainActivity.this, GeneratedCard.class);
                    intent.putExtra(VISITING_CARD_KEY, visitingCard);
                    startActivity(intent);
                }
                break;
        }
    }

    private void makeToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            fullPhotoUri = data.getData();
            selectedLogo.setImageURI(fullPhotoUri);
            selectedImageText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (isBackPressed) {
            super.onBackPressed();
            return;
        }

        isBackPressed = true;
        makeToast("Please press BACK button again to exit");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        }, TIME_DELAY);
    }
}