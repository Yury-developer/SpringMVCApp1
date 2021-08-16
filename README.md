# урок 26

> ---
> 
> ## Spring Framework. Урок 26: SQL инъекции. PreparedStatement. JDBC API.
> ---
> 
> > * [видео урока:](https://youtu.be/Y2sRuCUpJ78)
> > 
> > * [репозиторий стартового проекта:](https://github.com/NeilAlishev/SpringCourse/tree/master/Lesson26_Starter.SpringJDBC2)
> >
> > * [репозиторий этого урока:](https://github.com/NeilAlishev/SpringCourse/tree/master/Lesson26.SpringJDBC2)
> >
> >    
> > ---
> 
> >   [теоретическая часть урока](https://www.youtube.com/watch?v=5LHCmvzugQM&t=5m05s)
> > 
> >   [практическая часть урока](https://www.youtube.com/watch?v=5LHCmvzugQM&t=16m13s)
> > 
> >   [подключение к DB из IDEA](https://www.youtube.com/watch?v=5LHCmvzugQM&t=19m59s)


имя БД 'PostgreSQL 13'
имя: 'first_db'
пароль: 'postgres'

пароль на БД 'postgres' 

'1234'
---
---
При создании таблицы возникла ошибка:

"
Error encountered when performing Introspect database first_db schema public (details): ОШИБКА: столбец t.relhasoids не существует
  Позиция: 135.
ОШИБКА: столбец t.relhasoids не существует
  Позиция: 135
 (277 ms)
"

лечиться так:
Надо в Data sources and Drivers поставить галочку "Introspect using JDBC metadata" .

источник: https://qna.habr.com/q/572573

---




> >    
> > ---
>  
>  * [урок про Redirect и Forward](https://youtu.be/lesNd-lqUiM)
   
---
[разметка README.md в GitHub](https://coddism.com/zametki/razmetka_readmemd_v_github)
