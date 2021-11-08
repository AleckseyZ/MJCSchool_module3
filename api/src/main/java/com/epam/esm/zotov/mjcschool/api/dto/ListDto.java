package com.epam.esm.zotov.mjcschool.api.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class ListDto<T> extends RepresentationModel<ListDto<T>> {
    List<T> list;

    public ListDto() {
        list = new ArrayList<T>();
    }
}