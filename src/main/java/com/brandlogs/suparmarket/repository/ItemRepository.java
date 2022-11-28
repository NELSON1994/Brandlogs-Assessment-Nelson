package com.brandlogs.suparmarket.repository;

import com.brandlogs.suparmarket.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAll();
    List<Item> findItemsByCreatedDate(Date date);
    List<Item> findItemsByDeletionState(Boolean b);

    Optional<Item> findByIdAndItemConditionAndAction(Long itemId,String Condition,String action);

    List<Item> findItemsByReleasedDateAndAction(Date date,String action);
    List<Item> findByItemCondition(String itemCondition);

    List<Item> findItemsByReceivedDateBetweenAndAction(Date date, Date date2, String action);

    List<Item> findItemsByReturnedDateBetweenAndAction(Date date, Date date2, String action);
}
