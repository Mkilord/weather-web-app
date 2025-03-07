package ru.mkilord.wetherwebapp.exception;

public class WeatherNotFoundException extends Exception {
    public WeatherNotFoundException() {
        super("error.weather.not.found");
    }
}
