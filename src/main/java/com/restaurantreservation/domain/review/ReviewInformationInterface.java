package com.restaurantreservation.domain.review;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public interface ReviewInformationInterface {
    Integer getId();
    LocalDateTime getReviewtime();
    Integer getScore();
    String getDescription();
}
