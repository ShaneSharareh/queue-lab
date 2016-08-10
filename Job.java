import java.io.*;
import java.util.Scanner;
import java.util.Scanner;
/**
 * This class holds the variables, and the getters and setters of the job object variables.
 * 
 * @author Shane Sharareh 
 * @version 3.1 - March 25, 2014
 */
public class Job
{
    private int pid; 
    private int arrivalTime;
    private int cpuTimeRequired;
    private int cpuTimeRemaining;
    private int currentJob;
    private int tempReqTime;
 /**
  * This Job constructor, collects the arrival time, pid and the cpu required time of each job being passed in
  * 
  * @param newArrivalTime, the time the Job should arrived
  * @param newPid the jobs PID
  * @param newCpuTimeRequired, the jobs required time needed to finish its process
     */
public Job(int newArrivalTime, int newPid, int newCpuTimeRequired){
    arrivalTime = newArrivalTime;
    pid = newPid;
    cpuTimeRequired = newCpuTimeRequired;
    setTempClock(newCpuTimeRequired);
}

 /**
  * This method gets the arrival time job should enter cpu
  * 
  * @return the arrival time of job
     */
public int getArrivalTime(){
    return arrivalTime;
}

 /**
  * This method gets the pid of the job object
  * 
  * @return the job's pid
     */
public int getPid(){
    return pid;
}

/**
 * This method gets the required time a job needs to use the cpu
 * 
 * @return the jobs required time
 */
public int getCpuTimeRequired(){
    return cpuTimeRequired;
}

/**
 * This method decrements the jobs time while in the cpu
 */
public void decCpuTimeRequired(){
    cpuTimeRequired--;
}

 /**
  * This method sets the required time into the variable waitTime which is later used to calculate the average waiting time
  *@param tempClock, which is the cpu required time before it gets decrememnted 
  */
public void setTempClock(int tempClock){
    tempReqTime = tempClock;
    }

 /**
  * gets the required time of job before it gets decremented, which will will be used to calculate the average waiting time
  * 
  * @return the temporary required time  
  */
public int getTempTime(){
    return tempReqTime;
}
}