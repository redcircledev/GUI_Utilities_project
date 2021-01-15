package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class VentanaPrincipal extends JFrame {

    private JPanel panelPrincipal;
    private VentanaCalculadora calc;

    public VentanaPrincipal() {
        this.setTitle("Utilities");
        this.setSize(480, 640);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        panelPrincipal = new JPanel();
        //Esta linea solo es para probar
        panelPrincipal.setBackground(Color.red);
        GridLayout layoutPrincipal = new GridLayout(1, 1);
        panelPrincipal.setLayout(layoutPrincipal);

        JPanel panelTarjeta = new JPanel();
        JPanel panelTarSup = new JPanel();
        panelTarSup.setPreferredSize(new Dimension(50,50));
        JPanel panelTarInf = new JPanel();
        panelTarjeta.setLayout(new BoxLayout(panelTarjeta,BoxLayout.Y_AXIS));

        JLabel imagen = new JLabel();
        Image imgCalc = new ImageIcon(getClass().getResource("/icons/IconCalc.png")).getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
        Icon iconCalc = new ImageIcon(imgCalc);

        imagen.setIcon(iconCalc);

        JButton botonCalculadora = new JButton();
        botonCalculadora.setText("Calculadora");

        panelTarSup.add(imagen);
        panelTarInf.add(botonCalculadora);

        panelTarjeta.add(panelTarSup);
        panelTarjeta.add(panelTarInf);

        panelPrincipal.add(panelTarjeta);

        botonCalculadora.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Aqui vamos a crear otro frame desde otra clase
                calc = new VentanaCalculadora();
                calc.setVisible(true);
            }
        });

        this.add(panelPrincipal);
        this.pack();
    }

}
