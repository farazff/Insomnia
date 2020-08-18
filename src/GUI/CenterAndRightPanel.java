package GUI;
/**
 * creates an instance form center and right main panels of the GUI
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class CenterAndRightPanel extends JSplitPane
{
    private JScrollPane previewScroll;
    private JButton save;
    private HardWork hardWork;
    private Color text;
    private ButtonHandler handler = new ButtonHandler();
    private MouseHandler mouse = new MouseHandler();
    private GridLayout flagBody;
    private int gridBodyRows;
    private GridLayout flagHeader;
    private int gridHeaderRows;
    private GridLayout flagmultiPart;
    private int gridmultiPartRows;
    private JTextField address;
    private JLabel fileLoc;
    private JButton addFile;
    private JMenuItem binary;
    private JPanel binaryPanel;
    private JPanel centerPanel;
    private JPanel topCenter;
    private BackgroundMenuBar requests;
    private LineMenu formCenter;
    private LineMenu multiPartCenter;
    private JMenuItem formURL;
    private LineMenu HeaderCenter;
    private JPanel centerOfCenter;
    private JMenuBar requestCenter;
    private JMenu get;
    private JPanel body;
    private JPanel infoCenter;
    private LinedJTextField name;
    private LinedJTextField value;
    private JCheckBox radio;
    private JButton trash;
    private JMenuItem post;
    private JMenuItem put;
    private JButton send;
    private JMenuItem delete;
    private JLabel size;
    private JMenuItem getIn;
    private JScrollPane scrollBody;
    private JScrollPane scrollHeader;
    private JScrollPane scrollmultiPart;
    private JPanel header;
    private JPanel multiPart;
    private Color downC = null;
    private ImagePanel picture;
    private JLabel speed;
    private GridLayout gridAns;
    private int gridAnsRows=0;
    private ArrayList<JButton> buttonsBody;
    private ArrayList <JPanel> infosBody;
    private ArrayList <JTextField> namesBody;
    private ArrayList <JTextField> valuesBody;
    private ArrayList <JCheckBox> checkBody;
    private ArrayList <JButton> buttonsHeader;
    private ArrayList <JPanel> infosHeader;
    private ArrayList <JTextField> namesHeader;
    private ArrayList <JTextField> valuesHeader;
    private ArrayList <JCheckBox> checkHeader;
    private ArrayList <JButton> buttonsmultiPart;
    private ArrayList <JPanel> infosmultiPart;
    private ArrayList <JTextField> namesmultiPart;
    private ArrayList <JTextField> valuesmultiPart;
    private ArrayList <JCheckBox> checkmultiPart;
    private JLabel time;
    private JPanel rightPanel;
    private JPanel centerRight;
    private JPanel topRight;
    private BackgroundMenuBar respondMenuBAr;
    private LineMenu messageBodyRespond;
    private JMenuItem raw;
    private JMenuItem preview;
    private JTextArea rawPanel;
    private JEditorPane previewPanel;
    private JScrollPane scrollRaw;
    private LineMenu headerRespond;
    private JPanel headerPanel;
    private JPanel allHeader;
    private ArrayList<JLabel> labelsOfAns;
    private Path FilePath = null;

    public void setAddressText(String addressText)
    {
        address.setText(addressText);
    }

    public void setGetOption(String method)
    {
        if(method.equals("GET"))
        {
            get.setText("GET " + '\u23f7');
        }
        if(method.equals("POST"))
        {
            get.setText("POST " + '\u23f7');
        }
        if(method.equals("PUT"))
        {
            get.setText("PUT " + '\u23f7');
        }
        if(method.equals("DELETE"))
        {
            get.setText("DELETE " + '\u23f7');
        }
    }

    /**
     * remove muse handler for all forms and add it to the last one
     * @param a if a==1 its for body form url and if it is 2 it is for header
     */
    public void resetMouse(int a)
    {
        if(a==1)
        {
            for(JTextField jTextField : namesBody)
            {
                jTextField.removeMouseListener(mouse);
            }
            namesBody.get(namesBody.size() - 1).addMouseListener(mouse);
        }
        if(a==2)
        {
            for(JTextField jTextField : namesHeader)
            {
                jTextField.removeMouseListener(mouse);
            }
            namesHeader.get(namesHeader.size() - 1).addMouseListener(mouse);
        }
        if(a==3)
        {
            for(JTextField jTextField : namesmultiPart)
            {
                jTextField.removeMouseListener(mouse);
            }
            namesmultiPart.get(namesmultiPart.size() - 1).addMouseListener(mouse);
        }
    }

    /**
     * add a form to the form url or header
     * @param a if a==1 its for body form url and if it is 2 it is for header
     */
    public void addForm(int a,String nameT,String valueT)
    {
        infoCenter = new JPanel(new GridLayout(1, 4, 5, 5));
        infoCenter.setBackground(downC);
        infoCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
        name = new LinedJTextField(nameT);
        name.setBorder(new EmptyBorder(5,5,12,5));
        name.setBackground(downC);
        name.setForeground(text);

        value = new LinedJTextField(valueT);
        value.setBackground(downC);
        value.setForeground(text);
        value.setBorder(new EmptyBorder(5,5,12,5));
        radio = new JCheckBox("",false);
        radio.addActionListener(handler);

        trash = new JButton(Character.toString((char)647332));
        trash.setBackground(downC);
        trash.addActionListener(handler);

        infoCenter.add(name);
        infoCenter.add(value);
        infoCenter.add(radio);
        infoCenter.add(trash);

        if(a==1)
        {
            if(infosBody.size()>=10)
            {
                flagBody.setRows(gridBodyRows + 1);
                gridBodyRows++;
            }
            body.add(infoCenter);
            scrollBody.setVisible(false);
            scrollBody.setVisible(true);
            infosBody.add(infoCenter);
            namesBody.add(name);
            valuesBody.add(value);
            buttonsBody.add(trash);
            checkBody.add(radio);
            resetMouse(1);
        }
        if(a==2)
        {
            if(infosHeader.size()>=10)
            {
                flagHeader.setRows(gridHeaderRows + 1);
                gridHeaderRows++;
            }
            header.add(infoCenter);
            scrollHeader.setVisible(false);
            scrollHeader.setVisible(true);
            infosHeader.add(infoCenter);
            namesHeader.add(name);
            valuesHeader.add(value);
            buttonsHeader.add(trash);
            checkHeader.add(radio);
            resetMouse(2);
        }

        if(a==3)
        {
            if(infosmultiPart.size()>=10)
            {
                flagmultiPart.setRows(gridmultiPartRows + 1);
                gridmultiPartRows++;
            }
            multiPart.add(infoCenter);
            scrollmultiPart.setVisible(false);
            scrollmultiPart.setVisible(true);
            infosmultiPart.add(infoCenter);
            namesmultiPart.add(name);
            valuesmultiPart.add(value);
            buttonsmultiPart.add(trash);
            checkmultiPart.add(radio);
            resetMouse(3);
        }
    }

    /**
     * create center and right main panels of the GUI
     * @param theme the theme of the GUI
     *              dark or light
     */
    private String reqName = "";
    public CenterAndRightPanel(String theme,String reqName,int type)
    {
        this.reqName = reqName;
        labelsOfAns = new ArrayList<>();
        if(theme.equals("dark"))
        {
            text = Color.WHITE;
            picture = new ImagePanel("files/images/bodydark.PNG");
            downC = new Color(46,47,43);
        }
        if(theme.equals("light"))
        {
            text = Color.BLACK;
            downC = Color.WHITE;
            picture = new ImagePanel("files/images/bodylight.PNG");
        }

        //////cceenntteerr
        ////////////////////////
        ////////////////////////
        centerPanel = new JPanel();
        centerPanel.setVisible(false);
        buttonsBody = new ArrayList<>();
        infosBody = new ArrayList<>();
        namesBody = new ArrayList<>();
        valuesBody = new ArrayList<>();
        buttonsHeader = new ArrayList<>();
        infosHeader = new ArrayList<>();
        namesHeader = new ArrayList<>();
        valuesHeader = new ArrayList<>();
        buttonsmultiPart = new ArrayList<>();
        infosmultiPart = new ArrayList<>();
        namesmultiPart = new ArrayList<>();
        valuesmultiPart = new ArrayList<>();
        checkmultiPart=new ArrayList<>();
        checkHeader=new ArrayList<>();
        checkBody=new ArrayList<>();

        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(null);
        centerPanel.setBackground(downC);
        JPanel top2 = new JPanel(new BorderLayout());
        top2.setBackground(Color.white);
        centerPanel.add(top2, BorderLayout.NORTH);
        top2.setPreferredSize(new Dimension(top2.getSize().width, top2.getSize().height + 45));
        address = new JTextField("http://www.google.com");
        address.setBorder(BorderFactory.createLineBorder(Color.WHITE, 10));
        top2.add(address, BorderLayout.CENTER);
        send = new JButton("Send");
        send.addActionListener(handler);
        send.addMouseListener(mouse);
        send.setBorder(null);
        send.setFont(new Font("SansSerif", Font.BOLD, 12));
        send.setPreferredSize(new Dimension(send.getSize().width + 40, send.getSize().height));
        send.setBackground(null);
        top2.add(send, BorderLayout.EAST);

        requestCenter = new JMenuBar();
        requestCenter.setBorder(null);
        requestCenter.setBackground(Color.WHITE);
        get = new JMenu("GET  " + '\u23f7');
        get.setBackground(Color.BLACK);
        get.addMouseListener(mouse);
        get.setFont(new Font("SansSerif", Font.BOLD, 12));

        getIn = new JMenuItem("GET       ");
        getIn.addActionListener(handler);
        getIn.setFont(new Font("SansSerif", Font.BOLD, 12));
        getIn.setForeground(new Color(187, 177, 228));

        post = new JMenuItem("POST          ");
        post.addActionListener(handler);
        post.setFont(new Font("SansSerif", Font.BOLD, 12));
        post.setForeground(new Color(175, 210, 140));

        put = new JMenuItem("PUT           ");
        put.addActionListener(handler);
        put.setFont(new Font("SansSerif", Font.BOLD, 12));
        put.setForeground(new Color(235, 196, 147));

        delete = new JMenuItem("DELETE");
        delete.addActionListener(handler);
        delete.setFont(new Font("SansSerif", Font.BOLD, 12));
        delete.setForeground(new Color(237, 183, 183));

        requestCenter.add(get);
        get.add(getIn);
        get.add(post);
        get.add(put);
        get.add(delete);
        top2.add(requestCenter, BorderLayout.WEST);

        centerOfCenter = new JPanel(new BorderLayout());
        centerOfCenter.add(picture,BorderLayout.CENTER);
        centerPanel.add(centerOfCenter,BorderLayout.CENTER);
        topCenter = new JPanel(new GridLayout(1,1,5,5));
        topCenter.setBorder(null);
        centerOfCenter.add(topCenter,BorderLayout.NORTH);
        requests = new BackgroundMenuBar(downC);
        requests.setPreferredSize(new Dimension(requests.getWidth()+30,requests.getHeight()+45));
        topCenter.add(requests);
        formCenter = new LineMenu("    Form    ");
        formCenter.setFont(new Font("Arial",Font.BOLD,14));
        formCenter.setForeground(text);
        requests.add(formCenter);
        multiPartCenter = new LineMenu(" Multipart ");
        multiPartCenter.addMouseListener(mouse);
        multiPartCenter.setFont(new Font("Arial",Font.BOLD,14));
        multiPartCenter.setForeground(text);
        requests.add(multiPartCenter);
        formURL = new JMenuItem("FORM URL");
        formURL.setFont(new Font("Arial",Font.BOLD,14));
        formURL.addActionListener(handler);

        formCenter.add(formURL);

        HeaderCenter = new LineMenu("   Header    ");
        HeaderCenter.setFont(new Font("Arial",Font.BOLD,14));
        HeaderCenter.setForeground(text);
        HeaderCenter.addMouseListener(mouse);

        binary = new JMenuItem("Binary File  ");
        binary.setFont(new Font("Arial",Font.BOLD,14));
        binary.addActionListener(handler);
        binaryPanel = new JPanel(new GridLayout(8,1,5,5));
        binaryPanel.setBackground(downC);
        fileLoc = new JLabel("   File Location");
        fileLoc.setForeground(text);
        fileLoc.setBackground(downC);
        fileLoc.setBorder(BorderFactory.createLineBorder(Color.GREEN,3));
        addFile = new JButton("Add File");
        addFile.addActionListener(handler);
        binaryPanel.add(fileLoc);
        binaryPanel.add(addFile);

        requests.add(HeaderCenter);
        formCenter.add(binary);

        /////////////////
        /////////////////
        flagBody = new GridLayout(10,1,6,6);
        gridBodyRows = 10;
        body = new JPanel(flagBody);
        scrollBody = new JScrollPane(body);
        scrollBody.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ///////////////////
        ///////////////////

        //////////////////
        flagHeader = new GridLayout(10,1,6,6);
        gridHeaderRows = 10;
        header = new JPanel(flagHeader);
        scrollHeader = new JScrollPane(header);
        scrollHeader.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        /////////////////////

        //////////////////
        flagmultiPart = new GridLayout(10,1,6,6);
        gridmultiPartRows = 10;
        multiPart = new JPanel(flagmultiPart);
        scrollmultiPart = new JScrollPane(multiPart);
        scrollmultiPart.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        /////////////////////

        //////////////////////////
        /////////////////////////


        body.setBackground(downC);
        header.setBackground(downC);
        multiPart.setBackground(downC);

        if(type==1)
        {
            addForm(3, "New name", "New value");
            addForm(2, "New name", "New value");
            addForm(1, "New name", "New value");
        }
        centerPanel.setVisible(true);
        scrollBody.setVisible(true);

        ////////rriigghhtt
        ///////////////////////////
        ///////////////////////////

        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(downC);
        rightPanel.setOpaque(true);
        JPanel top3 = new JPanel(new BorderLayout());
        top3.setBackground(Color.WHITE);
        top3.setPreferredSize(new Dimension(top3.getSize().width,top3.getSize().height+45));
        rightPanel.add(top3,BorderLayout.NORTH);
        top3.setBorder(BorderFactory.createLineBorder(Color.WHITE,4));

        JPanel status = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        status.setBackground(Color.WHITE);
        top3.add(status,BorderLayout.CENTER);

        speed = new JLabel("200 OK");
        speed.setFont(new Font("Arial",Font.BOLD,14));
        speed.setPreferredSize(new Dimension( speed.getWidth()+50,speed.getHeight()+35 ));
        speed.setHorizontalAlignment(SwingConstants.CENTER);
        speed.setVerticalAlignment(SwingConstants.CENTER);
        speed.setBackground(Color.GREEN);
        speed.setOpaque(true);
        speed.setForeground(Color.WHITE);
        status.add(speed);

        time = new JLabel("TIME 0 ms");
        time.setFont(new Font("Arial",Font.BOLD,14));
        time.setPreferredSize(new Dimension( time.getWidth()+65,time.getHeight()+35 ));
        time.setHorizontalAlignment(SwingConstants.CENTER);
        time.setVerticalAlignment(SwingConstants.CENTER);
        time.setBackground(new Color(41,40,36));
        time.setForeground(Color.WHITE);
        time.setOpaque(true);
        status.add(time);

        size = new JLabel("SIZE 0 B");
        size.setFont(new Font("Arial",Font.BOLD,14));
        size.setPreferredSize(new Dimension( size.getWidth()+65,size.getHeight()+35 ));
        size.setHorizontalAlignment(SwingConstants.CENTER);
        size.setVerticalAlignment(SwingConstants.CENTER);
        size.setBackground(new Color(41,40,36));
        size.setForeground(Color.WHITE);
        size.setOpaque(true);
        status.add(size);

        centerRight = new JPanel(new BorderLayout());
        centerRight.setBackground(downC);
        centerRight.setOpaque(true);
        rightPanel.add(centerRight,BorderLayout.CENTER);
        topRight = new JPanel(new GridLayout(1,1,5,5));
        centerRight.add(topRight,BorderLayout.NORTH);
        respondMenuBAr = new BackgroundMenuBar(downC);
        respondMenuBAr.setPreferredSize(new Dimension(respondMenuBAr.getWidth()+45,respondMenuBAr.getHeight()+45));
        messageBodyRespond = new LineMenu(" Raw   ");
        messageBodyRespond.setFont(new Font("Arial",Font.BOLD,14));
        messageBodyRespond.setForeground(text);
        raw = new JMenuItem("Raw");
        raw.addActionListener(handler);
        preview = new JMenuItem("Preview");
        preview.addActionListener(handler);
        respondMenuBAr.add(messageBodyRespond);
        messageBodyRespond.add(raw);
        messageBodyRespond.add(preview);
        headerRespond = new LineMenu("  Header  ");
        headerRespond.setFont(new Font("Arial",Font.BOLD,14));
        headerRespond.setForeground(text);
        headerRespond.addMouseListener(mouse);
        respondMenuBAr.add(headerRespond);
        topRight.add(respondMenuBAr);

        rawPanel = new JTextArea("here" + "\n" + "is" +"here" + "\n" + "raw" +"here" + "\n" + "tex" +"here" + "\n" + "Area" +"here" + "\n" + "hello" ,10,10);
        rawPanel.setEditable(false);
        rawPanel.setForeground(text);
        rawPanel.setBackground(downC);
        rawPanel.setFont(new Font("Arial", Font.BOLD,14));
        scrollRaw = new JScrollPane(rawPanel);
        scrollRaw.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        previewPanel = new JEditorPane();
        previewPanel.setBackground(Color.RED);
        previewPanel.setOpaque(true);
        previewPanel.setEditable(false);
        previewScroll = new JScrollPane(previewPanel);
        previewScroll.setPreferredSize(new Dimension(200,200));


        allHeader = new JPanel(new BorderLayout());
        gridAns = new GridLayout(gridAnsRows,2,5,5);
        headerPanel = new JPanel(gridAns);
        headerPanel.setBorder(new EmptyBorder(5,5,5,5));
        headerPanel.setBackground(downC);
        allHeader.add(headerPanel,BorderLayout.CENTER);
        save = new JButton("save to clipboard");
        save.addActionListener(handler);
        allHeader.add(save,BorderLayout.SOUTH);
        //////////////////////
        //////////////////////

        this.setLeftComponent(centerPanel);
        this.setRightComponent(rightPanel);
        this.setDividerLocation(400);
        this.setDividerSize(6);
        this.setResizeWeight(1);
    }

    /**
     * if theme changed we use this method to change them
     * @param theme the new theme color
     *              dark or light
     */
    public void changeColors(String theme)
    {
        if(theme.equals("dark"))
        {
            text = Color.WHITE;
            picture.changeImage("files/images/bodydark.PNG");
            downC = new Color(46,47,43);
        }
        if(theme.equals("light"))
        {
            text = Color.BLACK;
            picture.changeImage("files/images/bodylight.PNG");
            downC = Color.WHITE;
        }

        ///////////
        ///////////
        ///////////

        for(int i=0;i<namesBody.size();i++)
        {
            namesBody.get(i).setForeground(text);
            valuesBody.get(i).setForeground(text);
        }
        for(int i=0;i<namesHeader.size();i++)
        {
            namesHeader.get(i).setForeground(text);
            valuesHeader.get(i).setForeground(text);
        }
        for(int i=0;i<namesmultiPart.size();i++)
        {
            namesmultiPart.get(i).setForeground(text);
            valuesmultiPart.get(i).setForeground(text);
        }

        formCenter.setForeground(text);
        HeaderCenter.setForeground(text);
        multiPartCenter.setForeground(text);

        messageBodyRespond.setForeground(text);
        headerRespond.setForeground(text);
        rawPanel.setForeground(text);

        //////////////
        /////////////
        /////////////
        /////////////
        centerPanel.setBackground(downC);
        body.setBackground(downC);
        header.setBackground(downC);
        multiPart.setBackground(downC);

        requests.changeColor(downC);
        for(int i=0;i<infosBody.size();i++)
        {
            infosBody.get(i).setBackground(downC);
            namesBody.get(i).setBackground(downC);
            valuesBody.get(i).setBackground(downC);
            buttonsBody.get(i).setBackground(downC);
        }
        for(int i=0;i<infosHeader.size();i++)
        {
            infosHeader.get(i).setBackground(downC);
            namesHeader.get(i).setBackground(downC);
            valuesHeader.get(i).setBackground(downC);
            buttonsHeader.get(i).setBackground(downC);
        }
        for(int i=0;i<infosmultiPart.size();i++)
        {
            infosmultiPart.get(i).setBackground(downC);
            namesmultiPart.get(i).setBackground(downC);
            valuesmultiPart.get(i).setBackground(downC);
            buttonsmultiPart.get(i).setBackground(downC);
        }
        rightPanel.setBackground(downC);
        rawPanel.setBackground(downC);
        headerPanel.setBackground(downC);
        for(JLabel label : labelsOfAns)
        {
            label.setForeground(text);
        }
        respondMenuBAr.changeColor(downC);
        centerRight.setBackground(downC);
        binaryPanel.setBackground(downC);
        binaryPanel.setForeground(text);
        fileLoc.setForeground(text);
        fileLoc.setBackground(downC);
        if(hardWork!=null)
            for(JLabel temp : hardWork.getForChangeColor())
            {
                temp.setForeground(text);
            }
    }

    private class ButtonHandler implements ActionListener, FocusListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource().equals(save))
            {
                String temp = hardWork.getClipBoard();
                StringSelection stringSelection = new StringSelection(temp);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
            //////rriigghhtt
            ///////////////////////
            //////////////////////
            if(e.getSource().equals(raw))
            {
                messageBodyRespond.setText("   Raw   ");
                centerRight.remove(previewScroll);
                centerRight.remove(scrollRaw);
                centerRight.remove(allHeader);
                centerRight.add(scrollRaw,BorderLayout.CENTER);
                centerRight.setVisible(false);
                centerRight.setVisible(true);
            }
            if(e.getSource().equals(preview))
            {
                messageBodyRespond.setText("   Preview   ");
                centerRight.remove(previewScroll);
                centerRight.remove(scrollRaw);
                centerRight.remove(allHeader);
                centerRight.add(previewScroll,BorderLayout.CENTER);
                centerRight.setVisible(false);
                centerRight.setVisible(true);
            }
            Iterator<JButton> it = buttonsBody.iterator();
            int i=0;
            while(it.hasNext())
            {
                JButton temp = it.next();
                if(e.getSource().equals(temp))
                {
                    if(infosBody.size()>1)
                    {
                        if(gridBodyRows>10)
                        {
                            gridBodyRows--;
                            flagBody.setRows(gridBodyRows);
                        }
                        namesBody.get(i).removeFocusListener(handler);
                        body.remove(infosBody.get(i));
                        it.remove();
                        namesBody.remove(i);
                        infosBody.remove(i);
                        checkBody.remove(i);
                        valuesBody.remove(i);
                        resetMouse(1);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"You can't delete the last one!","ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    scrollBody.setVisible(false);
                    scrollBody.setVisible(true);
                    break;
                }
                i++;
            }
            Iterator<JButton> it2 = buttonsHeader.iterator();
            i=0;
            while(it2.hasNext())
            {
                JButton temp = it2.next();
                if(e.getSource().equals(temp))
                {
                    if(infosHeader.size()>1)
                    {
                        if(gridHeaderRows>10)
                        {
                            gridHeaderRows--;
                            flagHeader.setRows(gridHeaderRows);
                        }
                        namesHeader.get(i).removeFocusListener(handler);
                        header.remove(infosHeader.get(i));
                        it2.remove();
                        infosHeader.remove(i);
                        namesHeader.remove(i);
                        checkHeader.remove(i);
                        valuesHeader.remove(i);
                        resetMouse(2);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"You can't delete the last one!","ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    scrollHeader.setVisible(false);
                    scrollHeader.setVisible(true);
                    break;
                }
                i++;
            }

            Iterator<JButton> it3 = buttonsmultiPart.iterator();
            i=0;
            while(it3.hasNext())
            {
                JButton temp = it3.next();
                if(e.getSource().equals(temp))
                {
                    if(infosmultiPart.size()>1)
                    {
                        if(gridmultiPartRows>10)
                        {
                            gridmultiPartRows--;
                            flagmultiPart.setRows(gridmultiPartRows);
                        }
                        namesmultiPart.get(i).removeFocusListener(handler);
                        multiPart.remove(infosmultiPart.get(i));
                        it3.remove();
                        infosmultiPart.remove(i);
                        namesmultiPart.remove(i);
                        checkmultiPart.remove(i);
                        valuesmultiPart.remove(i);
                        resetMouse(3);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"You can't delete the last one!","ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    scrollmultiPart.setVisible(false);
                    scrollmultiPart.setVisible(true);
                    break;
                }
                i++;
            }
            if(e.getSource().equals(getIn))
            {
                get.setText("GET " + '\u23f7');
            }
            if(e.getSource().equals(post))
            {
                get.setText("POST " + '\u23f7');
            }
            if(e.getSource().equals(put))
            {
                get.setText("PUT " + '\u23f7');
            }
            if(e.getSource().equals(delete))
            {
                get.setText("DELETE " + '\u23f7');
            }
            if(e.getSource().equals(formURL))
            {
                formCenter.setText("     Form URL    ");
                centerOfCenter.remove(picture);
                centerOfCenter.remove(binaryPanel);
                centerOfCenter.remove(scrollBody);
                centerOfCenter.remove(scrollHeader);

                centerOfCenter.remove(scrollmultiPart);
                centerOfCenter.add(scrollBody,BorderLayout.CENTER);
                centerOfCenter.setVisible(false);
                centerOfCenter.setVisible(true);
            }

            if(e.getSource().equals(binary))
            {
                formCenter.setText(" Binary File ");
                centerOfCenter.remove(picture);
                centerOfCenter.remove(scrollBody);
                centerOfCenter.remove(scrollHeader);

                centerOfCenter.remove(scrollmultiPart);
                centerOfCenter.add(binaryPanel,BorderLayout.CENTER);
                centerOfCenter.setVisible(false);
                centerOfCenter.setVisible(true);
            }

            if(e.getSource().equals(send))
            {
                hardWork = new HardWork(previewPanel,FilePath,size,address,get,reqName,namesmultiPart,valuesmultiPart,checkmultiPart,namesHeader,valuesHeader,checkHeader,namesBody,valuesBody,checkBody,rawPanel,text,labelsOfAns,gridAnsRows,gridAns,headerPanel,speed,time);
                hardWork.execute();
            }
            if(e.getSource().equals(addFile))
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                JFrame temp = new JFrame("faraz menu");
                int result = fileChooser.showOpenDialog(temp);
                temp.setSize(400, 400);
                temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                temp.setVisible(false);
                // return Path representing the selected file
                if(result != JFileChooser.CANCEL_OPTION)
                {
                    FilePath = fileChooser.getSelectedFile().toPath();
                    fileLoc.setText(FilePath.toString());
                }
            }
        }
        @Override
        public void focusGained(FocusEvent e)
        {

        }

        @Override
        public void focusLost(FocusEvent e)
        {

        }
    }
    private class MouseHandler implements MouseMotionListener,MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if(e.getSource().equals(headerRespond))
            {
                centerRight.remove(previewScroll);
                centerRight.remove(scrollRaw);
                centerRight.remove(binaryPanel);
                centerRight.remove(allHeader);
                centerRight.add(allHeader,BorderLayout.CENTER);
                centerRight.setVisible(false);
                centerRight.setVisible(true);
            }

            if(e.getSource().equals(namesBody.get(namesBody.size()-1)))
            {
                addForm(1,"New name","New value");
            }
            if(e.getSource().equals(namesHeader.get(namesHeader.size()-1)))
            {
                addForm(2,"New name","New value");
            }
            if(e.getSource().equals(namesmultiPart.get(namesmultiPart.size()-1)))
            {
                addForm(3,"New name","New value");
            }

            if(e.getSource().equals(HeaderCenter))
            {
                centerOfCenter.remove(picture);
                centerOfCenter.remove(scrollBody);
                centerOfCenter.remove(binaryPanel);
                centerOfCenter.remove(scrollHeader);

                centerOfCenter.remove(scrollmultiPart);
                centerOfCenter.add(scrollHeader,BorderLayout.CENTER);
                centerOfCenter.setVisible(false);
                centerOfCenter.setVisible(true);
            }
            if(e.getSource().equals(multiPartCenter))
            {
                centerOfCenter.remove(picture);
                centerOfCenter.remove(scrollBody);
                centerOfCenter.remove(binaryPanel);
                centerOfCenter.remove(scrollHeader);

                centerOfCenter.remove(scrollmultiPart);
                centerOfCenter.add(scrollmultiPart,BorderLayout.CENTER);
                centerOfCenter.setVisible(false);
                centerOfCenter.setVisible(true);
            }
        }
        @Override
        public void mousePressed(MouseEvent e)
        {

        }
        @Override
        public void mouseReleased(MouseEvent e)
        {

        }
        @Override
        public void mouseEntered(MouseEvent e)
        {
            if(e.getSource().equals(send))
            {
                send.setBackground(new Color(105,94,184));
                send.setForeground(Color.WHITE);
            }
            if(e.getSource().equals(get))
            {
                get.setBackground(new Color(105,94,184));
                get.setForeground(Color.WHITE);
                get.setOpaque(true);
            }
        }
        @Override
        public void mouseExited(MouseEvent e)
        {
            if(e.getSource().equals(send))
            {
                send.setBackground(Color.WHITE);
                send.setForeground(Color.BLACK);
            }
            if(e.getSource().equals(get))
            {
                get.setBackground(Color.WHITE);
                get.setForeground(Color.BLACK);
                get.setOpaque(true);
            }
        }
        @Override
        public void mouseDragged(MouseEvent e)
        {

        }
        @Override
        public void mouseMoved(MouseEvent e)
        {

        }
    }
}