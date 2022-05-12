package dev.applaudostudios.examples.finalassignment.web.controller;


import dev.applaudostudios.examples.finalassignment.common.dto.AddressDto;
import dev.applaudostudios.examples.finalassignment.common.dto.ItemDto;
import dev.applaudostudios.examples.finalassignment.common.dto.OrderDto;
import dev.applaudostudios.examples.finalassignment.common.dto.PaymentDto;
import dev.applaudostudios.examples.finalassignment.common.exception.RestExceptionHandler;
import dev.applaudostudios.examples.finalassignment.service.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@SpringBootTest
@DisplayName("When running OrderController")
public class OrderControllerTest {

    OrderDto orderDto;
    AddressDto addressDto;
    PaymentDto paymentDto;

    List<ItemDto> orderItems;

    @MockBean
    CheckoutService checkoutService;

    @Autowired
    @InjectMocks
    OrderController orderController = new OrderController();

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(orderController)
            .setControllerAdvice(new RestExceptionHandler())
            .build();

    @BeforeEach
    void initialize(){

        addressDto = new AddressDto(1,"address 123",null, "123456", "test name");
        paymentDto = new PaymentDto(1,"payment name", "description", UUID.randomUUID());

        orderItems = new ArrayList<>();
        orderItems.add(new ItemDto(1L, "product1", 1, 1.0));
        orderItems.add(new ItemDto(2L, "product2", 2, 2.0));

        orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setOrderItems(orderItems);

    }

    //@Test
    //void givenCompleteOrderDto_whenCreatingOrder_returnsOrder() throws Exception {
    //
    //}
    //
    //@Test
    //void givenBasicOrderDto_whenCreatingOrder_returnsOrder(){
    //
    //}
    //
    //@Test
    //void givenAddressDto_whenSavingOrder_returnsOrder(){
    //
    //}
    //
    //@Test
    //void givenPaymentDto_whenSavingPayment_returnsOrder(){
    //
    //}
    //
    //@Test
    //void givenItemDto_whenUpdatingItem_returnsOrder(){
    //
    //}
    //
    //@Test
    //void givenNewItemDto_whenDeletingItem_returnsOrder(){
    //
    //}
    //


}
