# Secret Santa Generator

## Introduction
* You and your friends want to do this "Secret Santa" thing that is trending these days, but you don't know how to do it ? 
* You're too lazy to do a draw with papers, and you cannot find a website that will do the draw for you ?
* You wanna impress your girlfriend this year by saying the words "Scala" and "Chrismas" in the same sentence ?

Then this application is definitely for you ! 
It allow to enter the list of your friend's names and then do the Secret Santa draw for you.

But wait ! That's not all ! It will also send a mail to each person in your group and tell them to who they must offer a gift. 
Garanted 100% without bugs ! 

You can also disabled some specific cases 
(eg: there is a couple / 2 people who don't like each other and you don't want them to offer a gift to each other, 
or you already did a draw last Christmas).


## Configuration
Before running this, you have to do some configuration ! All the config is done in the `src/main/scala/nowel/Config.scala` file.
You have to declare a `Person` object for each of your friends (including you) and fill their name and email,
and then declare for each person the list of person they cannot offer a gift to.
This is done by adding a `PersonWithExclusions` object to the list named `personsWithExclusions`, 
and the exclusion is done by setting the `exclusions` parameter.

You must also modify the email settings by providing a valid smtp config. 

## Run
Once everything is configurated, just do:
```
sbt run
```

## Resources
WTF is Secret Santa ? https://en.wikipedia.org/wiki/Secret_Santa

How do I install this ? http://www.scala-sbt.org/1.0/docs/Setup.html

## Contributions
Originally made by [@brissa-a](https://github.com/brissa-a)

Made available by [@freedonaab](https://github.com/freedonaab)
