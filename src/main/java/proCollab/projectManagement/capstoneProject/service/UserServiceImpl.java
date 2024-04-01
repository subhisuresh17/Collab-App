package proCollab.projectManagement.capstoneProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import proCollab.projectManagement.capstoneProject.model.Company;
import proCollab.projectManagement.capstoneProject.model.Project;
import proCollab.projectManagement.capstoneProject.model.Role;
import proCollab.projectManagement.capstoneProject.model.Task;
import proCollab.projectManagement.capstoneProject.model.User;
import proCollab.projectManagement.capstoneProject.repository.RoleRepository;
import proCollab.projectManagement.capstoneProject.repository.TaskRepository;
import proCollab.projectManagement.capstoneProject.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static final String PRO_ADMIN = "PROADMIN";
    private static final String SUPER_ADMIN = "SUPERADMIN";
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
            TaskRepository taskRepository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole(USER);
        user.setRoles(new ArrayList<>(Collections.singletonList(userRole)));
        return userRepository.save(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User changeRoleToProAdmin(User user) {
        Role proAdminRole = roleRepository.findByRole(PRO_ADMIN);
        user.setRoles(new ArrayList<>(Collections.singletonList(proAdminRole)));
        return userRepository.save(user);
    }

    @Override
    public User changeRoleToSuperAdmin(User user) {
        Role superAdminRole = roleRepository.findByRole(SUPER_ADMIN);
        user.setRoles(new ArrayList<>(Collections.singletonList(superAdminRole)));
        return userRepository.save(user);
    }

    @Override
    public User changeRoleToAdmin(User user) {
        Role adminRole = roleRepository.findByRole(ADMIN);
        user.setRoles(new ArrayList<>(Collections.singletonList(adminRole)));
        return userRepository.save(user);
    }

    @Override
    public User changeRoleToUser(User user) {
        Role userRole = roleRepository.findByRole(USER);
        user.setRoles(new ArrayList<>(Collections.singletonList(userRole)));
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean isUserEmailPresent(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.getOne(id);
        user.getTasksOwned().forEach(task -> task.setOwner(null));
        userRepository.delete(user);
    }

    public long countUsers() {
        return userRepository.count();
    }

    @Override
    public void updateProfilePic(User user, String picUrl) {
        user.setPhoto(picUrl);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }

    public User getUserByVerificationToken(String token) {
        return userRepository.findByVerificationToken(token);
    }

    public boolean isTokenExpired(User user) {
        return user.getTokenExpiryDate().isBefore(LocalDateTime.now());
    }

    public List<Project> getUserProjects(User user) {
        return user.getProjects();
    }

    @Override
    public List<User> getUsersByIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    @Override
    public List<User> createUsersFromExcel(MultipartFile file, Company company) throws IOException {
        List<User> users = new ArrayList<>();
        // Save the users to the database

        return users;
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

}
