# README для Weather Web App

## Описание

Weather Web App — это веб-приложение для получения данных о погоде. Оно работает в контейнере Docker и использует API для получения прогнозов погоды.

![image](https://github.com/user-attachments/assets/e3c42dae-8901-4305-9c0d-a8a3df726540)
![image](https://github.com/user-attachments/assets/7411fd1b-9957-4884-be4b-7a7c4a91d072)
![image](https://github.com/user-attachments/assets/fad2f39a-237f-4a03-a7f5-4af0a06da458)

## Требования

Перед запуском убедитесь, что у вас установлены:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Конфигурация

Перед запуском создайте файл `.env` в корневой директории проекта и укажите в нем:

```env
WEATHER_API_KEY=your_api_key
WEATHER_API_URL=https://api.weather.com/
```

## Сборка и запуск

1. Склонируйте репозиторий:
   ```sh
   git clone https://github.com/your-repo/weather-web-app.git
   cd weather-web-app
   ```

2. Соберите и запустите контейнер:
   ```sh
   docker-compose up --build -d
   ```

3. Приложение будет доступно по адресу:  
   [http://localhost:8080](http://localhost:8080)

## Остановка

Для остановки контейнера выполните:

```sh
docker-compose down
```

## Переменные окружения

| Переменная          | Описание                        |
|---------------------|--------------------------------|
| `WEATHER_API_KEY`  | API-ключ для получения погоды  |
| `WEATHER_API_URL`  | URL сервиса прогноза погоды    |
| `SPRING_PROFILES_ACTIVE` | Активный профиль Spring (`prod`) |

## Лицензия

Этот проект распространяется под лицензией MIT.
