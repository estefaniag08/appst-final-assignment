package dev.applaudostudios.examples.finalassignment.persistence.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.applaudostudios.examples.finalassignment.common.Mappable;
import dev.applaudostudios.examples.finalassignment.common.dto.UserDto;
import dev.applaudostudios.examples.finalassignment.persistence.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

@Transactional
public class UserQueries implements Mappable<User, UserDto> {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;
    public UserDto getUserFromEmail(String userEmail) {
        try {
            entityManager.getTransaction().begin();
            Session session = entityManager.unwrap(Session.class);
            return mapToDto(session.bySimpleNaturalId(User.class).load(userEmail));

        } catch(PersistenceException exception){
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
