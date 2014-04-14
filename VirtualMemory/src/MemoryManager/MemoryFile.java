package MemoryManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author Dasty
 */
public class MemoryFile 
{
    private long fileSize = 1073741825; //1 Gig
    private FileChannel fileRead;
    private FileChannel fileWrite;
    private int filePointer = 0;
    
    public MemoryFile(String filePath)
    {
        try {
            File tmp = new File(filePath);
            if(!tmp.exists())
                tmp.createNewFile();
            fileRead = new FileInputStream(filePath).getChannel();
            fileWrite = new FileOutputStream(filePath).getChannel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void writeBytes(byte[] b)
    {
        writeBytes(b,filePointer);
    }
    
    public void writeBytes(byte[] b, long position)
    {
        if(b.length + position >= fileSize)
            throw new Error("File Memory Size Exceeded."+(b.length+position)+" > "+fileSize);
        try {
            ByteBuffer buff = ByteBuffer.wrap(b);
            fileWrite.write(buff,position);
        } catch (Exception ex) {
            System.out.println(position);
            ex.printStackTrace();
        }
    }
    
    public byte[] readBytes(int length)
    {
        return readBytes(filePointer, length);
    }
    
    public byte[] readBytes(int position, int length)
    {
        ByteBuffer b = ByteBuffer.allocate(length);
        if(length + position >= fileSize)
            throw new Error("Reading Past File Size.");
        try {
            fileRead.read(b, position);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        b.flip();
        return b.array();
    }
    
    public long getMaxSize()
    {
        return fileSize;
    }
}
