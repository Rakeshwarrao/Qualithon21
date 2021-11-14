package utilities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileHandling {
	
    static final int DELTA = 3;


    public boolean fileExists(String filePath){
        Boolean fileStatus = null;
        File f = new File(filePath);
        if(f.exists() && !f.isDirectory()) {
            fileStatus = true;
        }
        return fileStatus;
    }

    public String currDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMMdd-hhmmss");
        String date = sdf.format(calendar.getTime());
        return date;
    }

    public String createFolder(String sourcefolderPath){
        File theDir = new File(sourcefolderPath);
        String foldPath = null;

        // if the directory does not exist, create it
        if (!theDir.exists()) {
            //System.out.println("creating directory: " + theDir.getName());
            boolean result = false;

            try{
                theDir.mkdirs();
                result = true;
            }
            catch(SecurityException se){
                // TODO Auto-generated catch block
                se.printStackTrace();
            }
            if(result) {
                foldPath = theDir.getAbsolutePath();
                //System.out.println("DIR created : " + foldPath);
            }
        }
        return foldPath;
    }

}
