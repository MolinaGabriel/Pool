package pool;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class juego extends JPanel {
    private Timer timer;
    private ArrayList<pelota> pelotas;
    private Image Fondo;

    public juego() {
        Fondo = new ImageIcon(getClass().getResource("/imagenes/fondo.png")).getImage();
        pelotas = new ArrayList<>();
        pelotas.add(new pelota(200, 200, true));
        pelotas.add(new pelota(200, 100, false));

        timer = new Timer(10, (ActionEvent e) -> {
            for (pelota p : pelotas) {
                p.mover();
            }
            verificarColision(); // Verifica si las pelotas colisionan
            repaint(); // Redibuja la ventana
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pelotas.get(0).calcularDireccionYPotencia();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    pelotas.get(0).moverPunto(-5, 0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    pelotas.get(0).moverPunto(5, 0);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    pelotas.get(0).moverPunto(0, -5);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    pelotas.get(0).moverPunto(0, 5);
                }
            }
        });

        timer.start();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Fondo, 0, 0, getWidth(), getHeight(), this);

        for (pelota p : pelotas) {
            p.paint(g);
        }
    }

    public void verificarColision() {
        for (int i = 0; i < pelotas.size(); i++) {
            for (int j = i + 1; j < pelotas.size(); j++) {
                pelota p1 = pelotas.get(i);
                pelota p2 = pelotas.get(j);
                double distancia = Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
                if (distancia <= 2 * p1.radio) {
                    // La pelota p1 colisiona con la pelota p2
                    double direccion1 = Math.atan2(p2.y - p1.y, p2.x - p1.x);
                    double velocidad1 = Math.sqrt(p1.dx * p1.dx + p1.dy * p1.dy);

                    p1.iniciarMovimiento(direccion1, velocidad1 * -1); // Cambia la dirección de la pelota p1

                    // Si deseas que la pelota p2 también cambie de dirección, descomenta las siguientes líneas:
                    // double direccion2 = Math.atan2(p1.y - p2.y, p1.x - p2.x);
                    // double velocidad2 = Math.sqrt(p2.dx * p2.dx + p2.dy * p2.dy);
                    // p2.iniciarMovimiento(direccion2, velocidad2 * -1); // Cambia la dirección de la pelota p2
                }
            }
        }
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            juego game = new juego();
            JFrame miVentana = new JFrame("Pool");
            miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            miVentana.getContentPane().setBackground(Color.BLACK);
            miVentana.add(game);
            miVentana.setSize(1000, 502);
            miVentana.setVisible(true);
            miVentana.setResizable(false);
        });
    }
}
