package csmp.part_a.p6.models;

public class City {
    private String cityName;
    private String humidity;
    private double latitude;
    private double longitude;
    private double temperature;

    public City() {
    }

    public City(String cityName, String humidity, String latitude, String longitude,String  temperature) {
        this.cityName = cityName;
        this.humidity = humidity;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.temperature = Double.parseDouble(temperature);
    }

    @Override
    public String toString() {
        return "\n City_Name \t : \t" + cityName +
                "\n Latitude \t : \t" + latitude +
                "\n Longitude \t : \t" + longitude +
                "\n Temperature \t : \t" + temperature +
                "\n Humidity \t : \t" + humidity + "\n";
    }
}