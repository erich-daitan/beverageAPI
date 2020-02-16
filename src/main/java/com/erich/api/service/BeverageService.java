package com.erich.api.service;

import com.erich.api.exception.BeverageServiceException;
import com.erich.api.model.Beverage;
import com.erich.api.repository.BeverageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeverageService {

    private BeverageRepository beverageRepository;

    @Autowired
    public BeverageService(BeverageRepository beverageRepository) {
        this.beverageRepository = beverageRepository;
    }

    public Iterable<Beverage> findAll() {
        return beverageRepository.findAllByOrderByTimestampAsc();
    }

    public Iterable<Beverage> findAllBySection(Integer section) throws BeverageServiceException {
        if(section == null || section <= 0) {
            throw new BeverageServiceException("Section field cannot be empty or less than 0");
        }
        if(section > 5) {
            throw new BeverageServiceException("There are only 5 sections available");
        }
        return beverageRepository.findAllBySection(section);
    }

    public Beverage addBeverage(Beverage beverage) throws BeverageServiceException {

        if(StringUtils.isBlank(beverage.getName())) {
            throw new BeverageServiceException("Name field cannot be empty");
        }
        if(beverage.getVolumeInLiters() == null || beverage.getVolumeInLiters() <= 0) {
            throw new BeverageServiceException("VolumeInLiters field cannot be empty or less than 0");
        }
        if(beverage.getSection() == null || beverage.getSection() <= 0) {
            throw new BeverageServiceException("Section field cannot be empty or less than 0");
        }
        if(StringUtils.isBlank(beverage.getResponsibleForStorage())) {
            throw new BeverageServiceException("ResponsibleForStorage field cannot be empty");
        }
        if(beverage.getSection() > 5) {
            throw new BeverageServiceException("There are only 5 sections available");
        }
        if(beverage.isAlcoholic()) {
            if(beverageRepository.findAllByAlcoholicAndSection( false, beverage.getSection()).size() > 0) {
                throw new BeverageServiceException("There`s already non-alcoholic beverage stored in this section, only non-alcoholic is allowed now");
            }

            int availableStorage = calculateAvailableLittlers(beverage.isAlcoholic() ,beverageRepository.getAllVolumenInLittersBySection(beverage.getSection()));

            if(availableStorage < beverage.getVolumeInLiters()) {
                String errMsg = String.format("There`s no space available in this section. Only %d litters are available", availableStorage);
                throw new BeverageServiceException(errMsg);
            }

        } else {
            if(beverageRepository.findAllByAlcoholicAndSection( true, beverage.getSection()).size() > 0) {
                throw new BeverageServiceException("There`s already alcoholic beverage stored in this section, only alcoholic is allowed now");
            }

            int availableStorage = calculateAvailableLittlers(beverage.isAlcoholic() ,beverageRepository.getAllVolumenInLittersBySection(beverage.getSection()));

            if(availableStorage < beverage.getVolumeInLiters()) {
                String errMsg = String.format("There`s no space available in this section. Only %d litters are available", availableStorage);
                throw new BeverageServiceException(errMsg);
            }
        }
        beverage.setTimestamp(System.currentTimeMillis());
        return beverageRepository.save(beverage);
    }

    public Integer getAvailableStorageInLitters(Integer section) throws BeverageServiceException {
        if(section == null || section <= 0) {
            throw new BeverageServiceException("Section field cannot be empty or less than 0");
        }
        if(section > 5) {
            throw new BeverageServiceException("There are only 5 sections available");
        }

        if(beverageRepository.findAllByAlcoholicAndSection( true, section).size() > 0) {
            return calculateAvailableLittlers(true, beverageRepository.getAllVolumenInLittersBySection(section));
        } else {
            return calculateAvailableLittlers(false, beverageRepository.getAllVolumenInLittersBySection(section));
        }
    }

    private Integer calculateAvailableLittlers(boolean isAlcoholic, List<Integer> previousStored) {
        int sum = 0;
        for (int i: previousStored) {
            sum += i;
        }
        if(isAlcoholic) {
            return 500 - sum;
        } else {
            return 400 - sum;
        }
    }
}
