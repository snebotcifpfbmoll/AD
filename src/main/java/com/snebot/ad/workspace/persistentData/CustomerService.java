package com.snebot.ad.workspace.persistentData;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public CustomerRepository getRepository() {
        return repository;
    }

    public void initDB() {
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Chloe", "O'Brian"));
        repository.save(new Customer("Kim", "Bauer"));
        repository.save(new Customer("David", "Palmer"));
        repository.save(new Customer("Michelle", "Dessler"));
    }
}
