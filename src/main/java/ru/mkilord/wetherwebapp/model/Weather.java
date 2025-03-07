package ru.mkilord.wetherwebapp.model;

public record Weather(String main, String description, double temp, double feelsLike, String icon) {
}
