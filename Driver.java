import java.io.*;
import java.util.Scanner;
/**
 * This class invokes the following methods to print out the data simulation of the cpu
 * 
 * @author Richard Stegman, Shane Sharareh 009059009
 * @version 3.1 - March 25, 2014
 */
public class Driver
{
    /**
     * This static method uses an object of mfq to run the methods within the mfq class that simulate whats happening as the jobs enter the cpu and leave the cpu
     * @param args a string of arguments
     */
    public static void main(String[]args) throws IOException{
        PrintWriter pw = new PrintWriter( new FileWriter("csis.txt"));
        MFQ mfq = new MFQ(pw);
        mfq.getJobs();
        mfq.outputHeader();
        mfq.runSimulation();
        mfq.outStats();
        pw.close();
    }
}
