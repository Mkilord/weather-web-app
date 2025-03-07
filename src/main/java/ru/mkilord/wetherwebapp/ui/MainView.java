package ru.mkilord.wetherwebapp.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import ru.mkilord.wetherwebapp.controller.WeatherController;
import ru.mkilord.wetherwebapp.exception.WeatherNotFoundException;
import ru.mkilord.wetherwebapp.model.Response;
import ru.mkilord.wetherwebapp.model.Weather;

import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Route("")
@UIScope
@SpringComponent
@RequiredArgsConstructor
public class MainView extends HorizontalLayout {

    final I18NProvider i18n;
    final WeatherController weatherController;

    List<Response> responseList;
    Grid<Response> responseGrid;

    @PostConstruct
    public void init() {
        responseGrid = new Grid<>();

        responseList = weatherController.getAllResponse();

        responseGrid.setItems(responseList);
        responseGrid.addColumn(Response::getTime).setHeader("Time").setSortable(true).setAutoWidth(true);
        responseGrid.addColumn(Response::getLongitude).setHeader("Longitude").setAutoWidth(true);
        responseGrid.addColumn(Response::getLatitude).setHeader("Latitude").setAutoWidth(true);
        responseGrid.addColumn(Response::getAnswer).setHeader("Answer").setAutoWidth(true).setSortable(true);

        var historySpan = new Span(src("span.history.text"));
        historySpan.getElement().getStyle()
                .set("font-size", "30px")
                .set("font-weight", "bold");

        var latitudeField = new NumberField(src("text-field.lat.label"));
        latitudeField.setValue(67.43);
        latitudeField.setMin(-90);
        latitudeField.setMax(90);
        latitudeField.setStep(0.0001);
        latitudeField.setRequiredIndicatorVisible(true);

        var longitudeField = new NumberField(src("text-field.lon.label"));
        longitudeField.setValue(53.765);
        longitudeField.setMin(-180);
        longitudeField.setMax(180);
        longitudeField.setStep(0.0001);
        longitudeField.setRequiredIndicatorVisible(true);

        var weatherLayout = new VerticalLayout();
        var weatherDesc = new Span();

        weatherDesc.getElement().getStyle()
                .set("font-size", "30px")
                .set("font-weight", "bold");
        var weatherTemp = new Span();
        weatherTemp.getElement().getStyle()
                .set("font-weight", "bold");

        var weatherImage = new Image();
        weatherImage.getStyle()
                .set("max-width", "100%")
                .set("max-height", "300px")
                .set("object-fit", "cover");

        weatherLayout.add(weatherDesc, weatherTemp, weatherImage);
        weatherLayout.setVisible(false);

        var submitButton = new Button(src("button.show-weather.text"), _ -> {
            if (latitudeField.isInvalid() || longitudeField.isInvalid()) {
                showNotification("msg.error.coordinate");
                return;
            }

            var lat = latitudeField.getValue();
            var lon = longitudeField.getValue();

            Weather weather;
            Response response;
            try {
                weather = weatherController.getWeather(lat, lon, LocaleContextHolder.getLocale().getLanguage());
                response = Response.builder()
                        .time(Time.valueOf(LocalTime.now().truncatedTo(ChronoUnit.SECONDS)))
                        .answer(weather.description())
                        .latitude(lat)
                        .longitude(lon)
                        .build();
            } catch (WeatherNotFoundException e) {
                showNotification(e.getMessage());
                weatherLayout.setVisible(false);
                return;
            }
            updateResponseGrid(response);

            weatherDesc.setText(weather.description());
            var sign = src("span.weather.temp.text");
            weatherTemp.setText("%s %s %s".formatted((int) weather.temp(), sign, (int) weather.feelsLike()));
            weatherImage.setSrc(getImagePathById(weather.icon()));
            showNotification("msg.weather.updated");
            weatherLayout.setVisible(true);
        });

        var weatherContent = new VerticalLayout();
        var fieldsLayout = new HorizontalLayout();
        fieldsLayout.add(latitudeField, longitudeField);
        weatherContent.add(fieldsLayout, submitButton, weatherLayout);

        var responseContent = new VerticalLayout();
        responseContent.add(responseGrid);

        add(weatherContent, responseContent);
    }

    public void updateResponseGrid(Response response) {
        weatherController.saveResponse(response);
        responseList.add(response);
        responseGrid.getDataProvider().refreshAll();
    }

    public String getImagePathById(String iconId) {
        /*Можно добавить и ночные иконки буква в индексе меняется на n*/
        return switch (iconId) {
            case "01d" -> "images/cat_sunny.jpg";
            case "02d" -> "images/cat_partly_cloudy.jpg";
            case "03d" -> "images/cat_cloudy.jpg";
            case "04d" -> "images/cat_dewy.jpg";
            case "09d" -> "images/cat_heavy_rain.jpg";
            case "10d" -> "images/cat_light_rain.jpg";
            case "11d" -> "images/cat_thunderstorm.jpg";
            case "13d" -> "images/snowy_cat.jpg";
            case "50d" -> "images/cat_foggy.jpg";
            default -> "images/cat_undefined.jpg";
        };
    }

    public void showNotification(String messageKey) {
        Notification.show(src(messageKey), 3000, Notification.Position.MIDDLE);
    }

    private String src(String key) {
        return i18n.getTranslation(key, LocaleContextHolder.getLocale());
    }
}
