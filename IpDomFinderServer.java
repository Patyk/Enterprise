// Author: Malgorzata Galazka
// Date: 7 Feb 2016
// Homework Assignment: 1
// Objective: The purspose of this program is to create
//          server-client connection
//          to check IP address based on given domain name
//          by the client or
//          to check domain name based on provided IP address.
//****************************************************************

import java.net.*;
import java.util.Scanner;
import java.util.regex.*;
import java.io.*;

public class IpDomFinderServer
{
    /***************** IP VALIDATOR ***************************/
    public static boolean ipValidator(String ip)
    {
        Pattern pattern =
        Pattern.compile("(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}"+
                            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])");
        Matcher match = pattern.matcher(ip);
        return match.find();
    }//end of ip validator

    /***************** FINDER *********************/
    public static String finder (String s)
    {
        String answer = null;
        InetAddress address = null;
        try
        {
            address = InetAddress.getByName(s);
        }
        catch (Exception e)
        {
            System.out.println("error2");
            e.printStackTrace();
        }

        // string is domain
        if (!ipValidator(s))
        {
            answer = address.getHostAddress();
            System.out.println("Server: Checking get host address");
        }
        else
        {
            answer = address.getHostName();
            System.out.println("Server: Checking get host name");
        }
        return answer;
    }// end of finder

    /*************** CLIENT HANDLER *************/
    static class ClientHandler implements Runnable
    {
        protected Socket s;
        public ClientHandler (Socket socket)
        {
            s=socket;
        }
        @Override
        public void run()
        {
            String str;
            try {
                Scanner scan = new Scanner(s.getInputStream());
                PrintWriter pw =
                    new PrintWriter(s.getOutputStream(), true);
                pw.println("Hello! To exit the program enter " +
                        "bye, end or finish." +
                        "\nEnter domain name to get IP or enter" +
                        " IP to get domain name: ");

                do {
                    str = scan.nextLine();
                    //close server if keyword
                    if(str.equalsIgnoreCase("starwars"))
                    {
                        System.out.print("exit");
                        System.exit(0);
                    }
                    else
                    {
                        try
                        {
                            pw.println("Your IP/domain is: "
                                    + finder(str));
                        }
                        catch (Exception e)
                        {
                            pw.println("Sorry, incorrect domain" +
                                " or IP. Closing client program.");
                            scan.close();
                            pw.close();
                            s.close();
                        }
                        pw.println("Enter domain name to get " +
                            "IP or enter IP to get domain name: ");
                    }
                } while (scan.hasNext());//end while
                scan.close();
                pw.close();
                s.close();
            }
            catch (Exception e)
            {
                System.out.println("Error.");
                e.printStackTrace();
            }
        }
    }
/**************!!!! MAIN !!!!* @throws IOException ***************/
    public static void main(String [] args) throws IOException
    {

        int port = 64001;
        //server connects to fixed port number
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Server connected. Waiting for client");
        for(;;) // no limit on threads created
        {
            try
            {
                Socket s = ss.accept();
                ClientHandler ch = new ClientHandler(s);
                Thread t = new Thread(ch);
                t.start();
            }
            catch (Exception e)
            {
                System.out.println("Error Occurred. " +
                        "Thread is not created");
                e.getStackTrace();
            }
        }//end for
    }//end of main
}//end class