package csmp.part_a.p6.helper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import csmp.part_a.p6.classes.City;

public class XmlPullParserHandler {
    private List<City> cityList;
    private City city;
    private String retrievedText;

    public List<City> parseXMLData(InputStream inputStream) {
        int eventType;
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(false);

            XmlPullParser xmlParser = xmlPullParserFactory.newPullParser();
            xmlParser.setInput(inputStream, null);

            eventType = xmlParser.getEventType(); //Returns the type of the current event (START_TAG, END_TAG, TEXT, etc.)

            while (eventType != XmlPullParser.END_DOCUMENT) { //No more events are available
                String tagName = xmlParser.getName(); //name of the current element is returned

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT: //Signalize that parser is at the very beginning of the document and nothing was read yet.
                        cityList = new ArrayList<City>();
                        break;
                    case XmlPullParser.START_TAG: //An XML start tag was read.
                        if (tagName.equals("city"))
                            city = new City();
                        break;
                    case XmlPullParser.TEXT: //TEXT :Text content was read
                        //the text content can be retrieved using the getText() method.
                        retrievedText = xmlParser.getText();
                        break;
                    case XmlPullParser.END_TAG: //An end tag was read.
                        switch (tagName) {
                            case "City_name":
                                city.setCityName(retrievedText);
                                break;
                            case "Latitude":
                                city.setLatitude(Double.parseDouble(retrievedText));
                                break;
                            case "Longitude":
                                city.setLongitude(Double.parseDouble(retrievedText));
                                break;
                            case "Temperature":
                                city.setTemperature(Double.parseDouble(retrievedText));
                                break;
                            case "Humidity":
                                city.setHumidity(retrievedText);
                                break;
                            case "city":
                                cityList.add(city);
                                break;
                        }
                        break;
                }
                eventType = xmlParser.next(); //to get next parsing event(moves the cursor pointer to the next event)
            }
        } catch (XmlPullParserException | IOException e) {// '|' will check 2nd cond even if first is true
            e.printStackTrace();
        }
        return cityList;
    }
}
