import javax.swing.*;
import java.util.*;

public class game extends Thread {
    class tile {
        int value;
        String image;
    }
    class board{
        tile[][] board;
        boolean[][] availbility;
        int highScore;

        board() {
            this.availbility = new boolean[4][4];
            this.board = new tile[4][4];
        }

        public int weightedChance() {
            int rand = (int)Math.floor(Math.random()*10);
            if(rand == 0) {
                return 4;
            }
            else {
                return 2;
            }
        }
        //Tailwind CSS

        public boolean isFull() {
            boolean full = true;
                for(boolean[] b : availbility) {
                    for(boolean bool : b) {
                        if(bool == false) {full = false;}
                    }
        }
        return full;
    }
    //maybe this should be in react?
        public void rendering(tile t) {
            switch(t.value) {
                case 2: t.image = "Assets/2048 block 2.png";
                case 4: t.image = "Assets/2048 4 block.png";
                case 8: t.image = "Assets/2048 block 8.png";
                case 16: t.image = "Assets/2048 Block 16.png";
                case 32: t.image = "Assets/2048 32 block.png";
                case 64: t.image = "Assets/2048 64 block.png";
                case 128: t.image = "Assets/2048 128 block.png";
                case 256: t.image = "Assets/2048 256 block.png";
                case 512: t.image = "Assets/2048 512 block.png";
                case 1024: t.image = "Assets/2048 1024 block.png";
                case 2048: t.image = "Assets/2048 2048 block.png";
                default: t.image = "Assets/2048 empty tile.png";
            }
        }
        public void CreateRand() {
            int rand = (int)Math.floor(Math.random() * 17);
            int x = rand%4;
            int y = rand/4;
            if(availbility[x][y] == false) {
                board[x][y].value = weightedChance();
            }
            else {
                if(!isFull()) {
                CreateRand();
                }
                else  {
                    end();
                }
            }
            
        }
        public void end() {
            
        }
        public void start() {
            JFrame start = new JFrame("start");
            
        }
        public void up() {
            for(int i = 0; i < board.length; i++) {
                for(int k = 0; k < board[0].length; k++) {

                }
            }
        }
    }
    public static void main(String args[]) {
        game g = new game();
        g.start();
    }
}