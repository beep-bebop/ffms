package org.csu.ffms.persistence;

import org.csu.ffms.domain.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {
    Admin getAdminByFamilyid(String adminid);

    Admin getAdminByAdminidAndPassword(Admin admin);

    void insertAdmin( Admin admin);

    void updateAdmin( Admin admin);

    void deleteAdmin(String adminid);

    List< Admin> getAllAdmin();
}