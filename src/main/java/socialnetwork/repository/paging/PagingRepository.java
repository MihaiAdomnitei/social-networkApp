package socialnetwork.repository.paging;

import socialnetwork.domain.Entity;
import socialnetwork.repository.Repository;

public interface PagingRepository<ID ,
        E extends Entity<ID>>
        extends Repository<ID, E> {

    Page<E> findAllOnPage(Pageable pageable, Long id);

}