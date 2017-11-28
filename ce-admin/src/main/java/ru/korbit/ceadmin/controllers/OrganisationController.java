package ru.korbit.ceadmin.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.korbit.ceadmin.dto.ModifyOrganisation;
import ru.korbit.ceadmin.dto.ROrganisation;
import ru.korbit.cecommon.dao.AddressDao;
import ru.korbit.cecommon.dao.ContactDao;
import ru.korbit.cecommon.dao.OrganisationDao;
import ru.korbit.cecommon.dao.UserDao;
import ru.korbit.cecommon.exeptions.BadRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@Transactional
public class OrganisationController extends SessionController {

    private final AddressDao addressDao;
    private final ContactDao contactDao;

    @Autowired
    protected OrganisationController(UserDao userDao,
                                     OrganisationDao organisationDao,
                                     AddressDao addressDao,
                                     ContactDao contactDao) {
        super(userDao);
        this.addressDao = addressDao;
        this.contactDao = contactDao;
    }

    @GetMapping(value = "admin/organisation")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        val user = getSessionUser(request);
        val organisation = new ROrganisation(user.getOrganisation());
        return new ResponseEntity<>(organisation, HttpStatus.OK);
    }

    @PostMapping(value = "admin/organisation")
    public ResponseEntity<?> changeOrganisationInfo(HttpServletRequest request,
                                                    @RequestBody ModifyOrganisation modify) {
        val user = getSessionUser(request);
        val origin = user.getOrganisation();

        origin.setName(modify.getName());
        origin.setLegalName(modify.getLegalName());
        origin.setType(modify.getType());
        origin.setWorkingHours(modify.getWorkingHours());

        modify.getContacts()
                .forEach(contact -> {
                    if (contact.getId() == null) {
                        contact.setOrganisation(origin);
                        contactDao.save(contact);
                        origin.getContacts().add(contact);
                    }
                    else if (contact.getType() == null || contact.getValue() == null) {
                        val currentContact = contactDao.get(contact.getId())
                                .orElseThrow(() -> new BadRequest("Contact is not exist"));

                        contactDao.delete(currentContact);
                        origin.getContacts().remove(currentContact);
                    }
                    else {
                        val currentContact = contactDao.get(contact.getId())
                                .orElseThrow(() -> new BadRequest("Contact is not exist"));

                        currentContact.setType(contact.getType());
                        currentContact.setValue(contact.getValue());
                        currentContact.setDescription(contact.getDescription());
                    }
                });

        modify.getAddresses()
                .forEach(address -> {
                    if (address.getId() == null) {
                        address.setOrganisation(origin);
                        addressDao.save(address);
                        origin.getAddresses().add(address);
                    }
                    else if (address.getCity() == null && address.getStreet() == null
                            && address.getHouse() == null && address.getPostcode() == null) {
                        val currentAddress = addressDao.get(address.getId())
                                .orElseThrow(() -> new BadRequest("Address is not exist"));

                        addressDao.delete(currentAddress);
                        origin.getAddresses().remove(currentAddress);
                    }
                    else {
                        val currentAddress = addressDao.get(address.getId())
                                .orElseThrow(() -> new BadRequest("Address is not exist"));

                        currentAddress.setCity(address.getCity());
                        currentAddress.setStreet(address.getStreet());
                        currentAddress.setHouse(address.getHouse());
                        currentAddress.setPostcode(address.getPostcode());
                    }
                });

        return new ResponseEntity<>(new ROrganisation(origin), HttpStatus.OK);
    }
}
