/* INSTITUTO POLITECNICO NACIONAL
  ESCUELA SUPERIOR DE COMPUTO
  Aplicaciones para Comunicaciones en Red
  Santiago Perez Carlos Augusto */

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class UDPServidor {

  public static final int PUERTO = 7;
  public static final int TAM_MAXIMO = 65507;

  public static void main(String[] args) {
    int port = PUERTO;
    try {
      DatagramChannel canal = DatagramChannel.open();
      canal.configureBlocking(false);
      DatagramSocket socket = canal.socket();
      SocketAddress dir = new InetSocketAddress(port);
      socket.bind(dir);
      Selector selector = Selector.open();
      canal.register(selector, SelectionKey.OP_READ);
      ByteBuffer buffer = ByteBuffer.allocateDirect(TAM_MAXIMO);
      while (true) {
        selector.select(5000);
        Set sk = selector.selectedKeys();
        Iterator it = sk.iterator();
        while (it.hasNext()) {
          SelectionKey key = (SelectionKey) it.next();
          it.remove();
          if (key.isReadable()) {
            buffer.clear();
            SocketAddress client = canal.receive(buffer);
            buffer.flip();
            int eco = buffer.getInt();
            if (eco == 1000) {
              canal.close();
              System.exit(0);
            } else {
              System.out.println("Dato leido: " + eco);
              buffer.flip();
              canal.send(buffer, client);
            } //else
          } //if
        } //while2
      } //while
    } catch (IOException e) {
      System.err.println(e);
    } //catch
  } //main
} //class
