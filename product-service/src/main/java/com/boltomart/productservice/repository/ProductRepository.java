package com.boltomart.productservice.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.boltomart.productservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "SELECT * FROM products where status <> 40;", nativeQuery = true)
	List<Product> findAllProducts();
	public List<Product> findByVendorId(Integer vendorId) throws Exception;
	

	@Query(value = "SELECT p.id, p.SKU, p.vendor_id, v.shop_name, p.product_name, p.stock, p.size, p.status, " +
	        "ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(a.longitude, a.latitude)) AS distance " +
	        "FROM products p " +
	        "JOIN vendor v ON p.vendor_id = v.id " +
	        "JOIN address a ON v.address_id = a.id " +
	        "JOIN category c ON p.category_id = c.id " +
	        "WHERE a.latitude BETWEEN :minLat AND :maxLat " +
	        "AND a.longitude BETWEEN :minLon AND :maxLon " +
	        "AND ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(a.longitude, a.latitude)) <= :radius " +
	        "AND (:productName IS NULL OR LOWER(p.product_name) LIKE LOWER(CONCAT('%', :productName, '%'))) " +
	        "AND (:shopName IS NULL OR LOWER(v.shop_name) LIKE LOWER(CONCAT('%', :shopName, '%'))) " +
	        "AND (:categoryName IS NULL OR LOWER(c.category_name) LIKE LOWER(CONCAT('%', :categoryName, '%'))) " +
	        "AND (:minPrice IS NULL OR p.unit_price >= :minPrice) " +
	        "AND (:maxPrice IS NULL OR p.unit_price <= :maxPrice)", nativeQuery = true)
	List<Object[]> searchProductsWithDistance(
	        @Param("longitude") Double longitude,
	        @Param("latitude") Double latitude,
	        @Param("minLat") Double minLat,
	        @Param("maxLat") Double maxLat,
	        @Param("minLon") Double minLon,
	        @Param("maxLon") Double maxLon,
	        @Param("radius") Double radius,
	        @Param("productName") String productName,
	        @Param("shopName") String shopName,
	        @Param("categoryName") String categoryName,
	        @Param("minPrice") BigDecimal minPrice,
	        @Param("maxPrice") BigDecimal maxPrice);

	@Query(value = "SELECT p.id, p.SKU, p.vendor_id, v.shop_name, p.product_name, p.stock, p.size, p.status, " +
	        "ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(a.longitude, a.latitude)) AS distance " +
	        "FROM products p " +
	        "JOIN vendor v ON p.vendor_id = v.id " +
	        "JOIN address a ON v.address_id = a.id " +
	        "JOIN category c ON p.category_id = c.id " +
	        "WHERE c.id = :categoryId " +
	        "AND a.latitude BETWEEN :minLat AND :maxLat " +
	        "AND a.longitude BETWEEN :minLon AND :maxLon " +
	        "AND ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(a.longitude, a.latitude)) <= :radius", nativeQuery = true)
	List<Object[]> findProductsByCategoryAndLocation(
	        @Param("longitude") Double longitude,
	        @Param("latitude") Double latitude,
	        @Param("minLat") Double minLat,
	        @Param("maxLat") Double maxLat,
	        @Param("minLon") Double minLon,
	        @Param("maxLon") Double maxLon,
	        @Param("radius") Double radius,
	        @Param("categoryId") Long categoryId);


	
}