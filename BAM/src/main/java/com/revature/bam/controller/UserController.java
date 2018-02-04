package com.revature.bam.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.bam.bean.BamUser;
import com.revature.bam.bean.Batch;
import com.revature.bam.service.BamUserService;
import com.revature.bam.service.BatchService;
import com.revature.bam.service.PasswordGenerator;

@RestController
@RequestMapping(value = "users/")
@CrossOrigin
public class UserController {
	
	private static final String USERID = "userId";
	private static final String BATCHID = "batchId";

	@Autowired
	BamUserService userService;

	@Autowired
	BatchService batchService;
	/**@author Jeffrey Camacho 1712-dec10-java-Steve
	 * Method gets All Users
	 * @return List<BamUser> : all users
	 */
	@GetMapping("all")
	public ResponseEntity<List<BamUser>> getAllUsers() {
		List<BamUser> allUsers = userService.findAllUsers();
		
		return new ResponseEntity<List<BamUser>>(allUsers, HttpStatus.ACCEPTED);
	}
	/**@author Jeffrey Camacho 1712-dec10-java-Steve
	 * Method gets all trainers
	 * @return List<BamUser> : all trainers
	 */
	@GetMapping("alltrainers")
	public ResponseEntity<List<BamUser>> getAllTrainers() {
		List<BamUser> allTrainers = userService.findByRole(2);
		
		return new ResponseEntity<List<BamUser>>(allTrainers, HttpStatus.ACCEPTED);
	}

	/**@author Jeffrey Camacho 1712-dec10-java-Steve
	 * Method gets all associates
	 * @return List<BamUser> : all associates
	 */
	@GetMapping("allassociates")
	public ResponseEntity<List<BamUser>> getAllAssociates() {
		List<BamUser> allAssociates = userService.findByRole(1);
		
		return new ResponseEntity<List<BamUser>>(allAssociates, HttpStatus.ACCEPTED);
	}

	/**@author Jeffrey Camacho 1712-dec10-java-Steve
	 * Method gets all users in batch
	 * @param request
	 * @return List<BamUser> : users in batch
	 * @throws IOException
	 * @throws ServletException
	 */
	@GetMapping("inbatch")
	public ResponseEntity<List<BamUser>> getUsersInBatch(HttpServletRequest request)throws IOException, ServletException {
		//Get the batch id from the request
		int batchId = Integer.parseInt( request.getParameter(BATCHID) );
		
		//Retrieve and return users in a batch from the database
		List<BamUser> usersInBatch = userService.findUsersInBatch(batchId);
		
		return new ResponseEntity<List<BamUser>>(usersInBatch, HttpStatus.ACCEPTED);
	}

	/**@author Jeffrey Camacho 1712-dec10-java-Steve
	 * removes user from batch then returns user with updated batch
	 * 
	 * @param request
	 * @return List<BamUser>: users from batch
	 * @throws IOException
	 * @throws ServletException
	 */
	@PostMapping("drop")
	public ResponseEntity<List<BamUser>> dropUserFromBatch(HttpServletRequest request) throws IOException, ServletException{
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter(USERID) );
		BamUser user = userService.findUserById( userId );
		int batchId = user.getBatch().getId();

		// Drop user from the batch
		user.setBatch(null);
		user.setRole(0);//0 role does not exist in database, use 1 to test method checks good.
		userService.addOrUpdateUser(user);

		// Return users from batch without the user
		List<BamUser> usersInBatch = userService.findUsersInBatch(batchId);
		
		return new ResponseEntity<List<BamUser>>(usersInBatch, HttpStatus.ACCEPTED);
	}

	/**@author Jeffrey Camacho 1712-dec10-java-Steve
	 * Updates the current user
	 * 
	 * @param currentUser
	 * @return BamUser
	 */
	@PostMapping("update")
	public ResponseEntity<BamUser> updateUser(@RequestBody BamUser currentUser) {
		BamUser user = userService.findUserByEmail(currentUser.getEmail());
		currentUser.setPwd(user.getPwd());
		
		BamUser updatedUser = userService.addOrUpdateUser(currentUser);
		if (updatedUser != null) {
			return new ResponseEntity<BamUser>(updatedUser, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<BamUser>(updatedUser, HttpStatus.BAD_REQUEST);
		}
	}
	/**@author Jeffrey Camacho 1712-dec10-java-Steve
	 * Method registers the current user
	 * 
	 * @param currentUser
	 * @return BamUser
	 */
	@PostMapping("register")
	public ResponseEntity<BamUser> addUser(@RequestBody BamUser currentUser){
		BamUser addedUser = null;
		if(userService.findUserByEmail(currentUser.getEmail())==null){
			currentUser.setRole(1);
			String password = currentUser.getPwd();
			String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
			currentUser.setPwd(hashed);
			addedUser = userService.addOrUpdateUser(currentUser);
			return new ResponseEntity<BamUser>(addedUser, HttpStatus.CREATED);
		} else {
			//throw new CustomException("Email exists in database");
			return new ResponseEntity<BamUser>(addedUser, HttpStatus.BAD_REQUEST);
		}
	}


	/**@author Jeffrey Camacho 1712-dec10-java-Steve
	 * Method resets the password for the current user
	 * 
	 * @param userNewPass
	 * @return BamUser
	 */
	@PostMapping("reset")
	public ResponseEntity<BamUser> resetPassword(@RequestBody BamUser userNewPass){
		BamUser updatedUser = null;
		BamUser currentUser = userService.findUserByEmail(userNewPass.getEmail());
		if(BCrypt.checkpw(userNewPass.getPwd(), currentUser.getPwd())){
			String hashed =  BCrypt.hashpw(userNewPass.getPwd2(), BCrypt.gensalt());
			currentUser.setPwd(hashed);
			updatedUser = userService.addOrUpdateUser(currentUser);
			return new ResponseEntity<BamUser>(updatedUser, HttpStatus.ACCEPTED);
		}else{
			return new ResponseEntity<BamUser>(updatedUser, HttpStatus.BAD_REQUEST);
		}
	}
	
	/**@author Jeffrey Camacho 1712-dec10-java-Steve
	 * Method removes user and returns updated batch
	 * 
	 * @param request
	 * @return List<BamUser>
	 * @throws IOException
	 * @throws ServletException
	 */
	@PostMapping("remove")
	public ResponseEntity<List<BamUser>> removeUser(HttpServletRequest request)throws IOException, ServletException{
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter(USERID) );
		BamUser user = userService.findUserById( userId );
		int batchId = user.getBatch().getId();

		// Set the user as inactive
		Batch b = null;
		user.setBatch(b);
		userService.addOrUpdateUser(user);

		// Return users from batch without the user
		List<BamUser> newBatchList = userService.findUsersInBatch(batchId);
		if (newBatchList != null) {
			return new ResponseEntity<List<BamUser>>(newBatchList, HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<List<BamUser>>(newBatchList, HttpStatus.BAD_REQUEST);
		}
	}

	/**@author Jeffrey Camacho 1712-dec10-java-Steve
	 * Method adds users to the batch
	 * 
	 * @param request
	 * @return List<BamUser>
	 */
	@PostMapping("add")
	public ResponseEntity<List<BamUser>> addUserToBatch(HttpServletRequest request) {
		//Get the user id from the request
		int userId = Integer.parseInt( request.getParameter(USERID) );
		//Get the batch to add the user to from the request
		int batchId = Integer.parseInt( request.getParameter(BATCHID) );
		
		BamUser user = userService.findUserById( userId );
		user.setBatch(batchService.getBatchById(batchId));
		
		BamUser addedUser = userService.addOrUpdateUser(user);
		
		if (addedUser != null) {
			return new ResponseEntity<List<BamUser>>(userService.findUsersNotInBatch(),HttpStatus.ACCEPTED);
		}else {
			return new ResponseEntity<List<BamUser>>(userService.findUsersNotInBatch(),HttpStatus.BAD_REQUEST);
		}
	}

	/**@author Jeffrey Camacho 1712-dec10-java-Steve
	 * Method returns users not in batch
	 * 
	 * @param request
	 * @return List<BamUser>
	 */
	@GetMapping("notinabatch")
	public ResponseEntity<List<BamUser>> getUsersNotInBatch(HttpServletRequest request) {
		List<BamUser> usersNotInBatch = userService.findUsersNotInBatch();
		return new ResponseEntity<List<BamUser>>(usersNotInBatch, HttpStatus.ACCEPTED);
	}
	/**@author Jeffrey Camacho 1712-dec10-java-Steve
	 * Method resets the password
	 * 
	 * @param email
	 * @return BamUser
	 */
	@PostMapping("recovery")
    public ResponseEntity<BamUser> recoverPassword(@RequestParam String email){
        // Lookup user in database by e-mail
        BamUser user = userService.findUserByEmail(email);
        if (user != null) {
        	String generate = PasswordGenerator.makePassword();
        	String hashed =  BCrypt.hashpw(generate, BCrypt.gensalt());
        	user.setPwd(hashed);
        	userService.addOrUpdateUser(user);
        	userService.recoverE(user, generate);
        	return new ResponseEntity<BamUser>(user,HttpStatus.ACCEPTED);
        } else { 
        	return new ResponseEntity<BamUser>(user,HttpStatus.BAD_REQUEST);
        }
    }

}