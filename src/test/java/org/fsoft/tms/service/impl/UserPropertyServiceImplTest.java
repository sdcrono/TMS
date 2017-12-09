package org.fsoft.tms.service.impl;

import org.fsoft.tms.entity.UserProperty;
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
public class UserPropertyServiceImplTest {

    @Autowired
    private UserPropertyServiceImpl userPropertyService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PropertyServiceImpl propertyService;

    @Test
    public void getListUserProperty() throws Exception {
        List<UserProperty> userProperties = userPropertyService.getListUserProperty(userService.findOneUser(3));
        assertEquals(5, userProperties.size());
    }

    @Test
    public void getUserProperty() throws Exception {
        UserProperty userProperty = userPropertyService.getUserProperty(userService.findOneUser(60), propertyService.findOneProperty(4));
        assertEquals("C#", userProperty.getValue());
    }

    @Test
    public void search() throws Exception {
        List<UserProperty> userProperties = userPropertyService.search("C#");
        assertEquals(1, userProperties.size());
    }

}