package com.ajitata.beginningspringbatch.batch;

import com.ajitata.beginningspringbatch.dto.PayrollDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PayrollItemProcessor implements ItemProcessor<PayrollDTO, PayrollDTO> {
    private static final Logger log = LoggerFactory.getLogger(PayrollItemProcessor.class);

    @Override
    public PayrollDTO process(PayrollDTO item) {
        final PayrollDTO resultTransformation = new PayrollDTO();
        resultTransformation.setFirstName(item.getFirstName().toUpperCase());
        resultTransformation.setLastName(item.getLastName().toUpperCase());
        resultTransformation.setDescription(item.getDescription().toUpperCase());
        resultTransformation.setAccountNumber(item.getAccountNumber());
        resultTransformation.setAccountType(item.getAccountType());
        resultTransformation.setCurrency(item.getCurrency());
        resultTransformation.setIdentification(item.getIdentification());
        resultTransformation.setAmount(item.getAmount());
        log.info("Transforming (" + item + ") into (" + resultTransformation + ")");

        return resultTransformation;
    }

}
