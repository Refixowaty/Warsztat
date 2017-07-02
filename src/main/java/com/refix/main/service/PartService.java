package com.refix.main.service;

import com.refix.main.entity.Part;
import com.refix.main.entity.PartUse;
import com.refix.main.entity.ServiceOrder;
import com.refix.main.repository.PartRepo;
import com.refix.main.repository.PartUseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PartService {

    @Autowired
    private PartRepo partRepo;
    @Autowired
    private PartUseRepo partUseRepo;

    public Part createPart(String name, double cost, String producer, String category, boolean newPart, int quantity){
        Part part = new Part(name, cost, producer, category, newPart, quantity);
        partRepo.save(part);
        return part;
    }

    public Part readPart(long id){
        return partRepo.findOne(id);
    }

    public Part readPart(String name){
        return partRepo.findByName(name);
    }

    public List<Part> readAllParts(){
        return partRepo.findAll();
    }

    public List<Part> readAllPartWithPlusQuantity(){
        return partRepo.findAllWhereQuantityMoreThanZero();
    }

    public void updatePart(long id, String name, double cost, String producer, String category, boolean newPart, int quantity){
        Part part = partRepo.findOne(id);
        part.setName(name);
        part.setCost(cost);
        part.setProducer(producer);
        part.setCategory(category);
        part.setNewPart(newPart);
        part.setQuantity(quantity);
        partRepo.save(part);
    }
    public void updatePartQuantity(long id, int quantity){
        Part part = partRepo.findOne(id);
        part.setQuantity(quantity);
        partRepo.save(part);
    }

    public void updatePart(Part part, String name, double cost, String producer, String category, boolean newPart, int quantity){
        part.setName(name);
        part.setCost(cost);
        part.setProducer(producer);
        part.setCategory(category);
        part.setNewPart(newPart);
        part.setQuantity(quantity);
    }

    public void delete(long id){
        partRepo.delete(id);
    }

    public void delete(Part part){
        partRepo.delete(part);
    }

    //USED PART Service

    public void attachPartToOrder(long partId, ServiceOrder serviceOrder){
        Part part = partRepo.findOne(partId);
        updatePartQuantity(partId, part.getQuantity()-1);

        PartUse partUse = partUseRepo.findByPartIdAndServiceOrder(partId, serviceOrder);
        if (partUse == null){
            partUse = new PartUse(part, serviceOrder);
        }
        else{
            partUse.setQuantity(partUse.getQuantity()+1);
        }
        partUseRepo.save(partUse);
    }

    public void detachPartFromOrder(long partUseId){
        PartUse partUse = partUseRepo.findOne(partUseId);
        if (partUse.getQuantity()>1){
            partUse.setQuantity(partUse.getQuantity()-1);
            partUseRepo.save(partUse);
        }
        else{
            partUseRepo.delete(partUse);
        }
        Part part = partRepo.findOne(partUse.getPart().getId());
        updatePartQuantity(part.getId(), part.getQuantity()+1);
    }

    public double calculatePartsCostInServiceOrder(List<PartUse> partUses){
        double cost = 0;
        for (PartUse partUse : partUses) {
            cost += partUse.getPart().getCost() * partUse.getQuantity();
        }
        return cost;
    }


}
