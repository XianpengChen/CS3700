import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * a class to modulate the client described in the project specification;
 */
public class Client {

    //some parameters for socket, default port is 27993,
    private static int PORT = 27993;
    private static String HOST;
    private static boolean SSLEncrypted = false;
    private static String NEUId;

    //prefixes used to identify message from the server;
    private static final String helloPrefix = "cs3700fall2019 HELLO ";
    private static final String findPrefix = "cs3700fall2019 FIND ";
    private static final String countPrefix = "cs3700fall2019 COUNT ";
    private static final String byePrefix = "cs3700fall2019 BYE ";

    /**
     * use the command line arguments as specifications of the socket created to interact with the server.
     * @param args arguments from command line, like: <-p port> <-s> [hostname] [NEU ID]
     * @throws IOException may be thrown when try to establish connection between socket and server.
     */
    public static void main(String[] args) throws IOException {
        int l = args.length;
        //mapping command line arguments to HOST, PORT, SSLEncrypted and NEUId according to the length of the command
        // line arguments
        switch (l) {
            case 2:
                HOST = args[0];
                NEUId = args[1];
                break;
            case 3:
                if (args[0].equals("-s")) {
                    SSLEncrypted = true;
                    PORT = 27994;
                    HOST = args[1];
                    NEUId = args[2];
                }else {
                    System.out.println("invalid command line arguments!");
                    return;
                }
                break;
            case 4:
                if (args[0].equals("-p")) {
                    PORT = Integer.parseInt(args[1]);
                    HOST = args[2];
                    NEUId = args[3];
                }
                else {
                    System.out.println("invalid command line arguments!");
                    return;
                } break;
            case 5:
                if (args[0].equals("-p") && args[2].equals("-s")) {
                    PORT = Integer.parseInt(args[1]);
                    SSLEncrypted = true;
                    HOST = args[3];
                    NEUId = args[4];
                }
                else {
                    System.out.println("invalid command line arguments!");
                    return;
                }break;
            default:
                System.out.println("invalid commandLine arguments, please follow the protocol!");
                return;
        }
        //from https://stackoverflow.com/questions/2893819/accept-servers-self-signed-ssl-certificate-in-java-client,
        //Create a trust manager that does not validate certificate chains since the server is self-signed.
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        //create socket using specifications from command line arguments
        Socket s = null;
        boolean connected = false;
        int timeTried = 0;
        //wait for 2 seconds for every and at most 10 times trying to connect;
        while (!connected && timeTried <10) {
            try {
                //create a SSL socket if "-s" is provided in command line.
                if (SSLEncrypted) {
                    SSLContext sc = null;
                    try {
                        sc = SSLContext.getInstance("SSL");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    try {
                        sc.init(null, trustAllCerts, new java.security.SecureRandom());
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    }
                    SSLSocketFactory sslsocketfactory = sc.getSocketFactory();
                    s = sslsocketfactory.createSocket(HOST, PORT);
                }
                else {
                    s = new Socket(HOST, PORT);
                }
                connected = true;
            } catch (ConnectException e) {
                System.out.println("connect failed, waiting for 2 seconds and will try again");
                timeTried++;
                try
                {
                    Thread.sleep(2000);//2 seconds
                }
                catch(InterruptedException ie){
                    ie.printStackTrace();
                }
            }
        }
        if (!connected) {
            System.out.println("cannot establish connection after 10 times of trying, exiting");
            return;
        }


        //create a buffered reader to read from the server;
        BufferedReader fromServer;
        try {
            fromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("cannot retrieve from the server, check connection!");
            return;
        }
        //create a stream used to send message to the server;
        PrintStream toServer;
        try {
            toServer = new PrintStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("cannot print to the server, check connection!");
            s.close();
            return;
        }
        String messageToServer;
        String messageFromServer = "";
        // a boolean to represent if the NEU id is sent to the server yet.
        boolean sendNEUId = false;
        while (true) {

            if (!sendNEUId) {
                //send out HELLO message first;
                messageToServer = helloPrefix + NEUId +"\n";
                toServer.print(messageToServer);

                //read response from the server after sending out the HELLO message
                messageFromServer = fromServer.readLine();
                sendNEUId = true;
            }

            //if the message is a FIND message;
            else if (messageFromServer.startsWith(findPrefix)) {
                char singleSymbol = messageFromServer.charAt(findPrefix.length());
                String randomCharacters = messageFromServer.substring(findPrefix.length() + 2);
                int count = Count(singleSymbol, randomCharacters);

                //send out the COUNT message
                messageToServer = countPrefix + count + "\n";
                toServer.print(messageToServer);

                //read response from the server after sending out the COUNT message;
                messageFromServer = fromServer.readLine();
            }
            //if the message is a BYE message
            else if (messageFromServer.startsWith(byePrefix)) {
                String byeMessage = messageFromServer.substring(byePrefix.length());
                System.out.println(byeMessage);
                s.close();
                break;
            } else {
                //print out if the message from the server is neither a FIND message nor a BYE message;
                System.out.println("the message from server is neither a FIND message nor a BYE message, socket closing!");
                s.close();
                break;
            }
        }
    }

    /**
     * @param singleSymbol a char to represent a single symbol such as: 'A', '4', 'f' and '%'
     * @param str a string consists of random characters
     * @return an integer means the occurrence of the single symbol in the string;
     */
    private static int Count(char singleSymbol, String str) {
        int result = 0;
        int l = str.length();
        for (int i = 0; i < l; i++) {
            if (str.charAt(i) == singleSymbol) {
                result++;
            }
        }
        return result;
    }
}
