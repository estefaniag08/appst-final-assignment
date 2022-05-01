package dev.appladostudios.examples.finalassignment.model.orders;

import dev.appladostudios.examples.finalassignment.common.ItemDto;

import java.util.*;

public class Order{
    private final List<Observer> observerList;

    protected ItemOrderList orderList;
    private UUID reservationCode;
    private UUID verificationCode;
    private UUID orderCode;

    public Order(List<ItemDto> itemList){
        orderList = new ItemOrderList(itemList);
        observerList = new LinkedList<>();
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



    public UUID getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(UUID reservationCode) {
        this.reservationCode = reservationCode;
    }

    public UUID getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(UUID verificationCode) {
        this.verificationCode = verificationCode;
    }

    public UUID getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(UUID orderCode) {
        this.orderCode = orderCode;
    }

    public ItemOrderList getOrderList(){
        return this.orderList;
    }

    public class ItemOrderList{
        private final List<ItemDto> listOfItems;

        private ItemOrderList(List<ItemDto> listOfItems){
            this.listOfItems = new ArrayList<>(listOfItems);
        }

        public final Double getTotal(){
            double total = 0.0;
            for(ItemDto item: listOfItems){
                total += item.getPrice()* item.getUnits();
            }
            return total;
        }

        public final Iterator<ItemDto> getListOfItems(){
            return Collections.unmodifiableList(this.listOfItems).iterator();
        }
    }
}
