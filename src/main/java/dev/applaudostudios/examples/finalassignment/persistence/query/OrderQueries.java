package dev.applaudostudios.examples.finalassignment.persistence.query;

import dev.applaudostudios.examples.finalassignment.persistence.model.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

@Transactional
public class OrderQueries{
    @PersistenceContext
    private EntityManager entityManager;

    public Order getOrderById(Long id){
        try {
            return entityManager.find(Order.class, id);
        } catch(IllegalArgumentException exception){
            return null;
        }
    }

    public Order createOrder(Order order){
        try{
            entityManager.persist(order);
            return order;
        }catch(PersistenceException exception){
            return null;
        }
    }

    public boolean deleteOrder(Long id){
        try{
            Order order = getOrderById(id);
            entityManager.getTransaction().begin();
            if(order != null){
                entityManager.remove(order);
            }
            entityManager.getTransaction().commit();
            return true;
        }catch(PersistenceException exception){
            return false;
        }
    }

    public Order updateOrder(Long id, Order order){
        try{
            Order updatedOrder = null;
            entityManager.getTransaction().begin();
            Order orderToUpdate = getOrderById(id);
            if(orderToUpdate != null){
                updatedOrder = entityManager.merge(order);
            }
            entityManager.getTransaction().commit();
            return updatedOrder;
        }catch(PersistenceException exception){
            return null;
        }
    }
}
