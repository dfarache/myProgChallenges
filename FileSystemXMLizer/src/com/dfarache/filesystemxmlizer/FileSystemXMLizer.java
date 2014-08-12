
package com.dfarache.filesystemxmlizer;

import com.dfarache.logTools.LogTools;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystemXMLizer {

    protected static XMLBuilder xml;
    protected static final String HOMEPATH = System.getProperty("user.home");
    
    private static final String className = FileSystemXMLizer.class.getName();
    private static final Logger log = new LogTools(className).getLogInstace();
    
    public static void main(String[] args) {
        xml = XMLBuilder.getInstance();       
        goThroughFilesAndBuildXML();
        xml.writeIntoXMLAndClose();
    }
    
    private static void goThroughFilesAndBuildXML(){
        try{
            Path homePath = Paths.get(HOMEPATH);
            FileSystemWalker.startWalking(homePath);
        }catch(IOException ex){
            log.log(Level.SEVERE, ex.toString());
        }
    }
    
}
