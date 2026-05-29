import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {

    static JFrame frame;

    static ResourceBundle bundle;
    static Locale currentLocale = new Locale("no");

    static JMenu fileMenu;
    static JMenu helpMenu;
    static JMenu languageMenu;

    static JMenuItem restart;
    static JMenuItem exit;
    static JMenuItem about;

    public static void main(String[] args) {

        bundle = ResourceBundle.getBundle("lang", currentLocale);

        frame = new JFrame("Lab 7 - Rotating Triangle");

        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Панель малювання
        DrawPanel panel = new DrawPanel();
        frame.add(panel, BorderLayout.CENTER);

        // Меню
        JMenuBar menuBar = new JMenuBar();

        fileMenu = new JMenu(bundle.getString("file"));
        helpMenu = new JMenu(bundle.getString("help"));
        languageMenu = new JMenu("Language");

        restart = new JMenuItem(bundle.getString("restart"));
        exit = new JMenuItem(bundle.getString("exit"));
        about = new JMenuItem(bundle.getString("about"));

        // Exit
        exit.addActionListener(e -> System.exit(0));

        // Restart
        restart.addActionListener(e -> panel.reset());

        // About
        about.addActionListener(e ->
                JOptionPane.showMessageDialog(frame,
                        "Lab 7\nRotating Triangle\nSwing + Localization"));

        // Вибір мов
        JMenuItem norwegian = new JMenuItem("Norwegian");
        JMenuItem polish = new JMenuItem("Polish");
        JMenuItem romanian = new JMenuItem("Romanian");
        JMenuItem gaelic = new JMenuItem("Scottish Gaelic");

        norwegian.addActionListener(e -> changeLanguage("no"));
        polish.addActionListener(e -> changeLanguage("pl"));
        romanian.addActionListener(e -> changeLanguage("ro"));
        gaelic.addActionListener(e -> changeLanguage("gd"));

        languageMenu.add(norwegian);
        languageMenu.add(polish);
        languageMenu.add(romanian);
        languageMenu.add(gaelic);

        fileMenu.add(restart);
        fileMenu.add(exit);

        helpMenu.add(about);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        menuBar.add(languageMenu);

        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    static void changeLanguage(String lang) {

        currentLocale = new Locale(lang);

        bundle = ResourceBundle.getBundle("lang", currentLocale);

        fileMenu.setText(bundle.getString("file"));
        helpMenu.setText(bundle.getString("help"));

        restart.setText(bundle.getString("restart"));
        exit.setText(bundle.getString("exit"));
        about.setText(bundle.getString("about"));

        SwingUtilities.updateComponentTreeUI(frame);
    }
}