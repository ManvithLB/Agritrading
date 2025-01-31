package com.agritrading.AgritradingApplication.service;

import com.agritrading.AgritradingApplication.dto.FeedbackDTO;
import com.agritrading.AgritradingApplication.model.Feedback;
import com.agritrading.AgritradingApplication.util.MapCustomerDTO;
import com.agritrading.AgritradingApplication.util.MapProductDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MapFeedbackDTO {
    public static FeedbackDTO map(Feedback feedback){
        return  FeedbackDTO.builder()
                .product(MapProductDTO.map(feedback.getProduct()))
                .customer(MapCustomerDTO.map(feedback.getCustomer()))
                .customerPhone(feedback.getCustomerPhone())
                .description(feedback.getDescription())
                .rating(feedback.getRating())
                .build();
    }

    public static List<FeedbackDTO> map(List<Feedback> feedbacks ){
        return feedbacks.stream().map(MapFeedbackDTO::map).collect(Collectors.toList());
    }
}
