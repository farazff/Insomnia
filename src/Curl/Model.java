package Curl;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Model
{
    private boolean fromGUI;
    /**
     * constructor of the class
     * @param args the command line
     * @param temp 1 = from command ans 2 = input from GUI
     * @throws InterruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Model(String[] args,int temp) throws InterruptedException, IOException, ClassNotFoundException
    {
        if(temp==1)
            fromGUI=false;
        else
            fromGUI=true;
        Scanner scan = new Scanner(System.in);
        run(args);
    }
    private String guiAns = "";
    private HashMap<String , List<String>> guiHead = new HashMap<>();
    private int speed;
    private long time;
    private int size;
    boolean followRedirect;

    /**
     * find different parts of a request
     * @param args the input of the request
     * @throws IOException
     * @throws InterruptedException
     * @throws ClassNotFoundException
     */
    public void run(String[] args) throws IOException, InterruptedException, ClassNotFoundException
    {
        String reqName="";
        String address;
        String method = null;
        boolean i = false;
        boolean I =false;
        boolean header = false;
        boolean output = false;
        boolean form = false;
        boolean encode = false;
        String outputLoc = null;
        HashMap <String,String> headers = new HashMap<>();
        HashMap <String,String> forms = new HashMap<>();
        HashMap <String,String> encodes = new HashMap<>();
        String FileLoc = null;

        followRedirect=false;
        if(!fromGUI)
        {
            followRedirect = true;
        }
        else
        {
            for(String arg : args)
            {
                if(arg.equals("follow"))
                {
                    followRedirect = true;
                    break;
                }
            }
        }

        if(args[1].equals("--help") || args[1].equals("-H"))
        {
            File file = new File("files/help/helpFile.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            char[] buffer = new char[4096];
            int count;
            while(reader.ready())
            {
                count = reader.read(buffer);
                System.out.println(new String(buffer,0,count));
            }
            reader.close();
            return;
        }

        if(args[1].equals("list"))
        {
            File dir = new File("files/saved");
            File[] directoryListing = dir.listFiles();
            if(directoryListing != null)
            {
                for(File child : directoryListing)
                {
                   ReadObjectFromFile read  = new ReadObjectFromFile(child.getAbsolutePath());
                   Request temp = (Request)read.readFromFile();
                    System.out.println(child.getName() + " " + temp.toString());
                }
            }
            return;
        }

        if(args[1].equals("fire"))
        {
            ArrayList <Request> requests = new ArrayList<>();
            for(int w=2;w<args.length;w++)
            {
                ReadObjectFromFile read = new ReadObjectFromFile("files/saved/" + args[w]);
                requests.add((Request)read.readFromFile());
            }
            ExecutorService executorService = Executors.newCachedThreadPool();
            for(Request request : requests)
            {
                executorService.execute(request);
            }
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
            for(Request request : requests)
            {
                System.out.println(request.getReqAns());
                System.out.print("\n\n\n\n");
            }
            return;
        }

        if(args[1].equals("-R") || args[1].equals("--remove"))
        {
            for(int w=2;w<args.length;w++)
            {
               File temp = new File("files/saved/" + args[w]);
               temp.deleteOnExit();
            }
            return;
        }

        address=args[1];

        for(int j=1;j<args.length;j++)
        {
            if(args[j].equals("-M") || args[j].equals("--method"))
            {
               method=args[j+1];
               break;
            }
        }

        if(method == null)
        {
            method = "GET";
        }

        for(int j=1;j<args.length;j++)
        {
            if(args[j].equals("-S") || args[j].equals("--save"))
            {
               reqName = args[j+1];
               break;
            }
        }

        for(int j=1;j<args.length;j++)
        {
            if(args[j].equals("-i"))
            {
                i=true;
                break;
            }
        }

        for(int j=1;j<args.length;j++)
        {
            if(args[j].equals("-I"))
            {
                I=true;
                break;
            }
        }

        for(int j=1;j<args.length;j++)
        {
            if(args[j].equals("-U") || args[j].equals("--upload"))
            {
                FileLoc = args[j+1];
            }
        }

        for(int j=1;j<args.length;j++)
        {
            if(args[j].equals("-H") || args[j].equals("--headers"))
            {
                String temp = args[j+1];
                temp = temp + ";";
                while(temp.length()>0)
                {
                    int onePart = temp.indexOf(";");
                    int keyPart = temp.indexOf(":");
                    String key = temp.substring(0, keyPart);
                    String value = temp.substring(keyPart + 1, onePart);
                    temp = temp.substring(onePart + 1);
                    header = true;
                    headers.put(key,value);
                }
                break;
            }
        }

        for(int j=1;j<args.length;j++)
        {
            if(args[j].equals("-D") || args[j].equals("--data"))
            {
                String temp = args[j+1];
                temp = temp + "&";
                while(temp.length()>0)
                {
                    int onePart = temp.indexOf("&");
                    int keyPart = temp.indexOf("=");
                    String key = temp.substring(0, keyPart);
                    String value = temp.substring(keyPart + 1, onePart);
                    temp = temp.substring(onePart + 1);
                    form = true;
                    forms.put(key,value);
                }
                break;
            }
        }

        for(int j=1;j<args.length;j++)
        {
            if(args[j].equals("-URL") || args[j].equals("--urlencoded"))
            {
                String temp = args[j+1];
                temp = temp + "&";
                while(temp.length()>0)
                {
                    int onePart = temp.indexOf("&");
                    int keyPart = temp.indexOf("=");
                    String key = temp.substring(0, keyPart);
                    String value = temp.substring(keyPart + 1, onePart);
                    temp = temp.substring(onePart + 1);
                    encode = true;
                    encodes.put(key,value);
                }
                break;
            }
        }

        for(int j=1;j<args.length;j++)
        {
            if(args[j].equals("-O") || args[j].equals("--Output"))
            {
                outputLoc = args[j+1];
                output = true;
                break;
            }
        }


        Request temp = new Request(followRedirect,FileLoc,address,method,i,I,header,output,form,encode,headers,forms,encodes,outputLoc);

        for(int j=1;j<args.length;j++)
        {
            if(args[j].equals("-S") || args[j].equals("--save"))
            {
                File file = new File("files/saved/" + reqName);
                if(file.exists())
                {
                    file.delete();
                }
                WriteObjectToFile write = new WriteObjectToFile("files/saved/" + reqName);
                write.writeToFile(temp);
                write.closeConnection();
            }
        }
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(temp);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println(temp.getReqAns());
        guiAns=temp.getGuiAns();
        guiHead=temp.getHeadersGUI();
        speed=temp.getGUISpeed();
        time=temp.getGUIElapsedTime();
        size=temp.getGUISize();
    }

    /**
     * get the answer of the request
     * @return the guiAns
     */
    public String getGuiAns()
    {
        return guiAns;
    }

    /**
     * get the headers of the request
     * @return the guiHead field
     */
    public HashMap<String, List<String>> getGuiHead()
    {
        return guiHead;
    }

    /**
     * get the number of the request
     * @return the speed field
     */
    public int getGUISpeed()
    {
        return speed;
    }

    /**
     * get the time of the request
     * @return the time field
     */
    public long getGUITime()
    {
        return time;
    }

    /**
     * get the size of the request
     * @return the size field
     */
    public int getGUISize()
    {
        return size;
    }
}



