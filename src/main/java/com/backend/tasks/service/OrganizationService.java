package com.backend.tasks.service;

import com.backend.tasks.domain.dto.save.OrganizationSaveDto;
import com.backend.tasks.domain.dto.view.OrganizationViewDto;
import com.backend.tasks.domain.entity.Organization;
import com.backend.tasks.repository.OrganizationRepository;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private MapperFactory mapperFactory;

    public List<OrganizationViewDto> getAll() {
        List<Organization> orgs = organizationRepository.findAll();
        List<OrganizationViewDto> orgViews = mapperFacade.mapAsList(orgs, OrganizationViewDto.class);
        return orgViews;
    }

    public OrganizationViewDto get(Long orgId) {
        Organization organization = organizationRepository.getOne(orgId);
        OrganizationViewDto organizationViewDto = mapperFacade.map(organization, OrganizationViewDto.class);
        return organizationViewDto;
    }

    public OrganizationViewDto save(OrganizationSaveDto organizationSaveDto) {
        Organization organization = mapperFacade.map(organizationSaveDto, Organization.class);
        organizationRepository.save(organization);
        OrganizationViewDto organizationViewDto = mapperFacade.map(organization, OrganizationViewDto.class);
        return organizationViewDto;
    }

    public OrganizationViewDto update(Long orgId, OrganizationSaveDto organizationSaveDto) {
        Organization organization = organizationRepository.getOne(orgId);

        BoundMapperFacade<OrganizationSaveDto, Organization> mapper = mapperFactory.getMapperFacade(OrganizationSaveDto.class, Organization.class);
        mapper.map(organizationSaveDto, organization);

        organizationRepository.save(organization);
        OrganizationViewDto organizationViewDto = mapperFacade.map(organization, OrganizationViewDto.class);

        return organizationViewDto;
    }

}
