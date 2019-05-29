package gr.xe.java.codechallenge.statistics.ads.api;

import gr.xe.java.codechallenge.statistics.ads.domain.InvalidRequestException;
import gr.xe.java.codechallenge.statistics.ads.domain.RetrieveService;
import gr.xe.java.codechallenge.statistics.ads.infrastructure.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/ads/{id}")
public class RetrieveAdController {

    private RetrieveService retrieveService;

    @Autowired
    public RetrieveAdController(RetrieveService retrieveService) {
        this.retrieveService = retrieveService;
    }

    @GetMapping
    public ResponseEntity getAdDetails(@PathVariable(value = "id") String adId, @RequestParam(name = "isSearch", required = false) Boolean isSearch) {
        try {
            Optional<Ad> foundAd = retrieveService.searchForSpecificAd(adId, isSearch == null ? false: isSearch);

            if (!foundAd.isPresent())
                return responseWith(foundAd, NOT_FOUND);

            return responseWith(foundAd, OK);
        } catch (InvalidRequestException e) {
            return errorResponse(e);
        }
    }

    private ResponseEntity responseWith(Optional<Ad> ad, HttpStatus statusCode) {
        return new ResponseEntity<>(
            ad,
            null,
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
