package com.basilisk.dao;

import com.basilisk.dto.CategoryGridDTO;
import com.basilisk.dto.DropdownDTO;
import com.basilisk.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    //JPQL (JPA Querry Language)
    @Query("""
            SELECT new com.basilisk.dto.CategoryGridDTO(cat.id, cat.name, cat.description)
            FROM Category as cat
            WHERE cat.name LIKE %:cariNama%
            """)
    public Page<CategoryGridDTO> findByName(@Param("cariNama") String serachName, Pageable pageable);

    @Query("""
            SELECT COUNT(cat.id)
            FROM Category AS cat
            WHERE cat.name = :name AND cat.id != :id
            """)
    public  Long countByNameAndId(@Param("name") String name,@Param("id") Long id);

    @Query("""
            SELECT new com.basilisk.dto.DropdownDTO(cat.id,cat.name)
            FROM Category AS cat
            """)
    public List<DropdownDTO> getDropdown();

}
