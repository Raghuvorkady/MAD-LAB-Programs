package csmp.part_a.p6.classes;

public class City {
    private String cityName;
    private String humidity;
    private double latitude;
    private double longitude;
    private double temperature;

    public City() {
    }

    public City(String cityName, String humidity, double latitude, double longitude, double temperature) {
        this.cityName = cityName;
        this.humidity = humidity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
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