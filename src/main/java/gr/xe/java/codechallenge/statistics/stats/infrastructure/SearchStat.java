package gr.xe.java.codechallenge.statistics.stats.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "searchStat")
public class SearchStat {

    @Id
    @GeneratedValue
    protected long id;

    @Column(name = "adId")
    private String adId;

    @Column(name = "searchId")
    private long searchId;

    public SearchStat() {
    }

    public SearchStat(String adId, long searchId) {
        this.adId = adId;
        this.searchId = searchId;
    }

    public long getId() {
        return id;
    }

    public String getAdId() {
        return adId;
    }

    public long getSearchId() {
        return searchId;
    }
}
