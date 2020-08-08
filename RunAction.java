package edupad;//package for all work done
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import edupad.*;
/**
    * RunAction class creates a run dialog for Edupad Application
*/
public class RunAction extends JDialog implements ActionListener
{
    JButton run,cancel,browse;
    JPanel jp1,jp2;
    JTextField txt;
    Font applicationFont;
    public RunAction(boolean isDarkTheme)
    {
        //setting default font size to the program
        applicationFont = new Font("Bell MT",Font.ITALIC,19);
        setUIFont (new javax.swing.plaf.FontUIResource("Bell MT",Font.ITALIC,19));
        Container cp=getContentPane();

        jp1=new JPanel();
        jp2=new JPanel();

        JLabel lb=new JLabel("Select Program to Run");
        lb.setFont(applicationFont);
        txt=new JTextField(30);

        browse=new JButton("Choose File");
        browse.addActionListener(this);

        run=new JButton(" Run ");
        cancel=new JButton(" Cancel ");

        run.addActionListener(this);
        cancel.addActionListener(this);

        jp1.add(lb);
        jp1.add(txt,BorderLayout.CENTER);
        jp1.add(browse,BorderLayout.EAST);

        jp2.add(run);
        jp2.add(cancel);

        cp.add(jp1,BorderLayout.CENTER);
        cp.add(jp2,BorderLayout.SOUTH);
        ImageIcon icon = new ImageIcon(this.getClass().getResource("resources/newwindow.png"));
        setIconImage(icon.getImage());
        if(isDarkTheme)
        {
            lb.setForeground(Color.WHITE);
            txt.setForeground(Color.WHITE);
            browse.setForeground(Color.WHITE);
            run.setForeground(Color.WHITE);
            cancel.setForeground(Color.WHITE);
            lb.setBackground(Color.BLACK);
            txt.setBackground(Color.BLACK);
            browse.setBackground(Color.BLACK);
            run.setBackground(Color.BLACK);
            cancel.setBackground(Color.BLACK);
            jp1.setBackground(Color.BLACK);
            jp2.setBackground(Color.BLACK);
        }
    }

    public void actionPerformed(ActionEvent evt)
    {
        Object src=evt.getSource();
        if(src==browse)
        {
            FileDialog fd=new FileDialog(this,"Select File",FileDialog.LOAD);
            fd.setVisible(true);
            if(fd.getFile()!=null)
            {
                String file=fd.getDirectory()+fd.getFile();
                txt.setText(file);
            }
        }
        else if(src==run)
        {
            if(txt.getText()!=null)
            {
                File file = new File(txt.getText());
                if(file.exists())
                {
                    if(file.toString().contains(".exe"))
                    {
                        try
                        {
                          Runtime.getRuntime().exec(file.toString());
                        }
                        catch(Exception e){ }
                    }
                    else
                    {
                        try
                        {
                            Desktop d = Desktop.getDesktop();
                            d.open(file);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
            dispose();
        }
        else if(src==cancel)
        {
            dispose();
        }
    }
    /**
        * sets font for all swing components
        @param f FontUIResource Class object which sets the font for all components
    */
    public static void setUIFont (javax.swing.plaf.FontUIResource f)
    {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements())
        {
          Object key = keys.nextElement();
          Object value = UIManager.get (key);
          if (value instanceof javax.swing.plaf.FontUIResource)
          {
            UIManager.put (key, f);
            UIManager.put("OptionPane.messageFont",f);
          }
        }
    }
}
