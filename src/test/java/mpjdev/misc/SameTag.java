package mpjdev.misc;
/*
 * File         : SendRecv.java
 * Author       : Sang Lim
 * Revision     : $Revision: 1.1.1.1 $
 * Updated      : $Date: 2005/03/19 16:06:39 $
 */
import mpjbuf.Buffer;
import mpjbuf.Type;
import mpjdev.natmpjdev.Comm;  // PW: changed import

public class SameTag {
  public static void main(String[] args) throws Exception {
    Comm.init(args);
    Buffer wb = new Buffer(16);
    int id = Comm.WORLD.id();
    int size = Comm.WORLD.size();
    int tag = 99;

    if (id == 0) {

      for (int z = 0; z < 100; z++) {
        int[] fArray0 = new int[2];
        for (int i = 0; i < fArray0.length; i++)
          fArray0[i] = i + z;
        // PW: own change
//        wb.putSectionHeader(Buffer.INT);
        wb.putSectionHeader(Type.INT);
        wb.write(fArray0, 0, fArray0.length);
        wb.commit();
        // PW: own change
//        Comm.WORLD.send(wb, 1, tag);
        Comm.WORLD.send(wb, 1, tag, true);
        wb.clear();
      }
      System.out.println("Written all messages");

    }
    else if (id == 1) {
      System.out.println("sleeping");
      try {
        Thread.currentThread().sleep(5000);
      }
      catch (Exception e) {}
      System.out.println("sleeping");
      for (int z = 0; z < 100; z++) {
          // PW: own change
//        Comm.WORLD.recv(wb, 0, tag);
        Comm.WORLD.recv(wb, 0, tag, true);
        wb.commit();
        int[] rArray0 = new int[2];
        for (int i = 0; i < rArray0.length; i++)
          rArray0[i] = -1;
        // PW: own change
//        int numEls = wb.getSectionHeader(Buffer.INT);
        int numEls = wb.getSectionSize();
        wb.read(rArray0, 0, rArray0.length);

        for (int i = 0; i < rArray0.length; i++) {
          if (rArray0[i] != i + z) {
            System.out.println("z " + z);
            System.out.println("Its all fucked up");
            System.exit(0);
          }
        }
        wb.clear();
        System.out.print(" P<" + z + ">");
      }

    }

    // PW: own change
//    Comm.WORLD.nbarrier();
    Comm.WORLD.barrier();
    Comm.finish();
  }
}

