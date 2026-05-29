import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawPanel extends JPanel {

    private double angle = 0;

    private int[] x = {0, -60, 60};
    private int[] y = {-70, 60, 60};

    public DrawPanel() {

        Timer timer = new Timer(16, e -> {
            angle += 0.02;
            repaint();
        });

        timer.start();
    }

    public void reset() {
        angle = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        AffineTransform old = g2d.getTransform();

        // 🔹 центр
        g2d.translate(cx, cy);

        // 🔹 обертання
        g2d.rotate(angle);

        // 🔹 трикутник
        g2d.setColor(Color.BLUE);
        g2d.fillPolygon(x, y, 3);

        g2d.setTransform(old);
    }
}