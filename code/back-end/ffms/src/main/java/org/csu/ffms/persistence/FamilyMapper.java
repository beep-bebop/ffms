package org.csu.ffms.persistence;

        import org.csu.ffms.domain.Family;
        import org.springframework.stereotype.Repository;
        import java.util.List;

@Repository
public interface FamilyMapper {
    Family getFamilyByFamilyid(String familyid);

    Family getFamilyByFamilyandFamilykey(Family family);

    void insertFamily(Family family);

    void updateFamily(Family family);

    void deleteFamily(String familyid);

    List<Family> getAllFamily();
}
