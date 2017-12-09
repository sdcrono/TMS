package org.fsoft.tms.config;

import org.fsoft.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by thehaohcm on 5/26/17.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                //Disable CSRF for enable POST method in EmailController.java
                .csrf().disable()
                .authorizeRequests()
                //Login - Admin, Training Staff, Trainer
                .antMatchers("/").hasRole("Login")

//                //Admin
//                .antMatchers("/admin").hasRole("AccessAdminPage")
//                    //Trainer
//                .antMatchers("/admin/trainer/").hasRole("ViewSearchTrainerAccount")
//                .antMatchers("/admin/trainer/add").hasRole("CreateTrainerAccount")
//                .antMatchers("/admin/trainer/addTrainer").hasRole("CreateTrainerAccount")
//                .antMatchers("/admin/trainer/update/**").hasRole("UpdateTrainerAccount")
//                .antMatchers("/admin/trainer/updateTrainer").hasRole("UpdateTrainerAccount")
//                .antMatchers("/admin/trainer/delete/**").hasRole("DeleteTrainerAccount")
//                .antMatchers("/admin/trainer/deleteTrainer").hasRole("DeleteTrainerAccount")
                //Amin--------
                //Trainer
                .antMatchers("/tms/trainers/").hasRole("ViewSearchTrainerAccount")
                .antMatchers("/tms/trainers/add").hasRole("CreateTrainerAccount")
                .antMatchers("/tms/trainers/addAccount").hasRole("CreateTrainerAccount")
                .antMatchers("/tms/trainers/**/update").hasRole("UpdateTrainerAccount")
                .antMatchers("/tms/trainers/update").hasRole("UpdateTrainerAccount")
                .antMatchers("/tms/trainers/**/account").hasRole("UpdateTrainerAccount")
                .antMatchers("/tms/trainers/**/delete").hasRole("DeleteTrainerAccount")
//                    //Training Staff
//                .antMatchers("/admin/staff/").hasRole("ViewSearchTrainingStaffAccount")
//                .antMatchers("/admin/staff/add").hasRole("CreateTrainingStaffAccount")
//                .antMatchers("/admin/staff/addStaff").hasRole("CreateTrainingStaffAccount")
//                .antMatchers("/admin/staff/update/**").hasRole("UpdateTrainingStaffAccount")
//                .antMatchers("/admin/staff/updateStaff").hasRole("UpdateTrainingStaffAccount")
//                .antMatchers("/admin/staff/delete/**").hasRole("DeleteTrainingStaffAccount")
//                .antMatchers("/admin/staff/deleteStaff").hasRole("DeleteTrainingStaffAccount")
//                .antMatchers("/admin/trainer/addCourse").hasRole("AddTrainerToCourse")
                //Training Staff
                .antMatchers("/tms/staffs/").hasRole("ViewSearchTrainingStaffAccount")
                .antMatchers("/tms/staffs/add").hasRole("CreateTrainingStaffAccount")
                .antMatchers("/tms/staffs/addAcount").hasRole("CreateTrainingStaffAccount")
                .antMatchers("/tms/staffs/**/update").hasRole("UpdateTrainingStaffAccount")
                .antMatchers("/tms/staffs/update").hasRole("UpdateTrainingStaffAccount")
                .antMatchers("/tms/staffs/**/delete").hasRole("DeleteTrainingStaffAccount")
                .antMatchers("/tms/staffs/**/deleteanyway").hasRole("DeleteTrainingStaffAccount")
                .antMatchers("/tms/staffs/**/change").hasRole("DeleteTrainingStaffAccount")
                .antMatchers("/tms/staffs/**/managers/**/change").hasRole("DeleteTrainingStaffAccount")
                .antMatchers("/tms/trainers/addCourse").hasRole("AddTrainerToCourse")

                //Training Staff-------------
//                .antMatchers("/staff").hasRole("AccessStaffPage")
//                .antMatchers("staff/updateProfile").hasRole("EditTrainingStaffProfile")
//                    //Trainee
//                .antMatchers("/staff/trainee/").hasRole("ViewSearchTraineeAccount")
//                .antMatchers("/staff/trainee/add").hasRole("CreateTraineeAccount")
//                .antMatchers("/staff/trainee/addTrainee").hasRole("CreateTraineeAccount")
//                .antMatchers("/staff/trainee/update/**").hasRole("UpdateTraineeAccount")
//                .antMatchers("/staff/trainee/updateTrainee").hasRole("UpdateTraineeAccount")
//                .antMatchers("/staff/trainee/delete/**").hasRole("DeleteTraineeAccount")
//                .antMatchers("/staff/trainee/deleteTrainee").hasRole("DeleteTraineeAccount")
//                .antMatchers("/staff/trainee/updateProfile").hasRole("EditTraineeProfile")
                //Trainee
                .antMatchers("/tms/trainees/").hasRole("ViewSearchTraineeAccount")
                .antMatchers("/tms/trainees/search").hasRole("ViewSearchTraineeAccount")
                .antMatchers("/tms/trainees/add").hasRole("CreateTraineeAccount")
                .antMatchers("/tms/trainees/addTrainee").hasRole("CreateTraineeAccount")
                .antMatchers("/tms/trainees/**/update").hasRole("UpdateTraineeAccount")
                .antMatchers("/tms/trainees/update").hasRole("UpdateTraineeAccount")
                .antMatchers("/tms/trainees/**/profile").hasRole("UpdateTraineeAccount")
                .antMatchers("/tms/trainees/**/delete").hasRole("DeleteTraineeAccount")
                .antMatchers("/tms/trainees/**/recover").hasRole("DeleteTraineeAccount")
                //Trainer
                .antMatchers("/tms/trainers/view").hasRole("EditTrainerProfile")
                .antMatchers("/tms/trainers/**/profile").hasRole("EditTrainerProfile")
                .antMatchers("/tms/trainers/**/profile/updateProfile").hasRole("EditTrainerProfile")
                .antMatchers("/tms/trainers/updateProfile").hasRole("EditTrainerProfile")
//                    //Course
//                .antMatchers("/staff/course/").hasRole("ViewSearchCourse")
//                .antMatchers("/staff/course/add").hasRole("AddCourse")
//                .antMatchers("/staff/course/addCourse").hasRole("AddCourse")
//                .antMatchers("/staff/course/update/**").hasRole("UpdateCourse")
//                .antMatchers("/staff/course/updateCourse").hasRole("UpdateCourse")
//                .antMatchers("/staff/course/delete/**").hasRole("DeleteCourse")
//                .antMatchers("/staff/course/deleteCourse").hasRole("DeleteCourse")
                //Course
                .antMatchers("/tms/courses/").hasRole("ViewSearchCourse")
                .antMatchers("/tms/courses/search").hasRole("ViewSearchCourse")
                .antMatchers("/tms/courses/add").hasRole("AddCourse")
                .antMatchers("/tms/courses/addCourse").hasRole("AddCourse")
                .antMatchers("/tms/courses/**/update").hasRole("UpdateCourse")
                .antMatchers("/tms/courses/update").hasRole("UpdateCourse")
                .antMatchers("/tms/courses/**/delete").hasRole("DeleteCourse")
                .antMatchers("/tms/courses/**/trainees").hasRole("AssignTraineeToCouse")
                .antMatchers("/tms/courses/**/trainees/assignment").hasRole("AssignTraineeToCouse")
                .antMatchers("/tms/courses/**/trainees/**/assign").hasRole("AssignTraineeToCouse")
                .antMatchers("/tms/courses/**/trainees/**/delete").hasRole("AssignTraineeToCouse")
                .antMatchers("/tms/trainees/excel").hasRole("ImportExcelFile")
                .antMatchers("/tms/trainees/excel/*").hasRole("ImportExcelFile")
//                    //Category
//                .antMatchers("/staff/category/").hasRole("ViewSearchCourseCategory")
//                .antMatchers("/staff/category/add").hasRole("AddCourseCategory")
//                .antMatchers("/staff/category/addCategory").hasRole("AddCourseCategory")
//                .antMatchers("/staff/category/update/**").hasRole("UpdateCourseCategory")
//                .antMatchers("/staff/category/updateCategory").hasRole("UpdateCourseCategory")
//                .antMatchers("/staff/category/delete/**").hasRole("DeleteCourseCategory")
//                .antMatchers("/staff/category/deleteCategory").hasRole("DeleteCourseCategory")
                //Category
                .antMatchers("/tms/categories/").hasRole("ViewSearchCourseCategory")
                .antMatchers("/tms/categories/search").hasRole("ViewSearchCourseCategory")
                .antMatchers("/tms/categories/add").hasRole("AddCourseCategory")
                .antMatchers("/tms/categories/addCategory").hasRole("AddCourseCategory")
                .antMatchers("/tms/categories/**/update").hasRole("UpdateCourseCategory")
                .antMatchers("/tms/categories/update").hasRole("UpdateCourseCategory")
                .antMatchers("/tms/categories/**/delete").hasRole("DeleteCourseCategory")
//                    //Topic
//                .antMatchers("/staff/topic/").hasRole("ViewSearchCourseTopic")
//                .antMatchers("/staff/topic/add").hasRole("AddCourseTopic")
//                .antMatchers("/staff/topic/addTopic").hasRole("AddCourseTopic")
//                .antMatchers("/staff/topic/update/**").hasRole("UpdateCourseTopic")
//                .antMatchers("/staff/topic/updateTopic").hasRole("UpdateCourseTopic")
//                .antMatchers("/staff/topic/delete/**").hasRole("DeleteCourseTopic")
//                .antMatchers("/staff/topic/deleteTopic").hasRole("DeleteCourseTopic")
                //Topic
                .antMatchers("/tms/topics/").hasRole("ViewSearchCourseTopic")
                .antMatchers("/tms/topics/search").hasRole("ViewSearchCourseTopic")
                .antMatchers("/tms/topics/add").hasRole("AddCourseTopic")
                .antMatchers("/tms/topics/addTopic").hasRole("AddCourseTopic")
                .antMatchers("/tms/topics/**/update").hasRole("UpdateCourseTopic")
                .antMatchers("/tms/topics/update").hasRole("UpdateCourseTopic")
                .antMatchers("/tms/topics/**/delete").hasRole("DeleteCourseTopic")
                .antMatchers("/tms/topics/**/trainers").hasRole("AssignTrainerToTopic")
                .antMatchers("/tms/topics/**/trainers/**").hasRole("AssignTrainerToTopic")
//                    //Trainer
//                .antMatchers("/staff/trainer/addCourse").hasRole("AssignTraineeToCouse")
//                .antMatchers("/staff/trainer/addTopic").hasRole("AssignTraineeToTopic")
//                .antMatchers("/staff/trainer/editProfile").hasRole("EditTrainerProfile")
//                //Trainer
//                .antMatchers("/tms/trainers/addCourse").hasRole("AssignTraineeToCouse")
//                .antMatchers("/tms/trainers/addTopic").hasRole("AssignTrainerToTopic")
//                .antMatchers("/tms/trainers/editProfile").hasRole("EditTrainerProfile")

                //Trainer-------------------------
                .antMatchers("/tms/profile").hasRole("AccessTrainerPage")
                .antMatchers("/tms/updateProfile").hasRole("AccessTrainerPage")
                    //course
                .antMatchers("/tms/trainer/courses").hasRole("ViewTrainerListOfCourse")
                .antMatchers("/tms/trainer/topics").hasRole("ViewTrainerListOfCourse")
                .antMatchers("/tms/trainer/courses/**/topics").hasRole("ViewTrainerListOfCourse")

                .antMatchers("/demo/**").denyAll()
//                .antMatchers("/tms/**").denyAll()

                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error")
                    .and()
                .logout()
                    .logoutSuccessUrl("/login")
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/403");
    }
}
