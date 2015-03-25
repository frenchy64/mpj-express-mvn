package mpjdev.misc;

import java.util.Arrays;

import mpi.MPI;
import mpjbuf.Buffer;
import mpjbuf.Type;
import mpjdev.natmpjdev.Comm;  // PW: changed import

public class AnySrc {

  public static void main(String args[]) throws Exception {
    int DATA_SIZE = 100;
    Comm.init(args);

    for (int h = 0; h < 1000; h++) {
      System.out.println("\n\n\n************************<TEST==" + h +
                         ">**************************\n\n\n");
      // PW: own change
      System.out.println("\n\n\n************************<TEST==" + h +
                       ">**************************\n\n\n");
      int intArray[] = new int[DATA_SIZE];

      for (int i = 0; i < DATA_SIZE; i++) {
        intArray[i] = i + 1;
      }

      if (Comm.WORLD.id() == 0) {
        System.out.println("Writing intBuffer");
        Buffer intBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        // PW: own change
//        intBuffer.putSectionHeader(Buffer.INT);
        intBuffer.putSectionHeader(Type.INT);
        intBuffer.write(intArray, 0, DATA_SIZE);
        intBuffer.commit();

        for (int k = 1; k < Comm.WORLD.size(); k++) {
            // PW: own change
//          Comm.WORLD.send(intBuffer, k, h);
          Comm.WORLD.send(intBuffer, k, h, true);
        }

        System.out.println("Send Completed \n\n");

      }
      else {
        int intReadArray[] = new int[DATA_SIZE];

        for (int i = 0; i < intReadArray.length; i++) {
          intReadArray[i] = -1;
        }

        Buffer intBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        System.out.println("Receving ints ");
        // PW: own change
//        Comm.WORLD.recv(intBuffer, Comm.WORLD.ANY_SOURCE, h);
        Comm.WORLD.recv(intBuffer, MPI.ANY_SOURCE, h, true);
        intBuffer.commit();
        // PW: own change
//        intBuffer.getSectionHeader(Buffer.INT);
        intBuffer.getSectionHeader();
        System.out.println("Read Int");
        intBuffer.read(intReadArray, 0, DATA_SIZE);

        if (Arrays.equals(intArray, intReadArray)) {
          System.out.println("Passed");
        }
        else {
          System.out.println("Failed");
        }

      }

      //This should be the last call, in order to finish the communication
    } //end big for loop.

    Comm.WORLD.barrier();
    Comm.finish();
  } //end constr
} //end class
