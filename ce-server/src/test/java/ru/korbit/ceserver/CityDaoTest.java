package ru.korbit.ceserver;

import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.korbit.cecommon.dao.CityDao;

/**
 * Created by Artur Belogur on 12.10.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context-test.xml")
@Slf4j
@Transactional
public class CityDaoTest {

    @Autowired
    CityDao cityDao;

    @Test
    public void getTest() {
        val city = cityDao.getCity(1L);
        Assert.assertFalse(city.isPresent());
    }
}
