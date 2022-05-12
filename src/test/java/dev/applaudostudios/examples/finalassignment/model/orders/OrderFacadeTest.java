package dev.applaudostudios.examples.finalassignment.model.orders;

import dev.applaudostudios.examples.finalassignment.common.dto.AddressDto;
import dev.applaudostudios.examples.finalassignment.common.dto.ItemDto;
import dev.applaudostudios.examples.finalassignment.common.dto.OrderDto;
import dev.applaudostudios.examples.finalassignment.common.dto.PaymentDto;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("When running OrderFacade class")
public class OrderFacadeTest {
    //@Autowired
    OrderFacade orderFacade;
    OrderDto orderDto;
    List<ItemDto> listOfItems;

    @BeforeEach
    void initialize(){
        orderFacade = new OrderFacade();
        listOfItems = new ArrayList<>();
        listOfItems.add(new ItemDto(1L, "item1", 1, 10.1));
        listOfItems.add(new ItemDto(2L, "item2", 2, 5.0));

        orderDto = new OrderDto();
        orderDto.setOrderItems(listOfItems);
    }
    @Nested
    @DisplayName("when initializing order")
    class initializeOrderCases{
        @Test
        @DisplayName("given a list of items to create an order")
        void givenListOfItems_whenInitializingOrder_thenReturnsOrderWithGenerationCode(){
            OrderDto order = orderFacade.initializeOrder(listOfItems);
            MatcherAssert.assertThat("should have generation code.",
                    order.getOrderCode(), is(notNullValue()));

        }

        @Test
        @DisplayName("given an empty list of items to create an order")
        void givenEmptyListOfItems_whenInitializingOrder_thenReturnsOrderWithoutOrderCode(){
            OrderDto order = orderFacade.initializeOrder(new ArrayList<>());
            MatcherAssert.assertThat("should not have generation code.",
                    order.getOrderCode(), is(nullValue()));
        }
    }
    @Nested
    @DisplayName("when saving item into order")
    class saveItemCases{
        @Test
        @DisplayName("given an order and an item to save it")
        void givenOrderAndItem_whenSavingItem_thenReturnsOrderWithItemAdded(){
            ItemDto item = new ItemDto(4L, "item4", 1, 1.1);
            OrderDto order = orderFacade.saveItemToOrder(orderDto, item);
            MatcherAssert.assertThat("the saved item should be present.",
                    order.getOrderItems().get(2), equalTo(item));
        }

        @Test
        @DisplayName("given an order and an existing item to save it")
        void givenOrderAndItem_whenSavingItem_thenReturnsOrderWithItemUpdated(){
            ItemDto item = new ItemDto(1L, "item1", 2, 1.1);
            OrderDto order = orderFacade.saveItemToOrder(orderDto, item);
            assertAll(
                    () -> MatcherAssert.assertThat("the saved item should be present.",
                            order.getOrderItems().get(0), equalTo(item)),
                    () -> MatcherAssert.assertThat("the saved item should be present and be updated.",
                            order.getOrderItems().get(0).getUnits(), equalTo(2))
            );

        }

        @Test
        @DisplayName("given an order and an item without price to save it")
        void givenOrderAndItemWithoutPrice_whenSavingItem_thenReturnsNonUpdatedOrder(){
            ItemDto item = new ItemDto(1L, "item1", 2, 0.0);
            orderDto.setOrderItems(new ArrayList<>());
            OrderDto order = orderFacade.saveItemToOrder(orderDto, item);
            assertAll(
                    ()-> MatcherAssert.assertThat("the item list should remain the same.",
                            order.getOrderItems().size(), equalTo(0)),
                    ()-> MatcherAssert.assertThat("reservation code shouldn't change",
                            order.getReservationCode(), equalTo(orderDto.getReservationCode()))
            );

        }

        @Test
        @DisplayName("given an order and an item without price to save it")
        void givenOrderAndItemWithoutZeroUnits_whenSavingItem_thenReturnsDeletedItem(){
            ItemDto item = new ItemDto(1L, "item1", 0, 0.0);
            OrderDto order = orderFacade.saveItemToOrder(orderDto, item);
            assertAll(
                    ()-> MatcherAssert.assertThat("the item list shouldn't remain the same.",
                            order.getOrderItems().size(), lessThan(2)),
                    ()-> MatcherAssert.assertThat("remain item shouldn't be the one eliminated.",
                            order.getOrderItems().get(0), is(not(equalTo(item))))
            );

        }
    }

    @Nested
    @DisplayName("when deleting item from order")
    class deleteItemCases{
        @Test
        @DisplayName("given an order and an existing item to delete")
        void givenOrderAndItem_whenDeletingItem_thenReturnsOrderWithItemDeleted(){
            ItemDto item =  new ItemDto(2L, "item2", 2, 5.0);
            OrderDto order = orderFacade.deleteItemFromOrder(orderDto, item);
            assertAll(
                    () -> MatcherAssert.assertThat("item should be deleted from the order.",
                            order.getOrderItems().size(), equalTo(1)),
                    () -> MatcherAssert.assertThat("remain item shouldn't be the one eliminated.",
                            order.getOrderItems().get(0), is(not(equalTo(item))))
            );
        }

        @Test
        @DisplayName("given an order and a non existing item to delete")
        void givenOrderAndNonExistingItem_whenDeletingItem_thenReturnsNonUpdatedOrder(){
            ItemDto item =  new ItemDto(5L, "item5", 2, 5.0);
            OrderDto order = orderFacade.deleteItemFromOrder(orderDto, item);
            assertAll(
                    () -> MatcherAssert.assertThat("item list should remain the same.",
                            order.getOrderItems().size(), equalTo(2)),
                    () -> MatcherAssert.assertThat("reservation code should be the same.",
                            order.getReservationCode(), equalTo(orderDto.getReservationCode()))
            );
        }
    }

    @Nested
    @DisplayName("when reserving order items")
    class reservingOrderCases{
        @Test
        @DisplayName("given an order to reserve")
        void givenOrder_whenReserving_thenReturnsReservedOrder(){
            OrderDto order = orderFacade.reserveOrder(orderDto);
            MatcherAssert.assertThat("reservation code shouldn't be null.",
                    order.getReservationCode(), not(equalTo(null)));

        }

        @Test
        @DisplayName("given an invalid order to reserve")
        void givenInvalidOrder_whenReserving_thenReturnsNonReservedOrder(){
            orderDto.setOrderItems(new ArrayList<>());
            OrderDto order = orderFacade.reserveOrder(orderDto);
            MatcherAssert.assertThat("reservation code should not change.",
                    order.getReservationCode(), equalTo(orderDto.getReservationCode()));

        }

    }

    @Nested
    @DisplayName("when verifying order")
    class verifyingOrderCases{
        @Test
        @DisplayName("given an order to verify")
        void givenOrder_whenVerifyingOrder_thenReturnsVerifiedOrder(){
            orderDto.setPaymentMethod(new PaymentDto(1,"PaymentMethod","Description", UUID.randomUUID()));
            OrderDto order = orderFacade.verifyOrder(orderDto);
            MatcherAssert.assertThat("verification code should be generated.",
                    order.getVerificationCode(), is(not(equalTo(null))));
        }

        @Test
        @DisplayName("given an order to verify")
        void givenInvalidOrder_whenVerifyingOrder_thenReturnsNonVerifiedOrder(){
            orderDto.setPaymentMethod(new PaymentDto());
            OrderDto order = orderFacade.verifyOrder(orderDto);
            MatcherAssert.assertThat("verification code should be generated.",
                    order.getVerificationCode(), is(equalTo(null)));
        }
    }

    @Nested
    @DisplayName("when generating dispatch code")
    class dispatchOrderCases{
        @Test
        @DisplayName("given an order to generate dispatch code")
        void givenOrder_whenDispatchingOrder_thenReturnsVerifiedOrder(){
            orderDto.setAddress(new AddressDto(1, "Address", "Additional info", "101010", "Receiver"));
            OrderDto order = orderFacade.generateDispatchCode(orderDto);
            MatcherAssert.assertThat("dispatch code should be generated.",
                    order.getDispatchCode(), is(not(equalTo(null))));
        }

        @Test
        @DisplayName("given an order to generate dispatch code")
        void givenInvalidOrder_whenDispatchingOrder_thenReturnsNonVerifiedOrder(){
            orderDto.setAddress(new AddressDto());
            OrderDto order = orderFacade.generateDispatchCode(orderDto);
            MatcherAssert.assertThat("dispatch code shouldn't be generated.",
                    order.getDispatchCode(), is(equalTo(null)));
        }
    }

}
