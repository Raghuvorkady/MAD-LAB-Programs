package csmp.part_a.p4;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private static final int REQUEST_IMAGE_GET = 1;
    private final String[] dropDownOptions = {"App", "Device"};
    private final int[] appWallpapers = {R.drawable.wallpaper1, R.drawable.wallpaper2, R.drawable.wallpaper3, R.drawable.wallpaper4};

    private TextView selectedImagesTextBox, selectedWallpaperText;
    private Button addImageButton;
    private EditText timeIntervalText;

    private List<Uri> addedImages; // to store selected images
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedImagesTextBox = findViewById(R.id.imagesListTextView);
        ToggleButton changeWallpaperButton = findViewById(R.id.changeButton);
        addImageButton = findViewById(R.id.addImage);
        timeIntervalText = findViewById(R.id.editTextTime);
        selectedWallpaperText = findViewById(R.id.selectedWallpaperText);

        addedImages = new ArrayList<>();
        handler = new Handler();

        Spinner spinner = findViewById(R.id.planets_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dropDownOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        addImageButton.setOnClickListener(this);
        changeWallpaperButton.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        selectImage();
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
            addedImages.add(imageUri);

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
                int bound = addImageButton.isEnabled() ? addedImages.size() : appWallpapers.length;
                int randomInt = random.nextInt(bound);
                changeWallpaperFun(randomInt);
                handler.postDelayed(this, 1000 * timeDelay);
            }
        };
        handler.post(runnable);
    }

    private void changeWallpaperFun(int imageIndex) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);

        try {
            Bitmap bitmap;
            if (addImageButton.isEnabled()) // addImageButton is used to indicate type of option selected from the drop down
            {
                Uri uri = addedImages.get(imageIndex);
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            } else bitmap = BitmapFactory.decodeResource(getResources(), appWallpapers[imageIndex]);
            wallpaperManager.setBitmap(bitmap);
            selectedWallpaperText.setVisibility(View.VISIBLE);
            selectedWallpaperText.setText("Randomly selected wallpaper no : " + (imageIndex + 1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedImagesTextBox.setText("");
        if (i == 0) { // if App is selected
            addImageButton.setEnabled(false);
            for (int id : appWallpapers)
                selectedImagesTextBox.append(getString(id).split("/")[2] + "\n");
        } else {
            addImageButton.setEnabled(true);
            selectedImagesTextBox.setText("");
            addedImages.clear();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        if (isChecked) {
            if (addImageButton.isEnabled()) {
                if (addedImages.size() == 0) {
                    makeToast("Select at least one image for wallpaper");
                    button.setChecked(false); // to avoid change of button state
                    return;
                }
            }
            String time = timeIntervalText.getText().toString();

            if (time.isEmpty()) {
                makeToast("Please enter the time interval...");
                button.setChecked(false); // to avoid change of button state
                return;
            }

            scheduleWallpaperChange(Integer.parseInt(time));
        } else {
            makeToast("Stopped");
            handler.removeCallbacks(runnable);
        }
    }
}