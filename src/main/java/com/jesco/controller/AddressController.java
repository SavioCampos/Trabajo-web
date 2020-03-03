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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;


/**
 *
 * @author sandoval
 */
@Named
@SessionScoped
public class AddressController extends HttpServlet {
    
    @EJB   
    private AddressFacadeLocal addressFacade;
    private Address address;
    @EJB
    private CustomerFacadeLocal customerFacade;
    private Customer customer;
    private String cliente;
    private List<Address> listAddress;

    @PostConstruct
    public void init() {
        customer = new Customer();
        address = new Address();
        listAddress = new ArrayList<Address>();
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
    
    public String changeCustomerAddressReplace( Customer cust ){
        this.customer = cust;
        return "customerAddresses?faces-redirect=true";
    }
    
    public Collection<Address> listAdrressComplete(){
        return this.customer.getAddressCollection();
    }
    
    public String changeAddressEdit( Address addr ){
        this.address = addr;
        return "editAddress?faces-redirect=true";
    }
    
    public void delete(Address addr){
        String id = this.customer.getCustomerId();
        addressFacade.remove(addr);
        this.customer = customerFacade.find(id);
        System.out.println("Se ha eliminado la direccion:"+ addr.getAddressId());
    }
    
    public Collection<Address> getAllAddress() {
        return addressFacade.findAll();
    }
    
    public Address putAddress(){
        String addr = getRequestUrl();
        this.address = addressFacade.find(addr);
        return this.address;
    }
    
    public String edit(){
        addressFacade.edit(this.address);
        return "customerAddresses?faces-redirect=true";
    }
}
