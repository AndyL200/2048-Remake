package prodpack;

import javax.swing.SwingUtilities;

public class Launcher {
    Launcher() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow main = new MainWindow();
                main.show();
            }
        });

    }
}
