package org.ideacloud.repositories;

import org.ideacloud.models.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    List<Keyword> findAllByTeamIdAndKeywordIn(Long teamId, List<String> keywords);

    Page<Keyword> findAllByTeamIdOrderByIdDesc(Long teamId, Pageable pageable);


    @Query(value = """
            SELECT k
                FROM Keyword k
                LEFT JOIN FETCH k.keywordOrders ko
            WHERE k.teamId = :teamId
                AND ko.deleted = false
            ORDER BY ko.order ASC
    """)
    List<Keyword> findOnGoingKeyword(Long teamId);

    @EntityGraph(attributePaths = {"keywordHistories"})
    List<Keyword> findAllByTeamId(Long teamId);
}
