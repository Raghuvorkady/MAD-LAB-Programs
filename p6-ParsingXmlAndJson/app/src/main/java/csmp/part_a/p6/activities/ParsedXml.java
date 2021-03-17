package csmp.part_a.p6.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import csmp.part_a.p6.R;
import csmp.part_a.p6.classes.City;
import csmp.part_a.p6.helper.XmlPullParserHandler;

public class ParsedXml extends AppCompatActivity {

    ListView xmlListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsed_xml);

        xmlListView = (ListView) findViewById(R.id.xmlListView);

        List<City> cityList = null;
        try {
            XmlPullParserHandler xmlPullParserHandler = new XmlPullParserHandler(); //a custom class to parse XML
            InputStream inputStream = getAssets().open("city.xml");
            cityList = xmlPullParserHandler.parseXMLData(inputStream);

            ArrayAdapter<City> arrayAdapter = new ArrayAdapter<City>(this, android.R.layout.simple_list_item_1, cityList);
            xmlListView.setAdapter(arrayAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}