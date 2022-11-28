package com.brandlogs.suparmarket.service;

import com.brandlogs.suparmarket.model.Item;
import com.brandlogs.suparmarket.repository.ItemRepository;
import com.brandlogs.suparmarket.wrapper.GeneralResponseWrapper;
import com.brandlogs.suparmarket.wrapper.ItemDao;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ItemsServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemsService itemService;

    ItemDao itemDao=new ItemDao("Computers",900, "Nelson Moses");

    Item item = new Item(1L,itemDao.getItemName(),itemDao.getQuantity(),"GOOD",false,null,null,new Date(),null,itemDao.getVendorName(),"CREATED");

    @Test
    void createItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        GeneralResponseWrapper generalResponseWrapper=itemService.createItem(itemDao);
        Item item= (Item) generalResponseWrapper.getData();
        assertNotNull(generalResponseWrapper);
        assertEquals(HttpStatus.CREATED.value(),generalResponseWrapper.getResponseCode());
        assertNotNull(((Item) generalResponseWrapper.getData()).getItemName());
        assertNotNull(((Item) generalResponseWrapper.getData()).getCreatedDate());
        assertEquals(item.getItemName(),900);
        assertEquals(generalResponseWrapper.getMessage(),"Item created Successfully");
    }

    @Test
    void deleteItem() {

    }

    @Test
    void findIndividualItem() {
    }

    @Test
    void listAllItems() {
    }

    @Test
    void updateItem() {
    }

    @Test
    void receiveItem() {
    }

    @Test
    void releaseItem() {
    }

    @Test
    void returnItem() {
    }

    @Test
    void findItemsCreatedToday() {
    }

    @Test
    void findItemsReceivedLastWeek() {
    }

    @Test
    void findItemsReturnedLastMonth() {
    }

    @Test
    void findItemsReleasedToday() {
    }
}