package edupad;//package for all work done
import javax.swing.JProgressBar;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import edupad.*;
/**
	* Splash class is used here for creating splash screen ie. opening screen of the application
*/
public class Splash extends JFrame
{
	//declaring container and components
	JFrame frame;
	JLabel showLabel;
	JLabel author;
	JProgressBar progress;
	JLabel picture;
	//Notepad n;
	int i=0;
	public Splash()//constructor
	{
		//n = new Notepad();
		setSize(400,367);
		setUndecorated(true);
		setLocation(400,300);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setLayout(null);
		getContentPane().setBackground(new Color(32,178,170));//light sea green color
		ImageIcon icon = new ImageIcon(this.getClass().getResource("resources/newwindow.png"));
        setIconImage(icon.getImage());
		
		//creating progress bar
		progress = new JProgressBar(0,100);
		progress.setForeground(new Color(109,107,197));
		progress.setFont(new Font("Script MT Bold",Font.PLAIN,15));
		progress.setBounds(0,0,400,50);
		progress.setStringPainted(true);
		progress.setValue(0);
		add(progress);
		
		picture = new JLabel();
		picture.setIcon(new ImageIcon(this.getClass().getResource("resources/splashscreen.png")));
		add(picture);
		picture.setBounds(0,50,400,317);
	}
	public void iterate()
	{
		for(i=0;i<=100;i=i+10)
		{    
			progress.setValue(i);    
			try
			{
				Thread.sleep(150);
			}catch(Exception e){System.out.println(e);}    
		}
		if(i>100)
		{
			dispose();
			//n.show();
			//Edupad obj=new Edupad();
			//obj.setVisible(true);
			Edupad.start();
		}
	}
}