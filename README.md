# CryptoBudget
Desktop application to allow for management of financial assets.

## Dependencies
### Tesseract
*NOTE: CryptoBudget will still work if you do not install Tesseract, but OCR functionality will not parse text from image*

#### Download and Install
In order to use the OCR functionality for transacitons, you must install the latest version of Tesseract [here](https://github.com/tesseract-ocr/tesseract/wiki). Install it like you would install any other program. Make note of where you install it to.

#### Configure
One last step is required: in the file `tesseractbin.txt`, copy and paste the install location of where you can view the Tesseract binary.

For Example:

`C:/Program Files (x86)/Tesseract-OCR/tesseract`


## Jars
* jfoenix-9.0.1
* jsoup-1.11.2
* okhttp-3.0.0
* sqlite-jdbc-3.21.0