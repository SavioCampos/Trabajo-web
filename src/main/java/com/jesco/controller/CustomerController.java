package com.jesco.controller;

import com.jesco.model.ejb.AddressFacadeLocal;
import com.jesco.model.ejb.CustomerFacadeLocal;
import com.jesco.model.entities.Address;
import com.jesco.model.entities.Customer;
import com.jesco.controller.AddressController;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class CustomerController {

    @EJB
    private CustomerFacadeLocal customerFacade;
    private Customer customer;
    private Collection<Address> coleccionDirecciones;
    private String cliente;

    @PostConstruct
    public void init() {
        customer = new Customer();
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
        customer.setCustomerId(UUID.randomUUID().toString());
        customerFacade.create(customer);
        
    }

    public Collection<Customer> getAllCustomer() {
        Collection<Customer> listaCustomer = customerFacade.findAll();
        return listaCustomer;
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
    
//    public Collection<Address> getAddressesCustomer() {
//        String id = getRequestUrl();
//        this.customer = customerFacade.find(id);
//        return this.customer.getAddressCollection();
//    }
    
    public Customer getCustomerById(){
        String id = getRequestUrl();
        this.customer = customerFacade.find(id);
        return this.customer;
    }
    
    public void editSave(){
        customerFacade.edit(this.customer);
    }
    
    public String delete(Customer customer){
        customerFacade.remove(customer);
        customer = null;
        return "clientlist?faces-redirect=true";
    }
    
    public String changeCustomerEdit( Customer cust ){
        this.customer = cust;
        return "editCustomer";
    }

}
