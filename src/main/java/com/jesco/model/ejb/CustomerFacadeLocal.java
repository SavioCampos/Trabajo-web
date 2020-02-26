package com.jesco.model.ejb;

import com.jesco.model.entities.Customer;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CustomerFacadeLocal {

    void create(Customer customer);

    void edit(Customer customer);

    void remove(Customer customer);

    Customer find(Object id);

    List<Customer> findAll();

    List<Customer> findRange(int[] range);

    int count();
    
}
