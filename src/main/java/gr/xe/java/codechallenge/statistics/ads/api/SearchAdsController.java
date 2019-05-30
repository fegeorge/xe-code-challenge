package gr.xe.java.codechallenge.statistics.ads.api;

import gr.xe.java.codechallenge.statistics.ads.domain.exception.InvalidRequestException;
import gr.xe.java.codechallenge.statistics.ads.domain.SearchService;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.entity.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RestController
@RequestMapping("/api/ads/search")
public class SearchAdsController {

    private SearchService searchService;

    @Autowired
    public SearchAdsController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity search(
        @RequestParam("q") String query,
        @RequestParam("page") int page,
        @RequestParam("size") int size
    ) {
        try {
            Page<Ad> ads = searchService.searchForAds(query, page, size);

            if (ads.isEmpty())
                return responseWith(ads, NO_CONTENT);

            return responseWith(ads, OK);
        } catch (InvalidRequestException e) {
            return errorResponse(e);
        }
    }

    private ResponseEntity responseWith(Page<Ad> ads, HttpStatus statusCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON_UTF8);

        return new ResponseEntity<>(
            ads,
            headers,
            statusCode
        );
    }

    private ResponseEntity errorResponse(InvalidRequestException e) {
        return new ResponseEntity<>(
            e.getMessage(),
            null,
            BAD_REQUEST
        );
    }
}
