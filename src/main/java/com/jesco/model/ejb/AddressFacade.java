package com.jesco.model.ejb;

import com.jesco.model.entities.Address;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AddressFacade extends AbstractFacade<Address> implements AddressFacadeLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AddressFacade() {
        super(Address.class);
    }
    
}
