package com.tirestore.tireshop.service;

import com.tirestore.tireshop.repository.TireRepository;

public class TireService {

    private final TireRepository tireRepository;

    public TireService(TireRepository tireRepository) {
        this.tireRepository = tireRepository;
    }
}
