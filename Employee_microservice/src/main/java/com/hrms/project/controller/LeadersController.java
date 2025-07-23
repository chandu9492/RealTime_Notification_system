//package com.hrms.project.controller;
//
//
//import jakarta.validation.Valid;
//import org.hrm.Payloads.LeaderDTO;
//import org.hrm.Payloads.LeaderResponse;
//import org.hrm.service.LeaderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class LeadersController {
//
//    @Autowired
//    private LeaderService leaderService;
//
//    @GetMapping("/get/leaders/details")
//    public ResponseEntity<List<LeaderResponse>>  getAllLeaders() {
//        List<LeaderResponse> alleaders = leaderService.getAllLeaders();
//        return new ResponseEntity<>(alleaders, HttpStatus.OK);
//    }
//
//    @GetMapping("/get/leader/byRole/{role}")
//    public ResponseEntity<List<LeaderResponse>> getByRole(@PathVariable String role)
//        {
//            List<LeaderResponse> leadersByRole=leaderService.getLeadersByRole(role);
//            return new ResponseEntity<>(leadersByRole, HttpStatus.OK);
//        }
//}
