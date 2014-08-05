package com.dfarache.filesystemxmlizer;

import java.io.File;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLBuilder {

    private final Document doc;
    private Element rootElement;
    private String[] directoryTree;
    private static int treeDepth;

    private XMLBuilder() {
        DocumentBuilder docBuilder = instantiateDocBuilder();
        this.doc = docBuilder.newDocument();
        createRootElement();
    }

    private DocumentBuilder instantiateDocBuilder() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            return docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void createRootElement() {
        rootElement = doc.createElement("homeDirectory");
        doc.appendChild(rootElement);
        setAttribute(rootElement, "directoryName", FileSystemXMLizer.HOMEPATH);
    }

    private void setAttribute(Element element, String attribute, String value) {
        Attr attr = doc.createAttribute(attribute);
        attr.setValue(value);
        element.setAttributeNode(attr);
    }

    public void addFilesAndDirectoryToXML(String path, List<FileAttributes> files) {
        treeDepth = -1;
        String parsedPath = path.replace("/home/dafarache/", "");
        directoryTree = parsedPath.split("/");
        Element fatherElement = locateFatherElement(rootElement);
        Element lastElement = appendMissingDirectories(fatherElement);
        addFilesToLastElement(lastElement, files);
    }

    private void addFilesToLastElement(Element lastElement, List<FileAttributes> files) {
        for (FileAttributes file : files) {
            Element fileElement = doc.createElement("file");
            lastElement.appendChild(fileElement);
            setAttribute(fileElement, "fileName", file.getFileName());
            addAllFileAttributes(fileElement, file);
        }
    }

    private void addAllFileAttributes(Element fileElement, FileAttributes file) {
        addFileAttribute("size", file.getSizeOfFile(), fileElement);
        addFileAttribute("type", file.getTypeOfFile(), fileElement);
        addFileAttribute("lastMod", file.getLastModified(), fileElement);
    }

    private void addFileAttribute(String name, String value, Element file) {
        Element fileAttr = doc.createElement(name);
        fileAttr.appendChild(doc.createTextNode(value));
        file.appendChild(fileAttr);
    }

    private Element appendMissingDirectories(Element fatherElement) {
        if (directoryTree.length > treeDepth) {
            Element directory = doc.createElement("directory");
            fatherElement.appendChild(directory);
            setAttribute(directory, "directoryName", directoryTree[treeDepth]);
            treeDepth++;
            return appendMissingDirectories(directory);
        }
        return fatherElement;
    }

    private Element locateFatherElement(Element element) {
        ++treeDepth;
        if (!element.hasChildNodes() || treeDepth > directoryTree.length) {
            return element;
        }
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element childElement = (Element) nodeList.item(i);
            if (isTheNextDirectory(childElement)) {
                return locateFatherElement(childElement);
            }
        }
        System.out.println("element located!!!  " + element.getAttribute("directoryName"));
        return element;
    }

    private boolean isTheNextDirectory(Element childElement) {
        boolean ans;
        String fileNameAttr = childElement.getAttribute("directoryName");
        try{
            ans = directoryTree[treeDepth].equals(fileNameAttr);
        }catch(ArrayIndexOutOfBoundsException ex){
            return true;
        }
        return ans;
    }

    public void writeIntoXMLAndClose() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("./result.xml"));

            transformer.transform(source, result);
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
    }

    public static XMLBuilder getInstance() {
        return new XMLBuilder();
    }

}
