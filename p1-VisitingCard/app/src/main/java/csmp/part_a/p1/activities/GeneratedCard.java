package csmp.part_a.p1.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import csmp.part_a.p1.R;
import csmp.part_a.p1.models.VisitingCard;

public class GeneratedCard extends AppCompatActivity implements View.OnClickListener {

    private ImageView selectedLogo;
    private TextView companyName, name, jobTitle, phone, address, email, website, fax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_card);

        selectedLogo = findViewById(R.id.logo);
        companyName = findViewById(R.id.companyName);
        name = findViewById(R.id.name);
        jobTitle = findViewById(R.id.jobTitle);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        website = findViewById(R.id.website);
        fax = findViewById(R.id.fax);

        initialiseData();

        setOnClickListeners();
    }

    private void initialiseData() {
        Intent intent = getIntent();
        VisitingCard visitingCard = (VisitingCard) intent.getSerializableExtra(MainActivity.VISITING_CARD_KEY);
        Uri uri = visitingCard.getImageUri();
        if (uri != null)
            selectedLogo.setImageURI(uri);
        else selectedLogo.setImageDrawable(visitingCard.getNoImage(this));

        companyName.setText(visitingCard.getCompanyName());
        name.setText(visitingCard.getName());
        jobTitle.setText(visitingCard.getJobTitle());
        phone.setText(visitingCard.getPhone());
        address.setText(visitingCard.getAddress());
        email.setText(visitingCard.getEmail());
        website.setText(visitingCard.getWebsite());
        fax.setText(visitingCard.getFax());
    }

    private void setOnClickListeners() {
        phone.setOnClickListener(this);
        address.setOnClickListener(this);
        email.setOnClickListener(this);
        website.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone:
                createIntent("PHONE", "tel:", phone);
                break;
            /*case R.id.address:
                createIntent("ADDRESS", "geo:", address);
                break;*/
            case R.id.email:
                createIntent("EMAIL", "mailto:", email);
                break;
            case R.id.website:
                createIntent("WEBSITE", "https://", website);
                break;
        }
    }

    private void createIntent(String toastMessage, String str, TextView textView) {
        //makeToast(toastMessage);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str + textView.getText().toString()));
        if (str.equals("mailto:"))
            intent.putExtra(Intent.EXTRA_EMAIL, textView.getText().toString());
        startActivity(intent);
    }

    private void makeToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }
}