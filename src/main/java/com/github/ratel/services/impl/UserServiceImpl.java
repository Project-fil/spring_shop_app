package com.github.ratel.services.impl;

import com.github.ratel.entity.Address;
import com.github.ratel.entity.Cart;
import com.github.ratel.entity.FileEntity;
import com.github.ratel.entity.User;
import com.github.ratel.entity.enums.Roles;
import com.github.ratel.entity.enums.UserVerificationStatus;
import com.github.ratel.exceptions.AppException;
import com.github.ratel.exceptions.ConfirmPasswordException;
import com.github.ratel.exceptions.EntityNotFoundException;
import com.github.ratel.exceptions.statuscode.StatusCode;
import com.github.ratel.handlers.FileHandler;
import com.github.ratel.payload.filter.StatisticFilter;
import com.github.ratel.payload.request.CreateUserRequest;
import com.github.ratel.payload.request.UserUpdateRequest;
import com.github.ratel.repositories.UserRepository;
import com.github.ratel.services.AddressService;
import com.github.ratel.services.CartService;
import com.github.ratel.services.FileService;
import com.github.ratel.services.UserService;
import com.github.ratel.utils.CheckUtil;
import com.github.ratel.utils.transfer_object.UserTransferObj;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CartService cartService;
    private final FileService fileService;
    private final FileHandler fileHandler;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    @Lazy
    public UserServiceImpl(UserRepository userRepository, CartService cartService, FileService fileService,
                           FileHandler fileHandler, AddressService addressService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.fileService = fileService;
        this.fileHandler = fileHandler;
        this.addressService = addressService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser(Principal principal) {
        if (Objects.isNull(principal)) {
            throw new AppException("Invalid parameters value: principal(%s)", principal);
        }
        return this.findUserByEmail(principal.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllUsers(Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new AppException("Invalid parameters value: pageable(%s)", pageable);
        }
        return this.userRepository.findAllByRemovedFalse(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllUsersForAdmin(Pageable pageable) {
        if (Objects.isNull(pageable)) {
            throw new AppException("Invalid parameters value: pageable(%s)", pageable);
        }
        return this.userRepository.findAllByRemovedTrue(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findTotalSpendAll(StatisticFilter filter, Pageable pageable) {
        if (ObjectUtils.anyNull(filter, pageable)) {
            throw new NullPointerException("Invalid parameters value: filter(%s), pageable(%s)");
        }
        Date dateFrom;
        Date dateTo;
        if (Objects.nonNull(filter.getFromDate())) {
            dateFrom = filter.getFromDate();
        } else {
            dateFrom = Date.from(LocalDate.now().withDayOfYear(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (Objects.nonNull(filter.getToDate())) {
            dateTo = filter.getToDate();
        } else {
            dateTo = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (Objects.nonNull(filter.getUserIdSet()) && ObjectUtils.isNotEmpty(filter.getUserIdSet())) {
            return this.userRepository.findAllByUsersFilter(filter.getUserIdSet(), dateFrom, dateTo, pageable);
        }
        return this.userRepository.findAllByDateFilter(dateFrom, dateTo, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Invalid parameters value: id(%s)", id);
        }
        return this.userRepository.findByIdAndRemovedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(StatusCode.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserForAdmin(Long userId) {
        if (Objects.isNull(userId)) {
            throw new AppException("Invalid parameters value: userId(%s)", userId);
        }
        return this.userRepository.findUserByIdForAdmin(userId).orElseThrow(
                () -> new EntityNotFoundException(StatusCode.NOT_FOUND)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public User checkUserByEmail(String email) {
        if (Objects.isNull(email)) {
            throw new AppException("Invalid parameters value: email(%s)", email);
        }
        return this.userRepository.findByEmailAndRemovedFalse(email).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean findUserByRole(Roles role) {
        if (Objects.isNull(role)) {
            throw new AppException("Invalid parameters value: role(%s)", role);
        }
        User user = this.userRepository.findUserByRolesAndRemovedFalse(role).orElse(null);
        return user != null;
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        if (Objects.isNull(email)) {
            throw new AppException("Invalid parameters value: email(%s)", email);
        }
        return userRepository.findByEmailAndRemovedFalse(email)
                .orElseThrow(() -> new EntityNotFoundException(StatusCode.NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmailAndPassword(String email, String password) {
        if (ObjectUtils.anyNull(email, password)) {
            throw new AppException("Invalid parameters value: email or password");
        }
        User user = this.findUserByEmail(email);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new ConfirmPasswordException(("Wrong password"));
    }

    @Override
    @Transactional
    public User createUser(Roles role, CreateUserRequest payload) {
        if (ObjectUtils.anyNull(role, payload)) {
            throw new AppException("Invalid parameters value: role(%s) or CreateUserRequest", role);
        }
        CheckUtil.checkUserByEmail(this.checkUserByEmail(payload.getEmail()));
        CheckUtil.checkPassAndConfirmPass(payload.getPassword(), payload.getConfirmPassword());
        Address address = new Address();
        address.setPhone(payload.getPhone());
        address = this.addressService.save(address);
        User user = new User();
        user.setFirstname(payload.getFirstname());
        user.setLastname(payload.getLastname());
        user.setEmail(payload.getEmail());
        user.setPassword(this.passwordEncoder.encode(payload.getPassword()));
        user.setVerification(UserVerificationStatus.UNVERIFIED);
        user.setRoles(role);
        user.setAddress(address);
        this.userRepository.save(user);
        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(this.cartService.create(cart));
        return user;
    }

    @Override
    @Transactional
    public User editUser(UserUpdateRequest updateRequest, MultipartFile image) {
        if (ObjectUtils.anyNull(image, updateRequest)) {
            throw new AppException("Invalid parameters value: image(%s) or UserUpdateRequest", image);
        }
        User user = this.findById(updateRequest.getId());
        UserTransferObj.updateUser(user, updateRequest);
        FileEntity fileEntity = null;
        if (Objects.nonNull(image)) {
            fileEntity = this.fileService.create(this.fileHandler.writeFile(image));
            if (Objects.nonNull(user.getFileEntity())) this.fileService.deleteById(user.getFileEntity().getId());
        }
        user.setFile(fileEntity);
        return this.updateUser(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (Objects.isNull(user)) {
            throw new AppException("Invalid parameters value: user(%s)", user);
        }
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        if (Objects.isNull(userId)) {
            throw new AppException("Invalid parameters value: userId(%s)", userId);
        }
        this.cartService.deleteCartByUserId(userId);
        this.userRepository.deleteById(userId);
    }

}
