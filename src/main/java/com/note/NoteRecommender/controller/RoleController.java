package com.note.NoteRecommender.controller;

import com.note.NoteRecommender.dto.RoleDto;
import com.note.NoteRecommender.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @PostMapping(path = "/create",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})

    ResponseEntity<RoleDto> createRoleController(@RequestBody RoleDto roleDto){
        roleDto=roleService.createRole(roleDto);
        return new ResponseEntity<>(roleDto, HttpStatusCode.valueOf(200));
    }
    @PutMapping("/update/{roleId}")
    ResponseEntity<RoleDto> updateRoleController(@PathVariable Long roleId, @RequestBody RoleDto roleDto) throws Exception {
        roleDto=roleService.updateRole(roleDto,roleId);
        return new ResponseEntity<>(roleDto, HttpStatusCode.valueOf(200));

    }
}
