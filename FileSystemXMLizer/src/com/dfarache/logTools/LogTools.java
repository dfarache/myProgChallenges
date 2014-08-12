
package com.dfarache.logTools;

import java.io.IOException;
import java.util.logging.*;

public class LogTools{
    private final Logger log;  
    private final FileHandler fh;
    
    public LogTools(String className){
        log = Logger.getLogger(className);
        fh = initialiseFileHandler(className);
        addHandlerToLogger();
    }
    
    private void addHandlerToLogger(){
        log.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);
    }
    
    private FileHandler initialiseFileHandler(String logFile){
        try{
            return new FileHandler("./" + logFile + ".log");
        }catch(IOException ex){
            System.err.println("ex");
        }
        throw new RuntimeException();
    }
    
    public Logger getLogInstace(){
        return this.log;
    }
}
