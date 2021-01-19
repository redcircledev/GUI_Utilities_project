package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class VentanaCalculadora extends JFrame {

    JTextField screen;
    float opA;
    float opB;
    String operation = "";
    float result;
    boolean opIndicator;

    public VentanaCalculadora() {
        this.setTitle("Calculadora");
        this.setSize(330, 530);
        //True es A y False es B
        opIndicator = true;
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

        screen = new JTextField();
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
        JButton nuevoBoton;

        do{
            //Caracteres especiales
            if(j == 3){
                nuevoBoton = new JButton();
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
                nuevoBoton = new JButton();
                switch (j){
                    case 0:
                        nuevoBoton.setFont(fontPantalla);
                        nuevoBoton.setText("C");
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
                nuevoBoton = new JButton();
                nuevoBoton.setFont(fontPantalla);
                nuevoBoton.setText(String.valueOf(numpad + difference));
                panelBotones.add(nuevoBoton);
                numpad++;
            }
            //Ahora que ya terminamos de poner la parte gráfica de los botones añadimos los listener correspondientes
            final JButton finalNuevoBoton = nuevoBoton;
            nuevoBoton.addActionListener(e -> handleActionListeners(finalNuevoBoton.getText()));

            //Aquí controlamos el flujo del do/while
            if(j == 3){
                i++;
                difference = (short) (difference - 6);
                j=0;
            }else {
                j++;
            }

            it++;
            //System.out.println("[" + i + "]" + "[" + j + "]");
        }while(it < 16);

        panelPantalla.add(screen);
        panelPrincipal.add(panelPantalla);
        panelPrincipal.add(panelBotones);

        this.add(panelPrincipal);
    }

    private void handleActionListeners(String buttonPressed){

        StringBuilder screenText = new StringBuilder();
        screenText.append(screen.getText());
        //Vamos a eliminar los ceros a la izquierda
        if(screenText.charAt(0) == '0'){
            screenText.deleteCharAt(0);
        }
        //ahora vamos a excluir números de operaciones
        if(!Objects.equals(buttonPressed, "+") && !Objects.equals(buttonPressed, "-") && !Objects.equals(buttonPressed, "*") && !Objects.equals(buttonPressed, "=")){
            screenText.append(buttonPressed);
        }else {
            if(opIndicator){
                opA = Float.parseFloat(screenText.toString());
                operation = buttonPressed;
                screenText = new StringBuilder();
                screenText.append("0");
                opIndicator = false;
            }else{
                opB = Float.parseFloat(screenText.toString());
                result = Float.parseFloat(handleOperation());
                screenText = new StringBuilder(String.valueOf(result));
                opIndicator = true;
            }
        }
        screen.setText(screenText.toString());
    }

    private String handleOperation(){
        switch(operation){
            case "+":
                result = opA + opB;
                return String.valueOf(result);
            case "-":
                result = opA - opB;
                return String.valueOf(result);
            case "*":
                result = opA * opB;
                return String.valueOf(result);
            default:
                break;
        }
        return "0";
    }
}