/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.fils.semanticweb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fils.semanticweb.domain.XMLDocument;

/**
 *
 * @author andre
 */
public interface XMLDocumentRepository extends MongoRepository<XMLDocument,String>{
    public XMLDocument findByName(String name);
}

