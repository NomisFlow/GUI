import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements  ActionListener, MouseListener, KeyListener
{
    WeatherStation ws;
    JButton updateButton;
    JButton exitButton;
    JButton stadtSendenButton;
    JLabel temperatureLabel;
    JLabel humidityLabel;
    JLabel stadtLabel;
   

    JTextField stadttf;
    public static void main(String[] args){
        GUI g = new GUI();
    }

    public GUI()
    {
        
        ws = new WeatherStation();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setVisible(true);

        temperatureLabel = new JLabel(ws.getTemperature() + " °C");
        humidityLabel = new JLabel(ws.getHumidity()+ " %");
        stadtLabel = new JLabel("Berlin");
        updateButton = new JButton("Update");
        exitButton = new JButton("Exit");
        stadttf = new JTextField("Stadt eingeben");


        
        add(updateButton);
        add(stadttf);
        add(exitButton);

        add(temperatureLabel);
        add(humidityLabel);
        add(stadtLabel);



        updateButton.addActionListener(this);
        exitButton.addActionListener(this);
        stadttf.addMouseListener(this);
        stadttf.addKeyListener(this);

        pack();
        setSize(250, 210);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == updateButton){
            updaten();
        }
        else if(e.getSource() == exitButton){
            setVisible(false);
            dispose();
        }

    }

      public void mouseClicked(MouseEvent e){
        if(e.getSource() == stadttf )
        {
            stadttf.setText("");

        }
    }

    
      public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == 10){
            updaten();
        }
    }
    
    private void updaten()
    {
        setCursor(3);
        ws.setCityName(stadttf.getText());
        ws.update();
        stadtLabel.setText(ws.getCityName() + "");
        temperatureLabel.setText(ws.getTemperature() + " °C");
        humidityLabel.setText(ws.getHumidity() + " %");
        stadttf.setText("                 ");
        setCursor(0);
    }



  












    public void mouseExited(MouseEvent e){}     //Methoden des Interfaces MouseListener, die implementiert, aber nicht benutzt werden müssen.
    public void mouseEntered(MouseEvent e){}    //Methoden des Interfaces MouseListener, die implementiert, aber nicht benutzt werden müssen.
    public void mouseReleased(MouseEvent e){}   //Methoden des Interfaces MouseListener, die implementiert, aber nicht benutzt werden müssen.
    public void mousePressed(MouseEvent e){}    //Methoden des Interfaces MouseListener, die implementiert, aber nicht benutzt werden müssen.

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}
