package com.jesco.model.ejb;

import com.jesco.model.entities.Address;
import com.jesco.model.entities.Customer;
import java.util.List;
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
    
    public Customer find(String id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadCustomer");
        EntityManager em = emf.createEntityManager();
        TypedQuery<Customer> consultaCustomer= em.createNamedQuery("findByCustomerId", Customer.class);
        consultaCustomer.setParameter("customerId", id);
        List<Customer> lista= consultaCustomer.getResultList();
        System.out.println(lista);

        for(Customer a :lista){
            return a;
        }
        return null;
    }
    
}
