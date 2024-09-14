package prodpack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game {

            class Board{

                class Tile {
                    int value;
                    String image;
                    Tile() {
                        this.value = 0;
                        this.image = "Assets/2048 empty tile.png";
                    }
                }
                Tile[][] board;
                boolean[][] filled;
                int highScore;

                Board() {
                    this.filled = new boolean[4][4];
                    this.board = new Tile[4][4];

                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            this.board[i][j] = new Tile();
                        }
                    }
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
                        for(boolean[] b : filled) {
                            for(boolean bool : b) {
                                if(bool == false) {return false;}
                            }
                }
                return true;
            }
            public void end() {

            }
            //maybe this should be in react?
                public void rendering(Tile t) {
                    switch(t.value) {
                        case 2: t.image = "Assets/2048 block 2.png";break;
                        case 4: t.image = "Assets/2048 4 block.png";break;
                        case 8: t.image = "Assets/2048 block 8.png";break;
                        case 16: t.image = "Assets/2048 Block 16.png";break;
                        case 32: t.image = "Assets/2048 32 block.png";break;
                        case 64: t.image = "Assets/2048 64 block.png";break;
                        case 128: t.image = "Assets/2048 128 block.png";break;
                        case 256: t.image = "Assets/2048 256 block.png";break;
                        case 512: t.image = "Assets/2048 512 block.png";break;
                        case 1024: t.image = "Assets/2048 1024 block.png";break;
                        case 2048: t.image = "Assets/2048 2048 block.png";break;
                        default: t.image = "Assets/2048 empty tile.png";break;
                    }
                }
                public void CreateRand() {
                    if(isFull()) {
                        end();//improving the efficiency adding this line at the top rather than stack recursions
                        return;
                    }
 
                    int rand;
                    int x,y;
                    do{
                        
                        rand = (int)Math.floor(Math.random() * 16);
                        x = rand%4;
                        y = rand/4;

                    }while(filled[x][y]);
                    
                    board[x][y].value = weightedChance();
                    filled[x][y]=true;
                }


                public Tile merge(Tile t1, Tile t2) {
                    Tile res = new Tile();
                    res.value = t1.value + t2.value;
                    return res;
                }
            
                public void up() {
                    for(int x = 0; x < board.length; x++) {
                        for(int y = 1; y < board[0].length; y++) {
                            
                            if(filled[x][y-1]) {
                            Tile temp = board[x][y];
                            board[x][y] = board[x][y-1];
                            board[x][y-1] = temp;
                            boolean t = filled[x][y];
                            filled[x][y] = filled[x][y-1];
                            filled[x][y-1] = t;
                            }
                            else if(board[x][y-1] == board[x][y]) {
                                board[x][y] = merge(board[x][y-1],board[x][y]);
                                board[x][y-1].value = 0;
                                filled[x][y-1] = false;
                                y--;
                            }
                        }
                    }
                
                }
                public void down() {
                    for(int x = 0; x < board.length; x++) {
                        for(int y = 0; y < board[0].length-1; y++) {
                            if(filled[x][y+1]) {
                            Tile temp = board[x][y];
                            board[x][y] = board[x][y+1];
                            board[x][y+1] = temp;
                            boolean t = filled[x][y];
                            filled[x][y] = filled[x][y+1];
                            filled[x][y+1] = t;
                            }
                        }
                    }
                }
                public void left() {
                    for(int x = 1; x < board.length; x++) {
                        for(int y = 0; y < board[0].length; y++) {
                            if(filled[x-1][y]) {
                            Tile temp = board[x][y];
                            board[x][y] = board[x-1][y];
                            board[x-1][y] = temp;
                            boolean t = filled[x][y];
                            filled[x][y] = filled[x-1][y];
                            filled[x-1][y] = t;
                            }
                        }
                    }
                }
                public void right() {
                    for(int x = 0; x < board.length-1; x++) {
                        for(int y = 1; y < board[0].length; y++) {
                            if(filled[x+1][y]) {
                            Tile temp = board[x][y];
                            board[x][y] = board[x+1][y];
                            board[x+1][y] = temp;
                            boolean t = filled[x][y];
                            filled[x][y] = filled[x+1][y];
                            filled[x+1][y] = t;
                            }
                        }
                    }
                }   
            }

    JFrame window;
    Board gameboard;


    Game() {
        this.window = new JFrame();
        this.gameboard = new Board();
        window.setTitle("2048");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(256,256);
        window.setLocationRelativeTo(null); //Centers the Frame on the screen
    }

    public void update(JLabel[][] griddy, JPanel tset) {
        for(int row = 0; row < this.gameboard.board.length; row++) {
            for(int col = 0; col < this.gameboard.board[0].length;col++) {
                this.gameboard.rendering(this.gameboard.board[row][col]);
                ImageIcon image = new ImageIcon(this.gameboard.board[row][col].image);
                griddy[row][col] = new JLabel(image);
            }
        }
        
        tset.revalidate();
        tset.repaint();
    }
     public void start() {

        JPanel tileset = new JPanel(new GridLayout(4,4));
        JLabel[][] griddyLabels = new JLabel[4][4];
        for(int row = 0; row < this.gameboard.board.length; row++) {
            for(int col = 0; col < this.gameboard.board[0].length;col++) {
                this.gameboard.rendering(this.gameboard.board[row][col]);
                ImageIcon image = new ImageIcon(this.gameboard.board[row][col].image);
                griddyLabels[row][col] = new JLabel(image);
                tileset.add(griddyLabels[row][col]);
            }
        }
        window.getContentPane().add(tileset);

        window.setVisible(true);
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    gameboard.up();
                    gameboard.CreateRand();//don't use this keyword because gameboard does not belong to KeyAdapter
                    update(griddyLabels, tileset);
                }
                else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    gameboard.down();
                    gameboard.CreateRand();
                    update(griddyLabels, tileset);
                }
                else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                    gameboard.left();
                    gameboard.CreateRand();
                    update(griddyLabels, tileset);
                }
                else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    gameboard.right();
                    gameboard.CreateRand();
                    update(griddyLabels, tileset);
                }
            }
        });

    

            
        }
    }