package dev.applaudostudios.examples.finalassignment.model.orders;

import dev.applaudostudios.examples.finalassignment.common.dto.ItemDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class SimpleOrder {
    @Setter(value = AccessLevel.NONE)
    protected ItemOrderList orderList;
    private UUID reservationCode; //Para reservar productos
    private UUID verificationCode; //Para verificar método de pago
    private UUID dispatchCode; //Para verificar dirección de entrega
    private UUID orderCode; //Sería como el id de la orden

    public SimpleOrder(List<ItemDto> itemList) {
        orderList = new ItemOrderList(itemList);
    }

    public boolean reserveOrder() {
        new ReservedOrderState().handle(this);
        return this.reservationCode != null;
    }

    public boolean validateOrder(UUID paymentId) {
        VerifiedOrderState orderState = new VerifiedOrderState();
        orderState.setPaymentId(paymentId);
        orderState.handle(this);
        return this.verificationCode != null;
    }

    public boolean generateOrder() {
        new GeneratedOrderState().handle(this);
        return this.orderCode != null;
    }

    public boolean generateDispatchCode(String postalCode) {
        DispatchOrderState orderState = new DispatchOrderState();
        orderState.setPostalCode(postalCode);
        orderState.handle(this);
        return this.dispatchCode != null;
    }

    public static class ItemOrderList {
        private final List<ItemDto> listOfItems;

        private ItemOrderList(List<ItemDto> listOfItems) {
            this.listOfItems = new ArrayList<>(listOfItems);
        }

        public final Double getTotal() {
            double total = 0.0;
            for (ItemDto item : listOfItems) {
                total += item.getUnitPrice() * item.getUnits();
            }
            return total;
        }

        public final void saveItem(ItemDto item) {
            Optional<ItemDto> foundItem =
                    listOfItems.stream()
                            .filter(itemList -> Objects.equals(itemList, item))
                            .findFirst();
            if (foundItem.isPresent()) {
                //int units = foundItem.get().getUnits() + item.getUnits();
                int units = item.getUnits();
                foundItem.get().setUnits(units);
                if (units <= 0) {
                    deleteItem(foundItem.get());
                }
            } else {
                listOfItems.add(item);
            }
        }

        public final boolean deleteItem(ItemDto item) {
            Optional<ItemDto> foundItem =
                    listOfItems.stream()
                            .filter(itemList -> Objects.equals(itemList, item))
                            .findFirst();
            if (foundItem.isPresent()) {
                listOfItems.remove(foundItem.get());
                return true;
            } else {
                return false;
            }
        }

        public final List<ItemDto> getListOfItems() {
            return Collections.unmodifiableList(this.listOfItems);
        }
    }
}
