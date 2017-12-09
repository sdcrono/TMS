package org.fsoft.tms.service.impl;

import org.fsoft.tms.entity.Property;
import org.fsoft.tms.repository.PropertyRepository;
import org.fsoft.tms.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by thehaohcm on 5/30/17.
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public List<Property> getAllProperty() {
        return propertyRepository.findAll();
    }

    @Override
    public void addProperty(Property role) {
        propertyRepository.save(role);
    }

    @Override
    public Property findOneProperty(int id) {
        return propertyRepository.findOne(id);
    }

    @Override
    public void updateProperty(Property c) {
        Property temp = propertyRepository.findOne(c.getId());
        temp.setName(c.getName());
        temp.setDescription(c.getDescription());
        propertyRepository.save(temp);
    }
}

