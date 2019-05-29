package gr.xe.java.codechallenge.statistics.ads.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdsPagingRepository extends PagingAndSortingRepository<Ad, String> {

    Page<Ad> findByTextIgnoreCaseContaining(String text, Pageable page);
}
