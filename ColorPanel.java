import javax.swing.JPanel;
import java.awt.Color;    //There is where the colors love (not Crayola)
import java.awt.Graphics; //Graphics is like your pen...
import java.util.Random;

public class ColorPanel extends JPanel
{
    private Color myColor;
    
    public ColorPanel()
    {
        this(Color.BLUE);
    }
    
    public ColorPanel(Color c)
    {
        myColor = c;
    }
    
    public void setColor(Color c)
    {
        myColor = c;
        repaint();
    }
    public Color getColor()
    {
        return myColor;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(myColor);
        g.fillRect(0, 0, getWidth() , getHeight() );
    }
}