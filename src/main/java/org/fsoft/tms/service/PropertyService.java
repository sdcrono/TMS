package org.fsoft.tms.service;

import org.fsoft.tms.entity.Property;

import java.util.List;

/**
 * Created by Isabella on 29-May-2017.
 */
public interface PropertyService {

    List<Property> getAllProperty();

    void addProperty(Property role);

    Property findOneProperty(int id);

    void updateProperty(Property c);
}
