package dev.applaudostudios.examples.finalassignment.service;

import dev.applaudostudios.examples.finalassignment.common.dto.*;
import dev.applaudostudios.examples.finalassignment.common.exception.service.CheckoutServiceException;
import dev.applaudostudios.examples.finalassignment.model.orders.OrderFacade;
import dev.applaudostudios.examples.finalassignment.persistence.model.Order;
import dev.applaudostudios.examples.finalassignment.persistence.query.OrderQueries;
import dev.applaudostudios.examples.finalassignment.persistence.query.UserQueries;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("When executing CheckoutService class")
public class CheckoutServiceTest {
    @Autowired
    CheckoutService checkoutService;

    @MockBean
    ProductService productService;
    @MockBean
    UserQueries userQueries;
    @MockBean
    OrderQueries orderQueries;
    @MockBean
    OrderFacade orderFacade;
    UserDto userDto;
    OrderDto orderDto;
    AddressDto addressDto;
    PaymentDto paymentDto;
    List<ItemDto> orderItems;

    @BeforeEach
    void initialize(){

        userDto = new UserDto(1L, "test@test.com", "test", "test");
        addressDto = new AddressDto(1,"address 123",null, "123456", "test name");
        paymentDto = new PaymentDto(1,"payment name", "description", UUID.randomUUID());

        orderItems = new ArrayList<>();
        orderItems.add(new ItemDto(1L, "product1", 1, 1.0));
        orderItems.add(new ItemDto(2L, "product2", 2, 2.0));

        orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setOrderItems(orderItems);
        orderDto.setUser(userDto);

        Mockito.when(userQueries.getUserFromEmail(anyString()))
                .thenReturn(userDto);
    }

    @Nested
    class createOrderTestCases{
        @Test
        void givenBasicOrderDto_whenCreatingOrder_returnsOrder(){
            Mockito.when(orderFacade.initializeOrder(any(List.class))).thenReturn(orderDto);
            Mockito.when(orderFacade.reserveOrder(any(OrderDto.class))).thenReturn(orderDto);
            Mockito.when(orderQueries.createOrder(any(OrderDto.class))).thenReturn(orderDto);
            MatcherAssert.assertThat("should return created order",
                    checkoutService.createOrder("test@test.com", orderDto), is(not(equalTo(null))));

        }

        @Test
        void givenCompleteOrderDto_whenCreatingOrder_returnsCompleteOrder(){
            orderDto.setAddress(addressDto);
            orderDto.setPaymentMethod(paymentDto);
            Mockito.when(orderFacade.initializeOrder(any(List.class))).thenReturn(orderDto);
            Mockito.when(orderFacade.reserveOrder(any(OrderDto.class))).thenReturn(orderDto);
            Mockito.when(userQueries.getAddressFromUser(any(UserDto.class), any())).thenReturn(addressDto);
            Mockito.when(userQueries.getPaymentFromUser(any(UserDto.class), any())).thenReturn(paymentDto);
            Mockito.when(orderFacade.generateDispatchCode(any(OrderDto.class))).thenReturn(orderDto);
            Mockito.when(orderFacade.verifyOrder(any(OrderDto.class))).thenReturn(orderDto);
            Mockito.when(orderQueries.createOrder(any(OrderDto.class))).thenReturn(orderDto);
            OrderDto finalDto = checkoutService.createOrder("test@test.com", orderDto);
            assertAll(
                    () -> MatcherAssert.assertThat("should return created order", finalDto, is(not(equalTo(null)))),
                    () -> Mockito.verify(orderFacade, description("should generate dispatch code.")).generateDispatchCode(any(OrderDto.class)),
                    () -> Mockito.verify(orderFacade, description("should generate verification code.")).verifyOrder(any(OrderDto.class))
            );
        }
    }


    @Nested
    class saveAddressTestCases{
        @BeforeEach
        void initializeMockBehaviour(){
            Mockito.when(orderQueries.getOrderById(any())).thenReturn(new Order());
            Mockito.when(orderQueries.mapToDto(any(Order.class))).thenReturn(orderDto);
            Mockito.when(orderFacade.generateDispatchCode(any(OrderDto.class))).thenReturn(orderDto);
            Mockito.when(orderQueries.updateOrder(any(), any(OrderDto.class))).thenReturn(new Order());
        }
        @Test
        void givenIdAddress_whenSavingIntoOrder_returnOrderModified(){
            orderDto.setAddress(addressDto);
            Mockito.when(userQueries.getAddressFromUser(any(UserDto.class), any())).thenReturn(addressDto);

            OrderDto finalOrder = checkoutService.saveAddress("test@test.com", 1L, addressDto);
            assertAll(
                    () -> MatcherAssert.assertThat("should return modified order", finalOrder, is(not(equalTo(null)))),
                    () -> Mockito.verify(orderFacade, description("should generate dispatch code.")).generateDispatchCode(any(OrderDto.class)),
                    () -> Mockito.verify(userQueries, description("should get address from user.")).getAddressFromUser(userDto, addressDto.getId())
            );
        }

        @Test
        void givenNonExistingIdAddress_whenSavingIntoOrder_returnOrderModified(){
            AddressDto otherAddress = new AddressDto();
            otherAddress.setId(1);
            Mockito.when(userQueries.getAddressFromUser(any(UserDto.class), any())).thenReturn(null);

            OrderDto finalOrder = checkoutService.saveAddress("test@test.com", 1L, otherAddress);
            assertAll(
                    () -> MatcherAssert.assertThat("should return not modified order", finalOrder.getAddress(), is(equalTo(null))),
                    () -> Mockito.verify(orderFacade, never()).generateDispatchCode(any(OrderDto.class)),
                    () -> Mockito.verify(userQueries).getAddressFromUser(userDto, addressDto.getId())
            );
        }
    }

    @Nested
    class savePaymentTestCases{
        @BeforeEach
        void initializeMockBehaviour(){
            Mockito.when(orderQueries.getOrderById(any())).thenReturn(new Order());
            Mockito.when(orderQueries.mapToDto(any(Order.class))).thenReturn(orderDto);
            Mockito.when(orderFacade.verifyOrder(any(OrderDto.class))).thenReturn(orderDto);
            Mockito.when(orderQueries.updateOrder(any(), any(OrderDto.class))).thenReturn(new Order());
        }
        @Test
        void givenIdPayment_whenSavingIntoOrder_returnOrderModified(){
            orderDto.setPaymentMethod(paymentDto);
            Mockito.when(userQueries.getPaymentFromUser(any(UserDto.class), any())).thenReturn(paymentDto);
            OrderDto finalOrder = checkoutService.savePaymentMethod("test@test.com", 1L, paymentDto);
            assertAll(
                    () -> MatcherAssert.assertThat("should return modified order", finalOrder, is(not(equalTo(null)))),
                    () -> Mockito.verify(orderFacade, description("should generate dispatch code.")).verifyOrder(any(OrderDto.class)),
                    () -> Mockito.verify(userQueries, description("should get address from user.")).getPaymentFromUser(userDto, addressDto.getId())
            );
        }

        @Test
        void givenNonExistingIdPayment_whenSavingIntoOrder_returnOrderModified(){
            PaymentDto otherPayment = new PaymentDto();
            otherPayment.setId(1);
            Mockito.when(userQueries.getPaymentFromUser(any(UserDto.class), any())).thenReturn(null);
            OrderDto finalOrder = checkoutService.savePaymentMethod("test@test.com", 1L, otherPayment);
            assertAll(
                    () -> MatcherAssert.assertThat("should return modified order", finalOrder, is(not(equalTo(null)))),
                    () -> Mockito.verify(orderFacade, description("should generate dispatch code.")).verifyOrder(any(OrderDto.class)),
                    () -> Mockito.verify(userQueries, description("should get address from user.")).getPaymentFromUser(userDto, addressDto.getId())
            );
        }
    }
    
    @Nested
    class updateItemTestCases{
        @BeforeEach
        void initializeMockBehaviour(){
            Mockito.when(orderQueries.getOrderById(any())).thenReturn(new Order());
            Mockito.when(productService.get(any())).thenReturn(
                    Optional.of(new ProductDto(1L, "product1", 10, 1.0))
            );
            Mockito.when(productService.returnStock(any(), anyInt())).thenReturn(true);
            Mockito.when(productService.reserveStock(any(), anyInt())).thenReturn(true);
            Mockito.when(orderFacade.saveItemToOrder(any(OrderDto.class), any(ItemDto.class))).thenReturn(orderDto);
            Mockito.when(orderQueries.updateOrder(any(), any(OrderDto.class))).thenReturn(new Order());
            Mockito.when(orderQueries.mapToDto(any(Order.class))).thenReturn(orderDto);
            Mockito.when(orderQueries.updateOrderItem(any(), any(ItemDto.class))).thenReturn(new Order());
        }
        
        @Test
        void givenItemDto_whenUpdatingIntoOrder_thenReturnsUpdatedOrder(){
            ItemDto itemToUpdate = new ItemDto();
            itemToUpdate.setCode(1L);
            itemToUpdate.setUnits(5);

            OrderDto order = checkoutService.updateOrderItemStock("test@test.com", 1L, itemToUpdate);
            assertAll(
                    () -> verify(productService, description("should return the associated product.")).get(itemToUpdate.getCode()),
                    () -> verify(orderFacade, description("should save into the model order")).saveItemToOrder(orderDto, itemToUpdate),
                    () -> verify(orderQueries, description("should update item into database")).updateOrderItem(orderDto.getId(), itemToUpdate),
                    () -> verify(orderQueries, description("should update order into database")).updateOrder(orderDto.getId(), order)
            );
        }

        @Test
        void givenInvalidItemDto_whenUpdatingIntoOrder_throwsCheckoutEx(){
            ItemDto itemToUpdate = new ItemDto();
            itemToUpdate.setCode(3L);
            itemToUpdate.setUnits(5);

            assertThrows(CheckoutServiceException.class, () -> checkoutService.updateOrderItemStock("test@test.com", 1L, itemToUpdate));
        }

        //@Test
        //void givenItemDto_whenUpdatingIntoCheckedOrder_throwsOrderAlreadyCheckedEx(){
        //    ItemDto itemToUpdate = new ItemDto();
        //    itemToUpdate.setCode(2L);
        //    itemToUpdate.setUnits(2);
        //
        //    orderDto.setReservationCode(UUID.randomUUID());
        //    orderDto.setVerificationCode(UUID.randomUUID());
        //
        //    Mockito.when(orderQueries.mapToDto(any(Order.class))).thenReturn(orderDto);
        //
        //    assertThrows(OrderAlreadyChecked.class, () -> checkoutService.updateOrderItemStock("test@test.com", 1L, itemToUpdate));
        //}

    }

    @Nested
    class addItemTestCases{
        @BeforeEach
        void initializeMockBehaviour(){
            Mockito.when(orderQueries.getOrderById(any())).thenReturn(new Order());
            Mockito.when(productService.get(any())).thenReturn(
                    Optional.of(new ProductDto(3L, "product3", 10, 1.0))
            );
            Mockito.when(productService.returnStock(any(), anyInt())).thenReturn(true);
            Mockito.when(productService.reserveStock(any(), anyInt())).thenReturn(true);
            Mockito.when(orderFacade.saveItemToOrder(any(OrderDto.class), any(ItemDto.class))).thenReturn(orderDto);
            Mockito.when(orderQueries.updateOrder(any(), any(OrderDto.class))).thenReturn(new Order());
            Mockito.when(orderQueries.mapToDto(any(Order.class))).thenReturn(orderDto);
            //Mockito.when(orderQueries.addOrderItem(any(OrderDto.class), any(ItemDto.class))).thenReturn(new Order());
            doNothing().when(orderQueries).addOrderItem(any(OrderDto.class), any(ItemDto.class));
        }

        @Test
        void givenNewItem_whenAddingIntoOrder_thenReturnsModifiedOrder(){
            ItemDto itemToAdd = new ItemDto();
            itemToAdd.setCode(3L);
            itemToAdd.setUnits(5);
            OrderDto order = checkoutService.addOrderItem("test@test.com", 1L, itemToAdd);
            assertAll(
                    () -> verify(productService, description("should return the associated product.")).get(itemToAdd.getCode()),
                    () -> verify(orderFacade, description("should save into the model order")).saveItemToOrder(orderDto, itemToAdd),
                    () -> verify(orderQueries, description("should update item into database")).addOrderItem(orderDto, itemToAdd),
                    () -> verify(orderQueries, description("should update order into database")).updateOrder(orderDto.getId(), order)
            );
        }

        @Test
        void givenExistingItem_whenAddingIntoOrder_thenReturnsModifiedOrder(){
            ItemDto itemToAdd = new ItemDto();
            itemToAdd.setCode(1L);
            itemToAdd.setUnits(5);
            assertThrows(CheckoutServiceException.class, ()-> checkoutService.addOrderItem("test@test.com", 1L, itemToAdd));
        }
    }

    @Nested
    class deleteItemTestCases{
        @BeforeEach
        void initializeMockBehaviour(){
            Mockito.when(orderQueries.getOrderById(any())).thenReturn(new Order());
            Mockito.when(productService.get(any())).thenReturn(
                    Optional.of(new ProductDto(1L, "product1", 10, 1.0))
            );
            Mockito.when(productService.returnStock(any(), anyInt())).thenReturn(true);
            Mockito.when(orderFacade.deleteItemFromOrder(any(OrderDto.class), any(ItemDto.class))).thenReturn(orderDto);
            Mockito.when(orderQueries.updateOrder(any(), any(OrderDto.class))).thenReturn(new Order());
            Mockito.when(orderQueries.mapToDto(any(Order.class))).thenReturn(orderDto);
            Mockito.when(orderQueries.deleteOrderItem(any(), any())).thenReturn(orderDto);
        }

        @Test
        void givenItem_whenDeletingFromOrder_thenReturnsModifiedOrder(){
            ItemDto itemToDelete = new ItemDto();
            itemToDelete.setCode(1L);
            OrderDto order = checkoutService.deleteOrderItem("test@test.com", 1L, itemToDelete.getCode());
            assertAll(
                    () -> verify(productService, description("should return the associated product.")).get(itemToDelete.getCode()),
                    () -> verify(orderFacade, description("should save into the model order")).deleteItemFromOrder(orderDto, itemToDelete),
                    () -> verify(orderQueries, description("should update item into database")).deleteOrderItem(orderDto.getId(), itemToDelete.getId()),
                    () -> verify(orderQueries, description("should update order into database")).updateOrder(orderDto.getId(), order)
            );
        }

        @Test
        void givenInvalidItem_whenDeletingFromOrder_thenReturnsModifiedOrder(){
            ItemDto itemToDelete = new ItemDto();
            itemToDelete.setCode(3L);
            assertThrows(CheckoutServiceException.class, ()->checkoutService.deleteOrderItem("test@test.com", 1L, itemToDelete.getCode()));
        }
    }

    @Nested
    class deleteOrderTestCases{
        @BeforeEach
        void initializeMockBehaviour(){
            when(orderQueries.getOrderById(any())).thenReturn(new Order());
            when(productService.get(1L)).thenReturn(
                    Optional.of(new ProductDto(1L, "product1", 10, 1.0))
            );
            when(productService.get(1L)).thenReturn(
                    Optional.of(new ProductDto(12L, "product12", 20, 2.0))
            );
            when(productService.returnStock(any(), anyInt())).thenReturn(true);
            when(orderQueries.mapToDto(any(Order.class))).thenReturn(orderDto);
            when(orderQueries.deleteOrder(any())).thenReturn(true);
        }

        @Test
        void givenIdOrder_whenDeleting_returnsNothing(){
            checkoutService.deleteOrder(orderDto.getId());
            assertAll(
                    () -> verify(productService, description("should return the associated product.")).get(1L),
                    () -> verify(productService, description("should return the associated product.")).get(2L),
                    () -> verify(orderQueries, description("should update item into database")).deleteOrder(orderDto.getId())
            );
        }
    }
}
