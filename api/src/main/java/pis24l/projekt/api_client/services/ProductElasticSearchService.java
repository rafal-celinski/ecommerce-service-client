package pis24l.projekt.api_client.services;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import pis24l.projekt.api_client.models.Product;

import java.math.BigDecimal;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class ProductElasticSearchService {

    private final ElasticsearchRestTemplate elasticsearchTemplate;

    @Autowired
    public ProductElasticSearchService(ElasticsearchRestTemplate elasticsearchTemplate) {

        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public Page<Product> searchProducts(String search, String category, String subcategory, BigDecimal minPrice, BigDecimal maxPrice, String location, Pageable pageable) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (search != null && !search.isEmpty()) {
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(search, "title", "description"));
        }

        if (category != null && !category.isEmpty()) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("category.keyword", category));
        }

        if (subcategory != null && !subcategory.isEmpty()) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("subcategory.keyword", subcategory));
        }

        if (minPrice != null || maxPrice != null) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("price");
            if (minPrice != null) {
                rangeQuery.gte(minPrice);
            }
            if (maxPrice != null) {
                rangeQuery.lte(maxPrice);
            }
            boolQueryBuilder.filter(rangeQuery);
        }

        if (location != null && !location.isEmpty()) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("location.keyword", location));
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageable)
                .build();
        SearchHits<Product> searchHits = elasticsearchTemplate.search(searchQuery, Product.class);

        List<Product> products = searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        long totalHits = searchHits.getTotalHits();

        return new PageImpl<>(products, pageable, totalHits);
    }
}
