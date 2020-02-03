package zone.god.blogprojectbe.service;

import zone.god.blogprojectbe.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();
    List<Tag> findAllById(Iterable<Long> id);
    Tag addTag(Tag tag);
    Tag findById(long id);
}
