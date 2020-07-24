import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestAsServer {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // starting server at the port 2109
        try (ServerSocket server = new ServerSocket(2109)){
            // stand to waiting for connection to socket under the name
            // "client" at the server side
            Socket client = server.accept();
            // after handshaking server associates connecting client
            // with this socket connection
            System.out.println("Connection accepted.");
            // initiating canals for socket connection, for server
            // canal of writing into socket
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("DataOutputStream created");
            // canal of reading from socket
            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println("DataInputStream created");
            // starts a dialog with connected client in cycle until socket is closed

            while (!client.isClosed()){
                System.out.println("Server reading from channel");
                // server is waiting for client's data in the inputStream
                String entry = in.readUTF();
                // after getting data it reads it
                System.out.println("READ from client message - " + entry);
                System.out.println("Server tries writing to channel");
                // check the code word "quit"
                if (entry.equalsIgnoreCase("quit")){
                    System.out.println("Client initialize connections break...");
                    out.writeUTF("Server reply - " + entry + " - OK");
                    out.flush();
                    break;
                }
                out.writeUTF("Server reply - " + entry + " - OK");
                System.out.println("Server wrote message to client");
                out.flush();
            }
            // closing the connection
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels");

            in.close();
            out.close();

            client.close();

            System.out.println("Closing connections & channels - DONE");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
