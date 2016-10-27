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
    JLabel sonnenaufgang;
    JLabel sonnenuntergang;
    JComboBox sun;

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
        setIconImage(getToolkit().getImage("wetter.jpg"));

        String choice[] = {"Anzeige wählen", "Sonnenaufgang", "Sonnenuntergang"};

        temperatureLabel = new JLabel(ws.getTemperature() + " °C");
        humidityLabel = new JLabel(ws.getHumidity()+ " %");
        stadtLabel = new JLabel("Berlin");

        updateButton = new JButton("Update");
        exitButton = new JButton("Exit");
        stadttf = new JTextField("Stadt eingeben");
        sun = new JComboBox(choice);

        add(updateButton);
        add(stadttf);
        add(exitButton);

        add(temperatureLabel);
        add(humidityLabel);
        add(stadtLabel);
        add(sun);

        updateButton.addActionListener(this);
        exitButton.addActionListener(this);
        stadttf.addMouseListener(this);
        stadttf.addKeyListener(this);
        sun.addActionListener(this);

        pack();
        setSize(290, 200);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == updateButton){
            updaten();
        }
        else if(e.getSource() == exitButton){
            setVisible(false);
            dispose();
        }
        else if(e.getSource() == sun){
            JComboBox selectedChoice = (JComboBox) e.getSource();

            if("Sonnenaufgang".equals(selectedChoice.getSelectedItem())){
                setSunrise();
            }
            else if("Sonnenuntergang".equals(selectedChoice.getSelectedItem())){
                setSunset();
            }
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
        //Wenn im JTextField Enter gedrückt wird:
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
        stadttf.setText("                     ");
        setCursor(0);
    }

    public void setSunrise(){
        sonnenaufgang = new JLabel(ws.getSunrise());
        add(sonnenaufgang);
        pack();
    }

    public void setSunset(){
        sonnenuntergang = new JLabel(ws.getSunset());
        add(sonnenuntergang);
        pack();
        setSize(290, 200);
    }

    public void mouseExited(MouseEvent e){}     

    public void mouseEntered(MouseEvent e){}    

    public void mouseReleased(MouseEvent e){}   

    public void mousePressed(MouseEvent e){}   

    public void keyReleased(KeyEvent e){}

    public void keyTyped(KeyEvent e){}
}
