import java.io.File;
import java.time.Duration;
import java.time.Instant;

/**
 *  Name: Yarden Toledano
 *  ID : 205576663
 *
 *  scouter thread id is - 99,
 *  searcher threads start form 0
 *  copier threads start from 100
 */


public class DiskSearcher {

    public static final int DIRECTORY_QUEUE_CAPACITY = 50;
    public static final int RESULTS_QUEUE_CAPACITY = 50;
    public static final int MILE_STONE_CAPACITY = 5000;

    public static void main(String[] args) {

        Instant start = Instant.now();

        // get all arguments
        boolean isMilestoneFlag = Boolean.parseBoolean(args[0]);
        String extension = args[1];
        File rootDirectory = new File(args[2]);
        File destinationDirectory = new File(args[3]);
        int num_searchers = Integer.parseInt(args[4]);
        int num_copiers = Integer.parseInt(args[5]);


        // print to check all the args
        System.out.println(isMilestoneFlag);
        System.out.println(extension);
        System.out.println(rootDirectory.toPath().toString());
        System.out.println(destinationDirectory.toPath().toString());
        System.out.println(num_searchers);
        System.out.println(num_copiers);
        ///
        SynchronizedQueue<File> directoryQueue = new SynchronizedQueue<>(DIRECTORY_QUEUE_CAPACITY);
        SynchronizedQueue<File> resultQueue = new SynchronizedQueue<>(RESULTS_QUEUE_CAPACITY);
        SynchronizedQueue<String> milestoneQueue = new SynchronizedQueue<>(MILE_STONE_CAPACITY);

        Thread[] search_arr = new Thread[num_searchers];
        Thread[] copier_arr = new Thread[num_copiers];

        if (!isMilestoneFlag) {
            milestoneQueue = null;
        } else {
            milestoneQueue.registerProducer();
            milestoneQueue.enqueue("General, program has started the search");
        }
        System.out.println("scouter thread id is - 99, searcher threads start form 0 , copier threads start from 100 ");


        Scouter scouter = new Scouter(99, directoryQueue, rootDirectory, milestoneQueue, isMilestoneFlag);
        Thread scouter_thread = new Thread(scouter);

        try {
            scouter_thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < num_searchers; i++) {
            Searcher s = new Searcher(i , extension, directoryQueue, resultQueue, milestoneQueue, isMilestoneFlag);
            search_arr[i] = new Thread(s);
            search_arr[i].start();
        }

        for (int i = 0; i < num_copiers; i++) {
            copier_arr[i] = new Thread(new Copier(i + 100, destinationDirectory, resultQueue, milestoneQueue, isMilestoneFlag));
            copier_arr[i].start();
        }

        // wait for threads to die
        try {
            scouter_thread.join();
            for (Thread s : search_arr) {
                s.join();
            }
            for (Thread c : copier_arr) {
                c.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String milestone = milestoneQueue.toString();
        System.out.println(milestone.replaceAll("\\!",System.getProperty("line.separator")));


        // ## test print all the element in the queue by empty the queue
////
////        milestoneQueue.unregisterProducer();
////        milestone = "";
////        while ((milestone = milestoneQueue.dequeue()) != null) {
////            System.out.println(milestone);
////        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("total time : " + timeElapsed.toMillis() + " milliseconds");
    }


}

