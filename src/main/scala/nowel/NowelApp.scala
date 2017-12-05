package nowel

import java.text.MessageFormat
import java.util.Properties
import javax.mail.{Message, PasswordAuthentication, Session, Transport}
import javax.mail.internet.{InternetAddress, MimeMessage}

import scala.util.Random

/**
  * Created by Alexis on 13/11/2016.
  */
object NowelApp extends App {

	import Config._

	/*
	* a person with its name and email
	* */
	case class Person(val name: String, val email: String)

	/*
	* a person with a list of person they cannot offer a gift
	* */
	case class PersonWithExclusions(val person: Person, val exclusions: List[Person])

	/*
	* A Santa (person who offers a gift) => Recipient (person who receives the gift) pair
	* */
	case class SantaWithRecipient(val santa: PersonWithExclusions, val recipient: PersonWithExclusions)

	/*
	* Generates all possible scenarios
	* */
	def findAllScenarios(toFind: List[PersonWithExclusions],
	                     eligiblePersons: List[PersonWithExclusions],
	                     currentScenario: List[SantaWithRecipient]): List[List[SantaWithRecipient]] = {
		if (toFind == Nil) {
			currentScenario :: Nil
		} else {
			val currentPerson = toFind.head
			val potentialRecipients = eligiblePersons.filter(vp => !currentPerson.exclusions.contains(vp.person) && currentPerson.person != vp.person)
			if (potentialRecipients.isEmpty) {
				currentScenario :: Nil
			} else {
				potentialRecipients.flatMap(victime => {
					findAllScenarios(toFind.tail, eligiblePersons.filter(_ != victime), SantaWithRecipient(currentPerson, victime) :: currentScenario)
				})
			}
		}
	}

	val allScenarios = findAllScenarios(personsWithExclusions, personsWithExclusions, Nil)
		.filter(scenario => scenario.size == personsWithExclusions.length) //Remove unsuccessful scenarios

	/*
	* Display result
	* */
	def displayOneSantaRecipientPair(pair: SantaWithRecipient) = pair.santa.person.name + " -> " + pair.recipient.person.name

	def displayOneScenario(scenario: List[SantaWithRecipient]) =
		scenario.map(displayOneSantaRecipientPair).reduce((v1, v2) => v1 + "   " + v2) + "\n"

	allScenarios.map(displayOneScenario).foreach(println)
	val countScenario = allScenarios.map(displayOneScenario).size
	println("There are " + countScenario + " possible scenarios")
	println("")
	println("Selected scenario :")
	println("")
	val random = new Random(System.currentTimeMillis)
	allScenarios(random.nextInt(allScenarios.size))
//		.map(displayOneSantaRecipientPair)
//		.foreach(println)
		.foreach(sendTestMail)
	//.foreach(sendMail)

	def sendTestMail = sendMail(_: SantaWithRecipient, test = true)

	def sendMail(gens: SantaWithRecipient, test: Boolean = false) {
		import gens.recipient.{person => recipient}
		import gens.santa.{person => santa}
		val props = new Properties()
		props.put("mail.smtp.auth", smtpAuth)
		props.put("mail.smtp.starttls.enable", smtpStartTlsEnabled)
		props.put("mail.smtp.host", smtpHost)
		props.put("mail.smtp.port", smtpPort)

		val session = Session.getInstance(props, MyPasswordAuthentication)
		try {
			val message = new MimeMessage(session)
			message.setFrom(new InternetAddress(mailFrom))
			if (test) {
				message.setRecipients(Message.RecipientType.TO, mailToTest)
			} else {
				message.setRecipients(Message.RecipientType.TO, santa.email)
			}
			message.setSubject(mailSubject)
			val text = MessageFormat.format(mailText, santa.name, recipient.name, recipient.email)
			message.setText(text)
//			println(text)

			Transport.send(message)
			println("Message sent to " + santa.email)

		} catch {
			case e: Throwable => throw new RuntimeException(e);
		}

	}

}

object MyPasswordAuthentication extends javax.mail.Authenticator {

	override def getPasswordAuthentication: PasswordAuthentication = {
		new PasswordAuthentication(Config.smtpUser, Config.smtpPassword)
	}
}
