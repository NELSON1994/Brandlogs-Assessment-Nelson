package com.brandlogs.suparmarket.service;

import com.brandlogs.suparmarket.model.Item;
import com.brandlogs.suparmarket.repository.ItemRepository;
import com.brandlogs.suparmarket.wrapper.GeneralResponseWrapper;
import com.brandlogs.suparmarket.wrapper.ItemDao;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class ItemsService {

    private ItemRepository itemRepository;

    //create item
    public GeneralResponseWrapper createItem(ItemDao itemDao) {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        try {
            Item item = new Item();
            item.setItemName(itemDao.getItemName());
            item.setQuantity(itemDao.getQuantity());
            item.setVendorName(itemDao.getVendorName());
            item.setCreatedDate(new Date());
            item.setDeletionState(false);
            item.setAction("CREATE");
            itemRepository.save(item);
            generalResponseWrapper.setResponseCode(HttpStatus.CREATED.value());
            generalResponseWrapper.setMessage("Item created Successfully");
            generalResponseWrapper.setData(item);

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    //delete item(for audit, we dont delete it but just change the status to deleted true)
    public GeneralResponseWrapper deleteItem(Long itemId) {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        try {
            Optional<Item> item = itemRepository.findById(itemId);
            if (item.isPresent()) {
                Item item1 = item.get();
                item1.setDeletionState(true);
                item1.setAction("DELETE");
                itemRepository.save(item1);
                generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
                generalResponseWrapper.setMessage("Item deleted Successfully");
                generalResponseWrapper.setData(item1);
            } else {
                generalResponseWrapper.setResponseCode(HttpStatus.NOT_FOUND.value());
                generalResponseWrapper.setMessage("Item with ID : " + itemId + "Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    // getting individual Item
    public GeneralResponseWrapper findIndividualItem(Long itemId) {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        try {
            Optional<Item> item = itemRepository.findById(itemId);
            if (item.isPresent()) {
                Item item1 = item.get();
                generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
                generalResponseWrapper.setMessage("Item fetched Successfully");
                generalResponseWrapper.setData(item1);
            } else {
                generalResponseWrapper.setResponseCode(HttpStatus.NOT_FOUND.value());
                generalResponseWrapper.setMessage("Item with ID : " + itemId + "Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    //list all items  minus deleted items
    public GeneralResponseWrapper listAllItems() {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        try {
            List<Item> items = itemRepository.findItemsByDeletionState(false);
            generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
            generalResponseWrapper.setMessage("Item fetched Successfully");
            generalResponseWrapper.setData(items);

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    //update item
    public GeneralResponseWrapper updateItem(Long itemId,ItemDao itemDao) {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        try {
            Optional<Item> item = itemRepository.findById(itemId);
            if (item.isPresent()) {
                Item item1 = item.get();
                item1.setItemName(itemDao.getItemName());
                item1.setQuantity(itemDao.getQuantity());
                item1.setVendorName(itemDao.getVendorName());
                item1.setAction("UPDATE");
                itemRepository.save(item1);
                generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
                generalResponseWrapper.setMessage("Item updated Successfully");
                generalResponseWrapper.setData(item1);
            } else {
                generalResponseWrapper.setResponseCode(HttpStatus.NOT_FOUND.value());
                generalResponseWrapper.setMessage("Item with ID : " + itemId + "Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    //receive item(either BAD or GOOD)
    public GeneralResponseWrapper receiveItem(Long itemId,String itemCondition) {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        try {
            Optional<Item> item = itemRepository.findById(itemId);
            if (item.isPresent()) {
                Item item1 = item.get();
                item1.setItemCondition(itemCondition);
                item1.setAction("RECEIVE");
                item1.setReceivedDate(new Date());
                itemRepository.save(item1);
                generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
                generalResponseWrapper.setMessage("Item Received Successfully");
                generalResponseWrapper.setData(item1);
            } else {
                generalResponseWrapper.setResponseCode(HttpStatus.NOT_FOUND.value());
                generalResponseWrapper.setMessage("Item with ID : " + itemId + "Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    //release item(ALLOW JUST RELEASE ITEMS THAT ARE ON GOOD CONDITION)
    public GeneralResponseWrapper releaseItem(Long itemId) {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        try {
            Optional<Item> item = itemRepository.findByIdAndItemConditionAndAction(itemId,"GOOD","RECEIVE");
            if (item.isPresent()) {
                Item item1 = item.get();
                item1.setAction("RELEASE");
                item1.setReleasedDate(new Date());
                itemRepository.save(item1);
                generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
                generalResponseWrapper.setMessage("Item Released Successfully");
                generalResponseWrapper.setData(item1);
            } else {
                generalResponseWrapper.setResponseCode(HttpStatus.NOT_FOUND.value());
                generalResponseWrapper.setMessage("Item with ID : " + itemId + "Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    //return spoilt items
    public GeneralResponseWrapper returnItem() {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        try {
            List<Item> items = itemRepository.findByItemCondition("BAD");
            for(Item item:items){
                item.setReturnedDate(new Date());
                item.setAction("RETURN");
                itemRepository.save(item);
            }
                generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
                generalResponseWrapper.setMessage("Item Returned Successfully");
                generalResponseWrapper.setData(items);

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    // list items created today
    public GeneralResponseWrapper findItemsCreatedToday() {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        try {
            List<Item> items = itemRepository.findItemsByCreatedDate(new Date());
            generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
            generalResponseWrapper.setMessage("Item fetched Successfully");
            generalResponseWrapper.setData(items);

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }


    //list items received from vendors in the last week
    public GeneralResponseWrapper findItemsReceivedLastWeek() {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();

        Date date = new Date();

        // Convert Date to Calendar
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, -168);

        // Convert calendar back to Date
        Date oneweekAgo = c.getTime();

        try {
            List<Item> items = itemRepository.findItemsByReceivedDateBetweenAndAction(new Date(),oneweekAgo,"RECEIVE");
            generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
            generalResponseWrapper.setMessage("Received Items over a week fetched Successfully");
            generalResponseWrapper.setData(items);

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    // list items returned to vendors in the last month
    public GeneralResponseWrapper findItemsReturnedLastMonth() {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();

        Date date = new Date();

        // Convert Date to Calendar
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        Date oneMonthAgo = c.getTime();

        try {
            List<Item> items = itemRepository.findItemsByReturnedDateBetweenAndAction(oneMonthAgo,new Date(),"RETURN");
            generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
            generalResponseWrapper.setMessage("Returned Items over a month fetched Successfully");
            generalResponseWrapper.setData(items);

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    // list items released to the supa today
    public GeneralResponseWrapper findItemsReleasedToday() {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        try {
            List<Item> items = itemRepository.findItemsByReleasedDateAndAction(new Date(), "RELEASE");
            generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
            generalResponseWrapper.setMessage("Released Items fetched Successfully");
            generalResponseWrapper.setData(items);

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }
}
