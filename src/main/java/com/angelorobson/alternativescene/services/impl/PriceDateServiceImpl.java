package com.angelorobson.alternativescene.services.impl;

import com.angelorobson.alternativescene.entities.PriceDate;
import com.angelorobson.alternativescene.repositories.PriceDateRepository;
import com.angelorobson.alternativescene.services.PriceDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceDateServiceImpl implements PriceDateService {

    private PriceDateRepository priceDateRepository;

    @Autowired
    public PriceDateServiceImpl(PriceDateRepository priceDateRepository) {
        this.priceDateRepository = priceDateRepository;
    }

    @Override
    public PriceDate save(PriceDate priceDate) {
        return priceDateRepository.save(priceDate);
    }

}
