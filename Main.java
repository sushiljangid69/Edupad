/**
	* edupad package is the package in which all the classes of Edupad Application and resources are presented
	* classes are Main,Splash,Edupad,RunAction,LookAndFeelAction,Find,FontAction,HelpContentsAction,Views
	@author Sushil Jangid
	@version 1.0
*/
package edupad;
import edupad.*;
/**
	* starter class for Edupad i.e. it first starts the splash screen then runs Edupad application
*/
public class Main
{
	/**
		* main method that starts first the Splash class and then Edupad class
	*/
	public static void main(String[] args)
	{
		Splash obj=new Splash();//creating Splash class object
		obj.setVisible(true);//making Splash class frame visible
		obj.iterate();//for showing splash screen progress
		//Edupad.start();
	}
}