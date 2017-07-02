package com.refix.main.repository;


import com.refix.main.entity.PartUse;
import com.refix.main.entity.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartUseRepo extends JpaRepository<PartUse, Long> {

    @Query(value = "SELECT * FROM PART_USE partUse WHERE partUse.part_id = :partID AND partUse.service_order_id = :orderID", nativeQuery = true)
    PartUse findByPartIdAndServiceOrder(@Param("partID") long partId, @Param("orderID") ServiceOrder serviceOrder);

    @Query(value = "SELECT * FROM PART_USE partUse WHERE partUse.service_order_id = :orderID", nativeQuery = true)
    List<PartUse> findPartUsesOfOrder(@Param("orderID") long orderId);
}
