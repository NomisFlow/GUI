import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements  ActionListener, MouseListener, KeyListener
{
    WeatherStation ws;
    
    JButton updateButton;
    JButton exitButton;
    
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
        
        //Array für JComboBox
        String choice[] = {"Anzeige wählen", "Sonnenaufgang", "Sonnenuntergang"};

        temperatureLabel = new JLabel(ws.getTemperature() + " °C");
        humidityLabel = new JLabel(ws.getHumidity()+ " %");
        stadtLabel = new JLabel("Berlin");

        updateButton = new JButton("Update");
        exitButton = new JButton("Exit");
        stadttf = new JTextField("Stadt eingeben");
        sun = new JComboBox(choice);

        //Hinzufügen der Bedienelemente
        add(updateButton);
        add(stadttf);
        add(exitButton);
        add(temperatureLabel);
        add(humidityLabel);
        add(stadtLabel);
        add(sun);

        //Hinzufügen der Listener
        updateButton.addActionListener(this);
        exitButton.addActionListener(this);
        stadttf.addMouseListener(this);
        stadttf.addKeyListener(this);
        sun.addActionListener(this);

        //Größe des JFrames einstellen 
        pack();
        setSize(290, 170);
    }

    public void actionPerformed(ActionEvent e) {
        //Updatebutton
        if(e.getSource() == updateButton){
            updaten();
        }
        //Exitbutton
        else if(e.getSource() == exitButton){
            setVisible(false);
            dispose();
        }
        //Kombobox zur Auswahl 
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

    //Leert das Textfeld, wenn man draufklickt
    public void mouseClicked(MouseEvent e){
        if(e.getSource() == stadttf )
            stadttf.setText("");
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
        setCursor(3); //Ladeanimation
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
        
        if(sonnenuntergang != null)
            remove(sonnenuntergang);
        pack();
        setSize(290, 170);
    }

    public void setSunset(){
        sonnenuntergang = new JLabel(ws.getSunset());
        add(sonnenuntergang);
        
        if(sonnenaufgang != null)
            remove(sonnenaufgang);
        pack();
        setSize(290, 170);
    }

    //Methoden die durch Interfaces implementiert werden müssen, aber nicht benutzt werden
    public void mouseExited(MouseEvent e){}     
    public void mouseEntered(MouseEvent e){}    
    public void mouseReleased(MouseEvent e){}   
    public void mousePressed(MouseEvent e){}   
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}
