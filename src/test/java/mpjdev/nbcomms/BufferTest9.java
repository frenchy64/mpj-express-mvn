package mpjdev.nbcomms;

import java.util.Arrays;

import mpjbuf.Buffer;
import mpjbuf.Type;
import mpjdev.MPJDev;
/**
 * this test runs with 4 processes
 */

public class BufferTest9 {

  public static void main(String args[]) throws Exception {

    int DATA_SIZE = 100;
    MPJDev.init(args);
    int h = 1;

    int intArray[] = new int[DATA_SIZE];
    float floatArray[] = new float[DATA_SIZE];
    double doubleArray[] = new double[DATA_SIZE];
    long longArray[] = new long[DATA_SIZE];
    boolean booleanArray[] = new boolean[DATA_SIZE];
    short shortArray[] = new short[DATA_SIZE];
    char charArray[] = new char[DATA_SIZE];
    byte byteArray[] = new byte[DATA_SIZE];

    for (int i = 0; i < DATA_SIZE; i++) {
      intArray[i] = i + 1;
      floatArray[i] = i + 11;
      doubleArray[i] = i + 11.11;
      longArray[i] = i + 11;
      booleanArray[i] = true;
      shortArray[i] = 1;
      charArray[i] = 's';
      byteArray[i] = 's';
    }

    for (h=0 ; h<2 ; h++) {
      System.out.println("************************<TEST==" + h +
                         ">**************************");

      if (MPJDev.WORLD.id() == 0) {

        Buffer byteBuffer = new Buffer(DATA_SIZE + 8); //No Exception ...
        // PW: own change
//        byteBuffer.putSectionHeader(Buffer.BYTE); //Exception ......
        byteBuffer.putSectionHeader(Type.BYTE); //Exception ......
        byteBuffer.write(byteArray, 0, DATA_SIZE); //Exception ......
        byteBuffer.commit(); //Exception ......

        Buffer charBuffer = new Buffer( (DATA_SIZE * 2) + 8);
        // PW: own change
//        charBuffer.putSectionHeader(Buffer.CHAR);
        charBuffer.putSectionHeader(Type.CHAR);
        charBuffer.write(charArray, 0, DATA_SIZE);
        charBuffer.commit();

        Buffer intBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        // PW: own change
//        intBuffer.putSectionHeader(Buffer.INT);
        intBuffer.putSectionHeader(Type.INT);
        intBuffer.write(intArray, 0, DATA_SIZE);
        intBuffer.commit();

        Buffer shortBuffer = new Buffer( (DATA_SIZE * 2) + 8);
        // PW: own change
//        shortBuffer.putSectionHeader(Buffer.SHORT);
        shortBuffer.putSectionHeader(Type.SHORT);
        shortBuffer.write(shortArray, 0, DATA_SIZE);
        shortBuffer.commit();

        Buffer booleanBuffer = new Buffer( (DATA_SIZE) + 8);
        // PW: own change
//        booleanBuffer.putSectionHeader(Buffer.BOOLEAN);
        booleanBuffer.putSectionHeader(Type.BOOLEAN);
        booleanBuffer.write(booleanArray, 0, DATA_SIZE);
        booleanBuffer.commit();

        Buffer longBuffer = new Buffer( (DATA_SIZE * 8) + 8);
        // PW: own change
//        longBuffer.putSectionHeader(Buffer.LONG);
        longBuffer.putSectionHeader(Type.LONG);
        longBuffer.write(longArray, 0, DATA_SIZE);
        longBuffer.commit();

        Buffer doubleBuffer = new Buffer( (DATA_SIZE * 8) + 8);
        // PW: own change
//        doubleBuffer.putSectionHeader(Buffer.DOUBLE);
        doubleBuffer.putSectionHeader(Type.DOUBLE);
        doubleBuffer.write(doubleArray, 0, DATA_SIZE);
        doubleBuffer.commit();

        Buffer floatBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        // PW: own change
//        floatBuffer.putSectionHeader(Buffer.FLOAT);
        floatBuffer.putSectionHeader(Type.FLOAT);
        floatBuffer.write(floatArray, 0, DATA_SIZE);
        floatBuffer.commit();
        //uses blocking send to

        for (int k = 0; k < 2; k++) {

          // PW: own changes
//          MPJDev.WORLD.send(intBuffer, (2 + k), (1 + (h * 10)));
          MPJDev.WORLD.send(intBuffer, (2 + k), (1 + (h * 10)), true);

//          MPJDev.WORLD.send(byteBuffer, 2 + k, (2 + (h * 10)));
          MPJDev.WORLD.send(byteBuffer, 2 + k, (2 + (h * 10)), true);

//          MPJDev.WORLD.send(charBuffer, 2 + k, (3 + (h * 10)));
          MPJDev.WORLD.send(charBuffer, 2 + k, (3 + (h * 10)), true);

//          MPJDev.WORLD.send(doubleBuffer, 2 + k, (4 + (h * 10)));
          MPJDev.WORLD.send(doubleBuffer, 2 + k, (4 + (h * 10)), true);

//          MPJDev.WORLD.send(longBuffer, 2 + k, (5 + (h * 10)));
          MPJDev.WORLD.send(longBuffer, 2 + k, (5 + (h * 10)), true);

//          MPJDev.WORLD.send(booleanBuffer, 2 + k, (6 + (h * 10)));
          MPJDev.WORLD.send(booleanBuffer, 2 + k, (6 + (h * 10)), true);

//          MPJDev.WORLD.send(shortBuffer, 2 + k, (7 + (h * 10)));
          MPJDev.WORLD.send(shortBuffer, 2 + k, (7 + (h * 10)), true);

//          MPJDev.WORLD.send(floatBuffer, 2 + k, (8 + (h * 10)));
          MPJDev.WORLD.send(floatBuffer, 2 + k, (8 + (h * 10)), true);


        }
      }
      else if (MPJDev.WORLD.id() == 2) {

        /********* THINGS TO BE READ ****************/
        int intReadArray[] = new int[DATA_SIZE];
        float floatReadArray[] = new float[DATA_SIZE];
        double doubleReadArray[] = new double[DATA_SIZE];
        long longReadArray[] = new long[DATA_SIZE];
        boolean booleanReadArray[] = new boolean[DATA_SIZE];
        short shortReadArray[] = new short[DATA_SIZE];
        char charReadArray[] = new char[DATA_SIZE];
        byte byteReadArray[] = new byte[DATA_SIZE];

        for (int i = 0; i < intReadArray.length; i++) {
          intReadArray[i] = 3;
          floatReadArray[i] = i + 19;
          doubleReadArray[i] = i + 99.11;
          longReadArray[i] = i + 9;
          booleanReadArray[i] = false;
          shortReadArray[i] = 2;
          charReadArray[i] = 'x';
          byteReadArray[i] = 'x';
        }

        Buffer intBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        Buffer floatBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        Buffer doubleBuffer = new Buffer( (DATA_SIZE * 8) + 8);
        Buffer shortBuffer = new Buffer( (DATA_SIZE * 2) + 8);
        Buffer booleanBuffer = new Buffer( (DATA_SIZE) + 8);
        Buffer charBuffer = new Buffer( (DATA_SIZE * 2) + 8);
        Buffer longBuffer = new Buffer( (DATA_SIZE * 8) + 8);
        Buffer byteBuffer = new Buffer( (DATA_SIZE) + 8);


        // PW: own changes
//        MPJDev.WORLD.recv(intBuffer, 0, (1 + (h * 10)));
        MPJDev.WORLD.recv(intBuffer, 0, (1 + (h * 10)), true);

//        MPJDev.WORLD.recv(byteBuffer, 0, (2 + (10 * h)));
        MPJDev.WORLD.recv(byteBuffer, 0, (2 + (10 * h)), true);

//        MPJDev.WORLD.recv(charBuffer, 0, (3 + (10 * h)));
        MPJDev.WORLD.recv(charBuffer, 0, (3 + (10 * h)), true);

//        MPJDev.WORLD.recv(doubleBuffer, 0, (4 + (10 * h)));
        MPJDev.WORLD.recv(doubleBuffer, 0, (4 + (10 * h)), true);

//        MPJDev.WORLD.recv(longBuffer, 0, (5 + (10 * h)));
        MPJDev.WORLD.recv(longBuffer, 0, (5 + (10 * h)), true);

//        MPJDev.WORLD.recv(booleanBuffer, 0, (6 + (10 * h)));
        MPJDev.WORLD.recv(booleanBuffer, 0, (6 + (10 * h)), true);

//        MPJDev.WORLD.recv(shortBuffer, 0, (7 + (10 * h)));
        MPJDev.WORLD.recv(shortBuffer, 0, (7 + (10 * h)), true);

//        MPJDev.WORLD.recv(floatBuffer, 0, (8 + (10 * h)));
        MPJDev.WORLD.recv(floatBuffer, 0, (8 + (10 * h)), true);

        intBuffer.commit();
        floatBuffer.commit();
        doubleBuffer.commit();
        longBuffer.commit();
        shortBuffer.commit();
        byteBuffer.commit();
        booleanBuffer.commit();
        charBuffer.commit();

        try {
          // PW: own changes
//          intBuffer.getSectionHeader(Buffer.INT);
          intBuffer.getSectionHeader();
//          floatBuffer.getSectionHeader(Buffer.FLOAT);
          floatBuffer.getSectionHeader();
//          doubleBuffer.getSectionHeader(Buffer.DOUBLE);
          doubleBuffer.getSectionHeader();
//          longBuffer.getSectionHeader(Buffer.LONG);
          longBuffer.getSectionHeader();
//          shortBuffer.getSectionHeader(Buffer.SHORT);
          shortBuffer.getSectionHeader();
//          byteBuffer.getSectionHeader(Buffer.BYTE);
          byteBuffer.getSectionHeader();
//          booleanBuffer.getSectionHeader(Buffer.BOOLEAN);
          booleanBuffer.getSectionHeader();
//          charBuffer.getSectionHeader(Buffer.CHAR);
          charBuffer.getSectionHeader();
          intBuffer.read(intReadArray, 0, DATA_SIZE);
          floatBuffer.read(floatReadArray, 0, DATA_SIZE);
          doubleBuffer.read(doubleReadArray, 0, DATA_SIZE);
          longBuffer.read(longReadArray, 0, DATA_SIZE);
          shortBuffer.read(shortReadArray, 0, DATA_SIZE);
          charBuffer.read(charReadArray, 0, DATA_SIZE);
          byteBuffer.read(byteReadArray, 0, DATA_SIZE);
          booleanBuffer.read(booleanReadArray, 0, DATA_SIZE);
        }
        catch (Exception e) {
          e.printStackTrace();
        }

        if (Arrays.equals(intArray, intReadArray) &&
            Arrays.equals(floatArray, floatReadArray) &&
            Arrays.equals(doubleArray, doubleReadArray) &&
            Arrays.equals(longArray, longReadArray) &&
            Arrays.equals(shortArray, shortReadArray) &&
            Arrays.equals(charArray, charReadArray) &&
            Arrays.equals(byteArray, byteReadArray) &&
            Arrays.equals(booleanArray, booleanReadArray)) {
          System.out.println("\n#################" +
                             "\n <<<<PASSED>>>> " +
                             "\n################");
        }
        else {
          System.out.println("\n#################" +
                             "\n <<<<FAILED>>>> " +
                             "\n################");
          System.exit(0);
        }

      }
      else if (MPJDev.WORLD.id() == 3) {

        int intReadArray[] = new int[DATA_SIZE];
        float floatReadArray[] = new float[DATA_SIZE];
        double doubleReadArray[] = new double[DATA_SIZE];
        long longReadArray[] = new long[DATA_SIZE];
        boolean booleanReadArray[] = new boolean[DATA_SIZE];
        short shortReadArray[] = new short[DATA_SIZE];
        char charReadArray[] = new char[DATA_SIZE];
        byte byteReadArray[] = new byte[DATA_SIZE];

        for (int i = 0; i < intReadArray.length; i++) {
          intReadArray[i] = 3;
          floatReadArray[i] = i + 19;
          doubleReadArray[i] = i + 99.11;
          longReadArray[i] = i + 9;
          booleanReadArray[i] = false;
          shortReadArray[i] = 2;
          charReadArray[i] = 'x';
          byteReadArray[i] = 'x';
        }

        Buffer intBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        Buffer floatBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        Buffer doubleBuffer = new Buffer( (DATA_SIZE * 8) + 8);
        Buffer shortBuffer = new Buffer( (DATA_SIZE * 2) + 8);
        Buffer booleanBuffer = new Buffer( (DATA_SIZE) + 8);
        Buffer charBuffer = new Buffer( (DATA_SIZE * 2) + 8);
        Buffer longBuffer = new Buffer( (DATA_SIZE * 8) + 8);
        Buffer byteBuffer = new Buffer( (DATA_SIZE) + 8);


        // PW: own changes
//        MPJDev.WORLD.recv(intBuffer, 0, (1 + (h * 10)));
        MPJDev.WORLD.recv(intBuffer, 0, (1 + (h * 10)), true);

//        MPJDev.WORLD.recv(byteBuffer, 0, (2 + (10 * h)));
        MPJDev.WORLD.recv(byteBuffer, 0, (2 + (10 * h)), true);

//        MPJDev.WORLD.recv(charBuffer, 0, (3 + (10 * h)));
        MPJDev.WORLD.recv(charBuffer, 0, (3 + (10 * h)), true);

//        MPJDev.WORLD.recv(doubleBuffer, 0, (4 + (10 * h)));
        MPJDev.WORLD.recv(doubleBuffer, 0, (4 + (10 * h)), true);

//        MPJDev.WORLD.recv(longBuffer, 0, (5 + (10 * h)));
        MPJDev.WORLD.recv(longBuffer, 0, (5 + (10 * h)), true);

//        MPJDev.WORLD.recv(booleanBuffer, 0, (6 + (10 * h)));
        MPJDev.WORLD.recv(booleanBuffer, 0, (6 + (10 * h)), true);

//        MPJDev.WORLD.recv(shortBuffer, 0, (7 + (10 * h)));
        MPJDev.WORLD.recv(shortBuffer, 0, (7 + (10 * h)), true);

//        MPJDev.WORLD.recv(floatBuffer, 0, (8 + (10 * h)));
        MPJDev.WORLD.recv(floatBuffer, 0, (8 + (10 * h)), true);

        intBuffer.commit();
        floatBuffer.commit();
        doubleBuffer.commit();
        longBuffer.commit();
        shortBuffer.commit();
        byteBuffer.commit();
        booleanBuffer.commit();
        charBuffer.commit();

        try {
          // PW: own changes
//          intBuffer.getSectionHeader(Buffer.INT);
          intBuffer.getSectionHeader();
//          floatBuffer.getSectionHeader(Buffer.FLOAT);
          floatBuffer.getSectionHeader();
//          doubleBuffer.getSectionHeader(Buffer.DOUBLE);
          doubleBuffer.getSectionHeader();
//          longBuffer.getSectionHeader(Buffer.LONG);
          longBuffer.getSectionHeader();
//          shortBuffer.getSectionHeader(Buffer.SHORT);
          shortBuffer.getSectionHeader();
//          byteBuffer.getSectionHeader(Buffer.BYTE);
          byteBuffer.getSectionHeader();
//          booleanBuffer.getSectionHeader(Buffer.BOOLEAN);
          booleanBuffer.getSectionHeader();
//          charBuffer.getSectionHeader(Buffer.CHAR);
          charBuffer.getSectionHeader();
          intBuffer.read(intReadArray, 0, DATA_SIZE);
          floatBuffer.read(floatReadArray, 0, DATA_SIZE);
          doubleBuffer.read(doubleReadArray, 0, DATA_SIZE);
          longBuffer.read(longReadArray, 0, DATA_SIZE);
          shortBuffer.read(shortReadArray, 0, DATA_SIZE);
          charBuffer.read(charReadArray, 0, DATA_SIZE);
          byteBuffer.read(byteReadArray, 0, DATA_SIZE);
          booleanBuffer.read(booleanReadArray, 0, DATA_SIZE);
        }
        catch (Exception e) {
          e.printStackTrace();
        }

        if (Arrays.equals(intArray, intReadArray) &&
            Arrays.equals(floatArray, floatReadArray) &&
            Arrays.equals(doubleArray, doubleReadArray) &&
            Arrays.equals(longArray, longReadArray) &&
            Arrays.equals(shortArray, shortReadArray) &&
            Arrays.equals(charArray, charReadArray) &&
            Arrays.equals(byteArray, byteReadArray) &&
            Arrays.equals(booleanArray, booleanReadArray)) {
          System.out.println("\n#################" +
                             "\n <<<<PASSED>>>> " +
                             "\n################");
        }
        else {
          System.out.println("\n#################" +
                             "\n <<<<FAILED>>>> " +
                             "\n################");
          System.exit(0);
        }
      }
      else if (MPJDev.WORLD.id() == 1) {

        Buffer byteBuffer = new Buffer(DATA_SIZE + 8); //No Exception ...
        // PW: own change
//        byteBuffer.putSectionHeader(Buffer.BYTE); //Exception ......
        byteBuffer.putSectionHeader(Type.BYTE); //Exception ......
        byteBuffer.write(byteArray, 0, DATA_SIZE); //Exception ......
        byteBuffer.commit(); //Exception ......

        Buffer charBuffer = new Buffer( (DATA_SIZE * 2) + 8);
        // PW: own change
//        charBuffer.putSectionHeader(Buffer.CHAR);
        charBuffer.putSectionHeader(Type.CHAR);
        charBuffer.write(charArray, 0, DATA_SIZE); //write the array of DATA_SIZE char
        charBuffer.commit();

        Buffer intBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        // PW: own change
//        intBuffer.putSectionHeader(Buffer.INT);
        intBuffer.putSectionHeader(Type.INT);
        intBuffer.write(intArray, 0, DATA_SIZE); //write the array of DATA_SIZE char
        intBuffer.commit();

        Buffer shortBuffer = new Buffer( (DATA_SIZE * 2) + 8);
        // PW: own change
//        shortBuffer.putSectionHeader(Buffer.SHORT);
        shortBuffer.putSectionHeader(Type.SHORT);
        shortBuffer.write(shortArray, 0, DATA_SIZE);
        shortBuffer.commit();

        Buffer booleanBuffer = new Buffer( (DATA_SIZE) + 8);
        // PW: own change
//        booleanBuffer.putSectionHeader(Buffer.BOOLEAN);
        booleanBuffer.putSectionHeader(Type.BOOLEAN);
        booleanBuffer.write(booleanArray, 0, DATA_SIZE);
        booleanBuffer.commit();

        Buffer longBuffer = new Buffer( (DATA_SIZE * 8) + 8);
        // PW: own change
//        longBuffer.putSectionHeader(Buffer.LONG);
        longBuffer.putSectionHeader(Type.LONG);
        longBuffer.write(longArray, 0, DATA_SIZE);
        longBuffer.commit();

        Buffer doubleBuffer = new Buffer( (DATA_SIZE * 8) + 8);
        // PW: own change
//        doubleBuffer.putSectionHeader(Buffer.DOUBLE);
        doubleBuffer.putSectionHeader(Type.DOUBLE);
        doubleBuffer.write(doubleArray, 0, DATA_SIZE);
        doubleBuffer.commit();

        Buffer floatBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        // PW: own change
//        floatBuffer.putSectionHeader(Buffer.FLOAT);
        floatBuffer.putSectionHeader(Type.FLOAT);
        floatBuffer.write(floatArray, 0, DATA_SIZE);
        floatBuffer.commit();
        //uses blocking send to


        // PW: own changes
//        MPJDev.WORLD.send(intBuffer, 1, (1 + (h * 10)));
        MPJDev.WORLD.send(intBuffer, 1, (1 + (h * 10)), true);

//        MPJDev.WORLD.send(byteBuffer, 1, (2 + (h * 10)));
        MPJDev.WORLD.send(byteBuffer, 1, (2 + (h * 10)), true);

//        MPJDev.WORLD.send(charBuffer, 1, (3 + (h * 10)));
        MPJDev.WORLD.send(charBuffer, 1, (3 + (h * 10)), true);

//        MPJDev.WORLD.send(doubleBuffer, 1, (4 + (h * 10)));
        MPJDev.WORLD.send(doubleBuffer, 1, (4 + (h * 10)), true);

//        MPJDev.WORLD.send(longBuffer, 1, (5 + (h * 10)));
        MPJDev.WORLD.send(longBuffer, 1, (5 + (h * 10)), true);

//        MPJDev.WORLD.send(booleanBuffer, 1, (6 + (h * 10)));
        MPJDev.WORLD.send(booleanBuffer, 1, (6 + (h * 10)), true);

//        MPJDev.WORLD.send(shortBuffer, 1, (7 + (h * 10)));
        MPJDev.WORLD.send(shortBuffer, 1, (7 + (h * 10)), true);

//        MPJDev.WORLD.send(floatBuffer, 1, (8 + (h * 10)));
        MPJDev.WORLD.send(floatBuffer, 1, (8 + (h * 10)), true);
        System.out.println("Send Completed \n\n");

      }
      else if (MPJDev.WORLD.id() == 4) {

        int intReadArray[] = new int[DATA_SIZE];
        float floatReadArray[] = new float[DATA_SIZE];
        double doubleReadArray[] = new double[DATA_SIZE];
        long longReadArray[] = new long[DATA_SIZE];
        boolean booleanReadArray[] = new boolean[DATA_SIZE];
        short shortReadArray[] = new short[DATA_SIZE];
        char charReadArray[] = new char[DATA_SIZE];
        byte byteReadArray[] = new byte[DATA_SIZE];

        for (int i = 0; i < intReadArray.length; i++) {
          intReadArray[i] = 3;
          floatReadArray[i] = i + 19;
          doubleReadArray[i] = i + 99.11;
          longReadArray[i] = i + 9;
          booleanReadArray[i] = false;
          shortReadArray[i] = 2;
          charReadArray[i] = 'x';
          byteReadArray[i] = 'x';
        }

        Buffer intBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        Buffer floatBuffer = new Buffer( (DATA_SIZE * 4) + 8);
        Buffer doubleBuffer = new Buffer( (DATA_SIZE * 8) + 8);
        Buffer shortBuffer = new Buffer( (DATA_SIZE * 2) + 8);
        Buffer booleanBuffer = new Buffer( (DATA_SIZE) + 8);
        Buffer charBuffer = new Buffer( (DATA_SIZE * 2) + 8);
        Buffer longBuffer = new Buffer( (DATA_SIZE * 8) + 8);
        Buffer byteBuffer = new Buffer( (DATA_SIZE) + 8);


        // PW: own changes
//        MPJDev.WORLD.recv(intBuffer, 0, (1 + (h * 10)));
        MPJDev.WORLD.recv(intBuffer, 0, (1 + (h * 10)), true);

//        MPJDev.WORLD.recv(byteBuffer, 0, (2 + (10 * h)));
        MPJDev.WORLD.recv(byteBuffer, 0, (2 + (10 * h)), true);

//        MPJDev.WORLD.recv(charBuffer, 0, (3 + (10 * h)));
        MPJDev.WORLD.recv(charBuffer, 0, (3 + (10 * h)), true);

//        MPJDev.WORLD.recv(doubleBuffer, 0, (4 + (10 * h)));
        MPJDev.WORLD.recv(doubleBuffer, 0, (4 + (10 * h)), true);

//        MPJDev.WORLD.recv(longBuffer, 0, (5 + (10 * h)));
        MPJDev.WORLD.recv(longBuffer, 0, (5 + (10 * h)), true);

//        MPJDev.WORLD.recv(booleanBuffer, 0, (6 + (10 * h)));
        MPJDev.WORLD.recv(booleanBuffer, 0, (6 + (10 * h)), true);

//        MPJDev.WORLD.recv(shortBuffer, 0, (7 + (10 * h)));
        MPJDev.WORLD.recv(shortBuffer, 0, (7 + (10 * h)), true);

//        MPJDev.WORLD.recv(floatBuffer, 0, (8 + (10 * h)));
        MPJDev.WORLD.recv(floatBuffer, 0, (8 + (10 * h)), true);

        intBuffer.commit();
        floatBuffer.commit();
        doubleBuffer.commit();
        longBuffer.commit();
        shortBuffer.commit();
        byteBuffer.commit();
        booleanBuffer.commit();
        charBuffer.commit();

        try {
          // PW: own changes
//          intBuffer.getSectionHeader(Buffer.INT);
          intBuffer.getSectionHeader();
//          floatBuffer.getSectionHeader(Buffer.FLOAT);
          floatBuffer.getSectionHeader();
//          doubleBuffer.getSectionHeader(Buffer.DOUBLE);
          doubleBuffer.getSectionHeader();
//          longBuffer.getSectionHeader(Buffer.LONG);
          longBuffer.getSectionHeader();
//          shortBuffer.getSectionHeader(Buffer.SHORT);
          shortBuffer.getSectionHeader();
//          byteBuffer.getSectionHeader(Buffer.BYTE);
          byteBuffer.getSectionHeader();
//          booleanBuffer.getSectionHeader(Buffer.BOOLEAN);
          booleanBuffer.getSectionHeader();
//          charBuffer.getSectionHeader(Buffer.CHAR);
          charBuffer.getSectionHeader();
          intBuffer.read(intReadArray, 0, DATA_SIZE);
          floatBuffer.read(floatReadArray, 0, DATA_SIZE);
          doubleBuffer.read(doubleReadArray, 0, DATA_SIZE);
          longBuffer.read(longReadArray, 0, DATA_SIZE);
          shortBuffer.read(shortReadArray, 0, DATA_SIZE);
          charBuffer.read(charReadArray, 0, DATA_SIZE);
          byteBuffer.read(byteReadArray, 0, DATA_SIZE);
          booleanBuffer.read(booleanReadArray, 0, DATA_SIZE);
        }
        catch (Exception e) {
          e.printStackTrace();
        }

        if (Arrays.equals(intArray, intReadArray) &&
            Arrays.equals(floatArray, floatReadArray) &&
            Arrays.equals(doubleArray, doubleReadArray) &&
            Arrays.equals(longArray, longReadArray) &&
            Arrays.equals(shortArray, shortReadArray) &&
            Arrays.equals(charArray, charReadArray) &&
            Arrays.equals(byteArray, byteReadArray) &&
            Arrays.equals(booleanArray, booleanReadArray)) {
          System.out.println("\n#################" +
                             "\n <<<<PASSED>>>> " +
                             "\n################");
        }
        else {
          System.out.println("\n#################" +
                             "\n <<<<FAILED>>>> " +
                             "\n################");
          System.exit(0);
        }
      } //if id==4
    }//end for

    System.out.println("process <"+MPJDev.WORLD.id()+"> finished");
    try { Thread.currentThread().sleep(10000); }catch(Exception e){}
    MPJDev.finish();

  }
}
