/* INSTITUTO POLITECNICO NACIONAL
  ESCUELA SUPERIOR DE COMPUTO
  Aplicaciones para Comunicaciones en Red
  Santiago Perez Carlos Augusto */

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class UDPCliente {

  public static final int PUERTO = 7;
  private static final int LIMITE = 100;

  public static void main(String[] args) {
    boolean bandera = false;
    // se declara el socket con la ip y el puerto
    SocketAddress remoto = new InetSocketAddress("127.0.0.1", PUERTO);
    try {
      // declaracion del datagrama
      DatagramChannel canal = DatagramChannel.open();
      // se pone en modo no bloqueante
      canal.configureBlocking(false);
      // se conecta el datagrama con el socket
      canal.connect(remoto);
      // abre un selector con open
      Selector selector = Selector.open();
      // registra el canal con el selector
      canal.register(selector, SelectionKey.OP_WRITE);
      // buffer con capacidad de 4 bytes
      ByteBuffer buffer = ByteBuffer.allocateDirect(4);
      int n = 0;
      while (true) {
        //espera 5 segundos
        selector.select(5000); //espera 5 segundos por la conexión
        // regresa las llaves de selector
        Set sk = selector.selectedKeys();
        // si esta vacio se cierra el canal
        if (sk.isEmpty() && n == LIMITE || bandera) {
          canal.close();
          break;
        } else {
          // Devuelve un iterador sobre los elementos de este conjunto
          Iterator it = sk.iterator();
          while (it.hasNext()) {
            // regresa la siguiente llave con el iterador
            SelectionKey key = (SelectionKey) it.next();
            // quita el ultimo elemento del iterador
            it.remove();
            // si la llave esta lista para ser escrita
            if (key.isWritable()) {
              // se limpia el buffer
              buffer.clear();
              // se coloca el int
              buffer.putInt(n);
              // voltea o hace flip al buffer
              buffer.flip();
              // se escribe en el canal el buffer
              canal.write(buffer);
              // se envian los datos
              System.out.println("Escribiendo el dato: " + n);
              n++;
              if (n == LIMITE) {
                //todos los paquetes han sido escritos;
                buffer.clear();
                buffer.putInt(1000);
                buffer.flip();
                canal.write(buffer);
                bandera = true;
                key.interestOps(SelectionKey.OP_READ);
                break;
              } //if
            } //if
          } //while
        } //else
      } //while
    } catch (Exception e) {
      System.err.println(e);
    } //catch
  } //main
} //class
