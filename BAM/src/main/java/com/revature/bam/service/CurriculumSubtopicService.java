package com.revature.bam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.bam.bean.Curriculum;
import com.revature.bam.bean.CurriculumSubtopic;
import com.revature.bam.repository.CurriculumSubtopicRepository;

@Service
public class CurriculumSubtopicService {

	@Autowired
	CurriculumSubtopicRepository curriculumSubtopic;
	
	public List<CurriculumSubtopic> getCurriculumSubtopicForCurriculum(Curriculum c){
		return curriculumSubtopic.findByCurriculum(c);
	}
	
	public void saveCurriculumSubtopic(CurriculumSubtopic cs){
		curriculumSubtopic.save(cs);
	}
	
}
