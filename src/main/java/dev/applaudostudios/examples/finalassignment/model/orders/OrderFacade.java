package dev.applaudostudios.examples.finalassignment.model.orders;

import dev.applaudostudios.examples.finalassignment.common.dto.ItemDto;
import dev.applaudostudios.examples.finalassignment.common.dto.OrderDto;
import dev.applaudostudios.examples.finalassignment.common.Mappable;

import java.util.List;

public class OrderFacade implements Mappable<SimpleOrder, OrderDto> {

    public OrderDto initializeOrder(List<ItemDto> listOfItems){
        SimpleOrder orderModel = new SimpleOrder(listOfItems);
        orderModel.generateOrder();
        return mapToDto(orderModel);
    }

    public OrderDto saveItemToOrder(OrderDto orderDto, ItemDto itemDto){
        SimpleOrder orderModel = mapToEntity(orderDto);
        orderModel.orderList.saveItem(itemDto);
        if(orderModel.reserveOrder()){
            orderDto.setReservationCode(orderModel.getReservationCode());
            orderDto.setOrderItems(orderModel.orderList.getListOfItems());
            orderDto.setOrderTotal(orderModel.orderList.getTotal());
        }
        return orderDto;
    }

    public OrderDto deleteItemFromOrder(OrderDto orderDto, ItemDto itemDto){
        SimpleOrder orderModel = mapToEntity(orderDto);
        orderModel.orderList.deleteItem(itemDto);
        if(orderModel.reserveOrder()){
            orderDto.setReservationCode(orderModel.getReservationCode());
            orderDto.setOrderItems(orderModel.orderList.getListOfItems());
            orderDto.setOrderTotal(orderModel.orderList.getTotal());
        }
        return orderDto;
    }

    public OrderDto reserveOrder(OrderDto orderDto){
        SimpleOrder orderModel = mapToEntity(orderDto);

        if(orderModel.reserveOrder()){
            orderDto.setReservationCode(orderModel.getReservationCode());
        }
        return orderDto;
    }
    public OrderDto verifyOrder(OrderDto orderDto){
        SimpleOrder orderModel = mapToEntity(orderDto);
        if(orderModel.validateOrder(orderDto.getPayment().getPaymentMethodCode())){
            orderDto.setVerificationCode(orderModel.getVerificationCode());
        }
        return orderDto;
    }

    public OrderDto generateDispatchCode(OrderDto orderDto){
        SimpleOrder orderModel = mapToEntity(orderDto);
        if(orderModel.generateDispatchCode(orderDto.getAddress().getPostalCode())){
            orderDto.setDispatchCode(orderModel.getDispatchCode());
        }
        return orderDto;
    }
    @Override
    public OrderDto mapToDto(SimpleOrder entity) {
        return new OrderDto(0L,
                entity.orderList.getListOfItems(),
                entity.getReservationCode(),
                entity.getVerificationCode(),
                entity.getDispatchCode(),
                entity.getOrderCode(),
                entity.getOrderList().getTotal(),
                null,
                null,
                null
                );
    }

    @Override
    public SimpleOrder mapToEntity(OrderDto entity) {
        SimpleOrder orderModel = new SimpleOrder(entity.getOrderItems());
        orderModel.setOrderCode(entity.getOrderCode());
        orderModel.setReservationCode(entity.getReservationCode());
        orderModel.setVerificationCode(entity.getVerificationCode());
        orderModel.setDispatchCode(entity.getDispatchCode());

        return orderModel;
    }
}
