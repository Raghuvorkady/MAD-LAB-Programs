package csmp.part_a.p4;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    // an array to store the wallpapers saved in res/drawable directory
    private final int[] appWallpapers =
            {R.drawable.wallpaper1, R.drawable.wallpaper2, R.drawable.wallpaper3, R.drawable.wallpaper4};

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton changeWallpaperBtn = findViewById(R.id.changeButton);

        handler = new Handler();

        changeWallpaperBtn.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        if (isChecked) { // if toggle button is switched ON
            makeToast("Started");
            scheduleWallpaperChange();
        } else { // if toggle button is switched OFF
            makeToast("Stopped");
            handler.removeCallbacks(runnable);
        }
    }

    public void scheduleWallpaperChange() {
        runnable = new Runnable() {
            @Override
            public void run() {
                // Random class is used to randomly generate a number,
                // which is within the bound(the size of our appWallpapers array)
                Random random = new Random();
                int bound = appWallpapers.length;
                int randomInt = random.nextInt(bound);

                changeWallpaper(randomInt);
                makeToast("Wallpaper" + randomInt + " is selected");

                handler.postDelayed(this, 1000 * 5);
            }
        };

        handler.post(runnable);
    }

    private void changeWallpaper(int imageIndex) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), appWallpapers[imageIndex]);

        try {
            wallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void makeToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }
}