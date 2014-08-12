package com.dfarache.filesystemxmlizer;

import com.dfarache.logTools.LogTools;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSystemWalker {
    
    private static final String className = FileSystemWalker.class.getName();
    private static final Logger log = new LogTools(className).getLogInstace();
    
    public static void startWalking(Path startingPoint) throws IOException {
        Files.walkFileTree(startingPoint, new DirectoryVisitor());
    }

    private static class DirectoryVisitor implements FileVisitor<Path> {

        private List<FileAttributes> filesInDirectory;
        
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            filesInDirectory = new ArrayList();
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            FileAttributes currentFile = getCurrentFile(file, attrs);
            filesInDirectory.add(currentFile);
            //System.out.println(currentFile.toString());
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir,
                IOException exc) {
            FileSystemXMLizer.xml.addFilesAndDirectoryToXML(dir.toString(), filesInDirectory);
            log.log(Level.INFO,"Accessing directory {0}", dir.toString());

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
           log.log(Level.SEVERE, exc.toString());
           return FileVisitResult.CONTINUE;
        }

        private FileAttributes getCurrentFile(Path file, BasicFileAttributes attrs) {
            return new FileAttributes(file.getFileName().toString(), getTypeOfFile(attrs),
                    attrs.size(), attrs.lastModifiedTime().toString());
        }

        private String getTypeOfFile(BasicFileAttributes attrs) {
            if (attrs.isOther()) {
                return "Other";
            }
            return attrs.isRegularFile()? "File":"Symbolic";
        }
    }
}
