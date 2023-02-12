/* INSTITUTO POLITECNICO NACIONAL
  ESCUELA SUPERIOR DE COMPUTO
  Aplicaciones para Comunicaciones en Red
  Santiago Perez Carlos Augusto */

import java.io.*;
import java.net.*;

public class SPrimD {

  public static void main(String args[]) {
    try {
      // puerto en el que se va a conectar el cliente
      DatagramSocket s = new DatagramSocket(2000);
      System.out.println("Servidor iniciado, esperando cliente");
      // ciclo para recibir cualquier cliente
      for (;;) {
        // datagrama para recibir los valores
        DatagramPacket p = new DatagramPacket(new byte[2000], 2000);
        s.receive(p);
        // imprime desde donde se recibio el datagrama
        System.out.println(
          "Datagrama recibido desde" + p.getAddress() + ":" + p.getPort()
        );
        // flujo de entrada
        DataInputStream dis = new DataInputStream(
          new ByteArrayInputStream(p.getData())
        );
        // recibe los datos enviados por el cliente
        int x = dis.readInt();
        float f = dis.readFloat();
        long z = dis.readLong();
        System.out.println(
          "\n\nEntero:" + x + " Flotante:" + f + " Entero largo:" + z
        );
      } //for
      //s.close()
    } catch (Exception e) {
      e.printStackTrace();
    } //catch
  } //main
}
