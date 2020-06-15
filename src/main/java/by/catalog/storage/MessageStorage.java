package by.catalog.storage;

import by.catalog.domain.Message;

import java.sql.*;
import java.util.ArrayList;

public class MessageStorage {


    private final static String URL_TABLES = "jdbc:postgresql://localhost:5432/postgres";
    private final static String LOGIN_TABLES = "postgres";
    private final static String PASS_TABLES = "1987Roll";
    Connection connection = null;

    public void addMessage(Message message) {

        try {
            connection = DriverManager.getConnection(URL_TABLES, LOGIN_TABLES, PASS_TABLES);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into messageforadvert (id, idadvert, iduser, body, date) values (default , ? , ? , ? , ?)");
            preparedStatement.setLong(1, message.getIdAdvert());
            preparedStatement.setLong(2, message.getIdUser());
            preparedStatement.setString(3, message.getBody());
            preparedStatement.setString(4, message.getDate());
            preparedStatement.executeQuery();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList returnMessageByIdAdvert(long idAdvert) {
        ArrayList listMessage = new ArrayList();
        try {
            connection = DriverManager.getConnection(URL_TABLES, LOGIN_TABLES, PASS_TABLES);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from messageforadvert s where s.idadvert = ?");
            preparedStatement.setLong(1, idAdvert);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long idUser = resultSet.getLong(3);
                String body = resultSet.getString(4);
                String date = resultSet.getString(5);
                listMessage.add(new Message(id, idUser, idAdvert, body, date));
            }
            connection.close();
            return listMessage;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
