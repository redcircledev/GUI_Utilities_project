package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaCalculadora extends JFrame {

    public VentanaCalculadora() {
        this.setTitle("Calculadora");
        this.setSize(330, 530);
        initComponents();
    }

    private void initComponents(){        

        //Generamos el panel de la pantalla principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal,BoxLayout.Y_AXIS));

        //Generamos los paneles secundarios
        JPanel panelPantalla = new JPanel();
        panelPantalla.setBackground(Color.blue);
        panelPantalla.setPreferredSize(new Dimension(320,40));

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.red);
        GridLayout layoutBotones = new GridLayout(4, 4);
        panelBotones.setLayout(layoutBotones);

        //Creamos una fuente que se vea mejor para la pantalla
        Font fontPantalla = new Font("Consolas", Font.PLAIN, 25);

        JTextField screen = new JTextField();
        screen.setFont(fontPantalla);
        screen.setPreferredSize(new Dimension(320,40));
        screen.setHorizontalAlignment(SwingConstants.RIGHT);
        screen.setEditable(false);
        screen.setText("0");

        //Ahora vamos a inicializar el panel de los botones
        short i = 0;
        short j = 0;
        short it = 0;
        short difference = 6;
        short numpad = 1;

        do{
            //Caracteres especiales
            if(j == 3){
                JButton nuevoBoton = new JButton();
                switch(i){
                    case 0:
                        nuevoBoton.setFont(fontPantalla);
                        nuevoBoton.setText("*");
                        panelBotones.add(nuevoBoton);
                        break;
                    case 1:
                        nuevoBoton.setFont(fontPantalla);
                        nuevoBoton.setText("-");
                        panelBotones.add(nuevoBoton);
                        break;
                    case 2:
                        nuevoBoton.setFont(fontPantalla);
                        nuevoBoton.setText("+");
                        panelBotones.add(nuevoBoton);
                        break;
                    case 3:
                        nuevoBoton.setFont(fontPantalla);
                        nuevoBoton.setText("=");
                        panelBotones.add(nuevoBoton);
                        break;
                }
            }
            //Ultima fila
            else if(i == 3){
                JButton nuevoBoton = new JButton();
                switch (j){
                    case 0:
                        nuevoBoton.setFont(fontPantalla);
                        nuevoBoton.setText(" ");
                        panelBotones.add(nuevoBoton);
                        break;
                    case 1:
                        nuevoBoton.setFont(fontPantalla);
                        nuevoBoton.setText("0");
                        panelBotones.add(nuevoBoton);
                        break;
                    case 2:
                        nuevoBoton.setFont(fontPantalla);
                        nuevoBoton.setText(".");
                        panelBotones.add(nuevoBoton);
                        break;
                }
            }
            //Teclado numerico
            else {
                JButton nuevoBoton = new JButton();
                nuevoBoton.setFont(fontPantalla);
                nuevoBoton.setText(String.valueOf(numpad + difference));
                panelBotones.add(nuevoBoton);
                numpad++;
            }

            if(j == 3){
                i++;
                difference = (short) (difference - 6);
                j=0;
            }else {
                j++;
            }

            it++;
            System.out.println("[" + i + "]" + "[" + j + "]");

        }while(it < 16);

        panelPantalla.add(screen);
        panelPrincipal.add(panelPantalla);
        panelPrincipal.add(panelBotones);

        this.add(panelPrincipal);
    }
}