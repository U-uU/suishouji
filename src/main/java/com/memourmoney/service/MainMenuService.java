package com.memourmoney.service;

import com.memourmoney.dto.BillsDTO;
import com.memourmoney.model.Bills;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface MainMenuService {
    List<BillsDTO> deleteBills(Long opt);
    List<BillsDTO> addBills(BillsDTO bills);
    List<BillsDTO> selectAll();
    List<BillsDTO> selectByTime(LocalDate start, LocalDate end);
    List<BillsDTO> selectByTime(LocalDate time);
    List<BillsDTO> selectByType(String opt);
}
