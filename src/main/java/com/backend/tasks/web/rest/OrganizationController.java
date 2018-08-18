package com.backend.tasks.web.rest;

import com.backend.tasks.domain.dto.save.OrganizationSaveDto;
import com.backend.tasks.domain.dto.view.OrganizationViewDto;
import com.backend.tasks.domain.entity.Organization;
import com.backend.tasks.repository.OrganizationRepository;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
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

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private MapperFactory mapperFactory;

    @GetMapping
    public List<OrganizationViewDto> getAll() {
        List<Organization> orgs = organizationRepository.findAll();
        List<OrganizationViewDto> orgViews = mapperFacade.mapAsList(orgs, OrganizationViewDto.class);
        return orgViews;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizationViewDto save(@RequestBody @Validated OrganizationSaveDto organizationSaveDto) {
        Organization organization = mapperFacade.map(organizationSaveDto, Organization.class);
        organizationRepository.save(organization);
        OrganizationViewDto organizationViewDto = mapperFacade.map(organization, OrganizationViewDto.class);
        return organizationViewDto;
    }

    @PutMapping(value = "/{orgId}")
    public OrganizationViewDto update(@RequestParam Long orgId, @RequestBody @Validated OrganizationSaveDto organizationSaveDto) {
        Organization organization = organizationRepository.getOne(orgId);

        BoundMapperFacade<OrganizationSaveDto, Organization> mapper = mapperFactory.getMapperFacade(OrganizationSaveDto.class, Organization.class);
        mapper.map(organizationSaveDto, organization);

        organizationRepository.save(organization);
        OrganizationViewDto organizationViewDto = mapperFacade.map(organization, OrganizationViewDto.class);

        return organizationViewDto;
    }

    @GetMapping(value = "/{orgId}")
    public OrganizationViewDto get(@RequestParam Long orgId) {
        Organization organization = organizationRepository.getOne(orgId);
        OrganizationViewDto organizationViewDto = mapperFacade.map(organization, OrganizationViewDto.class);
        return organizationViewDto;
    }

    @DeleteMapping(value = "/{orgId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Long orgId) {
        organizationRepository.deleteById(orgId);
    }
}
