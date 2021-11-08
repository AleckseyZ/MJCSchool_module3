package com.epam.esm.zotov.mjcschool.api.controller.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.api.dto.ListDto;
import com.epam.esm.zotov.mjcschool.api.dto.TagDto;
import com.epam.esm.zotov.mjcschool.api.exception.NoResourceFoundException;
import com.epam.esm.zotov.mjcschool.api.exception.RequestNotExecutedException;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;
import com.epam.esm.zotov.mjcschool.service.tag.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/tags")
public class TagControllerImpl implements TagController {
    private TagService tagService;

    @Autowired
    public TagControllerImpl(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public ListDto<TagDto> getPage(int limit, long afterId) {
        List<Tag> tags = tagService.getPage(limit, afterId);
        if (Objects.isNull(tags) || tags.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            List<TagDto> dtos = new ArrayList<>();
            for (Tag tag : tags) {
                TagDto tagDto = new TagDto(tag);
                addCommonHateoasLinks(tagDto);
                dtos.add(tagDto);
            }
            ListDto<TagDto> listDto = new ListDto<TagDto>(dtos);
            long lastId = dtos.get(dtos.size() - 1).getTagId();
            listDto.add(linkTo(methodOn(TagControllerImpl.class).getPage(limit, lastId)).withRel("next"));
            long firstId = dtos.stream().findFirst().get().getTagId();
            listDto.add(linkTo(methodOn(TagControllerImpl.class).getPage(limit, firstId - limit)).withRel("last"));
            return listDto;
        }
    }

    @Override
    public TagDto getById(long targetId) {
        Optional<Tag> tag = tagService.getById(targetId);
        if (tag.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            TagDto tagDto = new TagDto(tag.get());
            addCommonHateoasLinks(tagDto);
            return tagDto;
        }
    }

    @Override
    public TagDto save(TagDto tag) {
        Optional<Tag> savedTag = tagService.save(tag.convertToTag());
        if (savedTag.isEmpty()) {
            throw new NoResourceFoundException();
        } else {
            TagDto tagDto = new TagDto(savedTag.get());
            addCommonHateoasLinks(tagDto);
            return tagDto;
        }
    }

    @Override
    public void delete(long targetId) {
        if (!tagService.delete(targetId)) {
            throw new RequestNotExecutedException();
        }
    }

    private void addCommonHateoasLinks(TagDto tag) {
        tag.add(linkTo(methodOn(TagControllerImpl.class).getById(tag.getTagId())).withSelfRel());
    }
}