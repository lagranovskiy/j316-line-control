package gem.org.j316textcontrol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import org.apache.commons.lang3.ArrayUtils;

public class RowCommunicator {

    private final static byte STX = (byte) 0x00;
    private final static byte FTX = (byte) 0xff;

    private final static byte[] INITPACKET = new byte[]{
            STX, FTX, FTX, (byte) 0x01, // Searial Address Paket,
            0x0b, 0x01, FTX// xpanded serial address packet
            //   0x10, 0x50 // Größe und Breite
    };


    public String clear(String ip, int port) {

        return sendTxt(ip, port, "");
    }

    public String sendTxt(String ip, int port, String msg) {

        byte[] TEXTFILE = new byte[]{
                0x01, // Start of TXT
                0x30, 0x31, // Filename
                0x02, (byte) 0xef, (byte) 0xb0, (byte) 0xef, (byte) 0xa2 // Styles
        };

        byte[] sendMSG = ArrayUtils.addAll(INITPACKET, TEXTFILE);
        sendMSG = ArrayUtils.addAll(sendMSG, msg.getBytes(Charset.forName("US-ASCII")));
        sendMSG = ArrayUtils.addAll(sendMSG, FTX, FTX);
        String retVal = send(ip, port, sendMSG);

        if (msg.length() > 0)
            retVal = retVal + ("\n Text sent: " + msg);

        return retVal;
    }


    public String send(String ip, int port, byte[] frame) {
        StringBuilder retVal = new StringBuilder();
        Socket rowSocket = null;
        DataOutputStream os = null;
        DataInputStream is = null;
        try {
            rowSocket = new Socket();
            rowSocket.connect(new InetSocketAddress(ip, port), 1000 * 5);

            os = new DataOutputStream(rowSocket.getOutputStream());
            is = new DataInputStream(rowSocket.getInputStream());

            retVal.append("\n Connection ok.");
        } catch (SocketTimeoutException e) {
            System.err.println("Timeout occured " + ip);
            retVal.append("\n Zeitüberschreitung.. sorry");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + ip);
            retVal.append("\n Don't know about host: " + ip);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + ip);
            retVal.append("\n Couldn't get I/O for the connection to: " + ip);
            e.printStackTrace();
        }

        if (rowSocket != null && os != null && is != null) {
            try {

                os.write(frame);
                os.write(STX);

                os.flush();

                os.close();
                is.close();
                rowSocket.close();
                retVal.append("\n Fertig.");
            } catch (UnknownHostException e) {
                System.err.println("Trying to connect to unknown host: " + ip);
                retVal.append("\n Trying to connect to unknown host.");
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
                retVal.append("\n IOException");
            }
        }

        return retVal.toString();

    }

}
