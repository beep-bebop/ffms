package org.csu.ffms.persistence;


import org.csu.ffms.domain.Income;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeMapper {
    void newIncome(Income income);
    void deleteIncome(int incomeId);
    void updateIncome(Income income);
    List<Income> findIncomeList(Income income);
    int totalIncome(Income income);
}
