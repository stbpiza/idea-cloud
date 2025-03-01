package org.ideacloud.repositories;

import org.ideacloud.models.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    List<Keyword> findAllByTeamIdAndKeywordIn(Long teamId, List<String> keywords);

    Page<Keyword> findAllByTeamIdOrderByIdDesc(Long teamId, Pageable pageable);
}
