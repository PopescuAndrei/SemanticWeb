/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.fils.semanticweb.controller;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.fils.semanticweb.domain.Project;
import ro.fils.semanticweb.domain.User;
import ro.fils.semanticweb.util.ProjectConverter;
import ro.fils.semanticweb.util.UserConverter;

/**
 *
 * @author Vlad
 */
@Controller
@RequestMapping("/projects")
public class ProjectsController {

    private ProjectConverter projectConverter;

    @Autowired
    private ServletContext context;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<Project> getAllProjects() {
        projectConverter = new ProjectConverter();
        try {
            return projectConverter.readAll(context.getResource("/WEB-INF/projects.xml").toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
