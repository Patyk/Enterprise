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
import java.io.*;
import java.util.Scanner;
public class IpDomFinderClient
{
/******************* DIE ***************************************/
    public static void die(String s)
    {
        System.out.println(s);
    }//end die
/******************* !!!1 MAIN !!!! ****************************/
    public static void main (String []args)
    {
        String str = null;
        if (args.length==0)
        {
            die("usage :java IpDomFinderClient server-address");
        }//end if
        try
        {
            Socket s = new Socket(args[0], 64001);
            System.out.println("connected to IP/domain finder"
                    + s.getInetAddress());
            Scanner sc = new Scanner (s.getInputStream());
            PrintWriter pw =
                    new PrintWriter(s.getOutputStream(),true);

            Scanner sc1 = new Scanner (System.in);
            System.out.println(sc.nextLine());
            System.out.println(sc.nextLine());

            while(true)
            {
                System.out.print("#");
                System.out.flush();
                if (sc1.hasNext()) {
                    str = sc1.nextLine();
                    //closing client if key words
                    if(str.equalsIgnoreCase("bye")
                        || str.equalsIgnoreCase("end")
                        || str.equalsIgnoreCase("finish"))
                    {
                        break;
                    }//end if(bye,end,finish)
                    else
                    {
                        pw.println(str);
                        if(!str.equalsIgnoreCase("starwars"))
                        {
                            System.out.println(sc.nextLine());
                            System.out.println(sc.nextLine());
                        }//end if(starwars)
                        else
                        {
                            break;
                        }//end else(starwars)
                    }//end else (bye,end,finish)
                }//end if (hasNext())
            }//end while
            sc.close();
            pw.close();
            sc1.close();
            s.close();
        }//end try
        catch(Exception e)
        {
            System.out.println("Error: Lost connection.");
            System.exit(0);
        }//end catch
    }//end main
}//end class
