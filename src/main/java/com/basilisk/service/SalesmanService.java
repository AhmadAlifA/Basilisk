package com.basilisk.service;

import com.basilisk.dao.OrderRepository;
import com.basilisk.dao.RegionRepository;
import com.basilisk.dao.SalesmanRepository;
import com.basilisk.entity.Region;
import com.basilisk.utility.Dropdown;
import com.basilisk.dto.*;
import com.basilisk.entity.Salesman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesmanService {

    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Page<SalesmanGridDTO> getSalesmanGrid(Integer pageNumber, String employeeNumber, String fullName, String employeeLevel, String superriorFullName) {
        var pageable = PageRequest.of(pageNumber -1, 10, Sort.by("id"));
        var hasilGrid=salesmanRepository.findByAll(employeeNumber, fullName, employeeLevel, superriorFullName, pageable);
        return hasilGrid;
    }

    public Page<RegionGridDTO> getDetailGrid(Integer pageNumber, String employeeNumber, String city){
        var pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id"));
        var grid=regionRepository.findBySalesmanNumber(employeeNumber, city,pageable);
        return grid;
    }

    public SalesmanHeaderDTO getHeader(String employeeNumber){
        var entity = salesmanRepository.findById(employeeNumber).get();
        var dto = new SalesmanHeaderDTO(
                entity.getEmployeeNumber(),
                String.format("%s %s", entity.getFirstName(), entity.getLastName())
        );
        return dto;
    }

    public void saveSalesman(UpdateSalesmanDTO dto){
        Salesman entity = new Salesman(
                dto.getEmployeeNumber(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getLevel(),
                dto.getBirthDate(),
                dto.getHiredDate(),
                dto.getAddress(),
                dto.getCity(),
                dto.getPhone(),
                null
        );
        salesmanRepository.save(entity);

    }

    public UpdateSalesmanDTO getUpdate(String employeeNumber){
        var entity = salesmanRepository.findById(employeeNumber).get();
        UpdateSalesmanDTO dto= new UpdateSalesmanDTO(
                entity.getEmployeeNumber(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getLevel(),
                entity.getBirthDate(),
                entity.getHiredDate(),
                entity.getAddress(),
                entity.getCity(),
                entity.getPhone(),
                entity.getSuperiorEmployeeNumber());
        return dto;
    }

    public Boolean isValidNumber(String employeeNumber){
        var totalSalesman = salesmanRepository.countSalesmanByEmployeeNumber(employeeNumber);
        if (totalSalesman > 0){
            return true;
        }
        return false;
    }

    public List<Dropdown> getSuperiorDropdown() {
        List<Dropdown> Dropdowns = salesmanRepository.findAllOrderByFirstName();
        return Dropdowns;
    }

    public void delete(String employeeNumber) {
        salesmanRepository.deleteById(employeeNumber);
    }

    public Long dependentOrders(String employeeNumber) {
        long totalDependentOrders = orderRepository.countByEmployeeNumber(employeeNumber);
        return totalDependentOrders;
    }

    public Long dependentSubordinates(String superiorEmployeeNumber) {
        long totalDependentSubordinates = salesmanRepository.countBySuperiorEmployeeNumber(superiorEmployeeNumber);
        return totalDependentSubordinates;
    }

    public SalesmanHeaderDTO getSalesmanHeader(String employeeNumber) {
        Optional<Salesman> nullableEntity = salesmanRepository.findById(employeeNumber);
        Salesman entity = nullableEntity.get();
        SalesmanHeaderDTO salesmanDTO = new SalesmanHeaderDTO(
                employeeNumber,
                String.format("%s %s", entity.getFirstName(), entity.getLastName())
        );
        return salesmanDTO;
    }

    public List<Dropdown> getRegionDropdown() {
        List<Dropdown> regionDropdown = regionRepository.findAllOrderByCity();
        return regionDropdown;
    }

    public void assignRegion(AssignRegionDTO dto) {
        var nullableSalesman = salesmanRepository.findById(dto.getSalesmanEmployeeNumber()).get();
        Salesman salesman = nullableSalesman;
        var nullableRegion = regionRepository.findById(dto.getRegionId()).get();
        Region region = nullableRegion;
        salesman.getRegion().add(region);
        salesmanRepository.save(salesman);
    }

    public void detachRegionSalesman(Long regionId, String employeeNumber) {
        var nullableSalesman = salesmanRepository.findById(employeeNumber).get();
        Salesman salesman = nullableSalesman;
        var nullableRegion = regionRepository.findById(regionId).get();
        Region region = nullableRegion;
        region.getSalesmen().remove(salesman);
        regionRepository.save(region);
    }
}
