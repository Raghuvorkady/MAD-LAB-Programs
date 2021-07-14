package csmp.part_a.p6.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import csmp.part_a.p6.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button parseJsonButton = (Button) findViewById(R.id.parseJsonButton);
        Button parseXmlButton = (Button) findViewById(R.id.parseXmlButton);

        parseJsonButton.setOnClickListener(this);
        parseXmlButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.parseXmlButton:
                Intent xmlIntent = new Intent(this, ParsedXml.class);
                startActivity(xmlIntent);
                break;
            case R.id.parseJsonButton:
                Intent jsonIntent = new Intent(MainActivity.this, ParsedJson.class);
                startActivity(jsonIntent);
                break;
        }
    }
}