package GUI;
/**
 * the main class of the GUI
 * contains the left panel and handling it
 * and closing operations
 *
 * @Author : faraz farangizadeh
 */

import Curl.ReadObjectFromFile;
import Curl.Request;
import Curl.WriteObjectToFile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

public class MyWindow
{
    private GridLayout grid;
    private int gridN;
    private Color text;
    private JFrame frame;
    private boolean leftVisible;
    private boolean isFullScreen;
    public boolean getLeftVisible()
    {
        return leftVisible;
    }
    public void changeLeftVisible()
    {
        leftVisible = !leftVisible;
    }
    public JFrame getFrame()
    {
        return frame;
    }
    public boolean getIsFullScreen()
    {
        return isFullScreen;
    }
    public void changeFullScreen()
    {
        isFullScreen = !isFullScreen;
    }
    private JPanel all;
    private JMenuItem about;
    private JMenuItem toggleFS;
    private JMenuItem exit;
    private JMenuItem toggleSB;
    private JMenu help;
    private JMenuItem option;
    private JSplitPane sp2;
    private JPanel centerLeft;
    private ButtonHandler handler = new ButtonHandler();
    private MouseHandler mouse = new MouseHandler();
    private JMenuItem helpIn;
    private ArrayList<String> type = new ArrayList<>();
    private File info;
    private FileReader reader;
    private  Scanner scan;
    private JPanel leftJPanel;
    private JMenuBar menuBarLeft;
    private JMenuItem newRequest;
    private JMenu add;
    private JMenu delete;
    private JMenuItem deleteReq;
    private JPanel top;
    private ArrayList<RequestPaint> requests;
    private ArrayList<CenterAndRightPanel> centerAndRights;
    private ArrayList<String> namesOfRequests;
    private CenterAndRightPanel both;
    /**
     * read theme and exit or tray from the file and store it into type ArrayList
     * @throws FileNotFoundException
     */
    public void readFile() throws FileNotFoundException
    {
        info = new File("files/setting/info.txt");
        reader = new FileReader("files/setting/info.txt");
        scan = new Scanner(reader);
        type = new ArrayList<>();
        while(scan.hasNext())
        {
            type.add(scan.next());
        }

    }

    Color topC = null;
    Color downC = null;
    private ImagePanel cc;
    private JPanel rr;

    /**
     * create an instance form left Main panel and the main frame of the GUI
     * @throws FileNotFoundException
     */
    public MyWindow() throws IOException, ClassNotFoundException
    {
        requests = new ArrayList<>();
        centerAndRights = new ArrayList<>();
        namesOfRequests = new ArrayList<>();
        readFile();
        if(type.get(0).equals("dark"))
        {
            text = Color.WHITE;
            topC = new Color(105,95,184);
            downC = new Color(46,47,43);
        }
        if(type.get(0).equals("light"))
        {
            text = Color.BLACK;
            topC = new Color(105,95,184);
            downC = Color.WHITE;
        }
        isFullScreen = false;
        leftVisible = true;
        frame = new JFrame();
        ImageIcon img = new ImageIcon("files/images/insomnia.png");
        frame.setTitle("Insomnia");
        frame.setSize(1117, 650);
        frame.setLayout(new BorderLayout());
        frame.setIconImage(img.getImage());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                if (JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    try {
                        readFile();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    if(type.get(2).equals("exit"))
                        System.exit(0);
                    else
                        frame.setVisible(false);
                }
            }
        });
        frame.setLocationRelativeTo(null);

        SystemTray systemTray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().getImage("files/images/tray.png");
        PopupMenu trayPopupMenu = new PopupMenu();
        MenuItem action = new MenuItem("Open");
        action.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.setVisible(true);
            }
        });
        trayPopupMenu.add(action);
        MenuItem close = new MenuItem("EXIT");
        close.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        trayPopupMenu.add(close);
        TrayIcon trayIcon = new TrayIcon(image, "Insomnia", trayPopupMenu);
        trayIcon.setImageAutoSize(true);
        try
        {
            systemTray.add(trayIcon);
        }
        catch(AWTException awtException){
            awtException.printStackTrace();
        }

        all = new JPanel(new BorderLayout());
        frame.add(all,BorderLayout.CENTER);

        BackgroundMenuBar menuBar = new BackgroundMenuBar(new Color(240,240,240));
        JMenu application = new JMenu("Application");
        application.setMnemonic('A');
        application.setFont(new Font("SansSerif", Font.BOLD,12));
        JMenu view = new JMenu("View");
        view.setMnemonic('v');
        view.setFont(new Font("SansSerif", Font.BOLD,12));
        help = new JMenu("Help");
        help.setMnemonic('h');
        help.setFont(new Font("SansSerif", Font.BOLD,12));

        option = new JMenuItem("Option");
        option.addActionListener(handler);
        option.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O , InputEvent.ALT_MASK));
        option.setFont(new Font("SansSerif", Font.BOLD,12));

        exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E , InputEvent.ALT_MASK));
        exit.addActionListener(handler);
        exit.setFont(new Font("SansSerif", Font.BOLD,12));
        application.add(option);
        application.add(exit);

        toggleFS = new JMenuItem("Toggle FullScreen");
        toggleFS.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F , InputEvent.ALT_MASK));
        toggleFS.addActionListener(handler);
        toggleFS.setFont(new Font("SansSerif", 14,12));
        toggleSB = new JMenuItem("Toggle SideBar");
        toggleSB.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S , InputEvent.ALT_MASK));
        toggleSB.addActionListener(handler);
        toggleSB.setFont(new Font("SansSerif", 14,12));
        view.add(toggleFS);
        view.add(toggleSB);

        helpIn = new JMenuItem("Help");
        helpIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H , InputEvent.ALT_MASK));
        helpIn.setFont(new Font("SansSerif", 14,12));
        helpIn.addActionListener(handler);
        about = new JMenuItem("About");
        about.addActionListener(handler);
        about.setFont(new Font("SansSerif", 14,12));
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I , InputEvent.ALT_MASK));
        help.add(helpIn);
        help.add(about);

        menuBar.add(application);
        menuBar.add(view);
        menuBar.add(help);
        frame.add(menuBar,BorderLayout.NORTH);

        leftJPanel = new JPanel();
        leftJPanel.setLayout(new BorderLayout());

        top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setBorder(new EmptyBorder(5,5,5,5));
        top.setBackground(topC);
        top.setPreferredSize(new Dimension(top.getSize().width,top.getSize().height+45));
        leftJPanel.add(top,BorderLayout.NORTH);
        JLabel insomnia = new JLabel(" Insomnia                 ");
        insomnia.setForeground(Color.WHITE);
        insomnia.setFont(new Font("SansSerif", Font.BOLD,18));
        top.add(insomnia);

        leftJPanel.setBackground(downC);
        leftJPanel.setOpaque(true);
        menuBarLeft = new JMenuBar();
        menuBarLeft.setBackground(topC);
        menuBarLeft.setBorder(null);
        menuBarLeft.setOpaque(true);

        add = new JMenu(" + / - ");
        add.setFont(new Font("Arial",Font.BOLD,16));
        add.setBackground(topC);
        add.setBorder(null);
        add.setOpaque(true);
        newRequest = new JMenuItem((char) (534421) + "   New Request");
        newRequest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N , InputEvent.CTRL_MASK));
        newRequest.addActionListener(handler);

        deleteReq = new JMenuItem((char) (10134) + "   Delete Request  ");
        deleteReq.addActionListener(handler);

        add.add(newRequest);
        add.add(deleteReq);
        menuBarLeft.add(add);
        top.add(menuBarLeft);


        grid = new GridLayout(10,1,5,5);
        gridN = 10;
        centerLeft = new JPanel(grid);
        centerLeft.setBackground(downC);
        leftJPanel.add(centerLeft,BorderLayout.CENTER);

        both = new CenterAndRightPanel(type.get(0),"",2);

        cc = null;
        if(type.get(0).equals("dark"))
        {
             cc = new ImagePanel("files/images/firstpagedark.PNG");
        }
        if(type.get(0).equals("light"))
        {
            cc = new ImagePanel("files/images/firstpagelight.PNG");
        }
        rr = new JPanel();
        if(type.get(0).equals("dark"))
        {
            rr.setBackground(new Color(40,41,37));
        }
        if(type.get(0).equals("light"))
        {
            rr.setBackground(Color.WHITE);
        }

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,cc,rr);
        sp.setDividerLocation(350);
        sp.setDividerSize(6);
        sp.setResizeWeight(1);
        sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftJPanel, sp);
        sp2.setDividerLocation(350);
        sp2.setDividerSize(6);
        all.add(sp2, BorderLayout.CENTER);

        File dir = new File("files/saved");
        File[] directoryListing = dir.listFiles();
        if(directoryListing != null)
        {
            for(File child : directoryListing)
            {
                ReadObjectFromFile read  = new ReadObjectFromFile(child.getAbsolutePath());
                Request temp = (Request)read.readFromFile();
                newReq(child.getName(),temp);
                read.closeConnection();
            }
        }

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
            topC = new Color(105,95,184);
            downC = new Color(46,47,43);
        }
        if(theme.equals("light"))
        {
            text = Color.BLACK;
            topC = new Color(105,95,184);
            downC = Color.WHITE;
        }

        if(centerAndRights.size()==0)
        {
            if(theme.equals("dark"))
            {
                cc.changeImage("files/images/firstpagedark.PNG");
            }
            if(theme.equals("light"))
            {
                cc.changeImage("files/images/firstpagelight.PNG");
            }
            if(theme.equals("dark"))
            {
                rr.setBackground(new Color(40,41,37));
            }
            if(theme.equals("light"))
            {
                rr.setBackground(Color.WHITE);
            }

        }
        top.setBackground(topC);
        leftJPanel.setBackground(downC);
        centerLeft.setBackground(downC);
        menuBarLeft.setBackground(topC);
        add.setBackground(topC);
        for(int i=0;i<centerAndRights.size();i++)
        {
            requests.get(i).setForeground(text);
            centerAndRights.get(i).changeColors(theme);
            centerAndRights.get(i).setVisible(false);
            centerAndRights.get(i).setVisible(true);
        }
    }

    /**
     * set frame visible true
     */
    public void showGUI()
    {
        frame.setVisible(true);
    }

    private JButton save;
    private JButton cancel;
    private JButton setAsDefault;
    private JComboBox TB;
    private JComboBox FB;
    private JComboBox EB;
    private JFrame setting;

    /**
     * create the setting frame
     * @throws FileNotFoundException
     */
    public void OptionJFrame() throws FileNotFoundException
    {
        readFile();
        setting = new JFrame("Setting");
        ImageIcon img = new ImageIcon("files/images/setting.png");
        setting.setIconImage(img.getImage());
        setting.setResizable(false);
        setting.setLayout(null);
        setting.setBackground(Color.WHITE);
        setting.setLocation(500,160);
        setting.setSize(300,535);

        JPanelPaint p1 = new JPanelPaint();
        p1.setBorder(BorderFactory.createLineBorder(Color.white,5));
        p1.setLayout(null);
        p1.setSize(285,150);
        p1.setLocation(0,0);
        setting.add(p1);
        p1.setOpaque(true);
        JLabel theme = new JLabel("Theme: ",JLabel.CENTER);
        theme.setForeground(Color.WHITE);
        theme.setBackground(new Color(46,47,43));
        theme.setOpaque(true);
        theme.setSize(new Dimension(275,20));
        theme.setLocation(5,5);
        p1.add(theme);
        String[] TA = {"dark" , "light"};
        TB = new JComboBox(TA);
        TB.setSelectedItem(type.get(0));
        TB.setSize(100,35);
        TB.setLocation(95,65);
        p1.add(TB);

        JPanelPaint p2 = new JPanelPaint();
        p2.setBorder(BorderFactory.createLineBorder(Color.WHITE,5));
        p2.setLayout(null);
        p2.setSize(285,150);
        p2.setLocation(0,150);
        setting.add(p2);
        p2.setOpaque(true);
        JLabel follow = new JLabel("Follow Redirect: ",JLabel.CENTER);
        follow.setForeground(Color.WHITE);
        follow.setBackground(new Color(46,47,43));
        follow.setOpaque(true);
        follow.setSize(new Dimension(275,20));
        follow.setLocation(5,5);
        p2.add(follow);
        String[] FA = {"enable" , "disable"};
        FB = new JComboBox(FA);
        FB.setSelectedItem(type.get(1));
        FB.setSize(100,35);
        FB.setLocation(95,65);
        p2.add(FB);

        JPanelPaint p3 = new JPanelPaint();
        p3.setBorder(BorderFactory.createLineBorder(Color.WHITE,5));
        p3.setLayout(null);
        p3.setSize(285,150);
        p3.setLocation(0,300);
        setting.add(p3);
        p3.setOpaque(true);
        JLabel exit = new JLabel("Exit: ",JLabel.CENTER);
        exit.setForeground(Color.WHITE);
        exit.setBackground(new Color(46,47,43));
        exit.setOpaque(true);
        exit.setSize(new Dimension(275,20));
        exit.setLocation(5,5);
        p3.add(exit);
        String[] EA = {"exit" , "system-Tray"};
        EB = new JComboBox(EA);
        EB.setSelectedItem(type.get(2));
        EB.setSize(100,35);
        EB.setLocation(95,65);
        p3.add(EB);

        save = new JButton("Save");
        save.addActionListener(handler);
        save.addFocusListener(handler);
        cancel = new JButton("Cancel");
        cancel.addActionListener(handler);
        setAsDefault = new JButton("Set As Default");
        setAsDefault.addActionListener(handler);
        save.setSize(75,30);
        save.setLocation(205,457);
        cancel.setSize(75,30);
        cancel.setLocation(128,457);
        setAsDefault.setSize(120,30);
        setAsDefault.setLocation(5,457);
        setting.add(save);
        setting.add(cancel);
        setting.add(setAsDefault);
        setting.setVisible(true);
    }

    private class ButtonHandler implements ActionListener , FocusListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource().equals(exit))
            {
                try {
                    readFile();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                if(type.get(2).equals("exit"))
                {
                    getFrame().dispatchEvent(new WindowEvent(getFrame(), WindowEvent.WINDOW_CLOSING));
                }
                else
                {
                    frame.setVisible(false);
                }
            }
            if(e.getSource().equals(about))
            {
                JOptionPane.showMessageDialog(null,"Author: Faraz Farangizadeh\nEmail: f.farangizadeh@gmail.com\nCode: 9726060","About",JOptionPane.INFORMATION_MESSAGE);
            }
            if(e.getSource().equals(toggleFS))
            {
                if(!getIsFullScreen())
                {
                    getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
                    changeFullScreen();
                }
                else
                {
                    changeFullScreen();
                    frame.setSize(1117,650);
                    frame.setVisible(false);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            }
            if(e.getSource().equals(toggleSB))
            {
               if(getLeftVisible())
               {
                   leftJPanel.setVisible(false);
                   changeLeftVisible();
               }
               else
               {
                   leftJPanel.setVisible(true);
                   sp2.setDividerLocation(300);
                   changeLeftVisible();
               }
            }
            if(e.getSource().equals(option))
            {
                try
                {
                    OptionJFrame();
                }
                catch (FileNotFoundException ex)
                {
                    ex.printStackTrace();
                }
            }
            if(e.getSource().equals(setAsDefault))
            {
                TB.setSelectedItem("dark");
                FB.setSelectedItem("enable");
                EB.setSelectedItem("exit");
            }
            if(e.getSource().equals(cancel))
            {
                setting.setVisible(false);
            }
            if(e.getSource().equals(save))
            {
                if(info.exists())
                    info.delete();

                changeColors((String) Objects.requireNonNull(TB.getSelectedItem()));
                TB.setSelectedItem(TB.getSelectedItem());
                FB.setSelectedItem(FB.getSelectedItem());
                EB.setSelectedItem(EB.getSelectedItem());
                FileWriter writer = null;
                PrintWriter print = null;
                try
                {
                    writer = new FileWriter("files/setting/info.txt");
                    print = new PrintWriter(writer);
                    print.println(TB.getSelectedItem());
                    print.println(FB.getSelectedItem());
                    print.println(EB.getSelectedItem());
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
                finally
                {
                    try
                    {
                        readFile();
                    }
                    catch (FileNotFoundException ex)
                    {
                        ex.printStackTrace();
                    }
                    setting.setVisible(false);
                    assert print != null;
                    print.close();
                    try
                    {
                        writer.close();
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
            if(e.getSource().equals(deleteReq))
            {
                String temp = JOptionPane.showInputDialog("Enter the request's name you want to delete:");
                if(temp != null)
                {
                    Iterator<String> it = namesOfRequests.iterator();
                    boolean isThere = false;
                    int i = 0;
                    while(it.hasNext())
                    {
                        String SIT = it.next();
                        if (SIT.equals(temp))
                        {
                            if(requests.size()>=10)
                            {
                                gridN--;
                                grid.setRows(gridN);
                            }
                            isThere = true;
                            centerLeft.remove(requests.get(i));
                            centerLeft.setVisible(false);
                            centerLeft.setVisible(true);
                            it.remove();
                            requests.remove(i);
                            centerAndRights.remove(i);
                            File dir = new File("files/saved");
                            File[] directoryListing = dir.listFiles();
                            if(directoryListing != null)
                            {
                                for(File child : directoryListing)
                                {
                                    if(child.getName().equals(temp))
                                    {
                                        child.delete();
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        i++;
                    }
                    if(!isThere)
                    {
                        JOptionPane.showMessageDialog(null, "The request does nit exist", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
            if(e.getSource().equals(newRequest))
            {
                String nameR = JOptionPane.showInputDialog("Enter the Requests Name:");
                if(nameR != null)
                {
                    if(requests.size()>=10)
                    {
                        gridN++;
                        grid.setRows(gridN);
                    }
                    RequestPaint request = new RequestPaint("    " + nameR);
                    request.setForeground(text);
                    request.addMouseListener(mouse);
                    request.setBackground(new Color(46, 47, 43));
                    request.setFont(new Font("Arial", Font.BOLD, 20));
                    request.setBackground(new Color(46, 47, 43));
                    request.setOpaque(true);
                    centerLeft.add(request);
                    centerLeft.setVisible(false);
                    centerLeft.setVisible(true);
                    requests.add(request);
                    try {
                        readFile();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    centerAndRights.add(new CenterAndRightPanel(type.get(0),nameR,1));
                    namesOfRequests.add(nameR);
                }
            }
            if(e.getSource().equals(helpIn))
            {
                StringBuilder temp = new StringBuilder();
                temp.append("Create a new Request and Enter the url using http or https and select the method\n");
                temp.append("then set URL encoded form , headers and multipart forms or add file\n");
                temp.append("you can check or uncheck each form to active or deactivate them \n");
                temp.append("also by right click on request you can change the name of it\n");
                temp.append("then press send button and enjoy!!!");
                JOptionPane.showMessageDialog(null,temp.toString(),"Help",JOptionPane.INFORMATION_MESSAGE);
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

    private void newReq(String name,Request savedRequest)
    {
        if(requests.size()>=10)
        {
            gridN++;
            grid.setRows(gridN);
        }
        RequestPaint request = new RequestPaint("    " + name);
        request.setForeground(text);
        request.addMouseListener(mouse);
        request.setBackground(new Color(46, 47, 43));
        request.setFont(new Font("Arial", Font.BOLD, 20));
        request.setBackground(new Color(46, 47, 43));
        request.setOpaque(true);
        centerLeft.add(request);
        centerLeft.setVisible(false);
        centerLeft.setVisible(true);
        requests.add(request);
        try
        {
            readFile();
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        CenterAndRightPanel temp = new CenterAndRightPanel(type.get(0),name,2);
        temp.setAddressText(savedRequest.getAddress());
        savedRequest.getForms().forEach((key, value) -> temp.addForm(3,key,value));
        savedRequest.getHeaders().forEach((key, value) -> temp.addForm(2,key,value));
        savedRequest.getEncodes().forEach((key, value) -> temp.addForm(1,key,value));
        temp.addForm(3, "New name", "New value");
        temp.addForm(2, "New name", "New value");
        temp.addForm(1, "New name", "New value");
        temp.setGetOption(savedRequest.getMethod());
        centerAndRights.add(temp);
        namesOfRequests.add(name);
    }

    private class MouseHandler implements MouseMotionListener,MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            for(int i=0;i<requests.size();i++)
            {
                if(e.getSource().equals(requests.get(i)))
                {
                    if(SwingUtilities.isLeftMouseButton(e))
                    {
                        for(RequestPaint request : requests)
                        {
                            request.setBackground(new Color(46, 47, 43));
                        }
                        all.remove(sp2);
                        requests.get(i).setBackground(new Color(71, 72, 68));
                        requests.get(i).setOpaque(true);
                        both = centerAndRights.get(i);
                        sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftJPanel, both);
                        sp2.setDividerLocation(300);
                        sp2.setDividerSize(6);
                        all.add(sp2, BorderLayout.CENTER);
                        all.setVisible(false);
                        all.setVisible(true);
                    }
                    else
                    {
                        int yesNo = JOptionPane.showConfirmDialog(null,"Do you want to rename the request?","Rename",JOptionPane.YES_NO_OPTION);
                        if(yesNo==0)
                        {
                            String newName = JOptionPane.showInputDialog(null,"Enter the new name:","Rename",JOptionPane.INFORMATION_MESSAGE);
                            if(newName!=null)
                            {
                                File dir = new File("files/saved");
                                File[] directoryListing = dir.listFiles();
                                if(directoryListing != null)
                                {
                                    for(File child : directoryListing)
                                    {
                                        if(child.getName().equals(requests.get(i).getText().trim()))
                                        {
                                            requests.get(i).setText("    " + newName);
                                            try
                                            {
                                                ReadObjectFromFile read = new ReadObjectFromFile(child.getAbsolutePath());
                                                Request temp = (Request) (read.readFromFile());
                                                read.closeConnection();
                                                child.delete();
                                                WriteObjectToFile write = new WriteObjectToFile("files/saved/" + newName);
                                                write.writeToFile(temp);
                                                write.closeConnection();
                                                break;
                                            }
                                            catch (IOException | ClassNotFoundException ex)
                                            {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
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

        }
        @Override
        public void mouseExited(MouseEvent e)
        {

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