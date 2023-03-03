package com.basilisk.service;

import com.basilisk.dao.DeliveryRepository;
import com.basilisk.dto.DeliveryGridDTO;
import com.basilisk.dto.UpsertDeliveryDTO;
import com.basilisk.entity.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public Page<DeliveryGridDTO> getDeliveryGrid(Integer pageNumber, String serachName){
        var pageable = PageRequest.of(pageNumber -1, 10, Sort.by("id"));
        var hasilGrid=deliveryRepository.findByName(serachName,pageable);
        return hasilGrid;
    }

    public void saveDelivery(UpsertDeliveryDTO dto){
        Delivery entity = new Delivery(dto.getId(),dto.getCompanyName(),dto.getPhone(),dto.getCost());
        deliveryRepository.save(entity);
    }

    public UpsertDeliveryDTO getUpdate(Long id) {
        var entity = deliveryRepository.findById(id).get();
        UpsertDeliveryDTO dto;
        dto = new UpsertDeliveryDTO(
                entity.getId(),
                entity.getCompanyName(),
                entity.getPhone(),
                entity.getCost()
        );
        return dto;
    }

    public Boolean isValidName(String name, Long id){
        var totalDelivery = deliveryRepository.countByNameAndId(name,id);
        if (totalDelivery >= 1){
            return false;
        }
        return true;
    }

    public Long delete(long id){
        deliveryRepository.deleteById(id);
        return delete(id);
    }
}
