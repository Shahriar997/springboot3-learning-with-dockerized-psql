package com.learnSpring3;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> getCustomer() {
        return customerRepository.findAll();
    }

    @PostMapping
    public List<Customer> saveCustomer(@RequestBody @NotNull Customer customer) {
        customerRepository.save(customer);
        return List.of(customer);
    }

    @DeleteMapping(path = "{customerId}")
    public List<String> deleteCustomer(@PathVariable Integer customerId) {
        customerRepository.deleteById(customerId);
        return List.of("success");
    }

    @PutMapping(path = "{customerId}")
    public List<Customer> updateCustomer(@PathVariable Integer customerId, @RequestParam(required = false)String name, @RequestParam(required = false)String email, @RequestParam(required = false)Integer age) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                ()->new IllegalStateException("No Customer")
        );

        if (name != null) {
            customer.setName(name);
        }
        if (age != null) {
            customer.setAge(age);
        }
        if (email != null) {
            customer.setEmail(email);
        }

        customerRepository.save(customer);

        return List.of(customer);
    }
}
