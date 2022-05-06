package dev.appladostudios.examples.finalassignment.model.orders;

import dev.appladostudios.examples.finalassignment.common.dto.ItemDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class SimpleOrder {
    @Setter(value = AccessLevel.NONE)
    protected ItemOrderList orderList;
    private UUID reservationCode;
    private UUID verificationCode;
    private UUID orderCode;

    public SimpleOrder(List<ItemDto> itemList){
        orderList = new ItemOrderList(itemList);
    }

    public boolean reserveOrder(){
        new ReservedOrderState().handle(this);
        return true;
    }

    public boolean validateOrder(){
        new VerifiedOrderState().handle(this);
        return true;
    }

    public boolean generateOrder(){
        new GeneratedOrderState().handle(this);
        return true;
    }

    public class ItemOrderList{
        private final List<ItemDto> listOfItems;

        private ItemOrderList(List<ItemDto> listOfItems){
            this.listOfItems = new ArrayList<>(listOfItems);
        }

        public final Double getTotal(){
            double total = 0.0;
            for(ItemDto item: listOfItems){
                total += item.getUnitPrice() * item.getUnits();
            }
            return total;
        }

        public final void saveItem(ItemDto item){
            Optional<ItemDto> foundItem =
                    listOfItems.stream()
                            .filter(itemList -> Objects.equals(itemList, item))
                            .findFirst();
            if(foundItem.isPresent()){
                int units = foundItem.get().getUnits() + item.getUnits();
                foundItem.get().setUnits(units);
                if(units <= 0){
                    deleteItem(foundItem.get());
                }
            } else {
                  listOfItems.add(item);
            }
        }

        public final boolean deleteItem(ItemDto item){
            Optional<ItemDto> foundItem =
                    listOfItems.stream()
                            .filter(itemList -> Objects.equals(itemList, item))
                            .findFirst();
            if(foundItem.isPresent()){
                listOfItems.remove(foundItem.get());
                return true;
            } else {
                return false;
            }
        }
        public final Iterator<ItemDto> getListOfItems(){
            return Collections.unmodifiableList(this.listOfItems).iterator();
        }
    }
}
