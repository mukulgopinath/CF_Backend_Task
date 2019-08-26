# Currency Fair Backend Task

- Tools/Technologies used:
  1. Java 8
  2. Spring Boot
  3. MySQL
  4. Spring Test
  5. REST API
  6. MySQL Workbench
  7. Eclipse IDE
  8. PostMan

- Running the Application
  1. Download the github repository and import as a Maven Project in [Eclipse] (https://www.eclipse.org/downloads/)
  2. Right click on project, Run As > Maven build. Specify goals as ' mvn clean install '.
  3. At this stage, all the dependencies will be downloaded and compiled, as well the test cases produce test results.
  4. Right click on project, Run As > Spring Boot App.
  5. Once when the app is launched, Open [Postman](https://www.getpostman.com/downloads/).
  6. Run the following GET, POST requests specified in the REST API Request information provided below to test and exececute.
  
  
- REST API Guide

1. **Get all Transactions**
  
    - **URL** - 'http://localhost:'port-number'/getAllTransactions'
    - **Purpose -** To obtain all transactions from the table.
    - **Method** - 'GET'  
    - **URL params** - None    
    - **Response -** 
               
          [
            {
              "userId": "12345",
              '"currencyFrom": "EUR",
              "currencyTo": "GBP",
              "amountSell": 1000,
              "amountBuy": 747.1,
              "rate": 0.7471,
              "timestamp": 1516789664000,
              "originatingCountry": "FR",
            }, ....
          ]
          

2. **Add a Transaction**
    - **URL** - 'http://localhost:'port-number'/addTransaction'  
    - **Method** - 'POST'
    - **URL params -**
          
          userId = "1234"
          currencyFrom = "USD"
          currencyTo = "INR"
          amountBuy = "100"
          amountSell = "6761.52"
          rate = "67.6152"
          timePlaced = "02-JUN-2019 11:24:23"
          originatingCountry = "US"
          
    - **Response -** 'Sends success message'
    
3. **Get transactions with filters**
    - **URL -** 'http://localhost:'port-number'/getTransactionsWithFilters'
    - **Purpose -** To filter the transactions based on the custom field values to produce data for visualization
    - **Method -** 'POST'
    - **URL params -** 
          
          fromDate = "21-JAN-2019 00:00:00" // if null, assumes current day transactions from 00:00:00 to 23:59:59
          toDate = "24-JAN-2019 00:00:00"   // if fromDate is present and not toDate, assumes until current datetime
          currencyFrom = "EUR"
          currencyTo = "GBP"
          userId = "1234"
          
    
    - **Response -** Gets all transactions corresponding to the applied filters for visualizing
    
 4. **Run migration to load sample data into DB for visualization**
    - **URL -** 'http://localhost:'port-number'/runMigrationForDB'
    - **Purpose -** Initially load data into the db for visualizing without adding new data.
    - **Method -** 'POST'
    - **URL params -** None
    - **Response -** Success/Failure message
    


