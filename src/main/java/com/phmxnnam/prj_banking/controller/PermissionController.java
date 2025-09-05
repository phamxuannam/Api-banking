package com.phmxnnam.prj_banking.controller;

import com.phmxnnam.prj_banking.dto.ApiResponse;
import com.phmxnnam.prj_banking.dto.request.PermissionRequest;
import com.phmxnnam.prj_banking.dto.response.PermissionResponse;
import com.phmxnnam.prj_banking.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .code(200)
                .message("OK")
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(200)
                .message("OK")
                .result(permissionService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<PermissionResponse> getById(@PathVariable String id){
        return ApiResponse.<PermissionResponse>builder()
                .code(200)
                .message("OK")
                .result(permissionService.getById(id))
                .build();
    }

    @PatchMapping("/{id}")
    ApiResponse<String> turnOnOffById(@PathVariable String id){
        return ApiResponse.<String>builder()
                .code(200)
                .message("OK")
                .result(permissionService.turnOnOffByIf(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteById(@PathVariable String id){
        return ApiResponse.<String>builder()
                .code(200)
                .message("OK")
                .result(permissionService.deleteById(id))
                .build();
    }
}
