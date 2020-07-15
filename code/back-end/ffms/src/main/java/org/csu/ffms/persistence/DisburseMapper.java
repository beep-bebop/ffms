package org.csu.ffms.persistence;

import org.csu.ffms.domain.Disburse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisburseMapper {

    void newDisburse(Disburse disburse);
    void deleteDisburse(int disburseId);
    void updateDisburse(Disburse disburse);
    List<Disburse> findDisburseList(Disburse disburse);
    List<Disburse> findDisburseListByUser(Disburse disburse);
    List<String> findFamilyMember(String userId);
    String findFamily(String userId);
    int totalDisbursement(Disburse disburse);
    int totalDisburseByTypeAndWeek(Disburse disburse);

}
