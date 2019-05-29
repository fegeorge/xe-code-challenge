package gr.xe.java.codechallenge.statistics.ads.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SearchQuery {

    @Id
    @GeneratedValue
    private long searchId;
    @Column
    private String queryText;

    public SearchQuery() {
    }

    public SearchQuery(String queryText) {
        this.queryText = queryText;
    }

    public SearchQuery(long searchId, String query) {
        this.searchId = searchId;
        this.queryText = query;
    }

    public long getSearchId() {
        return searchId;
    }
}
