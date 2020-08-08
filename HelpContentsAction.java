package edupad;//package for all work done
//importing java.net package classes needed
import java.net.URI;
import java.net.URL;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import edupad.*;
/**
	* HelpContentsAction class creates a dialog for providing manual guide help for Edupad application
*/
public class HelpContentsAction
{
  public HelpContentsAction()
  {
    /*final String manualLink ="https://docs.google.com/document/d/159Ymfjor9KcaX458rCnlCfSPFHnmODP9_xlRbfj8eKw/edit?usp=sharing";
    //String manualLink = "resources/ManualGuide.pdf";
    System.out.println("welcome to help");
    /*if (Desktop.isDesktopSupported())
    {
      try
      {
        //URL path = Edupad.class.getResource(manualLink);
        //File f = new File(path.getFile());
        //Desktop.getDesktop().open(f);
        //Desktop.getDesktop().open(manualLink);
        
      } 
      catch (IOException ex)
      {
        System.out.println(ex);
      }
    }*/
        final String manualLinkOnline ="https://docs.google.com/document/d/159Ymfjor9KcaX458rCnlCfSPFHnmODP9_xlRbfj8eKw/edit?usp=sharing";
        String manualLinkOffline = "edupad/resources/Manual.pdf";
        if (Desktop.isDesktopSupported())
        {
            try
            {
                //trys if the manual guide file is available in the system with its files.
                File file = new File(manualLinkOffline);
                Desktop.getDesktop().open(file);
            } catch (Exception ex)
            {
                System.out.println(ex);
                //if file does not exist in the system then try to go online for it.
                try
                {
                    Desktop d = Desktop.getDesktop();
                    d.browse(new URI(manualLinkOnline));
                }
                catch(Exception except)
                {
                    //and if online link is not working or some error is occuring then show message
                    JOptionPane.showMessageDialog(null,"Unable to Load the Manual Guide file\n  Sorry for inconviniance","! ! Error Occured ! !",JOptionPane.ERROR_MESSAGE);

               }
            }
        }
    }
}
