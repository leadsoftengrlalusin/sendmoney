package com.gxchange.sendmoney.config;

import com.gxchange.sendmoney.model.Role;
import com.gxchange.sendmoney.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName("ROLE_UNVERIFIED") == null)
            roleRepository.save(new Role("ROLE_UNVERIFIED"));

        if (roleRepository.findByName("ROLE_VERIFIED_USER") == null)
            roleRepository.save(new Role("ROLE_VERIFIED_USER"));

        if (roleRepository.findByName("ROLE_ADMIN") == null)
            roleRepository.save(new Role("ROLE_ADMIN"));
    }
}