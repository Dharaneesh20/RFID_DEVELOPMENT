import gui.LoginWindow;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
    }
}
