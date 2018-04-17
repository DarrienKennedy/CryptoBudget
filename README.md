# CryptoBudget
Desktop application to allow for management of financial assets.

## 1. Welcome
Thank you for trying out *CryptoBudget*, the last budgetting application you will ever need to manage your everyday life along with your cryptocurrency investments.

To get *CryptoBudget* up and running, first there are some configurations that must be met in order to get everything functioning correctly. If you're too excited to even bother reading through this document, please at least read through **section 2** of the document to verify that your machine is properly configured to support all of the beauty that *CryptoBudget* has to offer.

## 2. Dependencies
## 2.1 Tesseract
*NOTE: CryptoBudget will still work if you do not install Tesseract, but OCR functionality will not parse text from image*

#### 2.1.1 Download and Install
**Version 4.0.0 tends to perform best and is able to parse text from `img/works.jpg`. At the time of writing this 4.0.0 is only available on windows machines.**

In order to use the OCR functionality for transacitons, you must install the latest version of Tesseract [here](https://github.com/tesseract-ocr/tesseract/wiki). Install it like you would install any other program. Make note of where you install it to.

#### 2.1.2 Configure
One last step is required: in the file `tesseractbin.txt`, copy and paste the install location of where you can view the Tesseract binary.

For Example:
`C:/Program Files (x86)/Tesseract-OCR/tesseract`

## 2.2 Jars
The development team used these jar files during compilation. Configure your IDE of choice to include these when compiling the application. These must be specified if you plan to compile or else you will be met with a gang of menacing errors. [Guide for IntelliJ](https://stackoverflow.com/questions/1051640/correct-way-to-add-external-jars-lib-jar-to-an-intellij-idea-project)

Download links for each required jar for compilation:
* [jfoenix-9.0.1.jar](http://central.maven.org/maven2/com/jfoenix/jfoenix/9.0.1/jfoenix-9.0.1.jar)
* [jsoup-1.11.2.jar](http://central.maven.org/maven2/org/jsoup/jsoup/1.11.2/jsoup-1.11.2.jar)
* [okhttp-3.0.0.jar](http://central.maven.org/maven2/com/squareup/okhttp3/okhttp/3.0.0-RC1/okhttp-3.0.0-RC1.jar)
* [okio-1.14.0.jar](http://central.maven.org/maven2/com/squareup/okio/okio/1.14.0/okio-1.14.0.jar)
* [sqlite-jdbc-3.21.0.jar](http://central.maven.org/maven2/org/xerial/sqlite-jdbc/3.21.0/sqlite-jdbc-3.21.0.jar)

## Key Features

### Local Database
One of the main design philosophies of CryptoBudget was to have a completely private database
when storing the user's financial transaction information, rather than having to trust a server to 
be configured securely. 

This file may be copy and pasted if need be.

### Users
CryptoBudget supports multiple users within one database file. Users may choose their own settings preferences
for their individual account. 

#### Password
Passwords are stored Users may choose to not have a password by simply leaving the password field blank when creating an account or changing
their password.

*NOTE: CryptoBudget has no means of password recovery, so think of a password that you can remember or alternatively do not 
 set a password.*

### Currencies
Users may view up-to-date values for various currencies, with a focus on cryptocurrencies which a web crawler is ran in the
background to get recent currency values.

### Goals
Users may provide multiple financial goals in currencies of their choosing. Users may edit these goals or add progress to
a given goal.

### Transactions
Users may provide income and payments in whatever currency they choose and search, edit, or delete these transactions.