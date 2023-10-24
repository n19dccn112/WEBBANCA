package com.n19dccn112.service;

import com.n19dccn112.model.Auth.*;
import com.n19dccn112.model.dto.UserDTO;
import com.n19dccn112.model.entity.User;
import com.n19dccn112.model.entity.UserDetail;
import com.n19dccn112.repository.*;
import com.n19dccn112.security.jwt.JwtUtils;
import com.n19dccn112.security.services.UserDetailsImpl;
import com.n19dccn112.service.Interface.IBaseService;
import com.n19dccn112.service.Interface.IModelMapper;
import com.n19dccn112.service.exception.ForeignKeyConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Service
public class UserService implements IBaseService<UserDTO, Long>, IModelMapper<UserDTO, User> {
    private final UserRepository userRepository;
    private final ProvincesRepository provincesRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;
    private final RoleRepository roleRepository;
    private final UserDetailRepository userDetailRepository;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, ProvincesRepository provincesRepository, DistrictRepository districtRepository, WardRepository wardRepository, RoleRepository roleRepository, UserDetailRepository userDetailRepository, ModelMapper modelMapper, JwtUtils jwtUtils, PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.provincesRepository = provincesRepository;
        this.districtRepository = districtRepository;
        this.wardRepository = wardRepository;
        this.roleRepository = roleRepository;
        this.userDetailRepository = userDetailRepository;
        this.modelMapper = modelMapper;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public List<UserDTO> findAll() {
        return createFromEntities(userRepository.findAll());
    }

    @Override
    public UserDTO findById(Long userId) {
        return createFromE(userRepository.findById(userId).get());
    }

    @Override
    public UserDTO update(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).get();
        userRepository.save(updateEntity(user, userDTO));
        return userDTO;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        userRepository.save(createFromD(userDTO));
        return userDTO;
    }

    @Override
    public UserDTO delete(Long userId) {
        User user = userRepository.findById(userId).get();
        try {
            userRepository.delete(user);
        }catch (ConstraintViolationException constraintViolationException){
            throw new ForeignKeyConstraintViolation(User.class, userId);
        }
        return createFromE(user);
    }

    @Override
    public User createFromD(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setRole(roleRepository.findById(userDTO.getRoleId()).get());
        try {
            userDetailRepository.findById(userDTO.getUserDetailIdDefault());
        }catch (Exception e){
            user.setUserDetailIdDefault(null);
        }
        return user;
    }

    @Override
    public UserDTO createFromE(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoleId(user.getRole().getRoleId());
        userDTO.setRoleName(user.getRole().getName());
        userDTO.setPassword("");
        try {
            userDetailRepository.findById(user.getUserDetailIdDefault());
        }catch (Exception e){
            userDTO.setUserDetailIdDefault(null);
        }
        return userDTO;
    }

    @Override
    public User updateEntity(User user, UserDTO userDTO) {
        if (user != null && userDTO != null){
            if (userDTO.getUsername() != "" && userDTO.getUsername() != null) {
                user.setUsername(userDTO.getUsername());
            }
            if (userDTO.getEmail() != "" && userDTO.getEmail() != null) {
                user.setEmail(userDTO.getEmail());
            }
            if (userDTO.getUserId() != null) {
                user.setRole(roleRepository.findById(userDTO.getUserId()).get());
            }
            if (userDTO.getUserDetailIdDefault() != null) {
                user.setUserDetailIdDefault(userDTO.getUserDetailIdDefault());
            }
            if (userDTO.getRoleId() != null){
                user.setRole(roleRepository.findById(userDTO.getRoleId()).get());
            }
            if (userDTO.getPassword() != null){
                user.setPassword(encoder.encode(userDTO.getPassword()));
            }
        }
        return user;
    }

    public ResponseEntity<JwtResponse> checkLogin(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        // if go there, the user/password is correct
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // generate jwt to return to client
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.getById(userDetails.getId());
        List<String> roles = List.of(user.getRole().getName());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getPhone(),
                userDetails.getEmail(),
                roles.get(0),
                userDetails.getName(),
                userDetails.getAddress()));
    }

    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername()) != 0) {
            return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Tên đã tồn tại!"));
        }
        if (userRepository.existsByPhone(registerRequest.getPhone()) != 0) {
            return ResponseEntity.badRequest().body(new MessageResponse("Lỗi: Số điện thoại đã tồn tại!"));
        }
        // Create new user's account
        UserDTO userDTO = modelMapper.map(registerRequest, UserDTO.class);
        userDTO.setRoleName(registerRequest.getRoleName());
        userDTO.setAddress(registerRequest.getAddress());
        userDTO.setRoleId(2L);
        save(userDTO);
        Long userId = userRepository.userIdNewSave(userDTO.getUsername());
        UserDetail userDetail = new UserDetail();
        userDetail.setUser(userRepository.findById(userId).get());
        userDetail.setAddress(userDTO.getAddress());
        userDetail.setName(userDTO.getUsername());
        userDetail.setPhone(registerRequest.getPhone());
        userDetail.setWard(wardRepository.findById(registerRequest.getWardId()).get());
        userDetail.setProvinces(provincesRepository.findById(registerRequest.getProvinceId()).get());
        userDetail.setDistrict(districtRepository.findById(registerRequest.getDistrictId()).get());
        userDetailRepository.save(userDetail);
        User user = userRepository.findById(userId).get();
        user.setUserDetailIdDefault(userDetailRepository.userDetailIdNewSave(userDTO.getUsername()));
        userRepository.save(user);
        return new ResponseEntity<>(new MessageResponse("Đăng ký thành công!"), HttpStatus.CREATED);
    }

    public ResponseEntity<?> changePass(ChangePass request) {
        if (userRepository.existsByUsername(request.getUsername()) == 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Lỗi: UserName không tìm thấy!"));
        }
        User user = userRepository.findAllByUsername(request.getUsername()).get();
        if (!encoder.matches(request.getOldPassword(), user.getPassword())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Lỗi: Password sai!"));
        }
        String passEncoder = encoder.encode(request.getPassword());
        user.setPassword(passEncoder);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Thay đổi mật khẩu thành công!"));
    }
}
