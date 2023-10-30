package pool;

import java.awt.Color;
import java.awt.Graphics;

public class pelota {
    int x, y;
    double dx = 0; // Velocidad inicial en X
    double dy = 0; // Velocidad inicial en Y
    int radio = 10;
    int puntoRadio = 5; // Tamaño del punto rojo
    private int puntoX;
    private int puntoY;
    private double angle = 0;
    private boolean isMoving = false; // Indica si la pelota se está moviendo

    public pelota() {
        x = 19;
        y = 20;
        puntoX = x + radio;
        puntoY = y;
    }

    public void choque() {
        dy *= -1;
    }

    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, 2 * radio, 2 * radio);
        g.setColor(Color.RED); // Establece el color a rojo
        g.fillOval(puntoX - puntoRadio, puntoY - puntoRadio, 2 * puntoRadio, 2 * puntoRadio);
    }

    public void iniciarMovimiento(double direccion, double fuerza) {
        if (!isMoving) {
            dx = fuerza * Math.cos(direccion);
            dy = fuerza * Math.sin(direccion);
            isMoving = true; // Inicia el movimiento
        }
    }

    public void mover() {
        if (isMoving) {
            x += dx;
            y += dy;
            angle += 0.1;
            puntoX = x + (int) (radio * Math.cos(angle));
            puntoY = y + (int) (radio * Math.sin(angle));
            dx *= 0.995;
            dy *= 0.995;

            if (x < 0 || x > 980 - 2 * radio) {
                dx *= -1;
            }
            if (y < 0 || y > 455 - 2 * radio) {
                dy *= -1;
            }

            if (Math.abs(dx) < 0.1 && Math.abs(dy) < 0.1) {
                isMoving = false;
            }
        }
    }

    public void moverPunto(int dx, int dy) {
        puntoX += dx;
        puntoY += dy;
    }

    public int getPuntoX() {
        return puntoX;
    }

    public int getPuntoY() {
        return puntoY;
    }
    public void calcularDireccionYPotencia() {
        double deltaX = puntoX - (x + radio);
        double deltaY = puntoY - (y + radio);
        angle = Math.atan2(deltaY, deltaX);
        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double fuerza = distancia / 10.0; // Ajusta el valor según tus necesidades
        iniciarMovimiento(angle, fuerza);
    }
}
