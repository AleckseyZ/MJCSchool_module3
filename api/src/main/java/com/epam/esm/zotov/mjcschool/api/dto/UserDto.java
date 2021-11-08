package com.epam.esm.zotov.mjcschool.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import com.epam.esm.zotov.mjcschool.dataaccess.model.User;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class UserDto extends RepresentationModel<UserDto> {
    private Long userId;
    private String username;

    public User convertToUser() {
        return new User(userId, username);
    }

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
    }
}
