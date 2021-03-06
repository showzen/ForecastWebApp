package com.ua.tqs_project_80124;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.json.*;


@SpringBootApplication()
public class TqsProject80124Application implements CommandLineRunner {
    

        @Autowired
        private  WeatherService weatherService;
        
        @Autowired
        private WeatherRepository weatherRepository;
        
        private Constants constants = new Constants();
        
	public static void main(String[] args) throws IOException {
		SpringApplication.run(TqsProject80124Application.class, args); 
 
        }

        @Override
        public void run(String... args) throws Exception {
            for (int city:constants.consts.values()){
                consumeWeathers(city);
            }
        }
        
        public void consumeWeathers(int city){
                URL url;
                try {
                    url = new URL("http://api.ipma.pt/open-data/forecast/meteorology/cities/daily/" + city +".json");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setDoOutput(true);
                    BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    JSONObject obj = new JSONObject(content.toString());
                    JSONArray jsonArray = obj.getJSONArray("data");
                    WeatherForecast weatherList = new WeatherForecast();
                    for (int i = 0; i < jsonArray.length(); i++){
                        double tMin = Double.parseDouble(jsonArray.getJSONObject(i).getString("tMin"));
                        double tMax = Double.parseDouble(jsonArray.getJSONObject(i).getString("tMax"));
                        String date = jsonArray.getJSONObject(i).getString("forecastDate");
                        Weather weather = new Weather(Constants.generateId(),tMin,tMax,date,city);
                        
                        weather.transformDate(date);
                        weatherList.addWeather(weather);
                    }
                    weatherList.setLocal(Constants.getLocal(city));
                    this.weatherService.addWeather(weatherList);

                } catch (MalformedURLException ex) {
                    Logger.getLogger(TqsProject80124Application.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ProtocolException ex) {
                Logger.getLogger(TqsProject80124Application.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TqsProject80124Application.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        
        
        


}
