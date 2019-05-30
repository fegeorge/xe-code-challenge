package gr.xe.java.codechallenge.statistics.stats.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "adAllStats")
public class AdAllStats {

    @Id
    @GeneratedValue
    protected long id;

    @Column(name = "adId")
    private String adId;

    @Column(name = "pageCount")
    private int pageCount;

    @Column(name = "clicksCount")
    private int clicksCount;

    @Column(name = "searchCount")
    private int searchCount;

    public AdAllStats() {
    }

    public AdAllStats(String adId, int pageCount, int clicksCount) {
        this.adId = adId;
        this.pageCount = pageCount;
        this.clicksCount = clicksCount;
    }

    public AdAllStats(String adId, int pageCount, int clicksCount, int searchCount) {
        this.adId = adId;
        this.pageCount = pageCount;
        this.clicksCount = clicksCount;
        this.searchCount = searchCount;
    }

    public AdAllStats(long id, String adId, int pageCount, int clicksCount, int searchCount) {
        this.id = id;
        this.adId = adId;
        this.pageCount = pageCount;
        this.clicksCount = clicksCount;
        this.searchCount = searchCount;
    }

    public AdAllStats(String adId, int clicksCount) {
        this.adId = adId;
        this.clicksCount = clicksCount;
    }

    public long getId() {
        return id;
    }

    public String getAdId() {
        return adId;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getClicksCount() {
        return clicksCount;
    }

    public void setClicksCount(int clicksCount) {
        this.clicksCount = clicksCount;
    }

    public int getSearchCount() {
        return searchCount;
    }
}
