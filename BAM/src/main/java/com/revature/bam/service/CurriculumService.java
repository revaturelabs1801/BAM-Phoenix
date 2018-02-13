package com.revature.bam.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.bam.bean.Curriculum;
import com.revature.bam.repository.CurriculumRepository;
import com.revature.bam.repository.CurriculumSubtopicRepository;
 
@Service
public class CurriculumService {

	@Autowired
	CurriculumRepository curriculumRepository;
	
	@Autowired
	CurriculumSubtopicRepository curriculumSubtopicRepository;
	
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
	
	/**
	 * @author Carter Taylor (1712-Steve)
	 * @param id curriculumId
	 * getCuricullumByIdKeepPwd: this method is necessary when updating curriculums server side.
	 * 		Setting the curriculumCreator's password to empty throws ConstraintViolationException when 
	 * 		updating the corresponding curriculum.
	 * @return curriculum object
	 */
	public Curriculum getCuricullumByIdKeepPwd(Integer id){
		Curriculum curriculum = curriculumRepository.findById(id);
		return curriculum;
	}
	
	public void save(Curriculum c){
		curriculumRepository.save(c);
	}
	
	public List<Curriculum> findAllCurriculumByName(String name){
		return curriculumRepository.findByCurriculumName(name);
	}

	/**
	 * @author Carter Taylor, James Holzer (1712-Steve)
	 * @param Curriculum version
	 * deleteCurriculum: calls deleteCurriculumSubtopic() to delete all related CurriculumSubtopics
	 * 					and then deletes the version of a curriculum
	 */
	@Transactional
	public void deleteCurriculum(Curriculum version) 
	{
		deleteCurriculumSubtopics(version);
		curriculumRepository.delete(version);
	}
	
	/**
	 * @author Carter Taylor, James Holzer (1712-Steve)
	 * @param Curriculum version
	 * deleteCurriculumSubtopics: Deletes all CurriculumSubtopics related to a curriculum version
	 */
	@Transactional
	public void deleteCurriculumSubtopics(Curriculum version)
	{
		curriculumSubtopicRepository.deleteByCurriculum(version);
	}
	
	
}
