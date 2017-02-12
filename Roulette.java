//import java.awt.*;
//
//public class Roulette extends EasyApp
//{  public static void main(String[] args)
//{
//	new Roulette();
//}
//
//Label lMoney = addLabel("Money",40,40,60,30,this);
//TextField tMoney = addTextField("",100,40,100,30,this);
//Button bBet = addButton("Bet",40,70,80,30,this);
//Button bSpin = addButton("Spin",120,70,80,30,this);
//TextField tBet = addTextField("",40,100,80,40,this);
//TextField tChoice = addTextField("",40,140,80,40,this);
//TextField tNumber = addTextField("",120,100,80,80,this);
//int money = 1000;
//int bet = 0;
//
//int betting = 0;
//String choice = "";
//
//public void actions(Object source,String command)
//{
//	if(source == bBet)
//	{  makeBet();  }
//	else if (source == bSpin)
//	{  spin(); }
//}
//
//public Roulette()
//{
//	setTitle("Raging Roulette");
//	setSize(250,200);
//	setBackground(Color.green);
//	tNumber.setFont(new Font("Arial",0,50));
//	tNumber.setForeground(Color.white);
//	tNumber.setBackground(Color.green);
//	tBet.setFont(new Font("Arial",0,30));
//	tChoice.setFont(new Font("Arial",0,30));
//	tMoney.setFont(new Font("Arial",0,16));
//
//	tMoney.setText(money + "");
//}
//
//public void makeBet()
//{
//	do
//	{
//		betting = inputInt("How much do you want to bet?");
//	}  while (betting < 0 || betting > money);
//
//	bet = betting;
//
//	choice = input("Betting on : Red, Black, or a Number");
//
//	tBet.setText(betting + "");
//	tChoice.setText(choice);
//}
//
//public int rand(int lowest, int highest)
//{
//	return (int)(Math.floor(Math.random()*(highest-lowest+1)+lowest));
//}
//
//public void spin()
//{
//	long start = System.currentTimeMillis();
//	do
//	{
//		int num = rand(0,36);
//		tNumber.setText( num + "");
//		if (num == 0)
//		{  tNumber.setBackground(Color.green); }
//		else if ( ((num-1)/3) % 2 == 0 )
//		{  tNumber.setBackground(Color.red); }
//		else
//		{  tNumber.setBackground(Color.black); }
//		repaint();
//	}  while (System.currentTimeMillis()-start < 3000);
//	money = money + checkWinner(); 
//	tMoney.setText(money + "");
//
//}      
//
//public int checkWinner()
//{
//	if ((tNumber.getBackground() == Color.red) && (tChoice.getText().equals("Red")))
//	{  return bet; }
//	else if ((tNumber.getBackground() == Color.black) && (tChoice.getText().equals("Black")))
//	{  return bet; }
//	else if (tNumber.getText().equals( tChoice.getText() ) )
//	{  return 36*bet; }
//	else
//	{  return -bet; }
//}
//
//public void pause(long millis)
//{
//	long start = System.currentTimeMillis();
//	do
//	{
//	} while (System.currentTimeMillis() - start < millis) ;
//}
//}