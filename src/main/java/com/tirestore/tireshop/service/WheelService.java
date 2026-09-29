package com.tirestore.tireshop.service;

import com.tirestore.tireshop.repository.WheelRepository;

public class WheelService {

    private final WheelRepository wheelRepository;

    public WheelService(WheelRepository wheelRepository) {
        this.wheelRepository = wheelRepository;
    }

}
