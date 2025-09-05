package com.phmxnnam.prj_banking.service.implement;

import com.phmxnnam.prj_banking.dto.request.PermissionRequest;
import com.phmxnnam.prj_banking.dto.response.PermissionResponse;
import com.phmxnnam.prj_banking.entity.PermissionEntity;
import com.phmxnnam.prj_banking.exception.AppException;
import com.phmxnnam.prj_banking.exception.ErrorCode;
import com.phmxnnam.prj_banking.mapper.PermissionMapper;
import com.phmxnnam.prj_banking.repository.PermissionRepository;
import com.phmxnnam.prj_banking.service.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        if(permissionRepository.existsById(request.getName())) throw
                new AppException(ErrorCode.PERMISSION_EXISTED);
        PermissionEntity permission = permissionMapper.toEntity(request);
        permission.setIsActive(1);

        return permissionMapper.toResponse(permissionRepository.save(permission));
    }

    @Override
    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll().stream()
                .map(permission -> permissionMapper.toResponse(permission))
                .toList();
    }

    @Override
    public PermissionResponse getById(String id) {
        PermissionEntity permission = permissionRepository.findById(id).orElseThrow( () ->
                new AppException(ErrorCode.PERMISSION_NOT_EXISTS));

        return permissionMapper.toResponse(permission);
    }

    @Override
    public String turnOnOffByIf(String id) {
        PermissionEntity permission = permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTS));
        boolean on = true; int active = 1;
        if(permission.getIsActive() == 1){
            on = false;
            active = 0;
        }
        permission.setIsActive(active);
        permissionRepository.save(permission);
        if(on) return "turned on permission.";
        else return "turned off permission.";
    }

    @Override
    public String deleteById(String id) {
        PermissionEntity permission = permissionRepository.findById(id).orElseThrow( () ->
                new AppException(ErrorCode.PERMISSION_NOT_EXISTS));
        permission.getRoles().clear();
        permissionRepository.save(permission);

        permissionRepository.delete(permission);
        return "deleted permission.";
    }

}
