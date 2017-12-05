package nowel

import nowel.NowelApp.{Person, PersonWithExclusions}

/**
  * Created by Alexis on 05/12/2017.
  */
object Config {

	/*
	* SMTP CONFIG
	* Here you can config the smtp that will be use to send mail.
	* You can use gmail's smtp by generating an access code with: https://myaccount.google.com/apppasswords
	* (This requires you have 2 Factor Authencation enable on you account
	* */
	/*your smtp user here*/
	val smtpUser = "superadmin"
	/*your smtp password here (access code in case of gmail)*/
	val smtpPassword = "xxxxx"
	val smtpAuth = "true"
	val smtpStartTlsEnabled = "true"
	val smtpHost = "smtp.gmail.com"
	val smtpPort = "587"

	/*MAIL CONFIG*/
	/*email from which all emails will be sent*/
	val mailFrom = "superadmin@gmail.com"
	/*email from which all emails will be sent, in test mode*/
	val mailToTest = "superadmin@gmail.com"
	/*subject of the mail*/
	val mailSubject = "Secret Santa"
	/*body of the mail {0} is santa name, {1} is recipient name, {2} is recipient email*/
	val mailText = "{0}, you have to offer a gift to {1} ({2})"


	val Alice = Person("Alice", "alice@gmail.com")
	val Bob = Person("Bob", "bob@gmail.com")
	val Chuck = Person("Chuck", "chuck@gmail.com")
	val Eve = Person("Eve", "eve@gmail.com")
	val Judy = Person("Judy", "judy@gmail.com")
	val Mallory = Person("Mallory", "mallory@gmail.com")
	val Trudy = Person("Trudy", "trudy@gmail.com")
	val Walter = Person("Walter", "walter@gmail.com")

	val personsWithExclusions: List[PersonWithExclusions] =
		PersonWithExclusions(
			person = Alice,
			/*Bob and Alice are in a couple, and Bob already offered a gift to Mallory last year*/
			exclusions = Bob :: Mallory :: Nil
		) ::
		PersonWithExclusions(
			person = Bob,
			/*Bob and Alice are in a couple, and Alice already offered a gift to Walter last year*/
			exclusions = Alice :: Walter :: Nil
		) ::
		PersonWithExclusions(
			person = Chuck,
			/*Chuck and Eve are in a couple, and Chuck already offered a gift to Trudy last year*/
			exclusions = Eve :: Trudy :: Nil
		) ::
		PersonWithExclusions(
			person = Eve,
			/*Chuck and Eve are in a couple, and Eve already offered a gift to Judy last year*/
			exclusions = Chuck :: Judy :: Nil
		) ::
		PersonWithExclusions(
			person = Judy,
			/*Judy already offered a gift to Chuck last year*/
			exclusions = Chuck :: Nil
		) ::
		PersonWithExclusions(
			person = Mallory,
			/*Mallory already offered a gift to Eve last year*/
			exclusions = Eve :: Nil
		) ::
		PersonWithExclusions(
			person = Trudy,
			/*Trudy already offered a gift to Alice last year*/
			exclusions = Alice :: Nil
		) ::
		PersonWithExclusions(
			person = Walter,
			/*you get the idea...*/
			exclusions = Bob :: Nil
		) :: Nil

}
