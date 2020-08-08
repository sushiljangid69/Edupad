package edupad;//package for all work done
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import edupad.*;
/**
	* Find class creates a find and replace dialog for Edupad Application
*/
public class Find extends JDialog implements ActionListener
{
        private static final long serialVersionUID = 1L;
        int startIndex=0;
        int select_start=-1;
        JLabel lab1, lab2;
        JTextField textF, textR;
        JButton findBtn, findNext, replace, replaceAll, cancel;
        Font applicationFont;
        public JTextArea txt;
        public Find(JTextArea text,String s,boolean isDark)
        {
                //setting default font size to the program
                applicationFont = new Font("Bell MT",Font.ITALIC,19);
                setUIFont (new javax.swing.plaf.FontUIResource("Bell MT",Font.ITALIC,19));
                setModal(true);
                ImageIcon icon = new ImageIcon(this.getClass().getResource("resources/newwindow.png"));
                setIconImage(icon.getImage());
                this.txt = text;

                lab1 = new JLabel("Find:");
                lab2 = new JLabel("Replace:");
                textF = new JTextField(30);
                textF.setText(s);
                textF.requestFocus();
                textR = new JTextField(30);
                findBtn = new JButton("Find");
                findNext = new JButton("Find Next");
                replace = new JButton("Replace");
                replaceAll = new JButton("Replace All");
                cancel = new JButton("Cancel");
                
                //setting font to lab1 lab2 and buttons
                Font font=new Font("Arial",Font.PLAIN,13);
                lab1.setFont(font);
                lab2.setFont(font);
                textF.setFont(font);
                textR.setFont(font);
                findBtn.setFont(font);
                findNext.setFont(font);
                replace.setFont(font);
                replaceAll.setFont(font);
                cancel.setFont(font);

                // Set Layout NULL
                setLayout(null);

                // Set the width and height of the label
                int labWidth = 80;
                int labHeight = 30;

                // Adding labels
                lab1.setBounds(10,10, labWidth, labHeight);
                add(lab1);
                textF.setBounds(10+labWidth, 10, 120, 30);
                add(textF);
                lab2.setBounds(10, 10+labHeight+10, labWidth, labHeight);
                add(lab2);
                textR.setBounds(10+labWidth, 10+labHeight+10, 120, 30);
                add(textR);

                // Adding buttons
                int bw=115;//button width
                int bh=30;//button height
                findBtn.setBounds(225, 6, bw, bh);
                add(findBtn);
                findBtn.addActionListener(this);

                findNext.setBounds(225, bh+5, bw, bh);
                add(findNext);
                findNext.addActionListener(this);

                replace.setBounds(225, 2*bh+5, bw, bh);
                add(replace);
                replace.addActionListener(this);

                replaceAll.setBounds(225, 3*bh+5, bw, bh);
                add(replaceAll);
                replaceAll.addActionListener(this);

                cancel.setBounds(225, 4*bh+5, bw, bh);
                add(cancel);
                cancel.addActionListener(this);


                // Set the width and height of the window
                int width = 360;
                int height = 210;
                
                //checking if it is dark mode or not
                if(isDark)
                {
                        setBackground(Color.BLACK);
                        findBtn.setForeground(Color.WHITE);
                        findNext.setForeground(Color.WHITE);
                        replace.setForeground(Color.WHITE);
                        replaceAll.setForeground(Color.WHITE);
                        cancel.setForeground(Color.WHITE);
                        lab1.setForeground(Color.WHITE);
                        lab2.setForeground(Color.WHITE);
                        textF.setForeground(Color.BLACK);
                        textR.setForeground(Color.BLACK);
                        
                }
                
                addWindowListener(new WindowAdapter()
                {
                        public void windowClosing(WindowEvent e16)
                        {
                            dispose();
                        }
                });
                // Set size window
                setSize(width,height);

                // center the frame on the frame
                //setLocationRelativeTo(txt);
                setLocation(200,150);
                setVisible(true);
        }
        public void actionPerformed(ActionEvent e)
        {
                if(e.getSource() == findBtn)
                {
                   find();
                }
                else if(e.getSource() == findNext)
                {
                   findNext();
                }
                else if(e.getSource() == replace)
                {
                    replace();
                }
                else if(e.getSource() == replaceAll)
                {
                   replaceAll();
                }
                else if(e.getSource() == cancel)
                {
                    this.dispose();
                    txt.requestFocus();
                }
        }
        public void find()
        {
                select_start = txt.getText().toLowerCase().indexOf(textF.getText().toLowerCase());
                if(select_start == -1)
                {
                    startIndex = 0;
                    JOptionPane.showMessageDialog(null, "Could not find \"" + textF.getText() + "\"!");
                    return;
                }
                if(select_start == txt.getText().toLowerCase().lastIndexOf(textF.getText().toLowerCase()))
                {
                    startIndex = 0;
                }
                int select_end = select_start + textF.getText().length();
                txt.select(select_start, select_end);
        }

        public void findNext()
        {
                String selection = txt.getSelectedText();
                try
                {
                    selection.equals("");
                }
                catch(NullPointerException e)
                {
                    selection = textF.getText();
                    try
                    {
                        selection.equals("");
                    }
                    catch(NullPointerException e2)
                    {
                        selection = JOptionPane.showInputDialog("Find:");
                        textF.setText(selection);
                    }
                }
                try
                {
                    int select_start = txt.getText().toLowerCase().indexOf(selection.toLowerCase(), startIndex);
                    int select_end = select_start+selection.length();
                    txt.select(select_start, select_end);
                    startIndex = select_end+1;

                    if(select_start == txt.getText().toLowerCase().lastIndexOf(selection.toLowerCase()))
                    {
                        startIndex = 0;
                    }
                }
                catch(NullPointerException e)
                {}
        }

        public void replace()
        {
                try
                {
                    find();
                    if (select_start != -1)
                        txt.replaceSelection(textR.getText());
                }
                catch(NullPointerException e)
                {
                    System.out.print("Null Pointer Exception: "+e);
                }
        }

        public void replaceAll()
        {
                txt.setText(txt.getText().toLowerCase().replaceAll(textF.getText().toLowerCase(), textR.getText()));
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