package dev.applaudostudios.examples.finalassignment.persistence.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.applaudostudios.examples.finalassignment.common.dto.OrderDto;
import dev.applaudostudios.examples.finalassignment.config.PersistenceConfiguration;
import dev.applaudostudios.examples.finalassignment.persistence.model.Order;
import dev.applaudostudios.examples.finalassignment.persistence.model.OrderDetail;
import dev.applaudostudios.examples.finalassignment.persistence.model.User;
import dev.applaudostudios.examples.finalassignment.persistence.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes = {PersistenceConfiguration.class})
@DisplayName("When executing OrderQueries class")
@Transactional
public class OrderQueriesTest {
    @Autowired
    private EntityManager entityManager;

    @MockBean
    private ObjectMapper objectMapper;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private OrderQueries orderQueries;

    Order order;
    List<OrderDetail> orderDetailList;
    OrderDto orderDto;
    User user;

    @BeforeEach
    void initialize(){
        user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("test");
        user.setLastName("lastname test");
        user.setPhoneNumber("12345678990");
        entityManager.persist(user);

       // orderDetailList.add(new OrderDetail(1L, order, n, 1, 1.0))
        order = new Order();
        order.setUser(user);
        order.setOrderCode(UUID.randomUUID());
        order.setOrderTotal(100.0);
        entityManager.persist(order);

        orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderCode(order.getOrderCode());
        orderDto.setOrderTotal(order.getOrderTotal());

    }

    //@Test
    //void givenOrderId_whenGettingOrder_returnsOrder(){
    //    Order foundOrder = orderQueries.getOrderById(order.getId());
    //    MatcherAssert.assertThat(foundOrder.getId(), equalTo(order.getId()));
    //}

}
