package com.epam.esm.zotov.mjcschool.api.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
    private static final String NEXT_RELATION = "next";
    private static final String PREVIOUS_RELATION = "previous";
    private List<T> list;

    public ListDto() {
        list = new ArrayList<T>();
    }

    public void addNextPageLink(Object link) {
        this.add(linkTo(link).withRel(NEXT_RELATION));
    }

    public void addPreviousPageLink(Object link) {
        this.add(linkTo(link).withRel(PREVIOUS_RELATION));
    }
}