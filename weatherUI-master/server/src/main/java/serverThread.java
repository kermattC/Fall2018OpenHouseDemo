import java.io.*;
import java.net.*;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class serverThread implements Runnable {
    Socket inputSocket;
    static Map<String, Object> dataMap;
    static boolean dayOrNight;

    public serverThread(Socket socket){
        inputSocket = socket;
    }
    public void run() {
            final String apiKey = "c050d97f4c229b8468bfa4ab650ac580";
            String cityID;
            try {
                DataInputStream input = new DataInputStream(inputSocket.getInputStream());
                PrintStream serverPrint = new PrintStream(inputSocket.getOutputStream());
                String cityNameSelect = input.readLine();
                // Gets the hour according to different time zones. This here is mainly to determine whether it is day time or night time in the city.
                long totalMilliseconds = System.currentTimeMillis();
                long totalSeconds = totalMilliseconds / 1000;
                long totalMinutes = totalSeconds / 60;
                long totalHours = totalMinutes / 60;
                long currentHoursGMT = totalHours % 24;
                if (cityNameSelect.equals("Mississauga, CA")) {
                    long currentHoursEST = getESTHour(currentHoursGMT);
                    dayOrNight = dayCheck(currentHoursEST);
                    cityID = "6075357";
                } else if (cityNameSelect.equals("Guelph, CA")) {
                    long currentHoursEST = getESTHour(currentHoursGMT);
                    dayOrNight = dayCheck(currentHoursEST);
                    cityID = "5967629";
                } else if (cityNameSelect.equals("Ottawa, CA")) {
                    long currentHoursEST = getESTHour(currentHoursGMT);
                    dayOrNight = dayCheck(currentHoursEST);
                    cityID = "6094817";
                } else if (cityNameSelect.equals("Kingston, CA")) {
                    long currentHoursEST = getESTHour(currentHoursGMT);
                    dayOrNight = dayCheck(currentHoursEST);
                    cityID = "5992500";
                } else if (cityNameSelect.equals("Markham, CA")) {
                    long currentHoursEST = getESTHour(currentHoursGMT);
                    dayOrNight = dayCheck(currentHoursEST);
                    cityID = "6066513";
                } else if (cityNameSelect.equals("London, CA")) {
                    long currentHoursEST = getESTHour(currentHoursGMT);
                    dayOrNight = dayCheck(currentHoursEST);
                    cityID = "6058560";
                } else if (cityNameSelect.equals("New York, USA")){
                    long currentHoursEST = getESTHour(currentHoursGMT);
                    dayOrNight = dayCheck(currentHoursEST);
                    cityID = "5128638";
                } 
                else if (cityNameSelect.equals("Biysk, RU")) {
                    long currentHoursMSK = getMSKHour(currentHoursGMT);
                    cityID = "1510018";
                    dayOrNight = dayCheck(currentHoursMSK);
                }else if (cityNameSelect.equals("Novinki, RU")){
                    long currentHoursMSK = getMSKHour(currentHoursGMT);
                    cityID = "519188";
                    dayOrNight = dayCheck(currentHoursMSK);
                }
                else if (cityNameSelect.equals("Hong Kong, HK")) {
                    long currentHoursHKT = getHKTHour(currentHoursGMT);
                    System.out.println(currentHoursHKT+ ":" + totalMinutes + ":" + totalSeconds);
                    cityID = "1819729";
                    dayOrNight = dayCheck(currentHoursHKT);
                } else if (cityNameSelect.equals("Northampton, GB")) {
                    long currentHoursNGB = getNGBHour(currentHoursGMT);
                    cityID = "2641430";
                    dayOrNight = dayCheck(currentHoursNGB);
                } else if (cityNameSelect.equals("London, GB")){
                    long currentHoursNGB = getNGBHour(currentHoursGMT);
                    cityID = "2643743";
                    dayOrNight = dayCheck(currentHoursNGB);
                }
                 else if (cityNameSelect.equals("Dubai, AE")) {
                    long currentHoursDAE = getDAEHour(currentHoursGMT);
                    cityID = "292223";
                    dayOrNight = dayCheck(currentHoursDAE);
                } else if (cityNameSelect.equals("Akita, JP")) {
                    long currentHoursAJP = getAJPHour(currentHoursGMT);
                    cityID = "2113126";
                    dayOrNight = dayCheck(currentHoursAJP);
                }else if (cityNameSelect.equals("Shanghai, CN")){
                    long currentHoursHKT = getHKTHour(currentHoursGMT);
                    cityID = "1796236";
                    dayOrNight = dayCheck(currentHoursHKT);
                }
                else{
                    cityID = "6094578";
                    long currentHoursEST = getESTHour(currentHoursGMT);
                    dayOrNight = dayCheck(currentHoursEST);
                }
                // Populate the map
                dataMap = getData(cityID, apiKey);
                Map<String, Object> mainMap = jsonToMap(dataMap.get("main").toString());    // Map for the "main" section from the api
                Map<String, Object> windMap = jsonToMap(dataMap.get("wind").toString());    // Map for the "wind" section from the api
                Map<String, Object> sysMap = jsonToMap(dataMap.get("sys").toString());      // Map for the "sys" section from the api
                Map<String, Object> cloudMap = jsonToMap((dataMap.get("clouds").toString())); // Map for the "clouds" section from the api

                // get the weather id
                String weatherID = dataMap.get("weather").toString();
                weatherID = weatherID.substring(weatherID.indexOf("id=") + 3);
                weatherID = weatherID.substring(0, weatherID.indexOf(","));
                serverPrint.println(weatherID);

                // get the weather main description
                String weatherMain = dataMap.get("weather").toString();
                weatherMain = weatherMain.substring(weatherMain.indexOf("main=") + 5);
                weatherMain = weatherMain.substring(0, weatherMain.indexOf(","));
                serverPrint.println(weatherMain);

                // get the weather description
                String weatherDesc = dataMap.get("weather").toString();
                weatherDesc = weatherDesc.substring(weatherDesc.indexOf("description=") + 12);
                weatherDesc = weatherDesc.substring(0, weatherDesc.indexOf(","));
                serverPrint.println(weatherDesc);

                // get the temperature
                serverPrint.println(mainMap.get("temp"));
                // get the humidity
                serverPrint.println(mainMap.get("humidity"));
                // get the minimum temperature
                serverPrint.println(mainMap.get("temp_min"));
                // get the max temperature
                serverPrint.println(mainMap.get("temp_max"));
                // get the wind speed
                serverPrint.println(windMap.get("speed"));
                // get the country code
                serverPrint.println(sysMap.get("country"));
                // get the cloud percentage
                serverPrint.println(cloudMap.get("all"));
                // get the city name
                String cityName = dataMap.toString();
                cityName = cityName.substring(cityName.indexOf("name=") + 5);
                cityName = cityName.substring(0, cityName.indexOf(","));
                serverPrint.println(cityName);
                System.out.println("(Server)" +dayOrNight);
                serverPrint.println(dayOrNight);

                // Test code, checks if selecting a new city updates the values
                System.out.println("City Name:" + cityNameSelect);
                System.out.println(mainMap.get("temp"));
                System.out.println(windMap.get("speed"));
                System.out.println(cloudMap.get("all"));

                inputSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    // Converts the data read from the api into a map for easier accessibility
    public Map<String, Object> getData(String cityID, String apiKey){
        String urlString = "http://api.openweathermap.org/data/2.5/weather?id=" + cityID + "&appid=" + apiKey + "&units=metric";
        try{
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = br.readLine()) != null){
                result.append(line);
            }
            br.close();
            Map<String, Object> dataMap = jsonToMap((result.toString()));
            return dataMap;
        }catch (IOException e){
            System.err.println("ERROR unable to retrieve data");
        }
        return null;
    }

    public static Map<String, Object> jsonToMap(String str){
        Map<String, Object> map = new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>(){}.getType());
        return map;
    }
    // returns the time in EST
    public long getESTHour(long currentHoursGMT){
        long currentHoursEST = currentHoursGMT-4;
        if (currentHoursEST < 0) {
            long difference = 4 - currentHoursGMT;
            currentHoursEST = 24 - difference;
            if (currentHoursEST == 24) {
                currentHoursEST = 0;
            }
        }
        return currentHoursEST;
    }
    // Returns the time in Moscow, Russia
    public long getMSKHour(long currentHoursGMT){
        long currentHoursMSK = currentHoursGMT +3;
        if (currentHoursMSK >= 24){
            long difference = 24 - currentHoursMSK;
            currentHoursMSK = difference;
            if (currentHoursMSK == 24){
                currentHoursMSK = 0;
            }
        }
        return currentHoursMSK;
    }
    // Returns the time in Hong Kong
    public long getHKTHour(long currentHoursGMT){
        long currentHoursHKT = currentHoursGMT + 8;
        if (currentHoursHKT > 24){
            long difference = 24 - currentHoursHKT;
            currentHoursHKT = difference;
            if (currentHoursHKT == 24){
                currentHoursHKT = 0;
            }
        }
        return currentHoursHKT;
    }
    // Returns the time in Northampton, Great Britain
    public long getNGBHour(long currentHoursGMT){
        long currentHoursNGB = currentHoursGMT + 1;
        if (currentHoursNGB > 23){
            long difference = 24 - currentHoursNGB;
            currentHoursNGB = difference;
            if (currentHoursNGB == 24){
                currentHoursNGB = 0;
            }
        }
        return currentHoursNGB;
    }
    // Returns the time in Dubai, Arab Emirates
    public long getDAEHour(long currentHoursGMT){
        long currentHoursDAE = currentHoursGMT + 4;
        if (currentHoursDAE > 23){
            long difference = 24 - currentHoursDAE;
            currentHoursDAE = difference;
            if (currentHoursDAE == 24){
                currentHoursDAE = 0;
            }
        }
        return currentHoursDAE;
    }
    // Returns time in Akita, Japan
    public long getAJPHour(long currentHoursGMT){
        long currentHoursAJP = currentHoursGMT+9;
        if (currentHoursAJP > 24){
            long difference = 24 - currentHoursAJP;
            currentHoursAJP = 13 + difference;
            if (currentHoursAJP == 24){
                currentHoursAJP = 0;
            }
        }
        return currentHoursAJP;
    }
    // Check if the time is in day or in night
    public boolean dayCheck(long time) {
        // From 7 am to 6 pm, the ui will use day time gifs
        if (time >= 7 && time < 19) {
            return true;
        } else {// From 7 pm to 6 am, the ui will use night time gifs
            return false;
        }
    }
}