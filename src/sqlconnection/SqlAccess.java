package sqlconnection;

import player.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlAccess {
    private String dbURL, user, pass;
    public SqlAccess() {
//
    }

    public void Setup(){
        dbURL = "jdbc:mysql://127.0.0.1:3306/multiplayersnake";
        user = "root";
        pass = "20102001";
    }

    public boolean insertPlayerToDB(Player player){
        Connection conn = null;

        try {

            Setup();
            conn = DriverManager.getConnection(dbURL, user, pass);
            Statement statement = conn.createStatement();
            statement.executeUpdate("insert into player (usernamePlayer, passwordPlayer, statusPlayer, score, highScore) values ('"+player.getUsername()+"','"+player.getPassword()+"',"+player.getStatus()+","+player.getScore()+","+player.getHighScore()+");");

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    public Player getPlayerByID(int id){
        Player player = new Player();
        Connection conn = null;

        try {

            Setup();
            conn = DriverManager.getConnection(dbURL, user, pass);
            Statement statement = conn.createStatement();
            String sql = "select * from player where idPlayer=" + id;
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            player.setIdPlayer(resultSet.getInt("idPlayer"));
            player.setUsername( resultSet.getString("usernamePlayer"));
            player.setPassword(resultSet.getString("passwordPlayer"));
            player.setStatus(resultSet.getInt("statusPlayer"));
            player.setScore( resultSet.getInt("score"));
            player.setHighScore( resultSet.getInt("highScore"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return player;
    }

    public void updatePlayerToDB(Player player){
        Connection conn = null;

        try {

            Setup();
            conn = DriverManager.getConnection(dbURL, user, pass);
            Statement statement = conn.createStatement();
            String sql = "update player set statusPlayer = "+player.getStatus()+" , score="+player.getScore()+" , highScore="+player.getHighScore()+" where idPlayer="+player.getIdPlayer();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Player getPlayerByUsername(String username){
        Player player = new Player();
        Connection conn = null;

        try {

            Setup();
            conn = DriverManager.getConnection(dbURL, user, pass);
            Statement statement = conn.createStatement();
            String sql = "select * from player where usernamePlayer=\"" + username + "\"";
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            player.setIdPlayer(resultSet.getInt("idPlayer"));
            player.setUsername( resultSet.getString("usernamePlayer"));
            player.setPassword(resultSet.getString("passwordPlayer"));
            player.setStatus(resultSet.getInt("statusPlayer"));
            player.setScore( resultSet.getInt("score"));
            player.setHighScore( resultSet.getInt("highScore"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return player;
    }

    public List<Player> getPlayer(){
        List<Player> players = new ArrayList<>();
        Connection conn = null;

        try {

            Setup();
            conn = DriverManager.getConnection(dbURL, user, pass);
            Statement statement = conn.createStatement();
            String sql = "select * from player order by highScore DESC";
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                Player player = new Player();
                player.setIdPlayer(resultSet.getInt("idPlayer"));
                player.setUsername(resultSet.getString("usernamePlayer"));
                player.setPassword(resultSet.getString("passwordPlayer"));
                player.setStatus(resultSet.getInt("statusPlayer"));
                player.setScore(resultSet.getInt("score"));
                player.setHighScore(resultSet.getInt("highScore"));
                players.add(player);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return players;
    }
}
