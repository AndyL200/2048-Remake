package prodpack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;


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
                int highScore;

                Board() {
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
                        for(int x = 0; x < board.length;x++) {
                            for(int y = 0; y < board[0].length;y++) {
                                if(board[x][y].value == 0 ) {return false;}
                            }
                        }
                return true;
            }

            public boolean win() {
                for(int x = 0; x < board.length;x++) {
                    for(int y = 0; y < board[0].length;y++) {
                        if(board[x][y].value == 2048 ) {return true;}
                    }
                }
        return false;
            }
            //End game
            public boolean lose() {
                if(isFull()) {
                    return true;
                }
                else {
                    return false;
                }
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
                    if(isFull()) {//improving the efficiency adding this line at the top rather than stack recursions
                        return;
                    }
 
                    int rand;
                    int x,y;
                    do{
                        
                        rand = (int)Math.floor(Math.random() * 16);
                        x = rand%4;
                        y = rand/4;

                    }while(board[x][y].value != 0);
                    
                    board[x][y].value = weightedChance();
                }


        //             public Tile merge(Tile t1, Tile t2) {
        //                 Tile res = new Tile();
        //                 res.value = t1.value + t2.value;
        //                 return res;
        // }
                //works in a top-down manner
                //for each column 
                /*
                 * [0][0][2][0]
                 * [0][0][0][0]
                 * [0][0][2][0]
                 * row 1 col 3 '2' value will not move, but the row 3 col 3 '2' value will and will also merge with the one above
                 *Multimerging? what if two '2's combine to make 4 underneath an existing 4? Should those 4s directly merge?
                 */
                
                 public boolean checkColUp(Tile[][] t, int col) {
                    for(int i = t.length-1; i > 0; i--) {
                        if((t[i][col].value != 0 && t[i-1][col].value == 0) || (t[i][col].value != 0 && t[i][col].value == t[i-1][col].value)) {return false;}
                    }
                    return true;
                }
                public void up() {
                    for(int x = 0; x < board[0].length;x++) {
                        int k = 0;
                        while (k < board.length-1) {
                            if(board[k][x].value == board[k+1][x].value) {
                                board[k][x].value *= 2;
                                board[k+1][x].value = 0;
                            }
                            else if(board[k][x].value == 0) {
                                board[k][x].value = board[k+1][x].value;
                                board[k+1][x].value = 0;
                            }
                            if(k == board.length-2 && !checkColUp(board,x)) {k = -1;}
                            k++;
                        }
        
                    }
                }
                public boolean checkColDown(Tile[][] t, int col) {
                    for(int i = 0; i < t.length-1;i++) {
                        if((t[i][col].value != 0 && t[i+1][col].value == 0) || t[i][col].value != 0 && t[i][col].value == t[i+1][col].value) {return false;}
                    }
                    return true;
                }
                public void down() {
                    for(int x = 0; x < board[0].length;x++) {
                        int k = board.length-1;
                        int count = 0;
                        while (k > 0) {
                            if(board[k][x].value == board[k-1][x].value) {
                                board[k][x].value *=2;
                                board[k-1][x].value = 0;
                            }
                            else if(board[k][x].value == 0) {
                                board[k][x].value = board[k-1][x].value;
                                board[k-1][x].value = 0;
                            }
                            if(k == 1 && !checkColDown(board,x)) {count++; k = board.length-count;}
                            k--;
                        }
                    }
            }      
            public boolean checkRowLeft(Tile[][] t, int row) {
                Tile[] x = t[row];
                for(int i = x.length-1; i > 0;i--) {
                    if(x[i].value != 0 && x[i-1].value == 0) {return false;}
                }
                return true;
            }
            public void left() {
                for(int y = 0; y < board.length;y++) {
                    int k = 0;
                    while(k < board[0].length-1) {
                        if(board[y][k].value == board[y][k+1].value) {
                            board[y][k].value *=2;
                            board[y][k+1].value = 0;
                        }
                        else if(board[y][k].value == 0) {
                            board[y][k].value = board[y][k+1].value;
                            board[y][k+1].value = 0;
            
                        }
                        if(k == board[0].length-2 && !checkRowLeft(board, y)) {k=-1;}
            
                        k++;
                    }
                }
            }
            public boolean checkRowRight(Tile[][] t, int row) {
                Tile r[] = t[row];
                for(int i = 0; i < r.length-1;i++) {
                    if((r[i].value != 0 && r[i+1].value == 0) || (r[i].value != 0 && r[i].value == r[i+1].value)) {
                        return false;
                    }
                }
                return true;
            }
            
            public void right() { //better time complexity
                for(int y = 0; y < board.length; y++) {
                    int k = board[0].length-1;
                    int count = 0;
                    while(k > 0) {
            
                        if(board[y][k].value == board[y][k-1].value) {
                            board[y][k].value *=2;
                            board[y][k-1].value = 0;
                            
                        }
                        else if (board[y][k].value == 0) {
                            board[y][k].value = board[y][k-1].value;
                            board[y][k-1].value = 0;
                            
                        }
                        
                        if(k == 1 && !checkRowRight(board,y)) {k = board[0].length-count;count++;}
                        k--;
                        
                    }
            
                }
                }
            
    }

    JFrame window;
    Board gameboard;
    java.awt.Dimension screen;


        public void gameOver() {

        }
        Game(int width, int height) {
            this.window = new JFrame();
            this.gameboard = new Board();
            window.setTitle("2048");
            window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            window.setSize(width, height);
            window.setLocationRelativeTo(null);
        }
    Game() {
        this.window = new JFrame();
        this.gameboard = new Board();
        window.setTitle("2048");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int width = screen.width/2, height = screen.height/2;
        window.setSize(width,height);
        window.setLocationRelativeTo(null); //Centers the Frame on the screen
    }

    public void update(JLabel[][] griddy, JPanel tset) {
        for(int row = 0; row < this.gameboard.board.length; row++) {
            for(int col = 0; col < this.gameboard.board[0].length;col++) {
                this.gameboard.rendering(this.gameboard.board[row][col]);
                ImageIcon icon = new ImageIcon(this.gameboard.board[row][col].image);
                Image scaled = icon.getImage().getScaledInstance(window.getWidth()/4, window.getHeight()/4, Image.SCALE_SMOOTH);
                ImageIcon image = new ImageIcon(scaled);
                griddy[row][col].setIcon(image);
                
            }
        }

        if(gameboard.lose()) {
            JPanel end = new JPanel();
            JLabel endScreen = new JLabel(new ImageIcon("Assets/Designer (1).png"));
            end.add(endScreen);
            this.window.dispose();
            JFrame gameOver = new JFrame("Over Screen");
            gameOver.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            gameOver.setSize(window.getWidth(),window.getHeight());
            gameOver.setLocationRelativeTo(null);
            gameOver.getContentPane().add(end);
            gameOver.setVisible(true);
        }
        else if(gameboard.win()) {
            JPanel end = new JPanel();
            JLabel endScreen = new JLabel(new ImageIcon("Assets/Designer (1).png"));
            end.add(endScreen);
            this.window.dispose();
            JFrame gameWin = new JFrame("Win Screen");
            gameWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            gameWin.setSize(window.getWidth(),window.getHeight());
            gameWin.setLocationRelativeTo(null);
            gameWin.getContentPane().add(end);
            gameWin.setVisible(true);
        }
        tset.revalidate();
        tset.repaint();
    }

    
    public void enterState() {
        ImageIcon icon = new ImageIcon("Assets/2048 block start screen.png");
        Image i = icon.getImage().getScaledInstance(window.getWidth(), window.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(i);
        JLabel j = new JLabel(image);
        JPanel enter = new JPanel();
        enter.add(j);
        window.getContentPane().add(enter);
        window.setVisible(true);
        window.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {start();}
            }
        });

    }
     public void start() {
        AtomicInteger fullCount = new AtomicInteger();

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

                else if(e.getKeyCode() == KeyEvent.VK_F11) {
                    java.awt.Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                    int width = screen.width, height = screen.height;

                    fullCount.set(fullCount.get()+1);
                    if(fullCount.get() %2 == 1) {
                    
                    window.setSize(width,height);
                }
                    else {
                    window.setSize(width/2,height/2);
                    }
            }

            }
        });

   
     }
    }
