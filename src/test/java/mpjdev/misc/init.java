package mpjdev.misc;

import xdev.ProcessID;
import xdev.niodev.NIODevice;

public class init {

  public static void main(String args[]) throws Exception {
      // PW: own changes
//    NIODevice dev = (NIODevice) Device.newInstance();
      NIODevice dev = new NIODevice();
    long t1 = System.nanoTime();
    ProcessID[] ids = dev.init(args);
    // PW: own changes
//    ProcessID myID = dev.myID();
    ProcessID myID = dev.id();

    // PW: there is no rank attribute in ProcessID anymore
//    if (myID.rank() == 0) {
//      System.out.print("\n" + myID.rank() + ">su-time<" +
//                       (System.nanoTime() - t1) / (1000 * 1000 * 1000) + ">");
//      System.out.println("myID " + myID);
//      System.out.println("rank " + myID.rank());
//      System.out.println("uuid " + myID.uuid());
//
//      for (int i = 0; i < ids.length; i++) {
//        System.out.println("\n -----<" + i + ">------");
//        System.out.print("ids[" + i + "]=>" + ids[i] + "\t");
//        System.out.print("rank " + ids[i].rank() + "\t");
//        System.out.println("uuid " + ids[i].uuid());
//      }
//      System.out.print("init<" + myID.rank() + ">\n");
//    }
//    dev.finish();
  }
}
