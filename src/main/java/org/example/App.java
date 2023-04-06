package org.example;
//Реализуйте возможность разметки класса с помощью набора ваших собственных аннотаций
//       (@Table(title), @Column). Напишите обработчик аннотаций, который позволит по размеченному
//        классу построить таблицу в базе данных.
//        2. * Второй обработчик аннотаций должен уметь добавлять объект размеченного класса в
//        полученную таблицу.


import java.lang.reflect.Field;
import java.sql.*;
import java.lang.annotation.Annotation;

public class App {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    public static void main( String[] args ) {
        try {
            connect();
           // anotationSelection();
            addendum();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            disconnect();
        }
    }
    public static void addendum() throws SQLException{
        Class[] classes ={Schoolboy.class,Students.class,Worker.class};
        for(Class o: classes) {
            StringBuilder stringBuilder = null;
            if (o.isAnnotationPresent(Table.class)) {
                stringBuilder = new StringBuilder("INSERT INTO ");
                Table table = (Table) o.getAnnotation(Table.class);
                stringBuilder.append(table.title() + " (");
                Field[] fields = o.getDeclaredFields();
                String[] str = {" ,", " ,", ")"};
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].isAnnotationPresent(Column.class)) {
                        stringBuilder.append(fields[i].getName() + str[i]);
                    }
                }
                stringBuilder.append(" VALUES (");
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].isAnnotationPresent(Column.class)) {
                        stringBuilder.append("?" + str[i]);
                    }
                }
                stringBuilder.append(";");
            }
            preparedStatement=connection.prepareStatement(stringBuilder.toString());
            for(int i=0;i<10;i++){
                preparedStatement.setInt(1,i+1);
                preparedStatement.setString(2,"Andrey"+ (i*3+1));
                preparedStatement.setInt(3, i*70+12);
                preparedStatement.executeUpdate();

            }
        }
    }
    public static void anotationSelection(){
        Class[] classes ={Schoolboy.class,Students.class,Worker.class};
        for(Class o: classes) {
            StringBuilder stringBuilder = null;
            if (o.isAnnotationPresent(Table.class)) {
                stringBuilder = new StringBuilder("CREATE TABLE ");
                Table table = (Table) o.getAnnotation(Table.class);
                stringBuilder.append(table.title() + " (");
                Field[] fields = o.getDeclaredFields();
                String[] str = {" SERIAL ,", " TEXT ,", " INTEGER);"};
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].isAnnotationPresent(Column.class)) {
                        stringBuilder.append(fields[i].getName() + str[i]);
                    }
                }
            }
            try {
                statement.executeUpdate(String.valueOf(stringBuilder));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void connect() throws SQLException{
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");
            connection= DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/pract1","postgres","9065");
            statement=connection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Unable to connection");
        }
    }
    public static void disconnect(){
        try {
            statement.close();
        } catch (SQLException e) {
           e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
