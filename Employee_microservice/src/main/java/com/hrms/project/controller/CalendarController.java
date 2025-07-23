//package com.hrms.project.controller;
//
//import org.hrm.Payloads.CalendarDTO;
//import org.hrm.service.CalendarService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Calendar;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class CalendarController {
//
//    @Autowired
//    private CalendarService calendarService;
//
//    @GetMapping("/calender")
//    public ResponseEntity<List<CalendarDTO>> getAllCalendar() {
//        List<CalendarDTO> calenderDTO = calendarService.getAllCalender();
//        return new ResponseEntity<>(calenderDTO, HttpStatus.OK);
//
//    }
//    @PostMapping("/calender")
//    public ResponseEntity<CalendarDTO> createCalender(@RequestBody CalendarDTO calenderDTO) {
//        CalendarDTO calenderDTO1=calendarService.createCalender(calenderDTO);
//        return new ResponseEntity<>(calenderDTO1, HttpStatus.CREATED);
//
//    }
//
//    @PutMapping("/calender/{id}")
//    public ResponseEntity<CalendarDTO>updateCalender(@RequestBody CalendarDTO calenderDTO, @PathVariable Integer id) {
//        CalendarDTO calenderDTO1=calendarService.updateCalender(calenderDTO,id);
//        return new ResponseEntity<>(calenderDTO1, HttpStatus.ACCEPTED);
//    }
//
//    @DeleteMapping("/calender/{id}")
//    public ResponseEntity<CalendarDTO>deleteCalender(@PathVariable Integer id) {
//        CalendarDTO calenderDTO=calendarService.deleteCalender(id);
//        return new ResponseEntity<>(calenderDTO, HttpStatus.OK);
//    }
//
//}