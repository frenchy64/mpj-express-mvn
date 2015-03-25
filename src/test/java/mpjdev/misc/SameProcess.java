package mpjdev.misc;

import java.util.Vector;

import mpjbuf.Buffer;
import mpjbuf.Type;
import mpjdev.MPJDev;
import mpjdev.Request;
import mpjdev.Status;

public class SameProcess {
  public static void main(String[] args) throws Exception {
    MPJDev.init(args);
    Buffer wb = new Buffer(16);
    Buffer rb = new Buffer(16);
    int id = MPJDev.WORLD.id();
    int size = MPJDev.WORLD.size();
    int tag = 99;
    int[] send = new int[2]; send[0]=1; send[1]=2;
    int[] recv = new int[2]; recv[0]=0; recv[1]=0;
    Vector v = new Vector();
    v.add("a"); v.add("b"); v.add("c"); v.add("d");

    Object [] sendObj = new Object[2]; sendObj[0]=v; sendObj[1]=v;
    Object [] recvObj = new Object[2]; recvObj[0]=null; recvObj[1]=null;

    if(id == 0) {

      // PW: own change
//      wb.putSectionHeader(Buffer.INT);
      wb.putSectionHeader(Type.INT);
      wb.write(send, 0, send.length);
      wb.commit();
      // PW: own change
//      Request req = MPJDev.WORLD.isend(wb, 0, tag);
      Request req = MPJDev.WORLD.isend(wb, 0, tag, true);
      wb.clear();

      // PW: own change
//      MPJDev.WORLD.recv(rb, 0, tag);
      MPJDev.WORLD.recv(rb, 0, tag, true);
      rb.commit();
      // PW: own change
//      rb.getSectionHeader(Buffer.INT);
      rb.getSectionHeader();
      rb.read(recv, 0, recv.length);
      req.iwait();

      if(java.util.Arrays.equals(send, recv)) {
        System.out.println("Basic Datatype PASSED");
      }else {
        System.out.println("BasicDatatype FAILED");
      }

      rb.clear();

      // PW: own change
//      wb.putSectionHeader(Buffer.OBJECT);
      wb.putSectionHeader(Type.OBJECT);
      wb.write(sendObj, 0, sendObj.length);
      wb.commit();
      // PW: own change
//      Request req1 = MPJDev.WORLD.isend(wb, 0, tag);
      Request req1 = MPJDev.WORLD.isend(wb, 0, tag, true);
      wb.clear();

      // PW: own change
//      MPJDev.WORLD.recv(rb, 0, tag);
      MPJDev.WORLD.recv(rb, 0, tag, true);
      rb.commit();
      // PW: own change
//      rb.getSectionHeader(Buffer.OBJECT);
      rb.getSectionHeader();
      rb.read(recvObj, 0, recvObj.length);
      req1.iwait();

      if(java.util.Arrays.equals(sendObj, recvObj)) {
        System.out.println("Objects PASSED");
      }else {
        System.out.println("Objects FAILED");
      }

      rb.clear();
      wb.clear();

    // PW: own change
//      Request req2 = MPJDev.WORLD.irecv(rb, 0, tag);
      Request req2 = MPJDev.WORLD.irecv(rb, id, size, new Status(), true);

      // PW: own changes
//      wb.putSectionHeader(Buffer.OBJECT);
      wb.putSectionHeader(Type.OBJECT);
      wb.write(sendObj, 0, sendObj.length);
      wb.commit();
      // PW: own changes
//      MPJDev.WORLD.send(wb, 0, tag);
      MPJDev.WORLD.send(wb, 0, tag, true);
      wb.clear();

      req2.iwait();
      rb.commit();
      // PW:own changes
//      rb.getSectionHeader(Buffer.OBJECT);
      rb.getSectionHeader();
      rb.read(recvObj, 0, recvObj.length);

      if(java.util.Arrays.equals(sendObj, recvObj)) {
        System.out.println("(with recv posted first) Objects PASSED");
      }else {
        System.out.println("Objects FAILED");
      }

    } else if (id == 1) {

      try {
        Thread.currentThread().sleep(10000);
      }
      catch (Exception e) {}

    }
    MPJDev.finish();
  }
}

