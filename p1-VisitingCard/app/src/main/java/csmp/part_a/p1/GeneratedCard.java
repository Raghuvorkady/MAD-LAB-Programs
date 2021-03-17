package csmp.part_a.p1;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

public class GeneratedCard extends AppCompatActivity {

    ImageView selectedLogo;
    Uri fullPhotoUri;
    TextView companyName, name, jobTitle, phone, address, email, website, fax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_card);

        selectedLogo = (ImageView) findViewById(R.id.imageView2);
        companyName = (TextView) findViewById(R.id.textView1);
        name = (TextView) findViewById(R.id.textView3);
        jobTitle = (TextView) findViewById(R.id.textView4);
        phone = (TextView) findViewById(R.id.textView5);
        address = (TextView) findViewById(R.id.textView6);
        email = (TextView) findViewById(R.id.textView7);
        website = (TextView) findViewById(R.id.textView8);
        fax = (TextView) findViewById(R.id.textView9);

        companyName.setText(MainActivity.visitingCardClass.companyName);
        name.setText(MainActivity.visitingCardClass.name);
        selectedLogo.setImageURI(MainActivity.visitingCardClass.imageUri);
        jobTitle.setText(MainActivity.visitingCardClass.jobTitle);
        phone.setText(MainActivity.visitingCardClass.phone);
        address.setText(MainActivity.visitingCardClass.address);
        email.setText(MainActivity.visitingCardClass.email);
        website.setText(MainActivity.visitingCardClass.website);
        fax.setText(MainActivity.visitingCardClass.fax);

    }
}