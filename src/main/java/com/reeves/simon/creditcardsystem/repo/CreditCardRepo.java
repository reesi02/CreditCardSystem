package com.reeves.simon.creditcardsystem.repo;

import org.springframework.data.repository.CrudRepository;
import com.reeves.simon.creditcardsystem.dto.CreditCardDTO;

public interface CreditCardRepo extends CrudRepository<CreditCardDTO,String>{

}
