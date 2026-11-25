package com.example.library_management_system.controller;
import com.example.library_management_system.entity.Member;
import com.example.library_management_system.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/members")
@CrossOrigin(origins="*")
public class MemberController {
    private final MemberService service; public MemberController(MemberService s){service=s;}
    @GetMapping public List<Member> all(@RequestParam(required=false) String search){return service.all(search);}
    @GetMapping("/{id}") public Member one(@PathVariable Long id){return service.one(id);}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Member create(@Valid @RequestBody Member m){return service.create(m);}
    @PutMapping("/{id}") public Member update(@PathVariable Long id,@Valid @RequestBody Member m){return service.update(id,m);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){service.delete(id);}
}