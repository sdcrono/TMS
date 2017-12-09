package org.fsoft.tms.service;

import org.fsoft.tms.entity.Property;
import org.fsoft.tms.entity.User;
import org.fsoft.tms.entity.UserProperty;

import java.util.List;
import java.util.Set;

/**
 * Created by Isabella on 29-May-2017.
 */
public interface UserPropertyService {

    List<UserProperty> getListUserProperty(User user);

    UserProperty getUserProperty(User user, Property property);

//    Set<UserProperty> setTrainerProperty(User user, String name, String email, String phone, String address);

    void saveTrainerProperty(List<UserProperty> userProperties);

    List<UserProperty> search(String input);
}
