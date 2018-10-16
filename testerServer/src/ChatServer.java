import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class CD 
{

    private static final int Port = 9001;

    private static HashSet<String> Names = new HashSet<String>();

    private static HashSet<PrintWriter> Writers = new HashSet<PrintWriter>();

    public static void main(String[] args) throws Exception 
    {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(Port);
        try 
        {
            while (true) 
            {
                new Handler(listener.accept()).start();
            }
        } finally 
        {
            listener.close();
        }
    }

    private static class Handler extends Thread 
    {
        private String Name = "";
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket) 
        {
            this.socket = socket;
        }

        public void run() 
        {
            try {

                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while (true) 
                {
                    out.println("SUBMITNAME");
                    Name = in.readLine();
                    if (Name.equals("null")) 
                    {
                    	//do nothing
                    }
                    else {
                        synchronized (Names) {
                            if (!Names.contains(Name)) 
                            {
                                Names.add(Name);
                                break;
                            }
                        }
                    }
                }

                out.println("NAMEACCEPTED");
                Writers.add(out);

                while (true) {
                    String input = in.readLine();
                    if (input == null) 
                    {
                        return;
                    }
                    for (PrintWriter writer : Writers) 
                    {
                        writer.println("MESSAGE " + Name + ": " + input);
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (Name != null) 
                {
                    Names.remove(Name);
                }
                if (out != null) 
                {
                    Writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) 
                {
                }
            }
        }
    }
}