package player;

import objects.Snake;

public class Player {
    private int idPlayer;
    private String username;
    private String password;
    private int status;
    private int score;

    private int highScore;

    private Snake snake;

    public Player(){

    }

    public Player(String username, String password){
        this.username = username;
        this.password = password;
        this.status = 0;
        this.score = 0;
        this.highScore = 0;
    }

    public void setStatus(int status){
        this.status = status;
    }
    public void setIdPlayer(int idPlayer) { this.idPlayer = idPlayer; }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) { this.score =score; }

    public void setHighScore(int highScore){ this.highScore = highScore; }

    public void setSnake(Snake snake){ this.snake = snake;}

    public void updateHighScore(int highScore){
        if(highScore > this.highScore) this.highScore = highScore;
    }
    public int getIdPlayer() {
        return idPlayer;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore(){
        return score;
    }

    public int getHighScore() {
        return highScore;
    }
    public int getStatus(){return status;}

    public Snake getSnake(){ return snake;}
}
