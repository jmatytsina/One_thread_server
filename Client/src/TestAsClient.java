import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestAsClient {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // launch the socket connection and initializing the message' accept
        try (Socket socket = new Socket("localhost", 2109);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             DataInputStream ois = new DataInputStream(socket.getInputStream());
            )
        {
            System.out.println("Client connected to socket");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized");

            while (!socket.isOutputShutdown()){
                String clientCommand = br.readLine();
                if (!clientCommand.equals("")) {
                    System.out.println("Client starts writing into channel...");

                    oos.writeUTF(clientCommand);
                    oos.flush();
                    System.out.println("Client sent message " + clientCommand + " to server");
                    if (clientCommand.equalsIgnoreCase("quit")) {
                        System.out.println("Client kill connections");

                        System.out.println("reading...");
                        String in = ois.readUTF();
                        System.out.println(in);
                        break;
                    }
                    System.out.println("Client sent message & starts waiting for data from server...");

                    System.out.println("reading...");
                    String in = ois.readUTF();
                    System.out.println(in);

                }
            }
            System.out.println("Closing connections & channels on client side - DONE");

        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
