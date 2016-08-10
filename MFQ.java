import java.io.*;
import java.util.Scanner;
import java.util.Scanner;
/**
 * This class runs the simulation of jobs arriving, entering the cpu through the queues, and departing. This class also calculates the averages. 
 * @author Shane Sharareh 
 * @version 3.1 - March 25, 2014
 */
public class MFQ
    
{
    private ObjectQueue  inputQueue = new ObjectQueue(); 
    private ObjectQueue queue1 = new ObjectQueue();
    private ObjectQueue queue2 = new ObjectQueue();
    private ObjectQueue queue3 = new ObjectQueue();
    private ObjectQueue queue4 = new ObjectQueue();
    CPU newCpu = new CPU();
    int totalJobs = 0;
    int sumOfTotalTime = 0;
    PrintWriter pw;
    int systemClockAccum = 0;
    int cpuIdleTime = 0;
   private double avgResponseTime;
   private double waitTime; 
    /**
     * This MFQ constructor accepts a printwriter object that will be used when printing the data into inte csis text file
     * 
     * @param newPw, which is used for printing the data and statistics to the text file
     */
    public MFQ(PrintWriter newPw)
    {
        pw =  newPw;
    }

    /**
     * This method gets the job's arrival time, pid, and cpu time from the mfq text and assigns them to a job object, which is assigned to an inputQueue 
     */
    public void getJobs()throws IOException {
         Scanner mfq = new Scanner(new File("mfq.txt"));
         while(mfq.hasNextLine()){ 
            String readMfq = mfq.nextLine();
            String[] jobInput = readMfq.split("[ ]+");
   
            Job newJob = new Job(Integer.parseInt(jobInput[0]), Integer.parseInt(jobInput[1]), Integer.parseInt(jobInput[2]));
            inputQueue.insert(newJob);
            totalJobs++;
            
                }
                
            }
            
   /**
     * This method prints out the header of the job's Event, System Time, PID, CPU Time Needed, Total Time In System and the Lowest Level Queue using printf
     */
public void outputHeader(){
    System.out.format( "%-11s %-16s %-8s %-25s %-25s%s\n", "Event", "System Time", "PID", "CPU Time Needed", "Total Time In System", "Lowest Level Queue");
    pw.format("%-11s %-16s %-8s %-25s %-25s%s\n", "Event", "System Time", "PID", "CPU Time Needed", "Total Time In System", "Lowest Level Queue");
    pw.println();
}

/**
 * The method run simulation handels the job objects, by taking them from their inputQueue, outputing the message, sending them to appropriote queue levels, and running them in the cpu
 * until it is ethier premted or finishes within its required time.
 */
   public void runSimulation(){
    Job job;       
    int systemClock =  0;
    while(!inputQueue.isEmpty()|| !queue4.isEmpty() || newCpu.jobIsFinished() == false){
        systemClock++;
        systemClockAccum += systemClock;
        if(!inputQueue.isEmpty()){
           if(((Job)inputQueue.query()).getArrivalTime() == systemClock){
                 System.out.format( "%-14s %-13s %-11s %-28s\n","Arrival",systemClock,((Job)inputQueue.query()).getPid(),((Job)inputQueue.query()).getCpuTimeRequired());
                 pw.format( "%-14s %-13s %-11s %-28s\n","Arrival",systemClock,((Job)inputQueue.query()).getPid(),((Job)inputQueue.query()).getCpuTimeRequired());
                 pw.println();
                 queue1.insert((Job)inputQueue.remove());
                 avgResponseTime += ((Job)queue1.query()).getArrivalTime() - systemClock;
        }
    }
        if(newCpu.getBusyFlag() == false){
           cpuIdleTime++;
           if(!queue1.isEmpty()){
                job = (Job) queue1.remove();
                newCpu.setLevel(1);
                newCpu.setCurrentJob(job);
                newCpu.setBusyFlag(true);
                newCpu.setQuantumClock(2);               
                
            }
            else if(!queue2.isEmpty()){
                job = (Job) queue2.remove();
                newCpu.setLevel(2);
                newCpu.setCurrentJob(job);
                newCpu.setQuantumClock(4);
                newCpu.setBusyFlag(true);
            }
            else if(!queue3.isEmpty()){ 
                job = (Job) queue3.remove();
                newCpu.setLevel(3);
                newCpu.setCurrentJob(job);
                newCpu.setQuantumClock(8);
                newCpu.setBusyFlag(true);
            }
            else if(!queue4.isEmpty()){
                job = (Job) queue4.remove();
                newCpu.setLevel(4);
                newCpu.setCurrentJob(job);
                newCpu.setLevel(4);
                newCpu.setQuantumClock(16);
                newCpu.setBusyFlag(true);
            } 
        }
        else if(newCpu.getBusyFlag() == true)
        {
            newCpu.runCycle(); 
            if(newCpu.jobIsFinished() == true){
               int queueLevel = newCpu.getLevel();
               int totalTime = newCpu.getTotalTime(systemClock);
               job = newCpu.getCurrentJob();
               waitTime += systemClock - job.getArrivalTime() - job.getTempTime();
               departMessage(systemClock,queueLevel, totalTime, job);
               sumOfTotalTime += totalTime;
               newCpu.setBusyFlag(false);
               sendNextJob();
               
            } 
           else if(newCpu.quantumIsFinished() == true){
               job = newCpu.getCurrentJob();
               int previousLevel = newCpu.getLevel();
               if(previousLevel == 1){
                queue2.insert(job);
                newCpu.setBusyFlag(false);
             }
              else if(previousLevel == 2){   
                queue3.insert(job);
                newCpu.setBusyFlag(false);
              }
              else if(previousLevel == 3){   
                queue4.insert(job);
                newCpu.setBusyFlag(false);
              }
             else if(previousLevel ==4){
                queue4.insert(job);
                newCpu.setBusyFlag(false);
            }
            sendNextJob();
        }
           else if(!queue1.isEmpty()){
               job = newCpu.getCurrentJob();
               int previousLevel = newCpu.getLevel();
               if(previousLevel == 1){
                queue2.insert(job);
                newCpu.setBusyFlag(false);
             }
              else if(previousLevel == 2){   
                queue3.insert(job);
                newCpu.setBusyFlag(false);
               }
              else if(previousLevel == 3){   
                queue4.insert(job);
                newCpu.setBusyFlag(false);
              }
             else if(previousLevel ==4){
                queue4.insert(job);
                newCpu.setBusyFlag(false);
            }
            sendNextJob();
    }
    
}
}
}

/**
 * This method sendNextJob will be invoked in the run simulation method when a job is preempted or finished in the cpu and its another jobs turn to go on the cpu, this is done by priority.
 * By checking which queue is full in order from top to bottom, it will see whats next in line.
 */
public void sendNextJob(){
        Job job;
        if(!queue1.isEmpty()){
                job = (Job) queue1.remove();
                newCpu.setLevel(1);
                newCpu.setCurrentJob(job);
                newCpu.setBusyFlag(true);
                newCpu.setQuantumClock(2);               
                
            }
            else if(!queue2.isEmpty()){
                job = (Job) queue2.remove();
                newCpu.setLevel(2);
                newCpu.setCurrentJob(job);
                newCpu.setQuantumClock(4);
                newCpu.setBusyFlag(true);
            }
            else if(!queue3.isEmpty()){ 
                job = (Job) queue3.remove();
                newCpu.setLevel(3);
                newCpu.setCurrentJob(job);
                newCpu.setQuantumClock(8);
                newCpu.setBusyFlag(true);
            }
            else if(!queue4.isEmpty()){
                job = (Job) queue4.remove();
                newCpu.setLevel(4);
                newCpu.setCurrentJob(job);
                newCpu.setLevel(4);
                newCpu.setQuantumClock(16);
                newCpu.setBusyFlag(true);
            }
        }
        /**
         * This method prints out the job's time it left, pid, total time it was in and its lowest level queue.
         * 
         * @param systemClock, which holds the time it left
         * @param lowestLevelQueue, the last queue level the job was in
         * @param totalTime, the systemclock - arrivaltime
         * @param job, the job thats departing
         */
public void departMessage(int systemClock, int lowestLevelQueue, int totalTime, Job job ){
         System.out.format("%-14s %-13s %-11s %-28s %-28s%s\n","Departure",systemClock, job.getPid(),"", totalTime, lowestLevelQueue);  
         pw.format("%-14s %-13s %-11s %-28s %-28s%s\n","Departure",systemClock, job.getPid(),"", totalTime, lowestLevelQueue);
         pw.println();
}

/**
 * this method calculates and outputs the total number of all jobs, total time of all jobs in System, the average response time, the average turnaround time for the jobs, the average waiting time, 
 * the average throughput for the system as a whole, and the total cpu idle time
 */
public void outStats(){
    double turnAroundAvg = sumOfTotalTime/totalJobs;
    double averageResponseTime = avgResponseTime/totalJobs;
    double averageWaitTime =waitTime/totalJobs; 
    double averageThroughput =  totalJobs/(double)sumOfTotalTime; 
    System.out.println("Total number of jobs: " +totalJobs);
    System.out.println("Total time of all jobs in system: "+ sumOfTotalTime);
    System.out.printf("Average response time: %.2f\n", averageResponseTime);
    System.out.printf("Average turnaround time for jobs: %.2f\n", turnAroundAvg);
    System.out.printf("Average waiting time: %.2f\n", averageWaitTime);
    System.out.printf("Average throughput for the system: %.2f\n", averageThroughput);
    System.out.println("Total cpu idle time is: "+ cpuIdleTime);
    pw.println("Total number of jobs: " + totalJobs);
    pw.println("Total time of all jobs in system: "+ sumOfTotalTime);
    pw.printf("Average response time is: %.2f", averageResponseTime);
    pw.println();
    pw.printf("Average turnaround time for jobs: %.2f" , turnAroundAvg);
    pw.println();
    pw.printf("Average waiting time: %.2f", averageWaitTime);
    pw.println();
    pw.printf("Average throughput for the system: %.2f", averageThroughput);
    pw.println();
    pw.println("Total cpu idle time is: "+ cpuIdleTime);
}
}
           
            