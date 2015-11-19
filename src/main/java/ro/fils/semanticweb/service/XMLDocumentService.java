/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.fils.semanticweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fils.semanticweb.domain.XMLDocument;

/**
 *
 * @author andre
 */
@Service("xmlFileService")
@Transactional
public class XMLDocumentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public XMLDocument getUsersFile() {
        Query query = new Query(Criteria.where("fileName").is("users"));
        XMLDocument file = mongoTemplate.findOne(query, XMLDocument.class);
        return file;
    }

    public XMLDocument getProjectsFile() {
        Query query = new Query(Criteria.where("fileName").is("projects"));
        XMLDocument file = mongoTemplate.findOne(query, XMLDocument.class);
        return file;
    }

    public void addOrUpdateUsers() {
        XMLDocument file = new XMLDocument();
        file.setFileName("users");
        file.setId("213124214");
        file.setFile(getUsersFile().getFile());
        Query query = new Query(Criteria.where("fileName").is("users"));
        Update update = new Update();
        mongoTemplate.updateFirst(query, update, XMLDocument.class);
        mongoTemplate.save(file);
    }

    public XMLDocument addOrUpdateProjects() {
        XMLDocument file = getProjectsFile();
        Query query = new Query(Criteria.where("fileName").is("projects"));
        if (file != null) {
            Update update = new Update();
            mongoTemplate.updateFirst(query, update, XMLDocument.class);
        } else {
            mongoTemplate.save(file);
        }
        return file;
    }
}
