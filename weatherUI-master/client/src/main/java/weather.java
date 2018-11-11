public class weather {
    private String cityName;
    private String countryCode;
    private String weatherID;
    private String weatherMainDesc;
    private String weatherDesc;
    private String temperature;
    private String humidity;
    private String temp_min;
    private String temp_max;
    private String clouds;
    private String windSpeed;
    private String dayOrNight;

    public weather(String cityName, String countryCode, String weatherID, String weatherMainDesc, String weatherDesc, String temperature, String humidity, String temp_min, String temp_max, String clouds, String windSpeed, String dayOrNight) {
        this.cityName = cityName;
        this.countryCode = countryCode;
        this.weatherID = weatherID;
        this.weatherMainDesc = weatherMainDesc;
        this.weatherDesc = weatherDesc;
        this.temperature = temperature;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.dayOrNight = dayOrNight;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getWeatherID() {
        return weatherID;
    }

    public void setWeatherID(String weatherID) {
        this.weatherID = weatherID;
    }

    public String getWeatherMainDesc() {
        return weatherMainDesc;
    }

    public void setWeatherMainDesc(String weatherMainDesc) {
        this.weatherMainDesc = weatherMainDesc;
    }

    public String getWeatherDesc() {
        return weatherDesc;
    }

    public void setWeatherDesc(String weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getDayOrNight(){ return dayOrNight;}

    public void setDayOrNight(String dayOrNight){this.dayOrNight = dayOrNight;}
}