# java-filmorate
Приложение Filmorate по оценке фильмов.
![ERD](https://github.com/v01cecrack/java-filmorate/blob/main/erdfilmfinal.png)
## Описание ER-диаграммы:
* `Таблица USERS` - модель пользователя
* `Таблица FILMS` - модель фильма
* `Таблица LIKES` - инфо о пользователях поставивших лайк фильму
* `Таблица GENRE` - список жанров
* `Таблица FILM_GENRE` - инфо о жанрах фильма
* `Таблицп RATING_MPA` - список возрастных ограничений к фильму
* `Таблица FRIENDSHIP` - инфо о дружбе между двумя пользователями. Если статус не подвержден: столбец status - false, подвержден - true
### Примеры запросов:
Примеры запросов для основных операций приложения:
1. Получение информации о пользователе по ID:
```
   SELECT *
   FROM USERS
   WHERE USER_ID = 1
```
2. Получение списка 5 пользователей по убыванию в лексикографическом порядке
```
   SELECT NAME
   FROM USERS
   ORDER BY NAME DESC
   LIMIT 5;
```
3. Получение информации о фильме по ID:
```
   SELECT *
   FROM FILMS
   WHERE FILM_ID = 1
```
4. Выборка 10 новых фильмов по году выпуска:
```
   SELECT NAME,
   EXTRACT(YEAR FROM CAST(RELEASEDATE AS date))
   FROM FILM
   ORDER BY RELEASEDATE DESC
   LIMIT 10;
```
