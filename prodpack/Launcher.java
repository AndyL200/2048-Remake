package prodpack;

import javax.swing.SwingUtilities;

public class Launcher {
    Launcher() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game main = new Game();
                main.start();
            }
        });

    }
    public static void main(String args[]) {
        Launcher l = new Launcher();
    }
}
