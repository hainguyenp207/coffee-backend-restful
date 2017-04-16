# Spring Boilerplate

# Technologies
* Spring Boot
* Spring Security
* Hibernate
* Hikari
# Introduce
In this project
I custom authentication provider of Spring Security
We can use ajax to login (Return Json Object), 3rparty authentication

# How to usage
1. Git clone
2. Import maven project into InteliJ or Eclipse,v,v..
3. All config file contain in folder resource/properties and Config. (Important, Read first)
You must change config of your database in file resource/properties/database.propeties

``` hikari.dataSourceClassName=com.mysql.jdbc.jdbc2.optional.MysqlDataSource
hikari.dataSource.url=jdbc:mysql://localhost:3306/xxx?useUnicode=yes&zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8
hikari.dataSource.username=root
hikari.dataSource.password=xxx 
```

4. Click run on toolbar :3

If you want to use CL
Type: mvn spring-boot:run


