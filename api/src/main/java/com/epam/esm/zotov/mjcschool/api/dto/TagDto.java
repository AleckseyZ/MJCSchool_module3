package com.epam.esm.zotov.mjcschool.api.dto;

import org.springframework.hateoas.RepresentationModel;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;

import lombok.EqualsAndHashCode;
import lombok.Getter;
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
public class TagDto extends RepresentationModel<TagDto> {
    private Long tagId;
    private String name;

    public Tag convertToTag() {
        return new Tag(tagId, name);
    }

    public TagDto(Tag tag) {
        this.tagId = tag.getTagId();
        this.name = tag.getName();
    }
}