/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(141, 141),new Point(142, 142),new Point(143, 143),new Point(143, 143),new Point(144, 144),new Point(145, 145)};
        Blueprint bp=new Blueprint("author1", "_bp1 ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        Point[] pts1=new Point[]{new Point(140, 140),new Point(141, 141),new Point(142, 142),new Point(143, 143),new Point(144, 144),new Point(145, 145)};
        Blueprint bp1=new Blueprint("mack", "plan1",pts1);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        Point[] pts2=new Point[]{new Point(140, 140),new Point(141, 141),new Point(142, 142),new Point(143, 143),new Point(144, 144),new Point(145, 145)};
        Blueprint bp2=new Blueprint("mack", "plan2 ",pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        Point[] pts3=new Point[]{new Point(140, 140),new Point(141, 141),new Point(142, 142),new Point(143, 143),new Point(144, 144),new Point(145, 145)};
        Blueprint bp3=new Blueprint("jhon", "plan1 ",pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);       
    }        
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }
    
    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author){
        Set<Blueprint> blues=new HashSet();
        for (Map.Entry<Tuple<String, String>,Blueprint> entry : blueprints.entrySet()) { 
            System.out.println(entry.getKey().getElem1());
            //System.out.println(author.length());
            if(entry.getKey().getElem1().equals(author)){                
                blues.add(entry.getValue());
            }
        }        
        return blues;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        Set<Blueprint> blueSet = new HashSet();
        for (Map.Entry<Tuple<String, String>,Blueprint> entry : blueprints.entrySet()) {   
            blueSet.add(entry.getValue());
        }
        return blueSet;
    }

    
    
}
