package mpjdev.misc;

import mpjbuf.Buffer;
import mpjbuf.Type;
import mpjdev.MPJDev;



public class RingTest {
  public RingTest() {
  }
  public RingTest(String[] args) throws Exception {

    int num, rank, size, tag, next, from;
    Buffer wBuffer = null;
    Buffer rBuffer = null;
    int[] sendArray = new int[1];
    int[] recvArray = new int[1];
    MPJDev.init(args);
    size = MPJDev.WORLD.size();
    rank = MPJDev.WORLD.id();
    tag = 100;
    next = (rank + 1) % size;
    from = (rank + size - 1) % size;

    if (rank == 0) {
      num = 100000;
      System.out.println("Process " + rank + " sending " + num + " to " + next +
                         " with tag " + tag * (rank + 100));
      wBuffer = new Buffer(8 + 8);
      sendArray = new int[1];
      sendArray[0] = num;
      // PW: own change
//      wBuffer.putSectionHeader(Buffer.INT);
      wBuffer.putSectionHeader(Type.INT);
      wBuffer.write(sendArray, 0, 1);
      wBuffer.commit();
      // PW: own change
//      MPJDev.WORLD.send(wBuffer, next, tag * (rank + 100));
      MPJDev.WORLD.send(wBuffer, next, tag * (rank + 100), true);
      wBuffer.clear();
      System.out.println("Process " + rank + " sent " + num + " to " + next +
                         " with tag " + tag * (rank + 100));
    }

    do {
      System.out.println("Process " + rank + " receiving from " + from +
                         " with tag " + tag * (from + 100));
      rBuffer = new Buffer(8 + 8);
      // PW: own change
//      MPJDev.WORLD.recv(rBuffer, from, tag * (from + 100));
      MPJDev.WORLD.recv(rBuffer, from, tag * (from + 100), true);
      rBuffer.commit();
      // PW: own change
//      rBuffer.getSectionHeader(Buffer.INT);
      rBuffer.getSectionHeader();
      rBuffer.read(recvArray, 0, 1);
      rBuffer.clear();
      num = recvArray[0];
      System.out.println("Process " + rank + " received " + num + " from " +
                         from + " with tag " + tag * (from + 100));

      if (rank == 0) {
        --num;
        System.out.println("Process 0 decremented num from" + (num + 1) +
                           " to " + num);
      }

      System.out.println("Process " + rank + " sending " + num + " to " + next +
                         " with tag " + tag * (rank + 100));
      wBuffer = new Buffer(8 + 8);
      // PW: own change
//      wBuffer.putSectionHeader(Buffer.INT);
      wBuffer.putSectionHeader(Type.INT);
      sendArray = new int[1];
      sendArray[0] = num;
      wBuffer.write(sendArray, 0, 1);
      wBuffer.commit();
      // PW: own change
//      MPJDev.WORLD.send(wBuffer, next, tag * (rank + 100));
      MPJDev.WORLD.send(wBuffer, next, tag * (rank + 100), true);
      wBuffer.clear();
      System.out.println("Process " + rank + " sent " + num + " to " + next +
                         " with tag " + tag * (rank + 100));
    }
    while (num > 0);

    System.out.println("Process " + rank + " exiting ...");

    if (rank == 0) {
      System.out.println("Process " + rank + " receiving from " + from +
                         " with tag " + tag * (from + 100));
      rBuffer = new Buffer(8 + 8);
      // PW: own change
//      MPJDev.WORLD.recv(rBuffer, from, tag * (from + 100));
      MPJDev.WORLD.recv(rBuffer, from, tag * (from + 100), true);
      rBuffer.commit();
      // PW: own change
//      rBuffer.getSectionHeader(Buffer.INT);
      rBuffer.getSectionHeader();
      rBuffer.read(recvArray, 0, 1);
      rBuffer.clear();
      num = recvArray[0];
      System.out.println("Process " + rank + " received " + num + " from " +
                         from + " with tag " + tag * (from + 100));
    }

    //System.out.println("Sleeping");
    //try { Thread.currentThread().sleep(10000); }catch(Exception e){}
    //System.out.println("Slept");
    //MPJDev.WORLD.nbarrier();
    MPJDev.finish();
  }

  public static void main(String args[]) {
    try {
      RingTest ringTest = new RingTest(args);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
