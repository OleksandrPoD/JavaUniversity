import java.net.*;
import java.util.Scanner;

public class UdpClient {

    public static void main(String[] args) {

        try {
            DatagramSocket clientSocket = new DatagramSocket();
            Scanner sc = new Scanner(System.in);

            System.out.print("Enter first number: ");
            int a = sc.nextInt();

            System.out.print("Enter second number: ");
            int b = sc.nextInt();

            String message = a + " " + b;

            byte[] sendBuffer = message.getBytes();

            InetAddress serverAddress = InetAddress.getByName("localhost");

            DatagramPacket sendPacket =
                    new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 9876);

            clientSocket.send(sendPacket);

            byte[] receiveBuffer = new byte[1024];

            DatagramPacket receivePacket =
                    new DatagramPacket(receiveBuffer, receiveBuffer.length);

            clientSocket.receive(receivePacket);

            String result = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength()
            );

            System.out.println("Result from server: " + result);

            clientSocket.close();

        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}