package org.unibl.etf.rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.rest_api.model.db.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
