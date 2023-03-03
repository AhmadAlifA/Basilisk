package com.basilisk.service;

import com.basilisk.dao.RegionRepository;
import com.basilisk.dao.SalesmanRepository;
import com.basilisk.dto.*;
import com.basilisk.entity.Region;
import com.basilisk.entity.Salesman;
import com.basilisk.utility.Dropdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private SalesmanRepository salesmanRepository;

    public Page<RegionGridDTO> getRegionGrid(Integer pageNumber, String searchCity){
        var pageable = PageRequest.of(pageNumber -1, 10, Sort.by("id"));
        var hasilGrid=regionRepository.findByName(searchCity,pageable);
        return hasilGrid;
    }

//    SalesmanGrid detail dari region menu.
    public Page<SalesmanGridDTO> getDetailGrid(Integer pageNumber, Long id,
    String employeeNumber, String fullName, String employeeLevel, String superiorFullName){
        var pageable = PageRequest.of(pageNumber -1, 10, Sort.by("employeeNumber"));
        var grid=salesmanRepository.findByRegionId(id, employeeNumber, fullName, employeeLevel, superiorFullName, pageable);
        return grid;
    }

    public RegionHeaderDTO getHeader(Long id){
        var entity = regionRepository.findById(id).get();
        var dto = new RegionHeaderDTO(
                entity.getId(),
                entity.getCity(),
                entity.getRemark()
        );
        return dto;
    }

    public void saveRegion(UpsertRegionDTO dto){
        Region entity = new Region(dto.getId(),dto.getCity(),dto.getRemark());
        regionRepository.save(entity);

    }

    public UpsertRegionDTO getUpdate(Long id){
        var entity = regionRepository.findById(id).get();
        UpsertRegionDTO dto= new UpsertRegionDTO(
                entity.getId(),
                entity.getCity(),
                entity.getRemark()
        );
        return dto;
    }

    public Boolean isValidCity(String city, Long id){
        var totalCity = regionRepository.countByCityAndId(city,id);
        if (totalCity >= 1){
            return false;
        }
        return true;
    }

    public void delete(long id) {
        regionRepository.deleteById(id);
    }

    public Boolean checkExistingRegionSalesman(Long regionId, String employeeNumber) {
        Long totalExistingRegionSalesman = regionRepository.count(employeeNumber, regionId);
        return (totalExistingRegionSalesman > 0) ? true : false;
    }

    public void assignSalesman(AssignSalesmanDTO dto) {
        var nullableSalesman = salesmanRepository.findById(dto.getSalesmanEmployeeNumber()).get();
        Salesman salesman = nullableSalesman;
        var nullableRegion = regionRepository.findById(dto.getRegionId()).get();
        Region region = nullableRegion;
        region.getSalesmen().add(salesman);
        regionRepository.save(region);
    }
    public void detachRegionSalesman(Long regionId, String employeeNumber) {
        var nullableSalesman = salesmanRepository.findById(employeeNumber).get();
        Salesman salesman = nullableSalesman;
        var nullableRegion = regionRepository.findById(regionId).get();
        Region region = nullableRegion;
        region.getSalesmen().remove(salesman);
        regionRepository.save(region);
    }
    public List<Dropdown> getSalesmanDropdown() {
        List<Dropdown> salesmanDropdown = salesmanRepository.findAllOrderByFirstName();
        return salesmanDropdown;
    }
}
