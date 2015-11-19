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
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.fils.semanticweb.domain.User;
import ro.fils.semanticweb.util.UserConverter;

/**
 *
 * @author andre
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    private UserConverter userConverter;

    @Autowired
    private ServletContext context;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ArrayList<User> getAllUsers() {
        userConverter = new UserConverter();
        try {
            testInsert();
            return userConverter.convertFromXmlToUsers(context.getResource("/WEB-INF/users.xml").toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    User insertUser(@RequestBody User user) {
        System.out.println("#########USERUL LA BAIAT#######" + user);
        userConverter = new UserConverter();
        User returnUser = null;
        try {
            returnUser = userConverter.insertUser(user, context.getResource("/WEB-INF/users.xml").toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnUser;
    }

    public void testInsert() {
        User user = new User();
        user.setId("32434");
        user.setLastName("last name");
        user.setFirstName("fdsfds");
        user.setMail("mail");
        user.setPassword("sdfsdf");
        user.setRole("director");
        user.setUsername("gogu");
        try {
            new UserConverter().insertUser(user, context.getResource("/WEB-INF/users.xml").toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
