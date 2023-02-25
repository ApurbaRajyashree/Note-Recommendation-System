package com.note.NoteRecommender.security;


import com.note.NoteRecommender.dto.UserDto;
import com.note.NoteRecommender.entities.User;
import com.note.NoteRecommender.repositories.UserRepositories;
import com.note.NoteRecommender.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepositories userRepo;

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found"+username);
        }
        UserDto userDto=this.userService.getUserDto(user);

        UserPrincipal userPrincipal=new UserPrincipal(userDto);

        return userPrincipal;
    }
}
