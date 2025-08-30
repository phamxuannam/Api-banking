package com.phmxnnam.prj_banking.service.implement;

import com.phmxnnam.prj_banking.dto.request.RoleRequest;
import com.phmxnnam.prj_banking.dto.response.RoleResponse;
import com.phmxnnam.prj_banking.entity.CustomerEntity;
import com.phmxnnam.prj_banking.entity.RoleEntity;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.mapper.RoleMapper;
import com.phmxnnam.prj_banking.repository.RoleRepository;
import com.phmxnnam.prj_banking.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    RoleRepository roleRepository;
    RoleMapper roleMapper;



    @Override
    public RoleResponse create(RoleRequest request) {
        if(roleRepository.existsById(request.getName())) throw new AppException(ErrorCode.ROLE_EXISTED);
        RoleEntity role = roleMapper.toEntity(request);
        role.setIsActive(1);

        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toResponse).toList();
    }

    @Override
    public String turnOnOffRole(String id) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_EXIST));
        role.setIsActive(0);
        roleRepository.save(role);


        return "";
    }

    @Override
    public String deleteRole(String id) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXIST));
        roleRepository.deleteById(id);
        return "Deleted Role.";
    }
}
