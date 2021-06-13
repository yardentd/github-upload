import java.io.File;
import java.io.FileFilter;
/**
 *  Name: Yarden Toledano
 *  ID : 205576663
 */


public class Searcher implements Runnable
{
    
    private int id;
    private String extension;
    private SynchronizedQueue<File> directoryQueue;
    private SynchronizedQueue<File> resultQueue;
    private SynchronizedQueue<String> milestonesQueue;
    private boolean isMilestones;

    public Searcher(int id, String extension, SynchronizedQueue<File> directoryQueue, SynchronizedQueue<File> resultQueue, SynchronizedQueue<String> milestonesQueue, boolean isMilestones)
    {
        this.id = id;
        this.directoryQueue = directoryQueue;
        this.resultQueue = resultQueue;
        this.extension = extension;
        this.milestonesQueue = milestonesQueue;
        this.isMilestones = isMilestones;
    }
    @Override
    public void run(){

        File directory;
        File[] directoryList;
        this.resultQueue.registerProducer();
        String extension = this.extension;

        try
        {
            // while there is something in the queue
            while((directory = this.directoryQueue.dequeue()) != null){
                directoryList = directory.listFiles();
                if(directoryList == null){
                    break;
                }

                for(File file : directoryList){
                    if(file.getName().endsWith(extension)){
                        this.resultQueue.enqueue(file);

                        if(this.isMilestones){
                            this.milestonesQueue.enqueue(" Searcher on thread id " + this.id + " : file named" + file.getName() + "was found!");
                        }
                    }

                }
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.resultQueue.unregisterProducer();
    }
}
