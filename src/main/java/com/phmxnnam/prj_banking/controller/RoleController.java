package com.phmxnnam.prj_banking.controller;

import com.phmxnnam.prj_banking.dto.ApiResponse;
import com.phmxnnam.prj_banking.dto.request.RoleRequest;
import com.phmxnnam.prj_banking.dto.response.RoleResponse;
import com.phmxnnam.prj_banking.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .code(200)
                .message("OK")
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .code(200)
                .message("OK")
                .result(roleService.getAll())
                .build();
    }

    @PatchMapping("/{id}")
    ApiResponse<String> turnOnOffRole(@PathVariable String id){
        return ApiResponse.<String>builder()
                .code(200)
                .message("OK")
                .result(roleService.turnOnOffRole(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteRole(@PathVariable String id){
        return ApiResponse.<String>builder()
                .code(200)
                .message("OK")
                .result(roleService.deleteRole(id))
                .build();
    }
}
