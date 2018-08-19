package com.backend.tasks.domain.dto.view;

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

    private Long organizationId;

}
