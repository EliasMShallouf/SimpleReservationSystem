package com.eliasshallouf.examples.simple_reservation_system;

import com.eliasshallouf.examples.simple_reservation_system.domain.model.User;
import com.eliasshallouf.examples.simple_reservation_system.domain.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class AppDatabase {
    @Autowired
    private UserRepository userRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        User admin = new User();
        admin.setId("admin");
        admin.setName("Admin");
        admin.setIsAdmin(true);
        admin.setPassword("admin");
        admin.setGithubEmail("admin");
        userRepository.save(admin);
    }
}
