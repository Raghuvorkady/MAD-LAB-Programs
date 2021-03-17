package csmp.part_a.p1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_GET = 1;
    ImageView selectedLogo;
    Button createButton, selectImageButton;
    static VisitingCardClass visitingCardClass;
    Uri fullPhotoUri;
    EditText companyName, name, jobTitle, phone, address, email, website, fax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedLogo = (ImageView) findViewById(R.id.imageView);
        companyName = (EditText) findViewById(R.id.editText1);
        name = (EditText) findViewById(R.id.editText2);
        jobTitle = (EditText) findViewById(R.id.editText3);
        phone = (EditText) findViewById(R.id.editText4);
        address = (EditText) findViewById(R.id.editText5);
        email = (EditText) findViewById(R.id.editText6);
        website = (EditText) findViewById(R.id.editText7);
        fax = (EditText) findViewById(R.id.editText8);

        createButton = (Button) findViewById(R.id.createButton);
        selectImageButton = (Button) findViewById(R.id.selectImageButton);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (companyName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter the company name", Toast.LENGTH_SHORT).show();
                } else {
                    visitingCardClass = new VisitingCardClass(companyName.getText().toString(), name.getText().toString(), jobTitle.getText().toString(), phone.getText().toString
                            (), address.getText().toString(), email.getText().toString(), website.getText().toString(), fax.getText().toString(), fullPhotoUri);

                    Intent intent = new Intent(MainActivity.this, GeneratedCard.class);
                    startActivity(intent);
                }
            }
        });
    }


    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            fullPhotoUri = data.getData();
            selectedLogo.setImageURI(fullPhotoUri);
        }
    }
}