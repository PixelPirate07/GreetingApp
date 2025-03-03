package com.example.greetingsapp.service;

import com.example.greetingsapp.model.Greeting;
import com.example.greetingsapp.repository.GreetingRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class GreetingService {

    private final GreetingRepository greetingRepository;

    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    // Fetch Greeting by ID
    public Greeting getGreetingById(Long id) {
        return greetingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Greeting not found with ID: " + id));
    }
    public Greeting saveGreeting(Greeting greeting) {
        return greetingRepository.save(greeting);
    }
    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }

}