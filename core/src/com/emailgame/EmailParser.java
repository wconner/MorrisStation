package com.emailgame;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
/**
 * Created by Tom on 2/26/2015.
 */

public class EmailParser {
    private ArrayList<Email> emails;

    public EmailParser(){
        emails = new ArrayList<Email>();
        try{
            ClassLoader classLoader = getClass().getClassLoader();
            //find the xml file with the list of emails
            File xmlFile = new File("android/assets/Email.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            //find all the elements from the email node
            NodeList nList = doc.getElementsByTagName("email");

            //creating the email object from the elements based on their tag name
            for(int i = 0; i < nList.getLength(); i++){
                Node nNode = nList.item(i);

                if(nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) nNode;
                    emails.add(new Email(element.getElementsByTagName("emailType").item(0).getTextContent(),
                            element.getElementsByTagName("from").item(0).getTextContent(),
                            element.getElementsByTagName("subject").item(0).getTextContent(),
                            element.getElementsByTagName("message").item(0).getTextContent()));
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("File Not Found");
        }
    }

    //returns the array of all the emails
    public ArrayList<Email> getEmails(){
        return emails;
    }
}