/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.fils.semanticweb.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.fils.semanticweb.domain.Project;
import ro.fils.semanticweb.domain.Partner;
import ro.fils.semanticweb.domain.Stage;

/**
 *
 * @author Vlad
 */
public class ProjectConverter {

    public ArrayList<Project> readAll(String xml) {
        ArrayList<Project> projects = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(xml);
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Project project = null;
                if (node instanceof Element) {
                    project = new Project();
                    NodeList childNodes = node.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node cNode = childNodes.item(j);
                        if (cNode instanceof Element) {
                            String content = cNode.getLastChild().getTextContent().trim();
                            System.out.println("CONTENT NUMBER " + i + " : " + content);
                            switch (cNode.getNodeName()) {
                                case "id": {
                                    project.setId(content);
                                    break;
                                }
                                case "title": {
                                    project.setTitle(content);
                                    break;
                                }
                                case "budget": {
                                    project.setBudget(Integer.parseInt(content));
                                    break;
                                }
                                case "duration": {
                                    project.setDuration(Integer.parseInt(content));
                                    break;
                                }
                                case "partners": {
                                    ArrayList<Partner> partners = new ArrayList<>();
                                    NodeList partnerNodes = node.getChildNodes();
                                    for (int k = 0; k < partnerNodes.getLength(); k++) {
                                        Node partnerNode = partnerNodes.item(k);
                                        Partner partner = null;
                                        if (partnerNode instanceof Element) {
                                            partner = new Partner();
                                            NodeList partnerChildNodes = partnerNode.getChildNodes();
                                            System.out.println("########" + partnerChildNodes.getLength());
                                            for (int p = 0; p < partnerChildNodes.getLength(); p++) {
                                                Node cPartnerNode = partnerChildNodes.item(p);
                                                if (cPartnerNode instanceof Element) {
                                                    String contentPartner = cPartnerNode.getLastChild().getTextContent().trim();
                                                    System.out.println("CONTENT NUMBER " + k + " : " + contentPartner);
                                                    switch (cPartnerNode.getNodeName()) {
                                                        case "name": {
                                                            partner.setName(contentPartner);
                                                            break;
                                                        }
                                                        case "leader": {
                                                            partner.setName(contentPartner);
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        partners.add(partner);
                                    }
                                    project.setPartners(partners);
                                    break;
                                }
                                case "domain": {
                                    project.setDomain(content);
                                    break;
                                }
                                case "objective": {
                                    project.setObjective(content);
                                    break;
                                }

                                case "stages": {
                                    ArrayList<Stage> stages = new ArrayList<>();
                                    NodeList stageNodes = node.getChildNodes();
                                    for (int t = 0; t < stageNodes.getLength(); t++) {
                                        Node stageNode = stageNodes.item(t);
                                        Stage stage = null;
                                        if (stageNode instanceof Element) {
                                            stage = new Stage();
                                            NodeList stageChildNodes = stageNode.getChildNodes();
                                            for (int s = 0; s < stageChildNodes.getLength(); s++) {
                                                Node sPartnerNode = stageChildNodes.item(s);
                                                if (sPartnerNode instanceof Element) {
                                                    String contentStage = sPartnerNode.getLastChild().getTextContent().trim();
                                                    System.out.println("CONTENT NUMBER " + t + " : " + contentStage);
                                                    switch (sPartnerNode.getNodeName()) {
                                                        case "name": {
                                                            stage.setName(contentStage);
                                                            break;
                                                        }
                                                        case "duration": {
                                                            stage.setDuration(Integer.parseInt(contentStage));
                                                            break;
                                                        }
                                                        case "description": {
                                                            stage.setDescription(contentStage);
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        stages.add(stage);
                                    }
                                    project.setStages(stages);
                                    break;
                                }
                            }
                        }
                    }
                    projects.add(project);
                }

            }
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            Logger.getLogger(ProjectConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Project project : projects) {
            System.out.println(project.getTitle());
        }
        return projects;
    }
}
