package com.language.service.repo.permission;

import java.util.List;

public interface PermissionRepoCustom {
    boolean hasPermission(long userId, String permissionCode);

    List<String> allPermCode(long userId);
}
