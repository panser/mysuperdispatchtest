package com.backend.tasks.service;

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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private MapperFactory mapperFactory;

    public List<UserViewDto> getAll(Long orgId) {
        List<User> users = userRepository.findAllByOrganizationId(orgId);
        List<UserViewDto> userViews = mapperFacade.mapAsList(users, UserViewDto.class);
        return userViews;
    }

    public UserViewDto save(Long orgId, UserSaveDto userSaveDto) {
        User user = mapperFacade.map(userSaveDto, User.class);
        Organization organization = organizationRepository.getOne(orgId);
        user.setOrganization(organization);
        userRepository.save(user);
        UserViewDto userViewDto = mapperFacade.map(user, UserViewDto.class);
        return userViewDto;
    }

    public UserViewDto update(Long orgId, Long userId, UserSaveDto userSaveDto) {
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

    public UserViewDto get(Long orgId, Long userId) {
        Optional<User> userOptional = userRepository.findOneByIdAndOrganizationId(userId, orgId);
        if (!userOptional.isPresent()) {
            throw new AppException("Current user does not belong to this organization");
        }

        UserViewDto userViewDto = mapperFacade.map(userOptional.get(), UserViewDto.class);
        return userViewDto;
    }

}
