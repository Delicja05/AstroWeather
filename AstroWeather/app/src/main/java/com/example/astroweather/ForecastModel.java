package com.example.astroweather;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForecastModel {
    @SerializedName("location")
    Location location;
    @SerializedName("current_observation")
    Current_observation current_observation;
    @SerializedName("forecasts")
    ForecastDay[] forecasts;

}

class Location {
    @SerializedName("woeid")
    String woeid;
    @SerializedName("city")
    String city;
    @SerializedName("region")
    String region;
    @SerializedName("country")
    String country;
    @SerializedName("lat")
    String latitude;
    @SerializedName("long")
    String longitude;
    @SerializedName("timezone_id")
    String timezone_id;
}

class Current_observation {
    @SerializedName("wind")
    Wind wind;
    @SerializedName("atmosphere")
    Atmosphere atmosphere;
    @SerializedName("astronomy")
    Astronomy astronomy;
    @SerializedName("condition")
    Condition condition;
    @SerializedName("pubDate")
    String pubDate;

}

class Wind {
    @SerializedName("chill")
    String chill;
    @SerializedName("direction")
    String direction;
    @SerializedName("speed")
    String speed;

}

class Atmosphere {
    @SerializedName("humidity")
    String humidity;
    @SerializedName("visibility")
    String visibility;
    @SerializedName("pressure")
    String pressure;

}

class Astronomy {
    @SerializedName("sunrise")
    String sunrise;
    @SerializedName("sunset")
    String sunset;

}

class Condition {
    @SerializedName("text")
    String text;
    @SerializedName("code")
    String code;
    @SerializedName("temperature")
    String temperature;

}

class ForecastDay {
    @SerializedName("day")
    String day;
    @SerializedName("date")
    String date;
    @SerializedName("low")
    String low;
    @SerializedName("high")
    String high;
    @SerializedName("text")
    String text;
    @SerializedName("code")
    String code;

}

