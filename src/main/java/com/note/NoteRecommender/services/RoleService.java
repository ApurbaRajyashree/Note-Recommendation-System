package com.note.NoteRecommender.services;

import com.note.NoteRecommender.dto.RoleDto;

public interface RoleService {
    RoleDto createRole(RoleDto  roleDto);
    RoleDto updateRole(RoleDto roleDto,Long roleId) throws Exception;
}
