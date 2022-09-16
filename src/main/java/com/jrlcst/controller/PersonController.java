package com.jrlcst.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jrlcst.data.vo.v1.PersonVO;
import com.jrlcst.data.vo.v2.PersonVOV2;
import com.jrlcst.service.PersonService;

@RestController
	@RequestMapping("/api/person")
	public class PersonController {
		
		@Autowired
		private PersonService service;
		
		@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
		public List<PersonVO> findAll() {
			return service.findAll();
		}
		
		@GetMapping(value = "/{id}",
				produces = MediaType.APPLICATION_JSON_VALUE)
		public PersonVO findById(@PathVariable(value = "id") Long id) {
			return service.findById(id);
		}
		
		@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public PersonVO create(@RequestBody PersonVO personVo) {
			return service.create(personVo);
		}
		
		/**
		 * metodo de insert o Person, via VO para versionamento v2 da api
		 * mudança = adição de data de nascimento (birthday)
		 * @param personVo
		 * @return
		 */
		@PostMapping(value = "/v2", consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public PersonVOV2 createV2(@RequestBody PersonVOV2 personVOV2) {
			return service.createV2(personVOV2);
		}
		
		@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
		public PersonVO update(@RequestBody PersonVO personVo) {
			return service.update(personVo);
		}
		
		
		@DeleteMapping(value = "/{id}")
		public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
			service.delete(id);
			return ResponseEntity.noContent().build();
		}
	}
