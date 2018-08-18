package com.backend.tasks.web.rest;

import com.backend.tasks.domain.Organization;
import com.backend.tasks.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/orgs")
public class OrganizationController {

    @Autowired
    OrganizationRepository organizationRepository;

    @GetMapping
    public List<Organization> getAll() {
        List<Organization> users = organizationRepository.findAll();
        return users;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Organization save(@RequestBody @Validated Organization organization) {
        organizationRepository.save(organization);
        return organization;
    }

    @PutMapping(value = "/{orgId}")
    public Organization update(@RequestParam Long orgId, @RequestBody @Validated Organization organization) {
        organization.setId(orgId);
        organizationRepository.save(organization);
        return organization;
    }

    @GetMapping(value = "/{orgId}")
    public Organization get(@RequestParam Long orgId) {
        Organization organization = organizationRepository.getOne(orgId);
        return organization;
    }

    @DeleteMapping(value = "/{orgId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Long orgId) {
        organizationRepository.deleteById(orgId);
    }
}
