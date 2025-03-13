package com.memourmoney.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memourmoney.common.utils.ResponseResult;
import com.memourmoney.dto.BillsDTO;
import com.memourmoney.model.Bills;
import com.memourmoney.service.MainMenuServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
@ResponseBody
public class BillsController {
    private final MainMenuServiceImpl mainMenuService;

    @PostMapping("/add")
    public ResponseResult<BillsDTO> addBills(@RequestBody BillsDTO billsDTO) {
        try {
            mainMenuService.addBills(billsDTO);
            return ResponseResult.success(billsDTO);
        } catch (Exception e) {
            return ResponseResult.error();
        }
    }

    @DeleteMapping("/delete")
    public ResponseResult<Long> deleteBills(@RequestParam Long id) {
        try {
            mainMenuService.deleteBills(id);
            return ResponseResult.success(id);
        } catch (Exception e) {
            return ResponseResult.error();
        }
    }

    @GetMapping("/select-all")
        public ResponseResult<List<BillsDTO>> getAllBills() {
        try {
            return ResponseResult.success(mainMenuService.selectAll());
        } catch (Exception e) {
            return ResponseResult.error();
        }
    }

    @GetMapping("/select-by-type")
    public ResponseResult<List<BillsDTO>> getBillsByType(@RequestBody String type) {
        try {
            return ResponseResult.success(mainMenuService.selectByType(type));
        } catch (Exception e) {
            return ResponseResult.error();
        }
    }

    @GetMapping("/select-by-time")
    public ResponseResult<List<BillsDTO>> getBillsByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endTime
    ) {
        try {
            return ResponseResult.success(mainMenuService.selectByTime(startTime, endTime));
        } catch (Exception e) {
            return ResponseResult.error();
        }
    }
}