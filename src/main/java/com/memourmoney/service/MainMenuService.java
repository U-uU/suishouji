package com.memourmoney.service;

import com.memourmoney.dto.BillsDTO;
import com.memourmoney.model.Bills;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MainMenuService {
    void deleteBills();
    void addBills();
    void select();
}
