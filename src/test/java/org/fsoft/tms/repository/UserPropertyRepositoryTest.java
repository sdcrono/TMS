package org.fsoft.tms.repository;

import org.fsoft.tms.entity.UserProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Isabella on 1-Jun-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest//(classes = {RepositoryConfiguration.class})
public class UserPropertyRepositoryTest {

    @Autowired
    private UserPropertyRepository userPropertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

//    @Test
//    public void getAllByPk_User() throws Exception {
//        List<UserProperty> listActual = userPropertyRepository.getAllByPk_User(userRepository.findOne(3));
//        int size = userRepository.findOne(3).getUserProperties().size();
////        int count = 0;
////        for(UserProperty c : listExpect){
////            count++;
////        }
//        assertEquals(size, listActual.size());
//    }

    @Test
    public void getAllByPk_UserAndPk_Property() throws Exception {
        UserProperty userPropertyActual =
                userPropertyRepository.getAllByPk_UserAndPk_Property(userRepository.findOne(3),
                        propertyRepository.findOne(1));
        List<UserProperty> list = userPropertyRepository.getAllByPk_User(userRepository.findOne(3));
        UserProperty userPropertyExpect = list.get(0);
        assertEquals(userPropertyExpect.getValue(), userPropertyActual.getValue());
    }

}