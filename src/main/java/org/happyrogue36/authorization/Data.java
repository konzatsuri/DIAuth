package org.happyrogue36.authorization;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Data {
    public String token;
    String chars;
    SecureRandom random = new SecureRandom();

    int time;
    int coolDown;


    public Data(FileConfiguration config){
        token = (String) config.get("token");
        chars = (String) config.get("chars");
        time =  config.getInt("time");
        coolDown = config.getInt("coolDown");
    }

    public void start(int coolDown){
        String query = "UPDATE User set time=time-? where time>0";
        String query1 = "UPDATE User set abilityToConnect=? where time<=0";
        Runnable task = new Runnable() {
            public void run() {
                while(true){
                    try {
                        PreparedStatement statement = SQLiteJDBC.connection.prepareStatement(query);
                        statement.setInt(1, coolDown);
                        statement.executeUpdate();
                        statement = SQLiteJDBC.connection.prepareStatement(query1);
                        statement.setInt(1,0);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Thread.sleep(coolDown * 1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void CreateCodeForPlayer(Player player) {
        User user = getUser(player);
        if (user.getTryToConnect()) return;
        String code = GenerateCode();
        user.setCode(code);
    }

    public User getUser(Player player) {
        try {
            String userName  = player.getName();
            if (!checkUserInDataBase(userName)) {
                SQLiteJDBC.statement.
                        execute(String.format("INSERT INTO User values('%s', 'code', 0, 0, 0, NULL, NULL)", userName));
            }
            ResultSet res = SQLiteJDBC.statement.
                    executeQuery(String.format("SELECT * FROM User where UserName='%s'", userName));
            return new User(res.getString("UserName"), res.getString("code"),
                    res.getInt("abilityToConnect") == 1, res.getInt("time"),
                    res.getInt("tryToConnect") == 1, res.getString("UserId")
                    , res.getString("channel"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByCode(String code){
        try {
            if (!checkCodeInDataBase(code)) {
                return null;
            }
            ResultSet res = SQLiteJDBC.statement.
                    executeQuery(String.format("SELECT * FROM User where code='%s'", code));
            return new User(res.getString("UserName"), res.getString("code"),
                    res.getInt("abilityToConnect") == 1, res.getInt("time"),
                    res.getInt("tryToConnect") == 1, res.getString("UserId")
                    , res.getString("channel"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserByChannel(String channel){
        try {
            if (!checkChannelInDataBase(channel)) {
                return null;
            }
            ResultSet res = SQLiteJDBC.statement.
                    executeQuery(String.format("SELECT * FROM User where channel='%s'", channel));
            return new User(res.getString("UserName"), res.getString("code"),
                    res.getInt("abilityToConnect") == 1, res.getInt("time"),
                    res.getInt("tryToConnect") == 1, res.getString("UserId")
                    , res.getString("channel"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String GenerateCode(){
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 5; i++){
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        return code.toString();
    }

    Boolean checkUserInDataBase(String userName){
        try {
            return SQLiteJDBC.statement.executeQuery(
                            String.format("SELECT Count(*) count FROM User where UserName='%s'", userName))
                    .getInt("count") > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    Boolean checkCodeInDataBase(String code){
        try {
            return SQLiteJDBC.statement.executeQuery(
                            String.format("SELECT Count(*) count FROM User where code='%s'", code))
                    .getInt("count") > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    Boolean checkChannelInDataBase(String channel){
        try {
            return SQLiteJDBC.statement.executeQuery(
                            String.format("SELECT Count(*) count FROM User where channel='%s'", channel))
                    .getInt("count") > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}