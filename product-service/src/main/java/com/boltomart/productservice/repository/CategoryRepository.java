package com.boltomart.productservice.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.boltomart.productservice.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	@Query(value = "SELECT * FROM category where status <> 40;", nativeQuery = true)
	List<Category> getAllCategories();

	@Query(value = "SELECT * FROM category where id = :id and status <> 40", nativeQuery = true)
	Optional<Category> findByCategoryId(@Param("id")Long id);
}
