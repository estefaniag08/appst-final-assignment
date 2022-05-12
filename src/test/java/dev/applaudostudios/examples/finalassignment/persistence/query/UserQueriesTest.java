package dev.applaudostudios.examples.finalassignment.persistence.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.applaudostudios.examples.finalassignment.common.dto.AddressDto;
import dev.applaudostudios.examples.finalassignment.common.dto.UserDto;
import dev.applaudostudios.examples.finalassignment.common.exception.user.AddressNotExistException;
import dev.applaudostudios.examples.finalassignment.common.exception.user.PaymentNotExistException;
import dev.applaudostudios.examples.finalassignment.config.PersistenceConfiguration;
import dev.applaudostudios.examples.finalassignment.persistence.model.Address;
import dev.applaudostudios.examples.finalassignment.persistence.model.PaymentMethod;
import dev.applaudostudios.examples.finalassignment.persistence.model.User;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes = {PersistenceConfiguration.class})
@DisplayName("When executing UserQueries class")
@Transactional
public class UserQueriesTest {
    @Autowired
    private EntityManager entityManager;

    @MockBean
    private ObjectMapper objectMapper;

    @Autowired
    UserQueries userQueries;

    User user;
    Address address;
    PaymentMethod payment;
    UserDto userDto;
    AddressDto addressDto;
    //PaymentDto paymentDto;

    @BeforeEach
    void initialize(){
        user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("test");
        user.setLastName("lastname test");
        user.setPhoneNumber("12345678990");
        entityManager.persist(user);

        address = new Address();
        address.setUser(user);
        address.setAddress("address 123");
        address.setPostalCode("123456");
        address.setReceiverName("test name");
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        user.setAddresses(addressList);
        entityManager.persist(address);

        payment = new PaymentMethod();
        payment.setUser(user);
        payment.setPaymentMethodName("payment name");
        payment.setPaymentMethodDescription("description");
        payment.setToken(payment.getToken());
        List<PaymentMethod> paymentMethodList = new ArrayList<>();
        paymentMethodList.add(payment);
        user.setPaymentMethods(paymentMethodList);
        entityManager.persist(payment);

        addressDto = new AddressDto(1,"address 123",null, "123456", "test name");
        //paymentDto = new PaymentDto(1,"payment name", "description", payment.getToken());
        userDto = new UserDto(1L,"test@test.com","test", "lastname test");

        Mockito.when(objectMapper.convertValue(user, UserDto.class)).thenReturn(userDto);
        Mockito.when(objectMapper.convertValue(userDto, User.class)).thenReturn(user);
    }

    @Test
    @DisplayName("given existing user email to find user")
    void givenUserEmail_whenGettingUserFromEmail_thenReturnsUser(){

        UserDto createdUserDto = userQueries.getUserFromEmail("test@test.com");

        assertAll(
                () -> MatcherAssert.assertThat("user should be on the database",
                        entityManager.find(User.class, user.getId()), equalTo(user)),
                () -> MatcherAssert.assertThat("user should be correctly mapped.",
                        createdUserDto, equalTo(userDto))
        );
    }

    @Test
    @DisplayName("given existing address id to find user address")
    void givenAddressId_whenGettingFromUserAddresses_thenReturnsAddress(){
        Mockito.when(objectMapper.convertValue(address, AddressDto.class)).thenReturn(
                addressDto
        );
        userDto.setId(user.getId());

        AddressDto addressDto = userQueries.getAddressFromUser(userDto, address.getId());

        MatcherAssert.assertThat("address should be present", addressDto.getId(), equalTo(address.getId()));
    }


    @Test
    @DisplayName("given non existing address id to find user address")
    void givenInvalidAddressId_whenGettingFromUserAddresses_thenThrowsAddressNotExistEx(){
        userDto.setId(user.getId());
        assertThrows(AddressNotExistException.class, () -> {
            userQueries.getAddressFromUser(userDto, 2);
        });

    }

    //@Test
    //@DisplayName("given existing payment id to find user payment")
    //void givenPaymentId_whenGettingFromUserPayments_thenReturnsPayment(){
    //    Mockito.when(objectMapper.convertValue(payment, PaymentDto.class))
    //            .thenReturn(paymentDto);
    //    userDto.setId(user.getId());
    //    PaymentDto paymentDto = userQueries.getPaymentFromUser(userDto, payment.getId());
    //    MatcherAssert.assertThat("payment should be present", paymentDto.getId(),equalTo(payment.getId()));
    //}

    @Test
    @DisplayName("given non existing payment id to find user payment")
    void givenInvalidPaymentId_whenGettingFromUserPayments_thenReturnsPaymentNotExistEx(){
        userDto.setId(user.getId());
        assertThrows(PaymentNotExistException.class, ()->{
            userQueries.getPaymentFromUser(userDto, -1);
        });
    }
}
