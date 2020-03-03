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
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import javax.enterprise.context.SessionScoped;


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
    
    public void newSave() {
        address.setAddressId(UUID.randomUUID().toString());
        customer = customerFacade.find(cliente);
        address.setCustomerId(customer);
        addressFacade.create(address);
    }

    public String getRequestUrl() {
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
        return "customerAddresses?id=" + cust.getCustomerId() + "&faces-redirect=true";
    }
    
    public List<Address> listAdrressComplete(){
        String id = getRequestUrl();
        this.listAddress = addressFacade.findAll();
        Iterator<Address> it = this.listAddress.iterator();
        while (it.hasNext()) {
            Address addr = it.next();
            if ( !addr.getCustomerId().getCustomerId().equals(id) ) {
                it.remove();
            }
        }
        this.customer = customerFacade.find(id);
        return this.listAddress;
    }
    
    public String changeAddressEdit( Address addr ){
        return addr.getAddressId();
    }
    
    public String delete (){
        String addr = getRequestUrl();
        this.address = addressFacade.find(addr);
        addressFacade.remove(this.address);
        return "customerAddresses?id="+this.address.getCustomerId().getCustomerId()+"faces-redirect=true";
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
        Address addr = addressFacade.find(this.address.getAddressId());
        this.address.setCustomerId(addr.getCustomerId());
        addressFacade.edit(this.address);
        return this.address.getCustomerId().getCustomerId();
    }
    
    public Collection<Address> collectionAdrressComplete(){
        String id = getRequestUrl();
        this.customer = customerFacade.find(id);
        return this.customer.getAddressCollection();
    }
    
    public String changeIdAddressforIdCustomer(){
        String id = getRequestUrl();
        Address addr = addressFacade.find(id);
        return addr.getCustomerId().getCustomerId();
    }
    
    public Customer getCustomerById(){
        String id = getRequestUrl();
        this.cliente = id;
        this.customer = customerFacade.find(id);
        this.address.setCustomerId(this.customer);
        return this.customer;
    }
}
