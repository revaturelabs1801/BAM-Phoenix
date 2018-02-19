package com.revature.bam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.bam.bean.Curriculum;
import com.revature.bam.repository.CurriculumRepository;
 
@Service
public class CurriculumService {

	@Autowired
	CurriculumRepository curriculumRepository;
	
	public List<Curriculum> getAllCurriculum(){
		List<Curriculum> curriculumList =  curriculumRepository.findAll();
		//obfuscate password
		for(Curriculum element : curriculumList){
			element.getCurriculumCreator().setPwd("");
			if(element.getCurriculumModifier() != null)
				element.getCurriculumModifier().setPwd("");
		}
		return curriculumList;
	}
	
	public Curriculum getCuricullumById(Integer id){
		//obfuscate password
		Curriculum curriculum = curriculumRepository.findById(id);
		curriculum.getCurriculumCreator().setPwd("");
		if(curriculum.getCurriculumModifier() != null)
			curriculum.getCurriculumModifier().setPwd("");
		return curriculum;
	}
	
	public void save(Curriculum c){
		curriculumRepository.save(c);
	}
	
	public List<Curriculum> findAllCurriculumByName(String name){
		
		return curriculumRepository.findByCurriculumName(name);
	}
	
	public List<Curriculum> findAllCurriculumByNameAndIsMaster(String name, Integer isMaster){
		List<Curriculum> master = curriculumRepository.findByCurriculumNameAndIsMaster(name, isMaster);
		return master;
	}
}
