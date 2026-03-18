package com.rapidrise.task2_jwt_crud_api.repository;

import com.rapidrise.task2_jwt_crud_api.entity.File;
import com.rapidrise.task2_jwt_crud_api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,Long> {
    Page<File> findByUser(User user, Pageable pageable);
}
