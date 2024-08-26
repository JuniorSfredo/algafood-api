package com.juniorsfredo.algafood_api.domain.repository;

import com.juniorsfredo.algafood_api.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
