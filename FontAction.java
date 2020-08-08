package edupad;//package for all work done
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import edupad.*;
/**
	* FontAction class creates a font dialog for setting font for Edupad application
*/
public final class FontAction extends JDialog
{
    String fontname[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    String fontstyle[] = {"Regular","Bold","Italic","Bold Italic"};
    String fontsizes[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20",
        "21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42",
        "43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63","64",
        "65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82","83","84","85","86",
        "87","88","89","90","91","92","93","94","95","96","97","98","99","100","101","102","103","104","105","106",
        "107","108","109","110","111","112","113","114","115","116","117","118","119","120","121","122","123","124",
        "125","126","127","128","129","130","131","132","133","134","135","136","137","138","139","140","141","142",
    "143","144","145","146","147","149","150","151","152","153","154","155","156","157","158","159","160","161","162",
    "163","164","165","166","167","168","169","170","171", "172","173","174","175","176","177","178","179","180","181",
    "182","183","184","185","186","187","188","189","190","191","192","193","194","195","196","197","198","199","200",
    "201","202","203","204","205","206","207","208","209","210","211","212","213","214","215","216","217","218","219",
    "220","221","222","223","224","225","226","227","228","229","230","231","232","233","234","235","236","237","238",
    "239","240","241","242","243","244","245","246","247","248","249","250","251","252","253","254","255","256","257",
    "258","259","260"};
    int size;					//default font size
    int pre_style=Font.PLAIN;
    int pre_size=20;
    int styl;			//default font style
	StringBuffer sb = new StringBuffer("Calibri");	//default font family
    StringBuffer styl1 = new StringBuffer("Regular");
	StringBuffer pre_font= new StringBuffer("Calibri");
    JTextArea ta;
    JComboBox<String> c1;	
    JComboBox<String> c2;
    JComboBox<String> c3;
    JLabel x,y,z,preview;
    JButton ok,cancel;
    Border border;
    int sendFontSize;
    Font previewFont,applicationFont;
    static boolean isDarkTheme = false;
    /**
        * parameterized constructor for FontAction which takes the JTextArea of Edupad class
        @param tx JTextArea of Edupad class for setting font to it
    */
    public FontAction(JTextArea ta,String textareaFontName,int textareaFontStyle,int textareaFontSize)
    {
        //System.out.println(textareaFontName+"\t"+textareaFontSize+"\t"+textareaFontSize);
        //setting default font size to the program
        applicationFont = new Font("Bell MT",Font.ITALIC,19);
        setUIFont (new javax.swing.plaf.FontUIResource("Bell MT",Font.ITALIC,19));
        //ta=tx;
        isDarkTheme = getNodeTextContent("lookAndFeel").equals("GlobalDark");
        
        String preview_data="AaBbYyZz";
        setLayout(null);
        
        x=new JLabel("Font:");
        y=new JLabel("Font-Style:");
        z=new JLabel("Size:");
        preview=new JLabel("<html><div style='color:blue;'>"+preview_data+"</div></html>");
        ok=new JButton("OK");
        cancel=new JButton("Cancel");
        
        preview.setHorizontalAlignment(SwingConstants.CENTER);
        preview.setVerticalAlignment(SwingConstants.CENTER);
        
        //comboxes for font-name, font-style and font-sizes
        c1 = new JComboBox<>(fontname);	//will use sb variable
        c2 = new JComboBox<>(fontsizes);//will use size variable
        c3 = new JComboBox<>(fontstyle);//will use styl variable
        //setting the font selected to combobox
        
        c1.setSelectedItem(textareaFontName);                //c1 is for font name
        sb.replace(0,sb.length(),""+textareaFontName);
        c2.setSelectedItem(""+textareaFontSize);             //c2 is for font size
        size = textareaFontSize;
        styl = textareaFontStyle;
        if(textareaFontStyle == Font.PLAIN)
        {
            c3.setSelectedItem("Regular");            //c3 is for font style
        }
        else if(textareaFontStyle == Font.BOLD)
        {
            c3.setSelectedItem("Bold");            //c3 is for font style
        }
        else if(textareaFontStyle == Font.ITALIC)
        {
            c3.setSelectedItem("Italic");
        }
        else
        {
            c3.setSelectedItem("Bold Italic");
        }
        //setting font to preview
        previewFont = new Font(textareaFontName,textareaFontStyle,textareaFontSize);
        preview.setFont(previewFont);
        //adding to JDialog
        add(x);
        add(y);
        add(z);
        add(c1);
        add(c2);
        add(c3);
        add(preview);
        preview.setOpaque(true);
        preview.setBackground(Color.WHITE);
        add(ok);
        add(cancel);
        
        
        border = BorderFactory.createLineBorder(Color.GRAY, 2);
        //setting bounds to the JDialog
        preview.setBorder(border);
        preview.setBounds(30,250,380,203);
        ok.setBounds(220,520,60,30);
        cancel.setBounds(300,520,100,30);
        x.setBounds(30,40,50,15);
        y.setBounds(220,40,80,15);
        z.setBounds(360,40,50,15);
        c1.setBounds(30,60,150,30);
        c3.setBounds(220,60,100,30);
        c2.setBounds(360,60,60,30);
        
        Font comboxFont = new Font("Arial",Font.PLAIN,13);
        c1.setFont(comboxFont);
        c2.setFont(comboxFont);
        c3.setFont(new Font("Arial",0,13));
        

        c1.setEditable(true);
        c2.setEditable(true);
        c3.setEditable(true);
        
        //action listeners for comboboxes
        
        //for font family or font name
        c1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e11)
            {
                JComboBox combobox = (JComboBox) e11.getSource();
                Object selected = combobox.getSelectedItem();
                sb.replace(0,sb.length(),""+selected);
                Font f = new Font(""+sb,styl,size);
                preview.setFont(f);
            }
        });
        //action listener for font sizes
        c2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e12)
            {
                JComboBox combobox = (JComboBox) e12.getSource();
                Object selected = combobox.getSelectedItem();
                try
                {
                    size = Integer.parseInt(""+selected);
                }
                catch(Exception e){size=16;}
                Font f = new Font(""+sb,styl,size);
                preview.setFont(f);
            }
        });
        //action listener for font styles ie: Bold , Italic etc
        c3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e13)
            {
                JComboBox combobox = (JComboBox) e13.getSource();
                Object selected = combobox.getSelectedItem();
                String op = ""+selected;
                styl1.replace(0,styl1.length(),op);
                if(op.equals("Bold"))
                {
                    styl = Font.BOLD;
                    Font f = new Font(""+sb,styl,size);
                    preview.setFont(f);

                }
                else if(op.equals("Italic"))
                {
                    styl = Font.ITALIC;
                    Font f = new Font(""+sb,styl,size);
                    preview.setFont(f);

                }
                else if(op.equals("Bold Italic"))
                {
                    styl = Font.BOLD | Font.ITALIC;
                    Font f = new Font(""+sb,styl,size);
                    preview.setFont(f);
                }
                else
                {
                    styl = Font.PLAIN;
                    Font f = new Font(""+sb,styl,size);
                    preview.setFont(f);
                }
            }
        });
        ok.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e14)
            {
                //for setting the selected item to combo box
                Font f = new Font(""+sb,styl,size);
                ta.setFont(f);
                setFontSize(f.getSize());
                //Font ff = new Font("Arial",Font.PLAIN,size);
                //lines.setFont(ff);
                preview.setFont(f);
                pre_font=sb;
                pre_size=size;
                pre_style=styl;
                if(styl == Font.PLAIN)
                {
                    styl1.replace(0,styl1.length(),"Regular");
                }
                else if(styl == Font.BOLD)
                {
                    styl1.replace(0,styl1.length(),"Bold");
                }
                else if(styl == Font.ITALIC)
                {
                    styl1.replace(0,styl1.length(),"Italic");
                }
                else
                {
                    styl1.replace(0,styl1.length(),"Bold Italic");
                }
                ta.requestFocus();
                dispose();
                
            }
        });
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e15)
            {
                sb=pre_font;
                styl= pre_style;
                size=pre_size;
                dispose();
                ta.requestFocus();
            }
        });
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e16)
            {
                sb=pre_font;
                styl= pre_style;
                size=pre_size;
                dispose();
                ta.requestFocus();
            }
        });
        setBackground(Color.gray);
        
        if (isDarkTheme)
        {
                setBackground(Color.BLACK);
            c1.getEditor().getEditorComponent().setBackground(Color.WHITE);
            c1.getEditor().getEditorComponent().setForeground(Color.BLACK);
            c2.getEditor().getEditorComponent().setBackground(Color.WHITE);
            c2.getEditor().getEditorComponent().setForeground(Color.BLACK);
            c3.getEditor().getEditorComponent().setBackground(Color.WHITE);
            c3.getEditor().getEditorComponent().setForeground(Color.BLACK);
            preview.setForeground(Color.WHITE);
            preview.setBackground(Color.BLACK);
            preview.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            x.setForeground(Color.WHITE);
            y.setForeground(Color.WHITE);
            z.setForeground(Color.WHITE);
            ok.setForeground(Color.BLACK);
            ok.setBackground(Color.WHITE);
            cancel.setForeground(Color.BLACK);
            cancel.setBackground(Color.WHITE);
        }
    }
        /**
            * returns content of node
            @param nodetag
            @return content
        */
        public String getNodeTextContent(String nodetag)
        {
                String content = "";

                try
                {

                    File fXmlFile = new File("files/viewsfile.xml");
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(fXmlFile);

                    doc.getDocumentElement().normalize();

                    NodeList nList = doc.getElementsByTagName("views");

                    for (int temp = 0; temp < nList.getLength(); temp++)
                    {

                        Node nNode = nList.item(temp);

                        Element eElement = (Element) nNode;

                        content = eElement.getElementsByTagName(nodetag).item(0).getTextContent();

                    }
                }catch (ParserConfigurationException | SAXException | IOException | DOMException e)
                {
                }
                return content;
        }
        /**
            * gets the font size selected by the user
            @return sendFontSize it is returned to the Edupad class
        */
        public int getFontSize()
        {
            return sendFontSize;
        }
        /**
            * sets the font size of textarea to the variable sendFontSize
            @param size
        */
        public void setFontSize(int size)
        {
            sendFontSize = size;
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
