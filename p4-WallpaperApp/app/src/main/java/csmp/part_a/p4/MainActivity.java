package csmp.part_a.p4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button changeWallpaperButton, addImageButton;
    static final int REQUEST_IMAGE_GET = 1;
    ImageView imageView;
    TextView selectedImages;
    EditText timeIntervalText;

    private final int THIRTY_SECONDS = 30000;
    List<Uri> imagesList = new ArrayList<Uri>();
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        selectedImages = (TextView) findViewById(R.id.imagesListTextView);
        changeWallpaperButton = (Button) findViewById(R.id.changeButton);
        addImageButton = (Button) findViewById(R.id.addImage);
        timeIntervalText = (EditText) findViewById(R.id.editTextTime);

        changeWallpaperButton.setOnClickListener(this);
        addImageButton.setOnClickListener(this);

        handler = new Handler();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changeButton:
                if (imagesList.size() != 0) {
                    String time = timeIntervalText.getText().toString();
                    int timeDelayInSeconds;

                    if (time.isEmpty()) {
                        makeToast("Please enter the time interval...");
                    } else {
                        timeDelayInSeconds = Integer.parseInt(time);
                        scheduleWallpaperChange(timeDelayInSeconds);
                    }
                } else makeToast("Select at least one image for wallpaper");
                break;
            case R.id.addImage:
                selectImage();
                break;
        }
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
            Uri imageUri = data.getData();
            imagesList.add(imageUri);

            File file = new File(imageUri.getPath());
            selectedImages.append(file.getName() + "\n");
        }
    }

    public void scheduleWallpaperChange(final int timeDelay) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int bound = imagesList.size();
                int randomInt = random.nextInt(bound);
                makeToast("Selected wallpaper no: " + randomInt);
                changeWallpaperFun(randomInt);
                handler.postDelayed(this, 1000 * timeDelay);
            }
        };
        handler.post(runnable);
        //handler.removeCallbacks(runnable);
    }

    private void changeWallpaperFun(int imageIndex) {
        WallpaperManager wallpaperManager
                = WallpaperManager.getInstance(getApplicationContext());

        try {
            Uri uri = imagesList.get(imageIndex);
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            imageView.setImageBitmap(bitmap);
            wallpaperManager.setBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }
}