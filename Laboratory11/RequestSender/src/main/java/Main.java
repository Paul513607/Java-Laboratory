import util.RequestSender;
import util.Timer;

public class Main {
    public static Integer NUMBER_OF_REQUESTS = 1000000;

    public void testRequests() {
        Timer timer = Timer.getInstance();
        timer.start();

        for (int count = 1; count <= NUMBER_OF_REQUESTS; ++count) {
            RequestSender requestSender = new RequestSender(count);
            Thread thread = new Thread(requestSender);
            thread.start();
        }

        timer.stop();
        double duration = timer.getDuration() / 60.0;
        System.out.println("Total time taken for the requests: " + duration + "min (minutes).");
        System.out.println("Requests handled per minute: " + NUMBER_OF_REQUESTS / duration + ".");
    }
    /*
    NUMBER_OF_REQUESTS = 10^5
    Total time taken for the requests: 0.3171728383333334min (minutes).
    Requests handled per minute: 315285.50970970857.

    NUMBER_OF_REQUESTS = 10^6
    Total time taken for the requests: 2.6550148123333335min (minutes).
    Requests handled per minute: 376645.73295587755.
     */

    public static void main(String[] args) {
        Main main = new Main();
        main.testRequests();
    }
}
