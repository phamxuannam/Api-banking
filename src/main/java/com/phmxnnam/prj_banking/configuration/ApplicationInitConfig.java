package com.phmxnnam.prj_banking.configuration;

import com.phmxnnam.prj_banking.dto.request.RoleRequest;
import com.phmxnnam.prj_banking.entity.CustomerEntity;
import com.phmxnnam.prj_banking.entity.RoleEntity;
import com.phmxnnam.prj_banking.entity.UserEntity;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.repository.CustomerRepository;
import com.phmxnnam.prj_banking.repository.RoleRepository;
import com.phmxnnam.prj_banking.repository.UserRepository;
import com.phmxnnam.prj_banking.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ApplicationInitConfig {

    RoleRepository roleRepository;
    IRoleService roleService;
    PasswordConfig passwordConfig;
    CustomerRepository customerRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(!roleRepository.existsById("ADMIN")){
                roleService.create(new RoleRequest("ADMIN","role admin of app"));
            }
            if(!userRepository.existsByUsername("admin")){
                RoleEntity role = roleRepository.findById("ADMIN").orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_EXIST));
                Set<RoleEntity> setRole = new HashSet<>();
                setRole.add(role);

                CustomerEntity customer = CustomerEntity.builder()
                        .fullName("ADMIN")
                        .dob(LocalDate.ofEpochDay(1990-12-03))
                        .build();
                customerRepository.save(customer);

                UserEntity user = UserEntity.builder()
                        .username("admin")
                        .password(passwordConfig.passwordEncoder().encode("admin"))
                        .roles(setRole)
                        .isActive(1)
                        .customer(customer)
                        .build();

                userRepository.save(user);
            }
        };

    }
}
