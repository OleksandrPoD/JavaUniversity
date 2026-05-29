import java.net.*;

public class UdpServer {

    public static void main(String[] args) {

        try {
            DatagramSocket serverSocket = new DatagramSocket(9876);

            byte[] receiveBuffer = new byte[1024];

            System.out.println("Server started... waiting for data");

            while (true) {

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveBuffer, receiveBuffer.length);

                serverSocket.receive(receivePacket);

                String data = new String(
                        receivePacket.getData(),
                        0,
                        receivePacket.getLength()
                );

                // очікуємо "a b"
                String[] numbers = data.trim().split(" ");

                int a = Integer.parseInt(numbers[0]);
                int b = Integer.parseInt(numbers[1]);

                int sum = a + b;

                String response = String.valueOf(sum);

                byte[] sendBuffer = response.getBytes();

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                DatagramPacket sendPacket =
                        new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);

                serverSocket.send(sendPacket);

                System.out.println("Received: " + a + " + " + b + " = " + sum);
            }

        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}