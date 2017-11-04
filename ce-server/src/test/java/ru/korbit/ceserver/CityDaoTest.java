package ru.korbit.ceserver;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.korbit.cecommon.dao.CityDao;
import ru.korbit.cecommon.domain.City;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Artur Belogur on 12.10.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context-test.xml")
@Transactional
public class CityDaoTest {

    @Autowired
    CityDao cityDao;

    @Test
    public void addGetCity() {
        val name = "Калининград";

        val c = new City();
        c.setName(name);
        val cityIn = cityDao.save(c);
        val cityOut = cityDao.get(cityIn.getId());
        Assert.assertTrue(cityOut.isPresent());
        Assert.assertTrue(Objects.equals(cityOut.get().getName(), name));
    }

    @Test
    public void getAllCity() {
        val numberOfCities = 10;

        Stream<City> cities = IntStream.range(0, numberOfCities)
                .boxed()
                .map(i -> {
                    val city = new City();
                    city.setName(Integer.toString(i));
                    return city;
                });
        cities.forEach(city -> cityDao.save(city));

        val newCities = cityDao.getAll().collect(Collectors.toList());
        Assert.assertTrue(newCities.size() == numberOfCities);
    }
}
