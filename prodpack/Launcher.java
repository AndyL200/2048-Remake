package prodpack;

import javax.swing.SwingUtilities;
import java.util.*;

public class Launcher {
    Launcher() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { 
                System.out.println("Would you like a custom screen size?");
                // Scanner s = new Scanner();
                Game main = new Game();

                main.enterState();

                // main.start();
            }
        });

    }
    public static void main(String args[]) {
        Launcher l = new Launcher();
    }
}
