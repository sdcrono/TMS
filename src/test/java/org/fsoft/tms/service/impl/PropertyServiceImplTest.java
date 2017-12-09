package org.fsoft.tms.service.impl;

import org.fsoft.tms.entity.Property;
import org.fsoft.tms.repository.PropertyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Isabella on 8-Jun-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyServiceImplTest {

    @Autowired
    private PropertyServiceImpl propertyService;

    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    public void getAllProperty() throws Exception {
        List<Property> properties = propertyService.getAllProperty();
        assertEquals(12, properties.size());
    }

    @Test
    public void addProperty() throws Exception {
        Property property = new Property("CurrentPassword", "Your current password");
        propertyRepository.save(property);
        Property property1 = propertyService.findOneProperty(property.getId());
        assertEquals("CurrentPassword", property1.getName());
    }

    @Test
    public void findOneProperty() throws Exception {
        Property property = propertyService.findOneProperty(13);
        assertEquals("CurrentPassword", property.getName());
    }

    @Test
    public void updateProperty() throws Exception {
        Property property = propertyService.findOneProperty(13);
        property.setName("OldPassword");
        propertyRepository.save(property);
        Property property1 = propertyService.findOneProperty( 13);
        assertEquals("OldPassword", property1.getName());
    }

}