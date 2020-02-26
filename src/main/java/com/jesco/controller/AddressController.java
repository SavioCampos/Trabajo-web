/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jesco.controller;

import com.jesco.model.ejb.AddressFacadeLocal;
import com.jesco.model.ejb.CustomerFacadeLocal;
import com.jesco.model.entities.Address;
import com.jesco.model.entities.Customer;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author sandoval
 */
@Named
@RequestScoped
public class AddressController extends HttpServlet {
    


    @EJB   
    private AddressFacadeLocal addressFacade;
    private Address address;
    @EJB
    private CustomerFacadeLocal customerFacade;
    private Customer customer;
    private String cliente;

    @PostConstruct
    public void init() {
        customer = new Customer();
        address = new Address();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCliente() {
      return cliente;
   } 

   public void setCliente(String cliente) {
      this.cliente = cliente;
   }
    
    public void save() {
        address.setAddressId(UUID.randomUUID().toString());
        customer = customerFacade.find(cliente);
        address.setCustomerId(customer);
        addressFacade.create(address);
    }

    

/*
    public static String getRequestUrl() {
        String valorBuscado = "id=";
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        StringBuffer requestURL = request.getRequestURL();
        if (request.getQueryString() != null) {
            requestURL.append("?");
            requestURL.append(request.getQueryString());
        }
        String idCustomer = requestURL.toString().substring(requestURL.toString().indexOf(valorBuscado) + valorBuscado.length());
        return idCustomer;
    }

    public String getAddressesCustomer() {
//        String id = getRequestUrl();
//        Customer customerx = customerFacade.find("dc994f8c-b2a1-4a21-bedf-0f33b891e189");
        List<Customer> listaCustomer = customerFacade.findAll();
        return "hola";

//        String id = getRequestUrl();
//        List<Address> listAddress = addressFacade.findByCustomerId(id);
//        return listAddress;
    }

*/
}
