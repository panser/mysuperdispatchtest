package com.backend.tasks.web.rest;

import com.backend.tasks.domain.dto.save.UserSaveDto;
import com.backend.tasks.domain.dto.view.UserViewDto;
import com.backend.tasks.repository.UserRepository;
import com.backend.tasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users/{orgId}/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserViewDto> getAll(@PathVariable Long orgId) {
        List<UserViewDto> userViews = userService.getAll(orgId);
        return userViews;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserViewDto save(@PathVariable Long orgId, @RequestBody @Validated UserSaveDto userSaveDto) {
        UserViewDto userViewDto = userService.save(orgId, userSaveDto);
        return userViewDto;
    }

    @PutMapping(value = "/{userId}")
    public UserViewDto update(@PathVariable Long orgId, @PathVariable Long userId, @RequestBody @Validated UserSaveDto userSaveDto) {
        UserViewDto userViewDto = userService.update(orgId, userId, userSaveDto);
        return userViewDto;
    }

    @GetMapping(value = "/{userId}")
    public UserViewDto get(@PathVariable Long orgId, @PathVariable Long userId) {
        UserViewDto userViewDto = userService.get(orgId, userId);
        return userViewDto;
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long orgId, @PathVariable Long userId) {
        userRepository.deleteByIdAndOrganizationId(userId, orgId);
    }

}
