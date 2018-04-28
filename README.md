# N26-Statistics
N26 Statistics Assignment

Requirements
---------------
We would like​ ​to​ ​have​ ​a​ ​restful​ ​API​ ​for​ ​our​ ​statistics.​ ​The​ ​main​ ​use​ ​case​ ​for​ ​our​ ​API​ ​is​ ​to calculate​ ​realtime​ ​statistic​ ​from​ ​the​ ​last​ ​60​ ​seconds.​ ​There​ ​will​ ​be​ ​two​ ​APIs,​ ​one​ ​of​ ​them​ ​is called​ ​every​ ​time​ ​a​ ​transaction​ ​is​ ​made.​ ​It​ ​is​ ​also​ ​the​ ​sole​ ​input​ ​of​ ​this​ ​rest​ ​API.​ ​The​ ​other​ ​one returns​ ​the​ ​statistic​ ​based​ ​of​ ​the​ ​transactions​ ​of​ ​the​ ​last​ ​60​ ​seconds.

    POST /transactions 
    {
      ​​​​​​​​​​​​​"amount": ​​12.3,
      ​​​​​​"timestamp": ​​1478192204000
    } 

Every Time a new transaction happened, this endpoint will be called. 

`GET​ ​/statistics` 

Returns:  

    {
      ​​​​​​​​​​​​​​"sum": ​​1000,
      ​​​​​​​​​​​​​​"avg": ​​100,
      ​​​​​​​​​​​​​​"max": ​​200,
      ​​​​​​​​​​​​​​"min": ​​50,
      ​​​​​​​​​​​​​​"count": ​​10
    }

This​ ​is​ ​the​ ​main​ ​endpoint​ ​of​ ​this​ ​task,​ ​this​ ​endpoint​ ​have​ ​to​ ​execute​ ​in​ ​constant​ ​time​ ​and memory​ ​(O(1)).​ ​It​ ​returns​ ​the​ ​statistic​ ​based​ ​on​ ​the​ ​transactions​ ​which​ ​happened​ ​in​ ​the​ ​last​ ​60 seconds.      

Solution
--------

To solve this problem, Sliding Window technique is used.

Here is the pseudo code:

    Initialize:
        BEGIN
            WINDOW_SIZE = 60*1000 (60 seconds in millis)
            ARRAY_SIZE = WINDOW_SIZE + 1 (Plus one is buffer)
            STATISTICS_ARRAY[ARRAY_SIZE]
            Fill each element with empty statistics
        END
    

    saveTransaction(transaction):
        BEGIN
            index = transaction.timestamp % ARRAY_SIZE (Find an index for Transaction based on its timestamp)
            // Re-calculate all statistics between index to index+WINDOW_SIZE
            for i=index, i<index+WINDOW_SIZE, i++
                current_statistics = STATISTICS_ARRAY[i % ARRAY_SIZE]
                if transaction.timestamp > current_statistics.timestamp-1 then
                    create new statistics with transaction and add to array
                else
                    add transaction into current statistics
            
        END
    
    getTransaction(currentTimestamp):
        BEGIN
            index = currentTimestamp % ARRAY_SIZE
            if STATISTICS_ARRAY[index].timestamp >= currentTimestamp + 60*1000 then
                return STATISTICS_ARRAY[index]
            else
                return empty statistics
        END
        
Run Project
-----------
In order to run project, just run command below under root folder:

        ./mvnw boot run -Dserver.port=9080

Then you can access to the endpoints from `http://localhost:9080`

Swagger UI
----------
In order to access Swagger UI, just open `http://localhost:9080/swagger-ui.html` from your browser.