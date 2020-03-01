package com.jesco.model.ejb;

import com.jesco.model.entities.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class CustomerFacade extends AbstractFacade<Customer> implements CustomerFacadeLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }
    
    /*
    @Override
    public Customer find(Object id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my_persistence_unit");
        emf.getCache().evictAll();
        EntityManager em = emf.createEntityManager();
        TypedQuery<Customer> consultaCliente = em.createNamedQuery("Customer.findByCustomerId", Customer.class);
        consultaCliente.setParameter("customerId", id);
        
        return consultaCliente.getSingleResult();
    }
    */
}
