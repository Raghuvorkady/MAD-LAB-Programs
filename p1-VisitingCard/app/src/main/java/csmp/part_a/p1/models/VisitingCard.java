package csmp.part_a.p1.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.io.Serializable;

import csmp.part_a.p1.R;

public class VisitingCard implements Serializable {
    private String companyName, name, jobTitle, phone, address, email, website, fax, imageUriString;

    public VisitingCard(TextView companyName, TextView name, TextView jobTitle, TextView phone, TextView address, TextView email, TextView website, TextView fax, Uri imageUri) {
        this.companyName = companyName.getText().toString();
        this.name = name.getText().toString();
        this.jobTitle = jobTitle.getText().toString();
        this.phone = phone.getText().toString();
        this.address = address.getText().toString();
        this.email = email.getText().toString();
        this.website = website.getText().toString();
        this.fax = fax.getText().toString();
        this.imageUriString = imageUri != null ? imageUri.toString() : null;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getName() {
        return name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public String getFax() {
        return fax;
    }

    public Uri getImageUri() {
        return imageUriString != null ? Uri.parse(imageUriString) : null;
    }

    public Drawable getNoImage(Context context) {
        int noImage = R.drawable.no_image;
        return ContextCompat.getDrawable(context, noImage);
    }
}
