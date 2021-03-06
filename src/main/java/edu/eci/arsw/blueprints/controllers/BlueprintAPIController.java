/*
 * To chang this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.impl.Tuple;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@Service
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {     
    @Autowired
    BlueprintsServices blue;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprints(){
        try {            
            return new ResponseEntity<>(blue.getAllBlueprints(),HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintsServices.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error",HttpStatus.NOT_FOUND);
        }        
    }
    
    @RequestMapping(value = "/{author}",method = RequestMethod.GET)         
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable String author) throws BlueprintNotFoundException {  
        try{         
            return new ResponseEntity<>(blue.getBlueprintsByAuthor(author),HttpStatus.ACCEPTED); 
        } catch(BlueprintNotFoundException ex){	
 		return new ResponseEntity<>("ERROR 404 \n El autor",HttpStatus.NOT_FOUND);
            }
    }
    
    @RequestMapping(value = "/{author}/{bpname}",method = RequestMethod.GET)         
    public ResponseEntity<?> GetBlueprintsByAuthorAndName(@PathVariable String author, @PathVariable String bpname) throws BlueprintNotFoundException {  
        try{   
            Set<Blueprint> blues=blue.getBlueprintsByAuthor(author);
            Set<Blueprint> plans=new HashSet();
            for (Blueprint blu:blues) {            
            //System.out.println(author.length());
            if(blu.getName().equals(bpname)){                
                plans.add(blu);
                }
            }
            return new ResponseEntity<>(plans,HttpStatus.ACCEPTED); 
        } catch(BlueprintNotFoundException ex){	
 		return new ResponseEntity<>("ERROR 404 \n El autor",HttpStatus.NOT_FOUND);
            }
    }
    
    @RequestMapping(method = RequestMethod.POST)	
    public ResponseEntity<?> manejadorPostRecursoXX(@RequestBody Blueprint bp){
        try {
            blue.addNewBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error the Blueprint could not be created",HttpStatus.FORBIDDEN);            
        }        
    }
    
}
           


