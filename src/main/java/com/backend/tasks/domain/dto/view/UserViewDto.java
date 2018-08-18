package com.backend.tasks.domain.dto.view;

import com.backend.tasks.domain.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserViewDto {

    private Long id;

    private String username;

    private String password;

    private Organization organizationId;

}
