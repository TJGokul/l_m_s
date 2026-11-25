package com.example.library_management_system.controller;
import com.example.library_management_system.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/dashboard")
@CrossOrigin(origins="*")
public class DashboardController {
    private final BookRepository books; private final MemberRepository members; private final BorrowRecordRepository records;
    public DashboardController(BookRepository b,MemberRepository m,BorrowRecordRepository r){books=b;members=m;records=r;}
    @GetMapping public Map<String,Long> stats(){
        long total=books.findAll().stream().mapToLong(x->x.getQuantity()).sum();
        long available=books.findAll().stream().mapToLong(x->x.getAvailableQuantity()).sum();
        long issued=records.findAll().stream().filter(x->"ISSUED".equals(x.getStatus())).count();
        return Map.of("totalTitles",books.count(),"totalCopies",total,"availableCopies",available,"issuedRecords",issued,"members",members.count());
    }
}