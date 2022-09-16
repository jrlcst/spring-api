package com.jrlcst.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrlcst.data.vo.v1.PersonVO;
import com.jrlcst.data.vo.v2.PersonVOV2;
import com.jrlcst.exceptions.ResourceNotFoundException;
import com.jrlcst.mapper.DozerMapper;
import com.jrlcst.mapper.custom.PersonMapper;
import com.jrlcst.model.Person;
import com.jrlcst.repository.PersonRepository;

@Service
public class PersonService {
	
	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;

	public List<PersonVO> findAll() {

		logger.info("Finding all people!");

		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id) {
		
		logger.info("Finding one person!");
		
		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO create(PersonVO personVO) {

		logger.info("Creating one person!");
		
		var entity = DozerMapper.parseObject(personVO, Person.class); 
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	/**
	 * versao 2, adição de campo birthday
	 * @param personVOV2
	 * @return
	 */
	public PersonVOV2 createV2(PersonVOV2 personVOV2) {

		logger.info("Creating one person, with v2!");
		
		var entity = mapper.convertVOtoEntity(personVOV2); 
		var vo =  mapper.convertEntityToVO(repository.save(entity));
		return vo;
	}
	
	public PersonVO update(PersonVO personVO) {
		
		logger.info("Updating one person!");
		
		var entity = repository.findById(personVO.getId())
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setFirstName(personVO.getFirstName());
		entity.setLastName(personVO.getLastName());
		entity.setAddress(personVO.getAddress());
		entity.setGender(personVO.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one person!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}
}
