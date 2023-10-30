package pool;

import java.awt.Color;
import java.awt.Graphics;

public class pelota {
    int x, y;
    double dx = 0; // Velocidad inicial en X
    double dy = 0; // Velocidad inicial en Y
    int radio = 10; // Ajusta el radio de la pelota
    private int puntoX;
    private int puntoY;
    private double angle = 0;
    private boolean isMoving = false; // Indica si la pelota se está moviendo
    private boolean esPelota1;
    private int puntoRojoX;
    private int puntoRojoY;

    private double velocidad = 0.2;
    public pelota(int x, int y, boolean esPelota1) {
        this.x = x;
        this.y = y;
        puntoX = x + radio;
        puntoY = y;
        this.esPelota1 = esPelota1; // Establece si es la pelota 1 o no
    }
    public void choque() {
        dy *= -1;
    }

    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, 2 * radio, 2 * radio);
        if (esPelota1) {
            g.setColor(Color.RED);
            g.fillOval(puntoRojoX - 5, puntoRojoY - 5, 10, 10);
        }
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

            // Evitar que la pelota se salga de los límites
            if (x < radio) {
                x = radio;
                dx = Math.abs(dx); // Cambiar la dirección en el eje X
            } else if (x > 970 - radio) {
                x = 970 - radio;
                dx = -Math.abs(dx); // Cambiar la dirección en el eje X
            }
            if (y < radio) {
                y = radio;
                dy = Math.abs(dy); // Cambiar la dirección en el eje Y
            } else if (y > 455 - radio) {
                y = 455 - radio;
                dy = -Math.abs(dy); // Cambiar la dirección en el eje Y
            }

            // Reducir la velocidad gradualmente
            dx *= 0.995;
            dy *= 0.995;

            // Ajustar la velocidad mínima para evitar movimientos infinitos
            double minSpeed = 0.1;
            if (Math.abs(dx) < minSpeed) {
                dx = 0;
            }
            if (Math.abs(dy) < minSpeed) {
                dy = 0;
            }
        }
    }



    public void moverPunto(int dx, int dy) {
        puntoX += dx;
        puntoY += dy;

        // Evitar que el punto rojo se salga de la circunferencia de la pelota
        double distancia = Math.sqrt((puntoX - x) * (puntoX - x) + (puntoY - y) * (puntoY - y));
        if (distancia > radio) {
            // Ajusta la posición del punto rojo si se sale de la circunferencia
            double factor = radio / distancia;
            puntoX = (int) (x + (puntoX - x) * factor);
            puntoY = (int) (y + (puntoY - y) * factor);
        }
    }

    public int getPuntoX() {
        return puntoX;
    }

    public int getPuntoY() {
        return puntoY;
    }
    
    public void moverHaciaPuntoRojo() {
        double deltaX = puntoRojoX - (x + radio);
        double deltaY = puntoRojoY - (y + radio);
        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distancia > 0) {  // Evita la división por cero
            double direccion = Math.atan2(deltaY, deltaX);
            iniciarMovimiento(direccion, velocidad);  // Usamos la velocidad que elijas
        }
    }
    public void calcularDireccionYPotencia() {
        double deltaX = puntoX - (x + radio);
        double deltaY = puntoY - (y + radio);
        angle = Math.atan2(deltaY, deltaX);
        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double fuerza = distancia / 10.0; // Ajusta el valor de la fuerza según tus necesidades
        iniciarMovimiento(angle, fuerza);
    }


}
