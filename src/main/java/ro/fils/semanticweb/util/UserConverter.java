/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.fils.semanticweb.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.fils.semanticweb.domain.User;

/**
 *
 * @author andre
 */
public class UserConverter {

    public ArrayList<User> convertFromXmlToUsers(String xml) {
        ArrayList<User> users = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(xml);
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                User user = null;
                if (node instanceof Element) {
                    user = new User();
                    NodeList childNodes = node.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node cNode = childNodes.item(j);
                        if (cNode instanceof Element) {
                            String content = cNode.getLastChild().getTextContent().trim();
                            System.out.println("CONTENT NUMBER " + i + " : " + content);
                            switch (cNode.getNodeName()) {
                                case "id": {
                                    user.setId(content);
                                    break;
                                }
                                case "username": {
                                    user.setUsername(content);
                                    break;
                                }
                                case "role": {
                                    user.setRole(content);
                                    break;
                                }
                                case "mail": {
                                    user.setMail(content);
                                    break;
                                }
                                case "password": {
                                    user.setPassword(content);
                                    break;
                                }
                                case "first-name": {
                                    user.setFirstName(content);
                                    break;
                                }
                                case "last-name": {
                                    user.setLastName(content);
                                    break;
                                }
                            }
                        }
                    }
                    users.add(user);
                }

            }
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            Logger.getLogger(UserConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (User user : users) {
            System.out.println(user.getFirstName());
        }
        return users;
    }

    public User insertUser(User user, String xml) {
        ArrayList<User> users = convertFromXmlToUsers(xml);
        boolean found = false;
        for (User u : users) {
            if (u.getMail().equals(user.getMail())) {
                found = true;
            }
        }
        if (found == false) {
            writeUserInXml(user, xml);
            return user;
        }
        return null;
    }

    public void writeUserInXml(User user, String xml) {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            System.out.println("intra");
            builder = domFactory.newDocumentBuilder();
            doc = builder.parse(xml);
            NodeList users = doc.getElementsByTagName("users");
            users.item(0).appendChild(createUserElement(doc, user));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult file = new StreamResult(new File(xml));
            transformer.transform(source, file);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException ex) {
            Logger.getLogger(UserConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Element createUserElement(Document doc, User user) {
        Element el = doc.createElement("user");
        el.appendChild(createPersonDetailElement(doc, "id", user.getId()));
        el.appendChild(createPersonDetailElement(doc, "username", user.getUsername()));
        el.appendChild(createPersonDetailElement(doc, "role", user.getRole()));
        el.appendChild(createPersonDetailElement(doc, "mail", user.getMail()));
        el.appendChild(createPersonDetailElement(doc, "password", user.getPassword()));
        el.appendChild(createPersonDetailElement(doc, "first-name", user.getFirstName()));
        el.appendChild(createPersonDetailElement(doc, "last-name", user.getLastName()));
        return el;
    }

    private Element createPersonDetailElement(Document doc, String name,
            String value) {
        Element el = doc.createElement(name);
        el.appendChild(doc.createTextNode(value));
        return el;
    }
}
