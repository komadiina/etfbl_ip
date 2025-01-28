package org.unibl.etf.rest_api.service.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.db.Post;
import org.unibl.etf.rest_api.repository.PostRepository;
import org.unibl.etf.rest_api.service.CRUDService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements CRUDService<Post> {
    @Autowired
    private PostRepository repository;

    @Override
    public Post create(Post post) {
        return repository.save(post);
    }

    @Override
    public Post update(Post post) {
        if (!repository.existsById(post.getPostID()))
            return null;

        return repository.save(post);
    }

    @Override
    public boolean delete(Post post) {
        if (!repository.existsById(post.getPostID()))
            return false;

        repository.delete(post);
        return true;
    }

    @Override
    public Post retrieve(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Post> retrieveAll() {
        return repository.findAll();
    }
}
