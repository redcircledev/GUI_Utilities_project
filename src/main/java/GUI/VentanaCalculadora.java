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
        panelPantalla.setPreferredSize(new Dimension(320,20));

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.red);
        GridLayout layoutBotones = new GridLayout(4, 5);
        panelBotones.setLayout(layoutBotones);
        
        JTextField screen = new JTextField();
        screen.setEditable(false);
        screen.setText("0");

        panelPantalla.add(screen);
        panelPrincipal.add(panelPantalla);
        panelPrincipal.add(panelBotones);

        this.add(panelPrincipal);
    }
}
