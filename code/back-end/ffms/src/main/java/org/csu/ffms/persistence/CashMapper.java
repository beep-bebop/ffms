package org.csu.ffms.persistence;

import org.csu.ffms.domain.Disburse;
import org.springframework.stereotype.Repository;

@Repository
public interface CashMapper {
    int totalDisbursement(String userId);
    int totalDisbursementByFamily(String userId);
    int totalIncome(String userId);
    int totalIncomeByFamily(String userId);

}
