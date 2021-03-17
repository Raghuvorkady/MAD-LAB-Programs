package csmp.part_a.p1;

import android.net.Uri;

public class VisitingCardClass {
    String companyName, name, jobTitle, phone, address, email, website, fax;
    Uri imageUri;

    public VisitingCardClass(String companyName, String name, String jobTitle, String phone, String address, String email, String website, String fax, Uri imageUri) {
        this.companyName = companyName;
        this.name = name;
        this.jobTitle = jobTitle;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.website = website;
        this.fax = fax;
        this.imageUri = imageUri;
    }
}
