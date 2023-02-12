/* INSTITUTO POLITECNICO NACIONAL
  ESCUELA SUPERIOR DE COMPUTO
  Aplicaciones para Comunicaciones en Red
  Santiago Perez Carlos Augusto */

import java.io.*;
import java.net.*;

public class CPrimD {

  public static void main(String args[]) {
    try {
      // puerto
      int pto = 2000;
      // direccion del host
      InetAddress dst = InetAddress.getByName("127.0.0.1");
      DatagramSocket cl = new DatagramSocket();
      // flujo de salida de bytes
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream dos = new DataOutputStream(baos);
      // escribir en el flujo de salida
      // se escribe el numero 4
      dos.writeInt(4);
      // se libera la salida forzando los bytes al flujo
      dos.flush();
      // manda un flotante
      dos.writeFloat(4.1f);
      // libera la salida
      dos.flush();
      // manda un long
      dos.writeLong(72);
      // libera la salida
      dos.flush();
      byte[] b = baos.toByteArray();
      // envio de paquetes
      DatagramPacket p = new DatagramPacket(b, b.length, dst, pto);
      cl.send(p);
      cl.close();
    } catch (Exception e) {
      e.printStackTrace();
    } //catch
  } //main
}
