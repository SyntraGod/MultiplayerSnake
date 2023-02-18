package client.ui;

import player.Player;
import sqlconnection.SqlAccess;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class HighScore{
    private final int width = 500;
    private final int height = 300;
    private JFrame jFrame;
    private JTable jTable;
    public HighScore(){
        this.jFrame = new JFrame("High Score");
        this.jTable = new JTable(new HighScoreTable());

        jFrame.add(jTable);
        jFrame.setResizable(false);
        jFrame.setSize(width,height);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean visibility) {
        jFrame.setVisible(visibility);
    }

}

class HighScoreTable extends AbstractTableModel {

    SqlAccess sqlAccess = new SqlAccess();
    List<Player> players = sqlAccess.getPlayer();

    String[] columnNames = {"Top", "Username", "Score"};
    public  Object[][] data = setData(players);
    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public Object[][] setData(List<Player> players) {
        Object[][] data1 = new Object[11][3];
        for(int i = 0; i<this.getColumnCount();++i){
            data1[0][i] = columnNames[i];
        }
        for(int i = 1; i<=10; ++i){
            data1[i][0] = i;
        }
        for(int i = 1; i <= Math.min(players.size(), 10); ++i){
            for(int j = 1; j< this.getColumnCount();++j) {
                if(j==1){
                    data1[i][j] = players.get(i-1).getUsername();
                }
                else data1[i][j] = players.get(i-1).getHighScore();
            }
        }
        return data1;
    }
}
