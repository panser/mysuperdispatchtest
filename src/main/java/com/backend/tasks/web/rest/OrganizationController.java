package com.backend.tasks.web.rest;

import com.backend.tasks.domain.dto.save.OrganizationSaveDto;
import com.backend.tasks.domain.dto.view.OrganizationViewDto;
import com.backend.tasks.repository.OrganizationRepository;
import com.backend.tasks.service.OrganizationService;
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
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping
    public List<OrganizationViewDto> getAll() {
        List<OrganizationViewDto> orgViews = organizationService.getAll();
        return orgViews;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrganizationViewDto save(@RequestBody @Validated OrganizationSaveDto organizationSaveDto) {
        OrganizationViewDto organizationViewDto = organizationService.save(organizationSaveDto);
        return organizationViewDto;
    }

    @PutMapping(value = "/{orgId}")
    public OrganizationViewDto update(@RequestParam Long orgId, @RequestBody @Validated OrganizationSaveDto organizationSaveDto) {
        OrganizationViewDto organizationViewDto = organizationService.update(orgId, organizationSaveDto);

        return organizationViewDto;
    }

    @GetMapping(value = "/{orgId}")
    public OrganizationViewDto get(@RequestParam Long orgId) {
        OrganizationViewDto organizationViewDto = organizationService.get(orgId);
        return organizationViewDto;
    }

    @DeleteMapping(value = "/{orgId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Long orgId) {
        organizationRepository.deleteById(orgId);
    }
}
