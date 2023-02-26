package BrickBreaker;

import java.awt.*;

public class MapGenerator {
    public int map[][];
    public int brickHeight;
    public int brickWidth;
    
    
    public MapGenerator(int row, int col){
        
        map = new int[row][col];

        for(int i=0; i<row; i++){
            for (int j=0; j<col ; j++) {
                map[i][j] = 1;
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    public void setBrick(int value, int r, int c) {
        map[r][c] = value;
    }

    public void draw(Graphics2D g) {

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {

                if(map[i][j] > 0){

                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);

                    g.setColor(Color.black);
                    g.setStroke(new BasicStroke(3));
                    g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                
                }
            }
        }

    }
    
}