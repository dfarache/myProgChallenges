
package com.dfarache.filesystemxmlizer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemXMLizer {

    protected static XMLBuilder xml;
    protected static final String HOMEPATH = System.getProperty("user.home");
    
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
            ex.printStackTrace();
        }
    }
    
}
