
import java.util.Scanner;
/**
 * This class will hold the current job in the cpu as long as the quantum time allows it to run until it finishes within its required time
 * This class will also tell the MFQ class whether the cpu is busy or not.
 * 
 * @author Shane Sharareh 
 * @version 3.1 - March 25, 2014
 */
public class CPU

{
    private Job job;
    private int queueLevel;
    private int quantumClock;
    private boolean busyFlag;
    private int totalTime;
    /**
     * This is the default constructor for class CPU
     */
    public CPU()
    {
    }

    /**
     *
     * This method gets the job object being passed in to the cpu
     * 
     * @param  newJob which is the current job being used in the cpu
     */
    public void setCurrentJob(Job newJob){
        job = newJob;
    }
    
    /**
     * This boolean method checks to see if the quantum clock is done, if its zero, it will return true and preemtped the current job in cpu
     * 
     * @return true or false based on whether the quantum clock has decremented to zero
     */
    public boolean quantumIsFinished(){
        if(quantumClock == 0){
            return true;
        }
        return false;
    }
            
    /**
     * This boolean method checks if the job has finished its time in the cpu, if so it returns true and is taken off the cpu
     * 
     * @return true or false based on whether the job time has decrememnted to zero
     */
    public boolean jobIsFinished(){
        if(job.getCpuTimeRequired() == 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * This method, of type Job, returns the job object from the cpu
     * 
     * @return the current job being help in the cpu
     */
    public Job getCurrentJob()
    {
        return job;
    }
    
    /**
     * This method will set the busy flag to ethier true or false depending on whether the job has entered the cpu
     * 
     * @param condition is a value of true or false
     */
    public void setBusyFlag(boolean condition){
        busyFlag= condition;
    }
    
    /**
     * This method will tell you whether the cpu is already holding a job, if the cpu is busy it will return true
     * 
     * @return true or false depending on whether the job is in the cpu
     */
    public boolean getBusyFlag(){
        return busyFlag;
    }
    
    /**
     * This method gets the queue level that the job entered previously
     * 
     * @return the previous queue level job was in
     */
    public int getLevel(){
        return queueLevel;
    }
    
    /**
     * This method sets the queue level of a job before it gets sent to the cpu
     * 
     * @param newLevel, the queue level of job before it entered the cpu
     */
    public void setLevel(int newLevel){
        queueLevel = newLevel;
    }
    /**
     * This method decrements quantum clock and job requried time as the clock ticks
     */
    public void runCycle(){
        job.decCpuTimeRequired();// sets the method in the job class
        decQuantumClock();
    }
    
    /**
     * This method sets the quantum clock to the new time allowed for the job in the cpu
     * 
     * @param newTime is the quantum time that the cpu will be given depending on the queue the job was previously on.
     */
    public void setQuantumClock(int newTime){
        quantumClock = newTime;
    }
    
    /**
     * This method decrements the quantum time during each tick in the mfq class
     * 
     */
    public void decQuantumClock(){
        quantumClock--;
    }
    
    /**
     * This method gets the total time a job has been inside the mfq, by subtracting the system time with the jobs arrival time
     * 
     * @param systemClock, the system time when job left the cpu
     * 
     * @return the system time minus the jobs arrival time
     */
    public int getTotalTime(int systemClock){
        
            totalTime = systemClock-job.getArrivalTime();
            return totalTime;
        }
    }
        