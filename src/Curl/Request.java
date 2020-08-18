package Curl;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * the request class that handles the request ad return the response
 */
public class Request implements Serializable , Runnable
{
    private String address;
    private String method;
    private boolean i;
    private boolean I;
    private boolean header;
    private boolean output;
    private boolean form;
    private boolean encode;
    private HashMap <String,String> headers;
    private HashMap <String,String> forms;
    private HashMap <String,String> encodes;
    private String outputLoc;
    private String reqAns;
    private String FileLoc = null;

    public HashMap<String, String> getForms()
    {
        return forms;
    }

    public HashMap<String, String> getHeaders()
    {
        return headers;
    }

    public HashMap<String, String> getEncodes()
    {
        return encodes;
    }

    public String getAddress()
    {
        return address;
    }

    public String getMethod()
    {
        return method;
    }

    private String guiAns = "";
    private HashMap<String, List<String>> headersGUI = new HashMap<>();
    private int speed;
    private long elapsedTime;
    private int size;
    private boolean followRedirect;

    /**
     * create url encoded fomr
     * @param body
     * @param boundary
     * @param bufferedOutputStream
     * @throws IOException
     */
    public static void bufferOutFormData(HashMap<String, String> body, String boundary, BufferedOutputStream bufferedOutputStream) throws IOException
    {
        for (String key : body.keySet())
        {
            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
            if (key.contains("file"))
            {
                bufferedOutputStream.write(("Conten t-Disposition: form-data; filename=\"" + (new File(body.get(key))).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());
                try
                {
                    BufferedInputStream tempBufferedInputStream = new BufferedInputStream(new FileInputStream(new File(body.get(key))));
                    byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                    bufferedOutputStream.write(filesBytes);
                    bufferedOutputStream.write("\r\n".getBytes());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                bufferedOutputStream.write((body.get(key) + "\r\n").getBytes());
            }
        }
        bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    /**
     * create url encoded form
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }
        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    }

    /**
     * constructor of the request class
     * @param followRedirect
     * @param FileLoc
     * @param address
     * @param method
     * @param i
     * @param I
     * @param header
     * @param output
     * @param form
     * @param encode
     * @param headers
     * @param forms
     * @param encodes
     * @param outputLoc
     */
    public Request(boolean followRedirect,String FileLoc,String address,String method,boolean i,boolean I,boolean header,boolean output,boolean form,boolean encode,HashMap<String,String> headers,HashMap<String,String> forms,HashMap<String,String> encodes,String outputLoc)
    {
        this.followRedirect=followRedirect;
        this.FileLoc=FileLoc;
        this.address=address;
        this.method=method;
        this.i=i;
        this.I=I;
        this.header=header;
        this.output=output;
        this.form=form;
        this.headers=headers;
        this.encode=encode;
        this.forms=forms;
        this.encodes=encodes;
        this.outputLoc=outputLoc;
    }

    /**
     * handles get method
     */
    public void doGET()
    {
        long start = System.currentTimeMillis();
        try
        {
            HttpURLConnection con = (HttpURLConnection) new URL(address).openConnection();
            con.setRequestMethod(method);
            con.setInstanceFollowRedirects(true);
            if(!followRedirect)
            {
                con.setInstanceFollowRedirects(false);
            }

            if(header)
            {
                headers.forEach(con::setRequestProperty);
            }
            if((speed=con.getResponseCode()) > 299)
            {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(con.getErrorStream());
                System.out.println(new String(bufferedInputStream.readAllBytes()));
                System.out.println(con.getResponseCode());
                System.out.println(con.getHeaderFields());

                BufferedInputStream bufferedInputStream1 = new BufferedInputStream(con.getErrorStream());
                byte[] o = bufferedInputStream1.readAllBytes();
                guiAns += new String(o);
                Map<String, List<String>> ansGUI = (con.getHeaderFields());
                ansGUI.forEach((key, value) -> headersGUI.put(key,value) );
                return;
            }

            print(con);
            con.disconnect();
            long finish = System.currentTimeMillis();
            elapsedTime = finish - start;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.err.println("invalid URL");
        }
    }

    /**
     * handle other methods
     * @throws IOException
     */
    public void doPOST() throws IOException
    {
        long start = System.currentTimeMillis();
        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setInstanceFollowRedirects(true);
        if(!followRedirect)
        {
            con.setInstanceFollowRedirects(false);
        }
        con.setDoInput(true);
        con.setDoOutput(true);

        if(form)
        {
            con.setDoOutput(true);
            String boundary1 = System.currentTimeMillis() + "";
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary1);
            BufferedOutputStream request = new BufferedOutputStream(con.getOutputStream());
            bufferOutFormData(forms, boundary1, request);
            request.close();
        }

        if(encode)
        {
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "utf-8");
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(getParamsString(encodes));
            out.flush();
            out.close();
        }

        if(FileLoc!=null)
        {
            con.setDoOutput(true);
            File hadi = new File(FileLoc);
            con.setRequestProperty("Content-Type", "application/octet-stream");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(con.getOutputStream());
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(hadi));
            bufferedOutputStream.write(fileInputStream.readAllBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }

        if(FileLoc==null && !form && !encode)
        {
            HttpURLConnection conTemp1 = (HttpURLConnection) url.openConnection();
            conTemp1.setRequestMethod(method);
            conTemp1.setInstanceFollowRedirects(true);
            if(!followRedirect)
            {
                conTemp1.setInstanceFollowRedirects(false);
            }
            conTemp1.setDoInput(true);
            conTemp1.setDoOutput(true);
            conTemp1.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conTemp1.setRequestProperty("charset", "utf-8");
            DataOutputStream out = new DataOutputStream(conTemp1.getOutputStream());
            out.writeBytes(getParamsString(encodes));
            out.flush();
            out.close();

            if(conTemp1.getResponseCode()>299)
            {
                String boundary = System.currentTimeMillis() + "";
                con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                BufferedOutputStream request = new BufferedOutputStream(con.getOutputStream());
                bufferOutFormData(forms, boundary, request);
                request.close();
            }
            else
            {
                con=conTemp1;
            }
        }

        if((speed= con.getResponseCode()) > 299)
        {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(con.getErrorStream());
            byte[] o = bufferedInputStream.readAllBytes();
            System.out.println(new String(o));
            System.out.println(con.getResponseCode());
            System.out.println(con.getHeaderFields());
            guiAns += new String(o);
            Map<String, List<String>> ansGUI = (con.getHeaderFields());
            ansGUI.forEach((key, value) -> headersGUI.put(key,value) );
            return;

        }


        print(con);
        con.disconnect();
        long finish = System.currentTimeMillis();
        elapsedTime = finish - start;
    }

    public void print(HttpURLConnection con) throws IOException
    {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(con.getInputStream());
        byte[] o = bufferedInputStream.readAllBytes();
        guiAns += new String(o);
        Map<String, List<String>> ansGUI = (con.getHeaderFields());
        headersGUI.clear();
        ansGUI.forEach((key, value) -> headersGUI.put(key,value) );
        if(i)
        {
            Map<String, List<String>> ans = (con.getHeaderFields());
            ans.forEach((key, value) -> reqAns += (key + " -> " + value + "\n"));
            reqAns = reqAns + '\n';
            reqAns += new String(o);
        }
        if(I)
        {
            Map<String, List<String>> ans = (con.getHeaderFields());
            ans.forEach((key, value) -> reqAns += (key + " -> " + value + "\n"));
        }
        if(!i && !I)
        {
            reqAns += new String(o);
        }
        if(output)
        {
            File temp = new File(outputLoc);
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(temp));
            out.write(o);
            out.flush();
            out.close();
        }
        size = o.length;
        bufferedInputStream.close();
    }

    @Override
    public String toString()
    {
        StringBuilder ans = new StringBuilder();
        ans.append("URL: ").append(address).append(" + ").append("Method: ").append(method).append(" + ").append("Headers: ");
        headers.forEach((key, value) -> ans.append(key + " , " + value + " "));
        ans.append("multiple Forms: ");
        forms.forEach((key, value) -> ans.append(key + " , " + value + " "));
        ans.append("URL Encoded Forms: ");
        encodes.forEach((key, value) -> ans.append(key + " , " + value + " "));
        return ans.toString();
    }

    @Override
    public void run()
    {
        if(method.equals("GET"))
        {
            doGET();
        }
        else
        {
            try
            {
                doPOST();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public String getReqAns()
    {
        return reqAns;
    }

    public String getGuiAns()
    {
        return guiAns;
    }

    public HashMap<String, List<String>> getHeadersGUI()
    {
        return headersGUI;
    }

    public int getGUISpeed()
    {
        return speed;
    }

    public long getGUIElapsedTime()
    {
        return elapsedTime;
    }

    public int getGUISize()
    {
        return size;
    }

}