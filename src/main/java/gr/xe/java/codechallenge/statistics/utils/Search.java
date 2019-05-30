package gr.xe.java.codechallenge.statistics.utils;

import gr.xe.java.codechallenge.statistics.ads.infrastructure.entity.Ad;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Class for generating Ad data
 */
public class Search {
    protected String searchId;
    protected Ad[] results;
    protected String searchText;
    protected Date createdAt;

    /**
     * Gets the search created date.
     *
     * @return the date of the search
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the search id.
     *
     * @return the search id
     */
    public String getSearchId() {
        return searchId;
    }

    /**
     * Gets the search results.
     *
     * @return an array of ads
     */
    public Ad[] getSearchResults() {
        return results;
    }

    /**
     * Gets the search text.
     *
     * @return the search text
     */
    public String getSearchText() {
        return searchText;
    }

    public Search() {
        Random rnd = new Random();

        int numberOfResults = Math.max(50, rnd.nextInt(500));
        results = new Ad[numberOfResults];

        for (int i = 0; i < numberOfResults; i++) {
            results[i] = new Ad();
        }

        searchId = UUID.randomUUID().toString();
        createdAt = Calendar.getInstance().getTime();
    }
}