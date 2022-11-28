package com.brandlogs.suparmarket.controller;

import com.brandlogs.suparmarket.service.ItemsService;
import com.brandlogs.suparmarket.wrapper.GeneralResponseWrapper;
import com.brandlogs.suparmarket.wrapper.ItemDao;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/brandlogs")
@AllArgsConstructor
public class ItemController {
    private ItemsService itemsService;

    //create item
    @PostMapping("/item")
    private ResponseEntity<GeneralResponseWrapper> createItem(@Validated @RequestBody ItemDao itemDao){
        GeneralResponseWrapper generalResponseWrapper=itemsService.createItem(itemDao);
        return ResponseEntity.ok(generalResponseWrapper);
    }

    //view items
    @GetMapping("/items")
    private ResponseEntity<GeneralResponseWrapper> viewItems(){
        GeneralResponseWrapper generalResponseWrapper=itemsService.listAllItems();
        return ResponseEntity.ok(generalResponseWrapper);
    }

    //view one item
    @GetMapping("/item/{itemId}")
    private ResponseEntity<GeneralResponseWrapper> viewOneItem(@Valid @PathVariable Long itemId){
        GeneralResponseWrapper generalResponseWrapper=itemsService.findIndividualItem(itemId);
        return ResponseEntity.ok(generalResponseWrapper);
    }

    //delete item
    @DeleteMapping("/item/{itemId}")
    private ResponseEntity<GeneralResponseWrapper> deleteItem(@Valid @PathVariable Long itemId){
        GeneralResponseWrapper generalResponseWrapper=itemsService.deleteItem(itemId);
        return ResponseEntity.ok(generalResponseWrapper);
    }

    //update item
    @PutMapping("/item/{itemId}")
    private ResponseEntity<GeneralResponseWrapper> updateItem(@Valid @PathVariable Long itemId,@RequestBody ItemDao itemDao){
        GeneralResponseWrapper generalResponseWrapper=itemsService.updateItem(itemId,itemDao);
        return ResponseEntity.ok(generalResponseWrapper);
    }

    //receive item
    @PutMapping("/item/{itemId}/{itemCondition}")
    private ResponseEntity<GeneralResponseWrapper> receiveItem(@Valid @PathVariable Long itemId, @PathVariable String itemCondition){
        GeneralResponseWrapper generalResponseWrapper=itemsService.receiveItem(itemId,itemCondition);
        return ResponseEntity.ok(generalResponseWrapper);
    }

    //release item
    @PutMapping("/item/release/{itemId}")
    private ResponseEntity<GeneralResponseWrapper> releaseItem(@Valid @PathVariable Long itemId){
        GeneralResponseWrapper generalResponseWrapper=itemsService.releaseItem(itemId);
        return ResponseEntity.ok(generalResponseWrapper);
    }
    //return spoilt item
    @PutMapping("/items/return")
    private ResponseEntity<GeneralResponseWrapper> returntems(){
        GeneralResponseWrapper generalResponseWrapper=itemsService.returnItem();
        return ResponseEntity.ok(generalResponseWrapper);
    }

    //list items created today
    @GetMapping("/items/created-today")
    private ResponseEntity<GeneralResponseWrapper> viewItemsCreatedToday(){
        GeneralResponseWrapper generalResponseWrapper=itemsService.findItemsCreatedToday();
        return ResponseEntity.ok(generalResponseWrapper);
    }

    //list items received from vendors in last week
    @GetMapping("/items/received-last-week")
    private ResponseEntity<GeneralResponseWrapper> viewItemsReceivedLastWeek(){
        GeneralResponseWrapper generalResponseWrapper=itemsService.findItemsReceivedLastWeek();
        return ResponseEntity.ok(generalResponseWrapper);
    }

    //list spoilt items returned to vendors last month
    @GetMapping("/items/returnrd-last-month")
    private ResponseEntity<GeneralResponseWrapper> viewItemsReturnedLastMonth(){
        GeneralResponseWrapper generalResponseWrapper=itemsService.findItemsReturnedLastMonth();
        return ResponseEntity.ok(generalResponseWrapper);
    }

    //list items released today
    @GetMapping("/items/released-today")
    private ResponseEntity<GeneralResponseWrapper> viewItemsReleasedToday(){
        GeneralResponseWrapper generalResponseWrapper=itemsService.findItemsReleasedToday();
        return ResponseEntity.ok(generalResponseWrapper);
    }


}
