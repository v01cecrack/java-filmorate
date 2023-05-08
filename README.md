# java-filmorate
Приложение Filmorate по оценке фильмов.
![ERD](https://github.com/v01cecrack/java-filmorate/blob/main/erdfilmorate.png)
##Описание ER-диаграммы:
*`Таблица USER` - модель пользователя
*`Таблица FILM` - модель фильма
*`Таблица FILM_LIKES` - инфо о пользователях поставивших лайк фильму
*`Таблица FILM_GENRE` - инфо о жанрах фильма
*`Таблица GENRE` - список жанров
*`Таблица FILM_MPA` - инфо о возрастном ограничении для фильма
*`Таблицп MPA` - список возрастных ограничений к фильму
*`Таблица USER_FRIEND` - инфо о дружбе между двумя пользователями. Если статус не подвержден: столбец status - false, подвержден - true
### Примеры запросов:
Примеры запросов для основных операций приложения:
1. Получение информации о пользователе по ID:
```
   SELECT *
   FROM USER
   WHERE USER_ID = 1
```
2. Получение списка 5 пользователей по убыванию в лексикографическом порядке
```
   SELECT NAME
   FROM USER
   ORDER BY NAME DESC
   LIMIT 5;
```
3. Получение информации о фильме по ID:
```
   SELECT *
   FROM FILM
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
