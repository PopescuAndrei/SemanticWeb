/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.fils.semanticweb.util;

import java.io.IOException;
import java.io.StringReader;
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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ro.fils.semanticweb.domain.Project;
import ro.fils.semanticweb.domain.Partner;
import ro.fils.semanticweb.domain.Stage;

/**
 *
 * @author Vlad
 */
public class ProjectConverter {

    public ArrayList<Project> readAllProjects(String xml) {
        ArrayList<Project> projects = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(new InputSource(new StringReader(xml)));
            NodeList nodeList = document.getElementsByTagName("project");
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
                                    NodeList partnerNodes = ((Element) cNode).getElementsByTagName("partner");
                                    System.out.println("LUNGIMEA LA PARTNERS: " + partnerNodes.getLength());
                                    for (int k = 0; k < partnerNodes.getLength(); k++) {
                                        System.out.println("INTRA");
                                        Node partnerNode = partnerNodes.item(k);
                                        Partner partner = null;
                                        if (partnerNode instanceof Element) {
                                            partner = new Partner();
                                            //NodeList partnerChildNodes = partnerNode.getChildNodes();
                                            System.out.println(partnerNode.getChildNodes().getLength());
                                            partner.setName(partnerNode.getChildNodes().item(0).getTextContent().trim());
                                            System.out.println("NAME: " + partnerNode.getChildNodes().item(0).getTextContent().trim());
                                            partner.setLeader(Boolean.parseBoolean(partnerNode.getChildNodes().item(1).getTextContent().trim()));
                                            System.out.println("BOOLEAN: " + partnerNode.getChildNodes().item(1).getTextContent().trim());
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
                                    NodeList stageNodes = cNode.getChildNodes();
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
