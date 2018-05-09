// Java program to delete a file 
import java.io.*;
 
public class del
{
    public static void main(String[] args)
    {
        File file = new File("D:/chordData1/srikar.txt");
         
        if(file.delete())
        {
            System.out.println("File deleted successfully");
        }
        else
        {
            System.out.println("Failed to delete the file");
        }
    }
}