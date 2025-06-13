package com.aritpal.mindwell_connect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ParsedAvailabilitySlot {

    private LocalTime start;
    private LocalTime end;

}
