import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener {
	
	private JPanel centerPanel, rightPanel;
	private JButton spinButton;
	private JLabel moneyLabel, pocketLabel, availLabel, amountLabel;
	private JTextField moneyField, pocketField;
	private JCheckBox betOdd, betEven;
	private boolean chkBetOdd, chkBetEven;
	private Notes note;
	private Wheel sw;
	private int amountI, pocketI;
	
	/**
	 * Constructor - creates the frame
	 */
	public GUI()
	{
		//Creating the frame
		this.setSize(500,350);	//width,height
		this.setLocation(300,250);
		this.setTitle("Roulette");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		sw = new Wheel();
		this.createCentrePanel();
		this.createRightPanel();
		checkBoxListener();
		sw.setN(note);			//initializing the notes
		sw.populateWheel();		//adding pocket numbers to the wheel
	}
	
	/**
	 * Creates and initializes a panel to display the result of the game.
	 */
	public void createCentrePanel()
	{
		//center panel
		centerPanel = new JPanel(new BorderLayout());
		note = new Notes();
        centerPanel.add(note);
		this.add(centerPanel,BorderLayout.CENTER);
	}
	
	/**
	 * Creates the right panel with input buttons and functionalities for the game.
	 */
	public void createRightPanel()
	{
		//Creating buttons
		spinButton = new JButton("Spin");
		
		//Creating labels
		moneyLabel = new JLabel("Money: £");
		pocketLabel = new JLabel("Pocket:   ");
		betEven = new JCheckBox("Bet Even");
		betOdd = new JCheckBox("Bet Odd");
		availLabel = new JLabel("Available Money");
		amountLabel = new JLabel("£"+sw.getAvailMoney());
		
		//Creating text fields
		moneyField = new JTextField(5);
		pocketField = new JTextField(5);
				
		//Sets the grid layout to 3 rows and 1 column	
		rightPanel = new JPanel(new GridLayout(3,1));
		rightPanel.setPreferredSize(new Dimension(180,150));

		JPanel pan = new JPanel(new GridLayout(2,1));
		JPanel pan1 = new JPanel(new GridLayout(2,1));
		JPanel pan2 = new JPanel(new GridLayout(3,1));
		
		//first panel (input)
		JPanel pan_1 = new JPanel();
		JPanel pan_2 = new JPanel();
		
		pan_1.add(moneyLabel);
		pan_1.add(moneyField);		//Adding buttons to the frame
		
		pan_2.add(pocketLabel);
		pan_2.add(pocketField);
		
		pan.add(pan_1);
		pan.add(pan_2);
		
		pan.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Input"));
		
		//second panel (bet odd/even)
		JPanel pan1_1 = new JPanel();
		JPanel pan1_2 = new JPanel();
		pan1_1.add(betEven);
		pan1_2.add(betOdd);
		
		pan1.add(pan1_1);
		pan1.add(pan1_2);
		
		pan1.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Additional Bets"));
		
		//third panel
		JPanel pan2_1 = new JPanel();
		JPanel pan2_2 = new JPanel();
		JPanel pan2_3 = new JPanel();
		
		pan2_1.add(availLabel);
		pan2_2.add(amountLabel);
		pan2_3.add(spinButton);
		
		pan2.add(pan2_1);
		pan2.add(pan2_2);
		pan2.add(pan2_3);
		spinButton.addActionListener(this);
		
		//Add labels/text-field/buttons to the right panel		
		rightPanel.add(pan);
		rightPanel.add(pan1);
		rightPanel.add(pan2);
		
		this.add(rightPanel, BorderLayout.EAST);
	}
	
	/**
	 * This method listens to all the check boxes and setting them to true
     * ..if they are checked otherwise false.
	 */
    public void checkBoxListener() 
    {
	    betOdd.addItemListener(new ItemListener() 
	    {
	    	public void itemStateChanged(ItemEvent e) 
	    	{
	    		if(e.getStateChange() == 1)
	    		{
	    			chkBetOdd = true;
	    			betEven.setEnabled(false);
	    		}
	    		else
	    		{
	    			chkBetOdd = false;
	    			betEven.setEnabled(true);
	    		}
	    	}           
	    });
	
	    betEven.addItemListener(new ItemListener() 
	    {
	    	public void itemStateChanged(ItemEvent e) 
	    	{             
	    		if(e.getStateChange() == 1)
	    		{
	    			chkBetEven = true;
	    			betOdd.setEnabled(false);
	    		}
	    		else
	    		{
	    			chkBetEven = false;
	    			betOdd.setEnabled(true);
	    		}
	    	}           
	    });
    }
    
    /**
     * This method checks if the input string is empty or if it contains a decimal number and 
     * throws an error if it does.
     * @param field String
     * @return String
     */
    public String checkField(String field)
    {
    	field = field.trim();
    	
    	if(field.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "You have not enetered any value.", 
					"Error: Empty Input Detected!", JOptionPane.ERROR_MESSAGE);
			return null;
		}
    	else if(field.contains("."))
		{
			JOptionPane.showMessageDialog(null, "Please input a non-decimal value.", 
					"Error: Decimal Detected!", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		else
			return field;
    }

    /**
     * Executes whenever the 'Spin' button is pressed - checks the amount and the pocket field
     * and ensures they both pass their criteria before executing the program.
     */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object s = e.getSource();
		boolean execute = false;
		boolean checkPocket = false;
		
		if(s == spinButton)
		{
			note.clearNotes();
			
			//checking amount field
			try
			{
				String amountS = checkField(moneyField.getText());
				
				if (amountS == null)
					return;
				
				amountI = Integer.parseInt(amountS);
				
				if(amountI <= 0)
				{
					JOptionPane.showMessageDialog(null, "Please enter an amount greater than £0.", 
							"Invalid Amount!", JOptionPane.ERROR_MESSAGE);
					moneyField.setText("");
					moneyField.requestFocus();
					return;
				}
				
				execute = true;
				
			}
			catch(NumberFormatException ei) 
			{
				JOptionPane.showMessageDialog(null, "Please enter a valid number.", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				moneyField.setText("");
				moneyField.requestFocus();
				execute = false;
			}
			
			//checking pocket field
			try
			{	
				String pocketS = pocketField.getText();
				
				if(pocketS.isEmpty())
					checkPocket = false;
				else
				{
					checkPocket = true;
					
					pocketI = Integer.parseInt(pocketS);
					
					if(pocketI < 0 || pocketI >= sw.getWheelSize())
					{
						JOptionPane.showMessageDialog(null, "Please enter an a valid pocket number from 0-36.", 
								"Invalid Pocket!", JOptionPane.ERROR_MESSAGE);
						pocketField.setText("");
						pocketField.requestFocus();
						return;
					}
					sw.userInputPocket(pocketI);
				}
				
				execute = true;
				
			}
			catch(NumberFormatException ei) 
			{
				JOptionPane.showMessageDialog(null, "Please enter a valid number.", 
						"Error!", JOptionPane.ERROR_MESSAGE);
				pocketField.setText("");
				pocketField.requestFocus();
				execute = false;
			}
			
			//if no errors are present then continue to execute the program
			if(execute)
			{
				sw.randomiser();
				sw.checkPocket(checkPocket, amountI, chkBetOdd, chkBetEven);
				amountLabel.setText("£"+sw.getAvailMoney());
			}
		}
	}

}
