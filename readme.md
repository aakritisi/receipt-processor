# Receipt Processor

This is a webservice that has API endpoints to process a given receipt and assign points to it according to some preset rules. Java and Javalin is used to implement the API. 

Instructions to setup & run the app:

1. Clone this repo in your local.
2. Download and install IntelliJ (https://www.jetbrains.com/idea/download)
3. Download JDK 17 in your local (https://www.oracle.com/java/technologies/downloads/#java17)
4. Open receipt-processor in IntelliJ:
   1. In the top menu bar File > New > Project from Existing Sources...
   2. Choose the receipt-processor folder you cloned the repo into
   3. Select Import Project from External Model and choose Gradle
   4. Click Finish
5. Configure JDK 17 in IntelliJ:
   1. In the top menu bar File > Project Structure
   2. Click the Project tab in the side-bar 
   3. Set the Project SDK to JDK 17
   4. Click Apply
6. Build the project using IntelliJ UI by right-clicking on ReceiptProcessor in the project structure shown on the left:
   ![Screen Shot 2023-07-02 at 7 24 27 AM](https://github.com/aakritisi/receipt-processor/assets/25239393/01e9159d-de9d-4566-9771-558191e7ad93)

7. Run src/main/java/Init.java using IntelliJ UI by right-clicking on Init.java in the project structure shown on the left:
   ![Screen Shot 2023-07-02 at 7 07 45 AM](https://github.com/aakritisi/receipt-processor/assets/25239393/85e67b30-8447-46de-87cd-ea17ff685b3d)


                        Or
just use the keyboard shortcut ctrl + R to run Init.java

### Once the app is up and running test the application:

### Using Postman
1. Download and install Postman (https://www.postman.com/downloads/)
2. Setup postman (https://learning.postman.com/docs/getting-started/navigating-postman/)
3. Change the method type to POST and enter URL: http://localhost:7002/receipts/process ,add the JSON body inside the body tab and click on send.
![Screen Shot 2023-07-02 at 8 59 09 AM](https://github.com/aakritisi/receipt-processor/assets/25239393/4142eecc-0a61-4754-9f83-918cb26f8486)

4. You can see the result in the response body, which should return a JSON body having an id.
![Screen Shot 2023-07-02 at 9 00 35 AM](https://github.com/aakritisi/receipt-processor/assets/25239393/fc9614d3-433d-4b2c-92d8-335e0773ac2e)
5. Copy the id and replace it in the place of id in this URL: http://localhost:7002/receipts/{id}/points
6. Copy the above URL and enter it, change the method to GET and click Send.
![Screen Shot 2023-07-02 at 9 08 06 AM](https://github.com/aakritisi/receipt-processor/assets/25239393/2d4a2f5e-47d7-4bfb-82b1-315762f81cc4)

7. The response should be a JSON body containing points
![Screen Shot 2023-07-02 at 9 09 26 AM](https://github.com/aakritisi/receipt-processor/assets/25239393/ffb229fc-c381-42dd-a1e7-5f7f4cc2bf86)


### Using Curl
1. Download and install Curl (https://curl.se/download.html)
2. To call the /receipts/process end point enter this command in the command line: curl -d '{"retailer": "Target",
   "purchaseDate": "2022-01-01",
   "purchaseTime": "13:01",
   "items": [
   {
   "shortDescription": "Mountain Dew 12PK",
   "price": "6.49"
   },{
   "shortDescription": "Emils Cheese Pizza",
   "price": "12.25"
   },{
   "shortDescription": "Knorr Creamy Chicken",
   "price": "1.26"
   },{
   "shortDescription": "Doritos Nacho Cheese",
   "price": "3.35"
   },{
   "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
   "price": "12.00"
   }
   ],
   "total": "9.00"
   }' -H 'Content-Type: application/json'  http://localhost:7002/receipts/process
   
which should return a JSON having an id.
4. To call the /receipts/{id}/points endpoint enter this command in the command line:
   curl -v http://localhost:7002/receipts/{id}/points
5. replace the {id} from the url with the receipt id which should return the receipts corresponding points.

##### Unit tests are provided and can be executed to test.


