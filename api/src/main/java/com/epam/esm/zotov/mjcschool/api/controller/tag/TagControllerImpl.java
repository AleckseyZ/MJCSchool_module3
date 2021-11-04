package com.epam.esm.zotov.mjcschool.api.controller.tag;

import java.util.List;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.api.exception.NoResourceFoundException;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;
import com.epam.esm.zotov.mjcschool.service.tag.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//TODO maybe add filter for HATEOAS?
@RestController
@RequestMapping("/tags")
public class TagControllerImpl implements TagController {
    private TagService tagService;

    @Autowired
    public TagControllerImpl(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public List<Tag> getPage(int limit, long afterId) {
        List<Tag> tags = tagService.getPage(limit, afterId);
        if (tags.isEmpty()) {
            throw new NoResourceFoundException();
        }
        for (Tag tag : tags) {
            addCommonHateoasLinks(tag);
        }
        return tags;
    }

    @Override
    public Tag getById(long targetId) {
        Optional<Tag> tag = tagService.getById(targetId);
        if (tag.isEmpty()) {
            throw new NoResourceFoundException();
        }
        addCommonHateoasLinks(tag.get());
        return tag.get();
    }

    @Override
    public Tag save(Tag tag) {
        Optional<Tag> returnedTag = tagService.save(tag);
        if (returnedTag.isEmpty()) {
            throw new NoResourceFoundException();
        }
        addCommonHateoasLinks(returnedTag.get());
        return returnedTag.get();
    }

    @Override
    public void delete(long targetId) {
        tagService.delete(targetId);
    }

    private void addCommonHateoasLinks(Tag tag) {
        tag.add(linkTo(methodOn(TagControllerImpl.class).getById(tag.getTagId())).withSelfRel());
    }
}