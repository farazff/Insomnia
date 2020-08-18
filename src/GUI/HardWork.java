package GUI;

import Curl.Model;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class HardWork extends SwingWorker<Void, String>
{
    private JEditorPane preview;
    private String clipBoard=null;
    private Path fileLoc;
    private JLabel size;
    private JTextField address;
    private JMenu get;
    private String reqName;
    private ArrayList<JTextField> namesmultiPart;
    private ArrayList<JTextField> valuesmultiPart;
    private ArrayList<JCheckBox> checkmultipart;
    private ArrayList<JTextField> namesHeader;
    private ArrayList<JTextField> valuesHeader;
    private ArrayList<JCheckBox> checkHeader;
    private ArrayList<JTextField> namesBody;
    private ArrayList<JTextField> valuesBody;
    private ArrayList<JCheckBox> checkBody;
    private JTextArea rawPanel;
    private Color text;
    private ArrayList<JLabel> labelsOfAns;
    private int gridAnsRows;
    private GridLayout gridAns;
    private JPanel headerPanel;
    private JLabel speed;
    private JLabel time;
    private ArrayList<JLabel> forChangeColor = new ArrayList<>();

    public ArrayList<JLabel> getForChangeColor() {
        return forChangeColor;
    }

    public HardWork(JEditorPane preview,Path fileLoc,JLabel size, JTextField address, JMenu get, String reqName, ArrayList<JTextField> namesmultiPart, ArrayList<JTextField> valuesmultiPart, ArrayList<JCheckBox> checkmultipart, ArrayList<JTextField> namesHeader, ArrayList<JTextField> valuesHeader, ArrayList<JCheckBox> checkHeader, ArrayList<JTextField> namesBody, ArrayList<JTextField> valuesBody, ArrayList<JCheckBox> checkBody, JTextArea rawPanel, Color text, ArrayList<JLabel> labelsOfAns, int gridAnsRows, GridLayout gridAns, JPanel headerPanel, JLabel speed, JLabel time)
    {
        this.preview=preview;
        this.fileLoc=fileLoc;
        this.size=size;
        this.address=address;
        this.get=get;
        this.reqName=reqName;
        this.namesmultiPart=namesmultiPart;
        this.valuesmultiPart=valuesmultiPart;
        this.checkmultipart=checkmultipart;
        this.namesHeader=namesHeader;
        this.valuesHeader=valuesHeader;
        this.checkHeader=checkHeader;
        this.namesBody=namesBody;
        this.valuesBody=valuesBody;
        this.checkBody=checkBody;
        this.rawPanel=rawPanel;
        this.text=text;
        this.labelsOfAns=labelsOfAns;
        this.gridAnsRows=gridAnsRows;
        this.gridAns=gridAns;
        this.headerPanel=headerPanel;
        this.speed=speed;
        this.time=time;
    }
    @Override
    protected Void doInBackground() throws Exception
    {
        ArrayList<String> args = new ArrayList<>();
        args.add("curl");


        preview.setPage(address.getText());
        args.add(address.getText());

        
        args.add("-M");
        String method  = get.getText();
        method=method.substring(0,method.indexOf(" "));
        System.out.println(method);
        args.add(method);

        args.add("-S");
        args.add(reqName);

        File info = new File("files/setting/info.txt");
        FileReader reader = new FileReader("files/setting/info.txt");
        Scanner scan = new Scanner(reader);
        ArrayList<String> type = new ArrayList<>();
        while(scan.hasNext())
        {
            type.add(scan.next());
        }
        scan.close();
        reader.close();
        System.out.println(type.get(1));
        if(type.get(1).equals("enable"))
        {
            args.add("follow");
        }

        if(fileLoc!=null)
        {
            args.add("--upload");
            args.add(fileLoc.toString());
        }

        if(namesmultiPart.size()>=2)
        {
            StringBuilder multiPart = new StringBuilder();
            boolean hasBeen = false;
            for(int w=0;w<checkmultipart.size();w++)
            {
                if(checkmultipart.get(w).isSelected())
                {
                    if(!hasBeen)
                        args.add("-D");
                    if(hasBeen)
                    {
                        multiPart.append("&");
                    }
                    multiPart.append(namesmultiPart.get(w).getText()).append("=");
                    multiPart.append(valuesmultiPart.get(w).getText());
                    hasBeen=true;
                }
            }
            args.add(multiPart.toString());
        }

        if(namesHeader.size()>=2)
        {
            StringBuilder Header = new StringBuilder();
            boolean hasBeen = false;
            for(int w=0;w<checkHeader.size();w++)
            {
                if(checkHeader.get(w).isSelected())
                {
                    if(!hasBeen)
                        args.add("-H");
                    if(hasBeen)
                    {
                        Header.append(";");
                    }
                    Header.append(namesHeader.get(w).getText()).append(":");
                    Header.append(valuesHeader.get(w).getText());
                    hasBeen=true;
                }
            }
            args.add(Header.toString());
        }

        if(namesBody.size()>=2)
        {
            StringBuilder Body = new StringBuilder();
            boolean hasBeen = false;
            for(int w=0;w<checkBody.size();w++)
            {
                if(checkBody.get(w).isSelected())
                {
                    if(!hasBeen)
                        args.add("-URL");
                    if(hasBeen)
                    {
                        Body.append("&");
                    }
                    Body.append(namesBody.get(w).getText()).append("=");
                    Body.append(valuesBody.get(w).getText());
                    hasBeen=true;
                }
            }
            args.add(Body.toString());
        }
        try
        {
            String[] args2 = new String[args.size()];
            for(int w=0;w<args.size();w++)
            {
                args2[w] = args.get(w);
            }
            Model model = new Model(args2,2);
            rawPanel.setText(model.getGuiAns());
            HashMap<String , List<String> > headers = model.getGuiHead();
            headerPanel.removeAll();
            headerPanel.revalidate();
            headerPanel.repaint();
            for(Map.Entry<String, List<String>> entry : headers.entrySet())
            {
                JLabel t1 = new JLabel(entry.getKey());
                t1.setForeground(text);
                JLabel t2 = new JLabel();
                t2.setText(String.valueOf(entry.getValue()));
                t2.setForeground(text);
                clipBoard = clipBoard + entry.getKey() + " ";
                clipBoard = clipBoard + entry.getValue() + "\n";
                gridAnsRows++;
                gridAns.setRows(gridAnsRows);
                headerPanel.add(t1);
                headerPanel.add(t2);
                forChangeColor.add(t1);
                forChangeColor.add(t2);
            }
            speed.setFont(new Font("Arial",Font.BOLD,14));
            speed.setText(Integer.toString(model.getGUISpeed()));
            if(model.getGUISpeed()>299)
            {
                speed.setBackground(Color.RED);
                speed.setOpaque(true);
            }
            if(model.getGUISpeed()<300)
            {
                speed.setBackground(Color.GREEN);
                speed.setOpaque(true);
            }
            time.setFont(new Font("Arial",Font.BOLD,14));
            time.setText(model.getGUITime() + "ms");
            float sizeT = model.getGUISize();
            size.setText(sizeT / 1000 + "kb");
        }
        catch (InterruptedException | ClassNotFoundException | IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public String getClipBoard() {
        return clipBoard;
    }
}
