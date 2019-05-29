package gr.xe.java.codechallenge.statistics.ads.infrastructure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchQueryRepository extends CrudRepository<SearchQuery, Long> {

    Optional<SearchQuery> findSearchQueryByQueryText(String text);
}
