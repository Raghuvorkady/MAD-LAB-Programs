package csmp.part_a.p4;

import android.annotation.SuppressLint;
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

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_IMAGE_GET = 1;
    private ImageView imageView;
    private TextView selectedImagesTextBox;
    private EditText timeIntervalText;

    private List<Uri> images; // to store selected images
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        selectedImagesTextBox = findViewById(R.id.imagesListTextView);
        Button changeWallpaperButton = findViewById(R.id.changeButton);
        Button addImageButton = findViewById(R.id.addImage);
        timeIntervalText = findViewById(R.id.editTextTime);
        Button stopButton = findViewById(R.id.stopButton);

        addImageButton.setOnClickListener(this);
        changeWallpaperButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        images = new ArrayList<>();
        handler = new Handler();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addImage:
                selectImage();
                break;
            case R.id.changeButton:
                if (images.size() != 0) {
                    String time = timeIntervalText.getText().toString();
                    int timeDelayInSeconds;

                    if (time.isEmpty())
                        makeToast("Please enter the time interval...");
                    else {
                        timeDelayInSeconds = Integer.parseInt(time);
                        scheduleWallpaperChange(timeDelayInSeconds);
                    }
                } else makeToast("Select at least one image for wallpaper");
                break;
            case R.id.stopButton:
                makeToast("Stopped");
                handler.removeCallbacks(runnable);
                break;
        }
    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_GET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            images.add(imageUri);

            File file = new File(imageUri.getPath());
            String text = file.getName() + "\n";
            selectedImagesTextBox.append(text);
            makeToast(text + " is selected");
        }
    }

    public void scheduleWallpaperChange(final int timeDelay) {
        runnable = new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int bound = images.size();
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
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);

        try {
            Uri uri = images.get(imageIndex);
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