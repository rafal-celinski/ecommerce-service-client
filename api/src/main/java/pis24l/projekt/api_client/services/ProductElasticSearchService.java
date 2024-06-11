package pis24l.projekt.api_client.services;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.repositories.elastic.ProductSearchRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductElasticSearchService {

    private final ProductSearchRepository productSearchRepository;
    private final ElasticsearchRestTemplate elasticsearchTemplate;

    @Autowired
    public ProductElasticSearchService(ProductSearchRepository productSearchRepository, ElasticsearchRestTemplate elasticsearchTemplate) {
        this.productSearchRepository = productSearchRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public Page<Product> searchProducts(String search, String category, String subcategory, BigDecimal minPrice, BigDecimal maxPrice, String location, Pageable pageable) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (search != null && !search.isEmpty()) {
            boolQuery.must(QueryBuilders.multiMatchQuery(search)
                    .field("title")
                    .field("description"));
        }

        if (category != null && !category.isEmpty()) {
            boolQuery.must(QueryBuilders.termQuery("category.keyword", category));
        }

        if (subcategory != null && !subcategory.isEmpty()) {
            boolQuery.must(QueryBuilders.termQuery("subcategory.keyword", subcategory));
        }

        if (minPrice != null || maxPrice != null) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("price");
            if (minPrice != null) {
                rangeQuery.gte(minPrice);
            }
            if (maxPrice != null) {
                rangeQuery.lte(maxPrice);
            }
            boolQuery.must(rangeQuery);
        }

        if (location != null && !location.isEmpty()) {
            boolQuery.must(QueryBuilders.termQuery("location.keyword", location));
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
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

