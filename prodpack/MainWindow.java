package prodpack;

import javax.swing.JFrame;

public class MainWindow extends game{
    JFrame window;
    game start;
    MainWindow() {
        this.start = new game();
        this.window = new JFrame();
        window.setTitle("2048");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(256,256);
        window.setLocationRelativeTo(null); //Centers the Frame on the screen
    }

    public void show() {

        window.setVisible(true);
    }
}
