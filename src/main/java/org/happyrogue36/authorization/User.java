package org.happyrogue36.authorization;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    String name;
    String code;
    String UserId;
    String channel;
    Boolean abilityToConnect;
    int time;

    Boolean tryToConnect = false;

    public User(String name, String code, Boolean abilityToConnect, int time, Boolean tryToConnect,
                String UserId, String channel){
        this.name = name;
        this.code = code;
        this.abilityToConnect = abilityToConnect;
        this.time = time;
        this.tryToConnect = tryToConnect;
        this.UserId = UserId;
        this.channel = channel;
    }
    public String getName() {
        return name;
    }

    public Boolean getTryToConnect() {
        return tryToConnect;
    }

    public String getUserId() {
        try {
            return SQLiteJDBC.statement.
                    executeQuery(String.format("SELECT * FROM User where UserName='%s'",
                            this.getName())).getString("UserId");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCode(String code) {
        String query = "UPDATE User set code=? where UserName=?";
        try {
            PreparedStatement statement = SQLiteJDBC.connection.prepareStatement(query);
            statement.setString(1, code);
            statement.setString(2, this.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAbilityToConnect(Boolean abilityToConnect) {
        String query = "UPDATE User set abilityToConnect=?, code='code', tryToConnect=0  where UserName=?";
        try {
            PreparedStatement statement = SQLiteJDBC.connection.prepareStatement(query);
            statement.setInt(1, abilityToConnect ? 1 : 0);
            statement.setString(2, this.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTryToConnect(Boolean tryToConnect) {
        String query = "UPDATE User set tryToConnect=? where UserName=?";
        try {
            PreparedStatement statement = SQLiteJDBC.connection.prepareStatement(query);
            statement.setInt(1, tryToConnect ? 1 : 0);
            statement.setString(2, this.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTime(int time) {
        String query = "UPDATE User set time=? where UserName=?";
        try {
            PreparedStatement statement = SQLiteJDBC.connection.prepareStatement(query);
            statement.setInt(1, time);
            statement.setString(2, this.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUserId(String userId) {
        String query = "UPDATE User set userId=? where UserName=?";
        try {
            PreparedStatement statement = SQLiteJDBC.connection.prepareStatement(query);
            statement.setString(1, userId);
            statement.setString(2, this.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setChannel(String channel) {
        String query = "UPDATE User set channel=? where UserName=?";
        try {
            PreparedStatement statement = SQLiteJDBC.connection.prepareStatement(query);
            statement.setString(1, channel);
            statement.setString(2, this.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
