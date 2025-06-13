package com.aritpal.mindwell_connect.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TherapistPublicProfileResponse {

    private TherapistSummaryDto profile;
    private Map<String, List<ParsedAvailabilitySlot>> availability;

}
