## Statistics Java Code Challenge
## REST API with Spring Boot, H2, JPA and Hibernate

### Steps to Setup

**1. Clone the application**
```bash
https://github.com/fegeorge/xe-code-challenge.git
```
**2. Run the app using maven**

```bash
mvn spring-boot:run
```
The app will start running at <http://localhost:8080>.

### Explore Rest APIs

The app defines following CRUD APIs.

    GET /api/ads/search with parameters q=searchText, page=page number and size=number of results per page
    
    Example: http://localhost:8080/api/ads/search?q=Marousi&page=0&size=10
    
    GET /api/ads/{id}
    
    Example: http://localhost:8080/api/ads/9d6f45d6-1049-4c62-aa6b-6787792176b5?isSearch=true

### Statistics
**Statistics are stored in H2 DB table AD_ALL_STATS.**
***In order to view the generated statistics connect to http://localhost:8080/console using as JDBC URL the following: jdbc:h2:mem:xe***

```bash
Column clicks_count holds the number of times ad details have been 
viewed(in case an ad has been clicked directly from a url this counter is not updated).
```

```bash
Column page_count holds the number of times an ad has been displayed in a page of search results.
```
```bash
Column search_count holds the number of times an ad has appeared in a search.
```