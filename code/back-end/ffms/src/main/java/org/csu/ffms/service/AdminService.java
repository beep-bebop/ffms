package org.csu.ffms.service;
import org.csu.ffms.domain.Admin;
import org.csu.ffms.persistence.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public Admin getAdmin(String adminid) {
        return adminMapper.getAdminByFamilyid(adminid);
    }

    public Admin getAdmin(String adminid, String password) {
        Admin admin = new Admin();
        admin.setAdminid(adminid);
        admin.setPassword(password);
        return adminMapper.getAdminByAdminidAndPassword(admin);
    }

    @Transactional
    public void insertAdmin(Admin admin) {
        adminMapper.insertAdmin(admin);
    }

    @Transactional
    public void updateAdmin(Admin admin) {
        adminMapper.updateAdmin(admin);
    }

    @Transactional
    public void deleteAdmin(String adminid)
    {
        adminMapper.deleteAdmin(adminid);
    }

    public List<Admin> getAllAdmin()
    {
        return adminMapper.getAllAdmin();
    }

}
