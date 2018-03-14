package com.revature.bam.bean.unit;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToStringFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.revature.bam.bean.Curriculum;

/**
 * @author Ramses
 * Testing the Curriculum Bean's setter and getters, no-args constructor
 * and toString method using JUnit.
 */
public class CurriculumTest {

	//PASS: Ensures a bean has a working no-args constructor.
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(Curriculum.class, hasValidBeanConstructor());
    }
    //PASS: Ensure all properties on the bean have working getters and setters.
    @Test
    public void gettersAndSettersShouldWorkForEachProperty() {
        assertThat(Curriculum.class, hasValidGettersAndSetters());
    }
    //PASS: Ensure all properties on the bean are included in the string value.
    @Test
    public void allPropertiesShouldBeRepresentedInToStringOutput() {
        assertThat(Curriculum.class, hasValidBeanToStringFor("curriculumNumberOfWeeks"));
        assertThat(Curriculum.class, hasValidBeanToStringFor("curriculumName"));
        assertThat(Curriculum.class, hasValidBeanToStringFor("curriculumVersion"));
        assertThat(Curriculum.class, hasValidBeanToStringFor("curriculumCreator"));
        assertThat(Curriculum.class, hasValidBeanToStringFor("curriculumModifier"));
        assertThat(Curriculum.class, hasValidBeanToStringFor("curriculumdateCreated"));
        assertThat(Curriculum.class, hasValidBeanToStringFor("curriculumNumberOfWeeks"));
    }
}
