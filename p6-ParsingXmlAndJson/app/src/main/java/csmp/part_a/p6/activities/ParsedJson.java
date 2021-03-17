package csmp.part_a.p6.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import csmp.part_a.p6.R;
import csmp.part_a.p6.classes.City;

public class ParsedJson extends AppCompatActivity {

    ListView jsonListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsed_json);

        jsonListView = (ListView) findViewById(R.id.jsonListView);

        List<City> cityList = new ArrayList<City>();

        try {
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            // fetch JSONObject named cities
            JSONArray cityArray = obj.getJSONArray("cities");

            for (int i = 0; i < cityArray.length(); i++) {
                JSONObject cityObject = cityArray.getJSONObject(i);
                City city = new City();
                city.setCityName(cityObject.getString("City_name"));
                city.setLatitude(cityObject.getDouble("Latitude"));
                city.setLongitude(cityObject.getDouble("Longitude"));
                city.setTemperature(cityObject.getDouble("Temperature"));
                city.setHumidity(cityObject.getString("Humidity"));
                cityList.add(city);
            }

            ArrayAdapter<City> arrayAdapter = new ArrayAdapter<City>(this, android.R.layout.simple_list_item_1, cityList);
            jsonListView.setAdapter(arrayAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String jsonString;
        try {
            InputStream inputStream = getAssets().open("city.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonString;
    }
}