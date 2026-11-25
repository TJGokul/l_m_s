package com.example.library_management_system.controller;
import com.example.library_management_system.entity.BorrowRecord;
import com.example.library_management_system.service.BorrowService;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/borrow")
@CrossOrigin(origins="*")
public class BorrowController {
    private final BorrowService service; public BorrowController(BorrowService s){service=s;}
    @GetMapping public List<BorrowRecord> all(){return service.all();}
    @PostMapping("/issue") public BorrowRecord issue(@RequestBody Map<String,Object> body){
        return service.issue(Long.valueOf(body.get("bookId").toString()),Long.valueOf(body.get("memberId").toString()),body.get("loanDays")==null?14:Integer.parseInt(body.get("loanDays").toString()));
    }
    @PutMapping("/return/{id}") public BorrowRecord giveBack(@PathVariable Long id){return service.giveBack(id);}
}