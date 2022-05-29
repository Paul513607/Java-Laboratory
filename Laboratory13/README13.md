# Java-Laboratory
 Homework and Projects from the Java Laboratory <br />

Name: Samson Ioan-Paul <br />
Group: 2A6 <br />

# Laboratory13
__Internationalization and Localization__ <br />
Create an application to explore the available locales included in the standard Java Development Kit. <br />

The main specifications of the application are: <br /> <br />

# Compulsory13
Create a package with the name res. Inside this package create the files: Messages.properties, Messages_ro.properties. <br /> <br />

#Messages.properties file <br />
prompt = Input command: <br />
locales = The available locales are: <br />
locale.set = The current locale is {0} <br />
info = Information about {0}: <br />
invalid = Unknown command <br /> <br />

#Messages_ro.properties file <br />
prompt = Comanda ta: <br />
locales = Localizarile disponibile sunt: <br />
locale.set = Localizarea curenta este {0} <br />
info = Informatii despre localizarea {0}: <br />
invalid = Comanda necunoascuta <br /> <br />

Create the package com and implement the following classes describing commands: <br />
DisplayLocales: to display all available locales <br />
SetLocale: to set the application current locale. <br />
Info: to display informations about the current or a specific locale.
When the user sets a specific language tag, various information obtained using standard Java classes such as Currency or DateFormatSymbols should be displayed in a text area, as in the following example: <br />
Country: Romania (România) <br />
Language: Romanian (română) <br />
Currency: RON (Romanian Leu) <br />
Week Days: luni, marţi, miercuri, joi, vineri, sâmbătă, duminică <br />
Months: ianuarie, februarie, martie, aprilie, mai, iunie, iulie, august, septembrie, octombrie, noiembrie, decembrie <br />
Today: May 8, 2016 (8 mai 2016) <br />
Create the package app and the main class LocaleExplore. Inside this class, read commands from the keyboard and execute them. <br />
All the locale-sensitive information should be translated in at least two languages (english-default and romanian), using the resource files. <br /> <br />

We add the two resources files. <br />
We implement a command parser, which checks for the commands specified above, and __exit__ in order to exit application. <br />
We create the class __DisplayLocales__ in order to display all the available Locales in the Locale class. <br />
We create the class __SetLocale__ in order to set the current local to what the user specified. <br />
We create the class __Information__ in order to display the information specified above for the current locale (__default locale: en US__). <br /> <br />



