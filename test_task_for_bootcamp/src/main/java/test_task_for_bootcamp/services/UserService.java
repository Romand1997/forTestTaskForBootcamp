package test_task_for_bootcamp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import test_task_for_bootcamp.model.Permission;
import test_task_for_bootcamp.model.User;
import test_task_for_bootcamp.repositories.PermissionRepository;
import test_task_for_bootcamp.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else
            return user;
    }
    public User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            User user = (User) authentication.getPrincipal();
            return user;
        } else {
            return null;
        }
    }
    public Boolean addUser(String email, String name, String password, String rePassword){
        User user = userRepository.findByEmail(email);
        if (user==null&&email!=null){
            if (password.equals(rePassword)&&password!=null){
                user = new User();
                user.setEmail(email);
                user.setFull_name(name);
                List<Permission> permissions = new ArrayList<>();
                permissions.add(permissionRepository.findByName("ROLE_USER"));
                user.setPermissions(permissions);
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
                return true;
            } else {
                return false;
            }
        } else
            return null;
    }
    public String changePassword(String oldPassword, String newPassword, String reNewPassword){
        User user = getUser();
        if (user!=null){
            if (passwordEncoder.matches(oldPassword, user.getPassword())){
                if (newPassword.equals(reNewPassword)&&newPassword!=null){
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                    return "passwordChangedSuccesfully";
                }
                return "PasswordsAreNotSame";
            }
            return "oldPasswordIncorrect";
        }
        return "userIsNotFound";
    }

}
