# Receipt Processor

This is a webservice that has API endpoints to process a given receipt and assign points to it according to some rules. Java and Javalin is used to implement the API. Since my mac is not able to support docker due to some versioning issue, I am unable to work with

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
![](/Users/aakritisingh/Desktop/Screen Shot 2023-07-02 at 7.24.27 AM.png)

7. Run src/main/java/Init.java using IntelliJ UI by right-clicking on Init.java in the project structure shown on the left:
![](/Users/aakritisingh/Desktop/Screen Shot 2023-07-02 at 7.07.45 AM.png)

                        Or
just use the keyboard shortcut ctrl + R to run Init.java

Once the app is up and running to test the application using

### Using Postman
1. Download and install Postman (https://www.postman.com/downloads/)
2. Setup postman (https://learning.postman.com/docs/getting-started/navigating-postman/)
3. Change the method type to POST and enter URL: http://localhost:7002/receipts/process ,add the JSON body inside the body tab and click on send.
![](/Users/aakritisingh/Desktop/Screen Shot 2023-07-02 at 8.59.09 AM.png)
4. You can see the result in the response body, which should return a JSON body having an id.
![](/Users/aakritisingh/Desktop/Screen Shot 2023-07-02 at 9.00.35 AM.png)
5. Copy the id and replace it in the place of id in this URL: http://localhost:7002/receipts/{id}/points
6. Copy the above URL and enter it, change the method to get, click on run.
![](/Users/aakritisingh/Desktop/Screen Shot 2023-07-02 at 9.08.06 AM.png)
7. The response should be a JSON body containing counts
![](/Users/aakritisingh/Desktop/Screen Shot 2023-07-02 at 9.09.26 AM.png)

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
3. To call the /receipts/{id}/points endpoint enter this command in the command line:
   curl -v http://localhost:7002/receipts/{id}/points
4. replace the {id} from the url with the receipt id which should return the receipts corresponding points.


