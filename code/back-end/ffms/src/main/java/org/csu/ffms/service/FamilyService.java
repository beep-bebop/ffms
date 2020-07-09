package org.csu.ffms.service;
import org.csu.ffms.domain.Family;
import org.csu.ffms.persistence.FamilyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class FamilyService {
    @Autowired
    private FamilyMapper familyMapper;

    public Family getFamily(String familyid) {
        return familyMapper.getFamilyByFamilyid(familyid);
    }

    public Family getFamily(String familyid, String familykey) {
        Family family = new Family();
        family.setFamilyid(familyid);
        family.setFamilykey(familykey);
        return familyMapper.getFamilyByFamilyandFamilykey(family);
    }


    @Transactional
    public void insertFamily(Family family) {
         familyMapper.insertFamily(family);
    }

    @Transactional
    public void updateFamily(Family family) {
        familyMapper.updateFamily(family);
    }

    @Transactional
    public void deleteFamily(String familyid)
    {
        familyMapper.deleteFamily(familyid);
    }

    public List<Family> getAllFamily()
    {
        return familyMapper.getAllFamily();
    }

}
