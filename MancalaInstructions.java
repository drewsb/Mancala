import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.Font;

class MancalaInstructions extends JFrame implements Runnable
{
    public void run()
    {
        JFrame fred = new JFrame("Mancala Instructions");
        fred.setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTextPane manual = new JTextPane();
        
        manual.setText("*Mancala is played with seven holes per player.\n"+
                       "*Your holes are the 6 small pits on your side of the board, and the store pile "+
                       "on the right hand side.\n"+
                       "*A player starts the game by placing 4 stones into each of their 6 small holes.\n"+
                       "*A turn consists of taking all the stones from one of your holes, and then dropping a stone into each successive hole in a counter-clockwise fashion.\n"+
                       "*If your final stone is placed in your store pile, then you get another turn.\n"+
                       "*If the final stone ends in one of your empty holes, then that stone plus any stones in the opposite hole are placed into your store pile.\n"+
                       "*If you drop a stone in your  store pile"+
                       ", and have stones left, then you continue dropping stones anti clockwise into your opponent's holes.\n"+
                       "*The game ends when all of a player's holes are empty. At that point, the other player places the remaining"+
                       "stones in his store pile.\n"+
                       "*The winner is the person with the most stones in his store pile.");
                       
        
        Font font = new Font("Verdana", Font.ITALIC, 15); 
        manual.setFont(font);
        manual.setEditable(false);
        fred.add(manual);
        fred.setVisible(true); 
}

public static void main(String [] args)
    {
        MancalaInstructions o = new MancalaInstructions();
        javax.swing.SwingUtilities.invokeLater(o);
    }
}