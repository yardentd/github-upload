import java.io.File;
import java.io.*;
/**
 *  Name: Yarden Toledano
 *  ID : 205576663
 */

public class Copier implements Runnable{
    
    private int id;
    private File destination;
    private SynchronizedQueue<File> resultsQueue;
    private SynchronizedQueue<String> milestonesQueue;
    private boolean isMilestones;

    public Copier(int id, File destination, SynchronizedQueue<File> resultsQueue, SynchronizedQueue<String> milestonesQueue,boolean isMilestones){

        this.id = id;
        this.destination = destination;
        this.resultsQueue = resultsQueue;
        this.milestonesQueue = milestonesQueue;
        this.isMilestones = isMilestones;

    }

    @Override

    public void run(){
        File cp_file;
        try
        {
            while((cp_file = this.resultsQueue.dequeue()) != null){

                int len;
                FileInputStream in_stream = new FileInputStream(cp_file.getPath());
                FileOutputStream out_stream = new FileOutputStream(this.destination.getPath() + File.separator + cp_file.getName());
                byte[] buffer = new byte[1024];

                while((len = in_stream.read(buffer)) > 0){
                    out_stream.write(buffer, 0, len);
                }

                if(this.isMilestones){
                    this.milestonesQueue.enqueue("Copier from thread id" + this.id + " : file named" + cp_file.getName() + " was copied!");
                }

                in_stream.close();
                out_stream.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
