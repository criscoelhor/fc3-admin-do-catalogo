package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import com.fullcycle.admin.catalogo.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

public interface ICategoryGateway {
    Category create(Category category);

    Category update(Category category);
    void deleteById(CategoryID id);
    Optional<Category> findById(CategoryID id);
    Pagination<Category> findAll(SearchQuery query);

    List<CategoryID> existsByIds(Iterable<CategoryID> ids);
}
