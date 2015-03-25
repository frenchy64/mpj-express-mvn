package mpjdev.misc;


//import timer.NativeTimer;

public class TestTimer {

  public static void main(String[] args) {

    System.out.println("#Making timer");
    // PW: own changes
//    NativeTimer microTimer = new NativeTimer();
    System.out.println("#Native timer online");

    // PW: own changes
//    double c = microTimer.getMicroseconds();
    double c = System.currentTimeMillis();
    System.out.println(c);

  }
}
