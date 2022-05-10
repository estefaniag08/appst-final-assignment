package dev.applaudostudios.examples.finalassignment.persistence.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.applaudostudios.examples.finalassignment.common.Mappable;
import dev.applaudostudios.examples.finalassignment.common.dto.AddressDto;
import dev.applaudostudios.examples.finalassignment.common.dto.PaymentDto;
import dev.applaudostudios.examples.finalassignment.common.dto.UserDto;
import dev.applaudostudios.examples.finalassignment.persistence.model.Address;
import dev.applaudostudios.examples.finalassignment.persistence.model.PaymentMethod;
import dev.applaudostudios.examples.finalassignment.persistence.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Objects;
import java.util.Optional;


public class UserQueries implements Mappable<User, UserDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    public UserDto getUserFromEmail(String userEmail) {
        try (Session session = entityManager.unwrap(Session.class)) {
            return mapToDto(session.bySimpleNaturalId(User.class).load(userEmail));

        } catch (PersistenceException exception) {
            return null;
        }
    }

    public AddressDto getAddressFromUserId(Long userId, Integer addressId) {
        try {
            User user = entityManager.find(User.class, userId);
            Optional<Address> address = user.getAddresses()
                    .stream().filter(addressItem -> addressItem.getId() == addressId).findFirst();
            if(address.isPresent()){
                return objectMapper.convertValue(address.get(), AddressDto.class);
            } else{
                return null;
            }
        } catch (PersistenceException exception) {
            return null;
        }
    }

    public PaymentDto getPaymentFromUserId(Long userId, Integer paymentId){
        try{
            User user = entityManager.find(User.class, userId);
            Optional<PaymentMethod> payment = user.getPaymentMethods()
                    .stream().filter(paymentItem -> Objects.equals(paymentItem.getId(), paymentId)).findFirst();
            if(payment.isPresent()){
                return objectMapper.convertValue(payment, PaymentDto.class);
            } else {
                return null;
            }
        }catch(PersistenceException exception){
            return null;
        }
    }
    @Override
    public UserDto mapToDto(User entity) {
        return objectMapper.convertValue(entity, UserDto.class);
    }

    @Override
    public User mapToEntity(UserDto entity) {
        return objectMapper.convertValue(entity, User.class);
    }
}
