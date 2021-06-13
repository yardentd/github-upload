import java.io.File;
/**
 *  Name: Yarden Toledano
 *  ID : 205576663
 */

public class Scouter implements Runnable {

    private int id;
    private SynchronizedQueue<File> directoryQueue;
    private File root;
    private SynchronizedQueue<String> milestonesQueue;
    boolean isMilestones;

    public Scouter(int id, SynchronizedQueue<File> directoryQueue, File root, SynchronizedQueue<String> milestonesQueue, boolean isMilestones) {

        this.id = id;
        this.directoryQueue = directoryQueue;
        this.root = root;
        this.milestonesQueue = milestonesQueue;
        this.isMilestones = isMilestones;

        if (this.root.isDirectory()) {
            this.directoryQueue.enqueue(root);
        }
    }


    @Override
    public void run() {
        this.directoryQueue.registerProducer();

        if (root == null) {
            this.directoryQueue.unregisterProducer();
            return;
        }

        File[] list_dir = root.listFiles();
        for (File file : list_dir) {
            if (file != null) {
                if (file.isDirectory()) {
                    this.directoryQueue.enqueue(file);
                    if (isMilestones) {
                        this.milestonesQueue.registerProducer();
                        this.milestonesQueue.enqueue("Scouter on thread id" + this.id + " : directory named " + file.getName() + " was scouted!");
                        this.milestonesQueue.unregisterProducer();
                    }
                    this.root = file;
                    run();
                }

            }

        }
        this.directoryQueue.unregisterProducer();
    }

}
