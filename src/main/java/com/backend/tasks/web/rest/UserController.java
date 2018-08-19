package com.backend.tasks.web.rest;

import com.backend.tasks.domain.dto.save.UserSaveDto;
import com.backend.tasks.domain.dto.view.UserViewDto;
import com.backend.tasks.domain.entity.Organization;
import com.backend.tasks.domain.entity.User;
import com.backend.tasks.exception.AppException;
import com.backend.tasks.repository.OrganizationRepository;
import com.backend.tasks.repository.UserRepository;
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
import java.util.Optional;

@RestController
@RequestMapping(value = "/users/{orgId}/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private MapperFactory mapperFactory;


    @GetMapping
    public List<UserViewDto> getAll(@RequestParam Long orgId) {
        List<User> users = userRepository.findAllByOrganizationId(orgId);
        List<UserViewDto> userViews = mapperFacade.mapAsList(users, UserViewDto.class);
        return userViews;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserViewDto save(@RequestParam Long orgId, @RequestBody @Validated UserSaveDto userSaveDto) {
        User user = mapperFacade.map(userSaveDto, User.class);
        Organization organization = organizationRepository.getOne(orgId);
        user.setOrganization(organization);
        userRepository.save(user);
        UserViewDto userViewDto = mapperFacade.map(user, UserViewDto.class);
        return userViewDto;
    }

    @PutMapping(value = "/{userId}")
    public UserViewDto update(@RequestParam Long orgId, @RequestParam Long userId, @RequestBody @Validated UserSaveDto userSaveDto) {
        Optional<User> userOptional = userRepository.findOneByIdAndOrganizationId(userId, orgId);
        if (!userOptional.isPresent()) {
            throw new AppException("Current user does not belong to this organization");
        }

        User user = userOptional.get();
        BoundMapperFacade<UserSaveDto, User> mapper = mapperFactory.getMapperFacade(UserSaveDto.class, User.class);
        mapper.map(userSaveDto, user);

        userRepository.save(user);
        UserViewDto userViewDto = mapperFacade.map(user, UserViewDto.class);

        return userViewDto;
    }

    @GetMapping(value = "/{userId}")
    public UserViewDto get(@RequestParam Long orgId, @RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findOneByIdAndOrganizationId(userId, orgId);
        if (!userOptional.isPresent()) {
            throw new AppException("Current user does not belong to this organization");
        }

        UserViewDto userViewDto = mapperFacade.map(userOptional.get(), UserViewDto.class);
        return userViewDto;
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Long orgId, @RequestParam Long userId) {
        userRepository.deleteByIdAndOrganizationId(userId, orgId);
    }

}
