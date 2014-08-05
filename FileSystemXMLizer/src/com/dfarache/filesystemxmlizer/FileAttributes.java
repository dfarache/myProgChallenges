
package com.dfarache.filesystemxmlizer;


public class FileAttributes {
    private final String fileName;
    private final String typeOfFile;
    private final long size;
    private final String lastModifiedTime;
    
    public FileAttributes(String fileName,String typeOfFile, long size, String lastModifiedTime){
        this.fileName = fileName;
        this.typeOfFile = typeOfFile;
        this.size = size;
        this.lastModifiedTime = lastModifiedTime;
    }
    
    @Override
    public String toString(){
        return "File name:  " + fileName + "\n" +
               "Type of File:  " + typeOfFile + "\n" +
               "Size of FIle:  " + size + "\n" +
                "last modified:  " + lastModifiedTime;
    }
    
    public String getFileName(){
        return this.fileName;
    }
    
    public String getTypeOfFile(){
        return this.typeOfFile;
    }
    
    public String getSizeOfFile(){
        return String.valueOf(this.size);
    }
    
    public String getLastModified(){
        return this.lastModifiedTime;
    }
}
