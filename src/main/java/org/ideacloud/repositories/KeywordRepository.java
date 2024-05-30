package org.ideacloud.repositories;

import org.ideacloud.models.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    List<Keyword> findAllByKeywordIn(List<String> keywords);
}
