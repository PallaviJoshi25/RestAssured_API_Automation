package pk_MyNotes;

import org.testng.annotations.Test;

public class Login_As_Existing_User extends Base_Class {
	@Test
	public void Login_Using_BaseClass(){
		String token = Base_Class.Login("pallavijoshi2002@gmail.com","12345678");
		System.out.println("Token is"+token);
	}
}
