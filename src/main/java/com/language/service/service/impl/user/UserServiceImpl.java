package com.language.service.service.impl.user;

import com.language.service.common.Constants;
import com.language.service.common.ConstantsErrorCode;
import com.language.service.domain.dtos.GroupDTO;
import com.language.service.domain.dtos.GroupUserDTO;
import com.language.service.domain.dtos.UserDTO;
import com.language.service.domain.entities.Group;
import com.language.service.domain.entities.User;
import com.language.service.domain.entities.UserGroupMap;
import com.language.service.domain.mapper.UserMapper;
import com.language.service.exception.BusinessException;
import com.language.service.repo.group.GroupDTORepo;
import com.language.service.repo.group.GroupRepo;
import com.language.service.repo.jpa.usergroupmap.UserGroupMapRepo;
import com.language.service.repo.user.UserRepo;
import com.language.service.rest.dto.request.*;
import com.language.service.rest.dto.request.searchparams.GroupSearchParams;
import com.language.service.rest.dto.response.BaseResponseDTO;
import com.language.service.service.AbstractService;
import com.language.service.service.abs.user.UserService;
import com.language.service.storage.StorageService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl extends AbstractService<User, Long> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepo userRepo;
    private final GroupRepo groupRepo;
    private final UserGroupMapRepo userGroupMapRepo;
    private final GroupDTORepo groupDTORepo;
    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;
    private final UserMapper userMapper;

    @Value("${sftp.server}")
    private String sftpServer;

    @Value("${sftp.username}")
    private String sftpUsername;

    @Value("${sftp.password}")
    private String sftpPassword;

    @Value("${sftp.port}")
    private int sftpPort;

    @Value("${sftp.file.url}")
    private String fileUrl;

    @Value("${sftp.publicBaseUrl}")
    private String publicBaseUrl;

    public UserServiceImpl(JpaRepository<User, Long> repo, UserRepo userRepo, GroupRepo groupRepo, UserGroupMapRepo userGroupMapRepo, GroupDTORepo groupDTORepo, PasswordEncoder passwordEncoder, StorageService storageService, UserMapper userMapper) {
        super(repo);
        this.userRepo = userRepo;
        this.groupRepo = groupRepo;
        this.userGroupMapRepo = userGroupMapRepo;
        this.groupDTORepo = groupDTORepo;
        this.passwordEncoder = passwordEncoder;
        this.storageService = storageService;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByUsername(String username, int deleted) {
        return userRepo.findByUsername(username, deleted);
    }


    @Override
    public UserDTO findByIdReturnDTO(Long id) {
        Optional<User> optUser = userRepo.findById(id, Constants.DELETE.INACTIVE);
        if (optUser.isPresent()) {
            User user = optUser.get();
            return userMapper.toDto(user);
        }
        return null;
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return userRepo.findByPhone(phone);
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO update(UpdateUserRequest cmd) throws IOException {
        try {
            User authUserUpdate = Mappers.getMapper(UserMapper.class).update(cmd);
            User user = userRepo.findById(cmd.getId()).orElseThrow(() -> new BusinessException(ConstantsErrorCode.USER.ERROR_USER_NOT_FOUND));
            user.setPhone(authUserUpdate.getPhone());
            user.setEmail(authUserUpdate.getEmail());
            user.setFullName(authUserUpdate.getFullName());
            user.setDob(authUserUpdate.getDob());
            user.setGender(authUserUpdate.getGender());
            user.setUsername(authUserUpdate.getUsername());
            user.setEnabled(authUserUpdate.isEnabled());
            if (cmd.getAvatar() != null && !cmd.getAvatar().isEmpty()) {
                String avatarUrl = uploadToSFTP(cmd.getAvatar());
                user.setAvatar(avatarUrl);
            }

            userRepo.save(user);
            return new BaseResponseDTO("success", 200);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public UserDTO create(CreateUserRequest cmd) throws IOException {
        try {
            User authUser = Mappers.getMapper(UserMapper.class).create(cmd);
            List<User> username = userRepo.checkUserExist(cmd.getUsername(), null);
            if (!username.isEmpty()) {
                throw new BusinessException(ConstantsErrorCode.USER.ERROR_USER_EXIST);
            }
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(cmd.getPassword());
            authUser.setPassword(hashedPassword);
            authUser.setDeleted(Constants.DELETE.INACTIVE);

            if (cmd.getAvatar() != null && !cmd.getAvatar().isEmpty()) {
                String avatarUrl = uploadToSFTP(cmd.getAvatar());
                authUser.setAvatar(avatarUrl);
            }

            userRepo.save(authUser);
            return Mappers.getMapper(UserMapper.class).toDto(authUser);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private String uploadToSFTP(MultipartFile file) throws IOException {
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            logger.info("Connecting to SFTP server: {}:{}", sftpServer, sftpPort);
            session = jsch.getSession(sftpUsername, sftpServer, sftpPort);
            session.setPassword(sftpPassword);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            logger.info("SFTP session connected");

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            logger.info("SFTP channel opened");

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String remotePath = fileUrl + "/" + fileName;
            logger.info("Uploading file to: {}", remotePath);

            try (InputStream inputStream = file.getInputStream()) {
                channelSftp.put(inputStream, remotePath);
            }
            logger.info("File uploaded successfully: {}", fileName);

            logger.info("Generated avatar URL: {}", publicBaseUrl + "/" + fileName);
            return publicBaseUrl + "/" + fileName;
        } catch (JSchException | SftpException e) {
            throw new IOException("Failed to upload to SFTP: " + e.getMessage(), e);
        } finally {
            if (channelSftp != null) {
                channelSftp.disconnect();
                logger.info("SFTP session disconnected");
            }
            if (session != null) {
                session.disconnect();
                logger.info("SFTP session disconnected");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO deleteUser(Long id) {
        try {
            User entity = userRepo.findById(id, Constants.DELETE.INACTIVE).orElseThrow(() -> new BusinessException(ConstantsErrorCode.USER.ERROR_USER_NOT_FOUND));
            entity.setEnabled(false);
            entity.setDeleted(Constants.DELETE.ACTIVE);
            return new BaseResponseDTO();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private List<UserGroupMap> getUserGroupMaps(List<Long> grs, User authUser) {
        List<Group> groups = groupRepo.findAllById(grs);
        List<UserGroupMap> userGroups = new ArrayList<>();
        for (Group authGroup : groups) {
            UserGroupMap userGroupMap = new UserGroupMap();
            userGroupMap.setUserId(authUser.getId());
            userGroupMap.setGroupId(authGroup.getId());
            userGroupMap.setStatus(Constants.STATUS.ACTIVE);
            userGroupMap.setDeleted(Constants.DELETE.INACTIVE);
            userGroups.add(userGroupMap);
        }
        return userGroups;
    }


    @Override
    public boolean existsByUsername(String username) {
        Optional<User> optUser = userRepo.findByUsername(username, Constants.DELETE.INACTIVE);
        return optUser.isPresent();
    }

    @Override
    public List<GroupDTO> getGroupsOfUser(long userId) {
        return groupRepo.getGroupsOfUser(userId);
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void unlinkGroup(long userId, UnlinkUserGroupRequest command) {
        userRepo.unlinkGroup(userId, command);
    }

    @Override
    public List<GroupDTO> getNotLinkedGroups(long userId) {
        Optional<User> optUser = userRepo.findById(userId);
        if (optUser.isPresent()) {
            List<Long> currentGroupIds = userRepo.getGroupIdsOfUser(userId);
            GroupSearchParams params = new GroupSearchParams();
            params.setIdNotIn(currentGroupIds);
            Page<GroupDTO> page = groupDTORepo.search(params, PageRequest.of(0, Integer.MAX_VALUE), false);
            return page.getContent();
        }
        return Collections.emptyList();
    }


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void linkGroups(long userId, LinkGroupsRequest command) {
        userRepo.linkGroups(userId, command);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO changeUserPassword(ChangeUserPasswordRequest command) {
        long userId = command.getId();
        Optional<User> optUser = userRepo.findById(userId);

        if (optUser.isEmpty()) {
            throw new BusinessException(ConstantsErrorCode.USER.ERROR_USER_NOT_FOUND);
        }

        User user = optUser.get();

        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(command.getPasswordOld(), user.getPassword())) {
            throw new BusinessException(ConstantsErrorCode.USER.ERROR_USER_PASSWORD_INCORRECT);
        }

        // Kiểm tra mật khẩu mới và cũ không được trùng nhau
        if (command.getPassword().equals(command.getPasswordOld())) {
            throw new BusinessException(ConstantsErrorCode.USER.ERROR_USER_PASSWORD_OLD_SAME_NEW);
        }

        // Mã hóa và cập nhật mật khẩu mới
        String encryptedPassword = passwordEncoder.encode(command.getPassword());
        user.setPassword(encryptedPassword);
        userRepo.save(user);

        return new BaseResponseDTO();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO changeUserPasswordByUser(ChangeUserPasswordRequestByUser command) {
        long userId = command.getId();
        Optional<User> optUser = userRepo.findById(userId);
        if (optUser.isPresent()) {
            User user = optUser.get();
            String encryptedPassword = user.getPassword();
            boolean passwordsMatch = passwordEncoder.matches(command.getOldPassword(), encryptedPassword);
            if (!passwordsMatch) {
                throw new BusinessException(ConstantsErrorCode.USER.ERROR_USER_PASSWORD);
            }
            user.setPassword(passwordEncoder.encode(command.getNewPassword()));
        } else {
            throw new BusinessException(ConstantsErrorCode.USER.ERROR_USER_NOT_FOUND);
        }
        return new BaseResponseDTO();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<GroupUserDTO> groupUserDTOS(List<Long> userIds) {
        return userGroupMapRepo.groupUserDTOS(userIds);
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public BaseResponseDTO updateUserProfile(UpdateUserProfileRequest cmd) {
        try {
            User entity = userRepo.findById(cmd.getId()).orElseThrow(() -> new BusinessException(ConstantsErrorCode.USER.ERROR_USER_NOT_FOUND));
            entity.setFullName(cmd.getFullName());
            entity.setEmail(cmd.getEmail());
            entity.setPhone(cmd.getPhone());
            return new BaseResponseDTO();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public List<UserDTO> getAllUser() {
        try {
            List<UserDTO> list = userMapper.toDto(userRepo.getAllUer(Constants.DELETE.INACTIVE));
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> optionalUser = userRepo.findById(id, Constants.DELETE.INACTIVE);
        User user = optionalUser.orElseThrow(()
                -> new BusinessException(ConstantsErrorCode.USER.ERROR_USER_NOT_FOUND)
        );
        return userMapper.toDto(user);
    }
}
