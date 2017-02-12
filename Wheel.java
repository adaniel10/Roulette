import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Wheel {

	private ArrayList<Pocket> pocket;
	private FileReader reader;
	private Scanner in;
	private Notes note;
	private int availMoney, winningsMoney, losingMoney, pick, betProfit, balance, userMon, betBalance, totalWon;
	
	/**
	 * Constructor
	 */
	public Wheel()
	{
		pocket = new ArrayList<Pocket>();
		this.note = new Notes();
		availMoney = 1000;
		winningsMoney = 36;
		losingMoney = 0;
		betProfit = 2;
		balance = 0;
		userMon = 0;
		betBalance = 0;
		totalWon = 0;
	}
	
	/**
	 * The FileReader loads the text file for a single wheel which contains information 
	 * about a pocket's color, number and their parity. This file is loaded and each pocket is stored
	 * in an array list.
	 */
	public void populateWheel() {
		try {
			reader = new FileReader("singlewheel.txt");	//opens the text file for reading
			in = new Scanner(reader);
			
			while (in.hasNextLine()) {
				boolean isOdd;
				String line = in.nextLine();			//reads a line
				String [] pocketA = line.split(" ");	//splits the line into 3 tokens
				int num = Integer.parseInt(pocketA[0]);	//stores the first token (pocket number)
				String color = pocketA[1];		//stores the 2nd token (color)
				String parity = pocketA[2];	//stores the 3rd token (boolean isOdd)
				
				//This block of code checks if a pocket is odd or even by looking
				//at their letter.
				if (parity.equals("T"))
					isOdd = true;
				else
					isOdd = false;
				
				//Creates a new pocket object and adds it to the pocket array.
				pocket.add(new Pocket(num,color,isOdd));
			}
			
			in.close();		//close the scanner
			reader.close();	//close the reader
		}
		
		catch(IOException x) {
			JOptionPane.showMessageDialog(null, "The Input File For This Game Has Not Been Found.", 
					"Input File Error!", JOptionPane.ERROR_MESSAGE);
			}
	}
	
	public int getWheelSize() { return pocket.size(); }
	public void setN(Notes note) { this.note = note; }
	public Notes getNote() { return this.note; }
	public void setAvailMoney(int m) { this.availMoney = m; }
	public int getAvailMoney() { return this.availMoney; }
	public void setWinnings(int win) { this.winningsMoney = win; }
	public int getWinning() { return this.winningsMoney; }
	public void setLosing(int los) { this.losingMoney = los; }
	public int getLosing() { return this.losingMoney; }
	public void userInputPocket(int userMon) { this.userMon = userMon; }
	public int getUserInputPocket() { return this.userMon; }
	
	public int randomiser()
	{
		Random rand = new Random();
		pick = rand.nextInt(getWheelSize());
		
		return pocket.get(pick).getNum();
	}
	
	public void checkPocket(boolean userSel, int userMon, boolean o, boolean e)
	{			
		//throw an error if the user has not placed any bets
		if(!userSel && !o && !e)
		{
			JOptionPane.showMessageDialog(null, "You have not made any bets!!!", 
					"Error!", JOptionPane.ERROR_MESSAGE);
			note.clearNotes();
			return;
		}
		
		int landedPocket = randomiser();		//contains the pocket no. in which the ball landed on
		boolean isOdd = pocket.get(pick).getParity();	//contains parity - true or false depending on the pocket
		boolean wonBet = false;				//contains parity - if the user won the bet - then set to true
		
		print(userMon,userSel,landedPocket,"None");
		
		//go to function if even bet is selected
		if(e)
		{
			print(userMon,userSel,landedPocket,"Bet on Even");
			
			if(!isOdd && e && landedPocket != 0)
				wonBet = true;
		}
		
		//go to function if odd bet is selected
		if(o)
		{
			print(userMon,userSel,landedPocket,"Bet on Odd");
			
			if(isOdd && o && landedPocket != 0)
				wonBet = true;
		}
		
		//if the user either selected even or odd bet then go to the following method
		if(o || e)
		{
			printBetResults(wonBet,o, userMon);
		}
		
		//if the user entered a pocket then perform this function
		if(userSel)
		{
			if(getUserInputPocket() == landedPocket)
			{
				balance = userMon*getWinning();
				
				getNote().setNote(getNote().getText()+"\n• Correct Pocket Gained You: + £"+balance);
				totalWon = balance + betBalance;
				availMoney += balance;
			}
			else
			{
				getNote().setNote(getNote().getText()+"\n• Wrong Pocket Lost You: - £"+userMon);
				
				if(o || e)
					totalWon = betBalance - userMon;
				
				availMoney -= userMon;
			}
		}
		
		getNote().setNote(getNote().getText()+"\n• Total Money Won: £"+totalWon);
		System.err.println("Close total: "+totalWon);
		balance = 0;
		betBalance = 0;
		totalWon = 0;
	}
	
	/**
	 * This method prints what the user has chosen - all its inputs and which pocket the ball has landed on.
	 * @param userMon int
	 * @param userSel boolean
	 * @param ranNum int
	 * @param addBet String
	 */
	public void print(int userMon, boolean userSel, int ranNum, String addBet)
	{
		if(userSel)
		{
			getNote().setNote("-----------------------\nBETS PLACED\n-----------------------\n"
					+ "• Money Placed: £"+userMon+"\n• Chosen Pocket: "+getUserInputPocket()+"\n• Additional Bets: "+addBet+"\n\n"+
					"----------------------\nPROCESSING\n----------------------\n"+"• Ball Landed on Pocket: "+ranNum+
					"\n\n-----------------\nOUTCOME\n-----------------");
		}
		else
		{
			getNote().setNote("-----------------------\nBETS PLACED\n-----------------------\n"
					+ "• Money Placed: £"+userMon+"\n• Chosen Pocket: None"+"\n• Additional Bets: "+addBet+"\n\n"+
					"----------------------\nPROCESSING\n----------------------\n"+"• Ball Landed on Pocket: "+ranNum+
					"\n\n-----------------\nOUTCOME\n-----------------");
		}
	}
	
	/**
	 * Perform the following method to print the bet results if only they are selected.
	 * @param wonBet boolean
	 * @param o boolean
	 * @param betBalance int
	 * @param userMon int
	 */
	public void printBetResults(boolean wonBet, boolean o, int userMon)
	{
		//if the user won the bet then print the following to the user
		if(wonBet)
		{
			betBalance = userMon * betProfit;
			totalWon = betBalance;
			
			//if the user made an odd bet then print the following
			if(o)
				getNote().setNote(getNote().getText()+"\n• Bet on Odd Gained You: + £"+betBalance);
			else
				getNote().setNote(getNote().getText()+"\n• Bet on Even Gained You: + £"+betBalance);
			
			availMoney += betBalance;	//updates the available money
		}
		else
		{
			betBalance = userMon;
			
			if(o)
				getNote().setNote(getNote().getText()+"\n• Bet on Odd Lost You: - £"+userMon);
			else
				getNote().setNote(getNote().getText()+"\n• Bet on Even Lost You: - £"+userMon);
			
			availMoney -= userMon;
		}
	}
}
