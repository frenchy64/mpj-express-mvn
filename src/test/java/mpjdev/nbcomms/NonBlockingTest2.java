package mpjdev.nbcomms;

import java.util.Arrays;

import mpi.Status;
import mpjbuf.Buffer;
import mpjbuf.Type;
import mpjdev.MPJDev;

public class NonBlockingTest2 {

  public static void main(String args[]) throws Exception {

    MPJDev.init(args);
    int id = MPJDev.WORLD.id();
    int DATA_SIZE = 100;
    mpjdev.Request [] sreqs = new mpjdev.Request[8];
    mpjdev.Request [] rreqs = new mpjdev.Request[8];
    int intArray[] = new int[DATA_SIZE];

    for (int i = 0; i < DATA_SIZE; i++) {
      intArray[i] = i + 1;
    }

    for (int h = 1; h < 5; h++) {

      if (id == 0) {
        Buffer intBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        // PW: own change
//        intBuffer.putSectionHeader(Buffer.INT);
        intBuffer.putSectionHeader(Type.INT);
        intBuffer.write(intArray, 0, DATA_SIZE);
        intBuffer.commit();
        // PW: own change
//        sreqs[0] = MPJDev.WORLD.isend(intBuffer, 1, (1 + (h * 10)));
        sreqs[0] = MPJDev.WORLD.isend(intBuffer, 1, (1 + (h * 10)), true);
	for(int r=0 ; r<1 ; r++) {
	    sreqs[r].iwait();
	}

      }
      else if (id == 1) {

        int intReadArray[] = new int[DATA_SIZE];
        for (int i = 0; i < intReadArray.length; i++) {
          intReadArray[i] = 3;
        }
        Buffer intBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        // PW: own change
//        rreqs[0] = MPJDev.WORLD.irecv(intBuffer, 0, (1 + (h * 10)));
        rreqs[0] = MPJDev.WORLD.irecv(intBuffer, 0, (1 + (h * 10)), new Status(), true);

	for(int r=0 ; r<1 ; r++) {
	    rreqs[r].iwait();
	}

        intBuffer.commit();
    // PW: own change
//	intBuffer.getSectionHeader(Buffer.INT);
	intBuffer.getSectionHeader();
	intBuffer.read(intReadArray, 0, DATA_SIZE);

        if (Arrays.equals(intArray, intReadArray) ) {
          System.out.println(" <<<<PASSED>>>> ");
        } else {
          System.out.println(" <<<<FAILED>>>> ");
          System.exit(0);
        }

      }

    } //end tests for loop ...

    Thread.currentThread().sleep(500);
    MPJDev.finish();

  }
}
