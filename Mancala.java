/*Drew Boyette
 *Vinay Kshirsagar 
 *5/27/14 
 *Computer Science 404 
 *Mr. Boyarsky 
 *Mancala Program
*/ 

//Skeleton 
import java.util.ArrayList; 
import javax.swing.JTextField; 
import javax.swing.JTextArea;
import javax.swing.JFrame; 
import javax.swing.JPanel; 
import javax.swing.JButton; 
import java.awt.Container; 
//Layout 
import java.awt.Font; 
import java.awt.GridLayout; 
import java.awt.BorderLayout; 
import java.awt.Color; 
import javax.swing.JScrollPane;
//Menu 
import javax.swing.JMenuBar; 
import javax.swing.JMenu; 
import javax.swing.JMenuItem; 
//Events 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener;  
 
 
public class Mancala extends JFrame implements Runnable 
{ 
    private JTextField updater; //instructions
    private JPanel p1Holes; 
    private JPanel p2Holes; 
    private JPanel holes; 
    private JPanel p1Store; 
    private JPanel p2Store; 
    private boolean isP1Turn; 
    private Hole[] holeOrder; 
    private int[] p1Beads; 
    private int[] p2Beads; 
    private Container cp;
     
    public Mancala() 
    { 
        p1Holes = new JPanel(); 
        p2Holes = new JPanel(); 
        p1Store = new JPanel(); 
        p2Store = new JPanel(); 
        I
        Font font = new Font("Verdana", Font.BOLD, 20); 
        updater.setHorizontalAlignment(JTextField.CENTER); 
        updater.setFont(font); 
        updater.setForeground(Color.BLUE); 
        updater.setBackground(Color.BLACK); 
    } 
    
     
    public void makeBoard() 
    { 
        isP1Turn = true; //Player one will go first.
        holeOrder = new Hole[14]; //This creates an array of holes. Holes are the pits on the mancala board (There are 14).
        for(int i = 0; i < 14; i++) 
        { 
            final int y = i; 
            if( (i+1)%7 == 0 ) //for stores
            { 
                holeOrder[i] = new Hole(0); 
                holeOrder[i].setColor(new Color(0x99FF66)); //This is the initial color of the store piles.  
                holeOrder[i].addMouseListener(new MouseListener(){ 
                    public void mouseEntered(MouseEvent e) 
                    { 
                        holeOrder[y].setColor(Color.RED); //A store pile will turn red when a mouse hovers it.
                    } 
                     
                    public void mouseExited(MouseEvent e) 
                    { 
                        holeOrder[y].setColor(new Color(0x99FF66)); 
                    } 
                    public void mousePressed(MouseEvent e) {} 
                    public void mouseReleased(MouseEvent e) {} 
                    public void mouseClicked(MouseEvent e) {} 
                }); 
            } 
            else //for regular holes
            { 
                holeOrder[i] = new Hole(); 
                holeOrder[i].setColor(new Color(0xABCDEF)); //Initial color of regular holes (There are 12)
                 
                holeOrder[i].addMouseListener(new MouseListener(){ 
                    public void mouseEntered(MouseEvent e) 
                    { 
                        if(isP1Turn && (0<=y)&&(y<=6)) 
                        { 
                            holeOrder[y].setColor(Color.YELLOW); 
                        } 
                        if(!isP1Turn && (7<=y)&&(y<=13)) 
                        { 
                            holeOrder[y].setColor(Color.YELLOW); 
                        } 
                    } 
                     
                    public void mouseExited(MouseEvent e) 
                    { 
                        holeOrder[y].setColor(new Color(0xABCDEF)); 
                    } 
                     
                    public void mousePressed(MouseEvent e) {} 
                    public void mouseReleased(MouseEvent e) {} 
                     
                    public void mouseClicked(MouseEvent e)  //This is where the action is.
                    { 
                        int d = 0;
                        if(d==0) 
                        { 
                            if(isP1Turn && (0<=y)&&(y<=5)) 
                            { 
                                int[] old = p1Beads(); 
                                int n = holeOrder[y].getBeads(); 
                                holeOrder[y].subtractAll(); //picks up beads from clicked hole
                                if(arrayEquals(old,p1Beads())==false) 
                                { 
                                    //checks to see if board has changed
                                    //if not, e.g. if you click on a hole with 0 beads, it's still your turn
                                    //otherwise, turn switches
                                    holeOrder[y].setColor(new Color(0xABCDEF)); 
                                    switcher(); //Switches players. Check the method to see exactly what is does. 
                                } 
                                for(int i = 1; i<=n ;i++) 
                                { 
                                    //Y is the indentification number of the hole. i is the
                                    //number the beads that have been placed into succeeding piles as of right now. 
                                    
                                    int subractor = ((y+i)/14)*14;
                                    int landing = y+i-subractor; //This determines the number of the hole in which the bead will land
                                    //If the number of beads plus the number hole it is in is >14
                                    // the subtractor will revert back to the start of the array list to avoid index out of bounds errors. 
                                    
                                    if(i==n && landing==6) //If player one's final bead lands in his store pile (most right pile), he will take another turn. 
                                    { 
                                        switcher(); 
                                    } 
                                    
                                    if(i==n && holeOrder[landing].getBeads()==0 && landing>=0 && landing<=5) //If player one's final bead lands in an empty
                                    {                                                                        //hole on his side, he will take his one bead and
                                        holeOrder[6].addBeads(holeOrder[12-landing].getBeads()+1);           //the opponent's beads on the opposite side and put
                                        holeOrder[12-landing].subtractAll();                                 //them in his store pile.
                                    } 
                                    else if(landing==13) //This allows player one to skip player two's store pile when he is placing the beads.
                                    { 
                                        n+=1; 
                                    } 
                                    else  //If none of the previously mentioned events happen, the bead will be dropped into the hole.
                                    { 
                                        holeOrder[landing].addOne(); 
                                    } 
                                } 
                            } 
                             
                            if(!isP1Turn && (7<=y)&&(y<=12)) //Player two's time to shine
                            { 
                                int[] old = p2Beads(); 
                                int n = holeOrder[y].getBeads();  
                                holeOrder[y].subtractAll(); 
                                if(!arrayEquals(old,p2Beads())) 
                                { 
                                    holeOrder[y].setColor(new Color(0xABCDEF)); 
                                    switcher(); 
                                } 
                                for(int i = 1; i<=n ;i++) 
                                { 
                                    int subractor = ((y+i)/14)*14; 
                                    int landing = y+i-subractor; 
                                    if(i==n && landing==13) 
                                    { 
                                        switcher(); 
                                    } 
                                    if(i==n && holeOrder[landing].getBeads()==0 && landing>=7 && landing<=12)  
                                    { 
                                        holeOrder[13].addBeads(holeOrder[12-landing].getBeads()+1); 
                                        holeOrder[12-landing].subtractAll(); 
                                    } 
                                    else if(landing==6) 
                                    { 
                                        n+=1; 
                                    } 
                                    else 
                                    { 
                                        holeOrder[landing].addOne(); 
                                    } 
                                } 
                            } 
                        }
                        if(P1sum()==0 || P2sum()==0) //checks to see if either side has no beads left
                        { 
                            endGame(); //GAMEOVER
                        } 
                    } 
                }); 
            } 
        } 
         
        //0-5: P1 holes 
        //6: P1 store 
        //7-12: P2 holes 
        //13: P2 store 
         
        cp = getContentPane(); 
        //Holes 
        holes = new JPanel(); 
         
         
        holes.setLayout( new GridLayout(2,1) ); 
        cp.add(BorderLayout.CENTER, holes); 
         
        holes.add(p2Holes); 
        holes.add(p1Holes); 
         
        p1Holes.setLayout( new GridLayout(1,6) ); 
        p2Holes.setLayout( new GridLayout(1,6) ); 
         
        for(int i = 0; i < 6; i++) 
        { 
            p1Holes.add(holeOrder[i]); 
        } 
        for(int i = 13; i > 6 ; i--) 
        { 
            p2Holes.add(holeOrder[i]); 
        } 
         
        //Stores 
         
        updater.setEditable(false); 
        cp.add(BorderLayout.NORTH, updater); 
        cp.add(BorderLayout.EAST, p1Store); 
        cp.add(BorderLayout.WEST, p2Store); 
         
        p1Store.setLayout( new GridLayout(1,1) ); 
        p2Store.setLayout( new GridLayout(1,1) ); 
         
        p1Store.add(holeOrder[6]); 
        p2Store.add(holeOrder[13]); 
         
         
    } 
     
    public void makeMenus() //Makes the menu of the Mancala Board. 
    { 
        JMenuBar bar = new JMenuBar(); 
        setJMenuBar(bar); 
        JMenu fileMenu = new JMenu("Options"); 
        bar.add(fileMenu); 
         
        JMenuItem quitItem = new JMenuItem("Quit"); 
        JMenuItem resetItem = new JMenuItem("Reset");  //resets game board
        JMenuItem manualItem = new JMenuItem("Instructions");
        
        fileMenu.add(quitItem); 
        fileMenu.add(resetItem); 
        fileMenu.add(manualItem);
         
        resetItem.addActionListener(new ResetListener()); 
        quitItem.addActionListener(new QuitListener()); 
        manualItem.addActionListener(new InstructionListener());
    } 
     
    class ResetListener implements ActionListener //Resets the game when clicked.
    { 
        public void actionPerformed(ActionEvent e) 
        { 	
            for(int i = 0; i<14 ; i++) 
            { 
                holeOrder[i].setBeads(4); 
            } 
            holeOrder[6].setBeads(0); 
            holeOrder[13].setBeads(0); 
            if(!isP1Turn) 
            { 
                switcher(); 
            }
            updater.setText("Player One's Turn!");
        } 
    } 
    
    class InstructionListener implements ActionListener //Produces another GUI with instructions. 
    { 
        public void actionPerformed(ActionEvent e) 
        {
            MancalaInstructions manual = new MancalaInstructions();
            javax.swing.SwingUtilities.invokeLater(manual);
        }
    }
            
    
     
    public void endGame() //Ends the game when one of the players run out of beads. 
    {   
        int P1score = holeOrder[6].getBeads()+P1sum(); 
        int P2score = holeOrder[13].getBeads()+P2sum();  
        
        if(P1sum()!=0)
        {
            holeOrder[6].addBeads(P1sum());
            for(int i = 0;i<6;i++)
            {
                holeOrder[i].subtractAll();
            }
        }
        
        if(P2sum()!=0)
        {
            holeOrder[13].addBeads(P2sum());
            for(int i = 7;i<13;i++)
            {
                holeOrder[i].subtractAll();
            }
        }
        
         
        if(P1score > P2score) 
        { 
            System.out.println("Player 1 has won!"); 
            updater.setText("Player 1 has won!"); 
            updater.setForeground(Color.BLUE);
        } 
        else 
        { 
            System.out.println("Player 2 has won!"); 
            updater.setText("Player 2 has won!"); 
            updater.setForeground(Color.RED);
        } 
    } 
 
    public int[] p1Beads() //Produces an array of ints that contains the number of beads in each of player one's holes in chronological order. 
    { 
        p1Beads = new int[6]; 
        for(int i = 0;i<6 ; i++) 
        { 
            p1Beads[i]=holeOrder[i].getBeads(); 
        } 
        return p1Beads; 
    } 
     
    public int[] p2Beads() 
    { 
        p2Beads = new int[6]; 
        for(int i = 0;i<6 ; i++) 
        { 
            p2Beads[i]=holeOrder[i+7].getBeads(); 
        } 
        return p2Beads; 
    } 
     
    public boolean arrayEquals(int[] one, int[] two) //Compares two arrays of ints for equivalence. This is used to make sure a player 
    {                                                //made his turn by placing his stones into the succeeding holes. 
        int d= 0; 
        for(int i = 0; i<6 ; i++) 
        { 
            if(one[i]!=two[i]) 
            { 
                d+=1; 
            } 
        } 
        if(d==0) 
        { 
            return true; 
        } 
        else 
        { 
            return false; 
        } 
        //won't work if arrays are different sizes; in this case it doesn't matter
    } 
     
//    We tried to put in a timer, but we couldn't figure it out.
    
//    public void mySleep(int timer) 
//    { 
//        try 
//        { 
//            Thread.sleep(timer); 
//        } 
//        catch(InterruptedException ex) 
//        { 
//        } 
//    } 
     
 
     
     
     
    public void switcher() //switches turn
    { 
        if(isP1Turn) 
        { 
            isP1Turn = false; 
            updater.setText("Player Two's Turn!"); 
            updater.setForeground(Color.RED); 
        } 
        else 
        { 
            isP1Turn=true; 
            updater.setText("Player One's Turn!"); 
            updater.setForeground(Color.BLUE); 
        } 
    } 
     
     
    public int P1sum() //adds up # of beads on P1's side
    { 
        int d = 0; 
        for(int i =0; i<6 ; i++) 
        { 
            d+=holeOrder[i].getBeads(); 
        } 
        return d; 
    } 
      
    public int P2sum() 
    { 
        int e = 0; 
        for(int i =7; i<13 ; i++) 
        { 
            e+=holeOrder[i].getBeads(); 
        } 
        return e; 
    } 
     
    public void run() 
    { 
        setSize(1000, 330); 
        setResizable(false); 
        setTitle("Mancala"); 
        makeBoard(); 
        makeMenus(); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setVisible(true);         
    } 
     
 
             
     
    public static void main(String[] args) 
    { 
        Mancala mancala = new Mancala(); 
        javax.swing.SwingUtilities.invokeLater(mancala); 
    } 
     
     
}