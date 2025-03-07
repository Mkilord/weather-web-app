package ru.mkilord.wetherwebapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.time.LocalTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;


@Data
@FieldDefaults(level = PRIVATE)
@Entity(name = "response")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Response {
    @Column(nullable = false)
    Time time;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    long id;


    @Column(nullable = false)
    double longitude;

    @Column(nullable = false)
    double latitude;

    @Column(nullable = false)
    String answer;
}
