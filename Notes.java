import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 * This class is used to display the result when the user
 * clicks on the 'Spin' button.
 */
@SuppressWarnings("serial")
public class Notes extends JTextPane {
	
	/**
	 * Constructor for Notes Class
	 */
	public Notes() 
	{
		this.setFont(new Font("Helvetica", Font.PLAIN, 16));
		this.setForeground(Color.RED);	//setting font color to red
		
		StyledDocument document = this.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);
		document.setParagraphAttributes(0, document.getLength(), center, false);
		
	    this.setEditable(false);
	    clearNotes();
	}
	
	/**
	 * This method is used to set the text on the notes field.
	 */
	public void setNote(String note) {
		this.setText(note);
	}
	
	/**
	 * This method is used to clear the notes field.
	 */
	public void clearNotes() {
		this.setText("");
	}

}
