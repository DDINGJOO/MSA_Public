package dding.timeManager.controller;


import dding.timeManager.dto.response.TimeSlotResponse;
import dding.timeManager.service.BandTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import java.util.List;



//TODO : IMPL !!!!!!!!!!!!!!!!!!!!! NOY YET SERVICE
@RestController
@RequestMapping("/api/timeslots/band")
@RequiredArgsConstructor
public class BandTimeSlotController {
    BandTimeSlotService bandTimeSlotService;

    @GetMapping("/{bnadId}")
    public List<TimeSlotResponse> bandCommonTimeSlot(@Param("bandId") List<String> UserIds)
    {
        bandTimeSlotService.calCommonTimeSlot(UserIds);
    }
}
