<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title>Project 1</title>
    </head>
    <body>
        <h2>Project 1</h2>
        <form name="formname" onsubmit="location.reload()">
            Enter an address: <input name="address" type="text" required><br>
            Enter the city, state and zip: <input name="citystatezip" type="text" required><br>
            <input type="submit" value="Submit">
        </form>
        
        <?php
            //Setting ZWSID to easily remember
            $ZWSID = "X1-ZWz1a0zhxgs3kb_1is62";
            
            //Place user inputs into variables
            $address = $_GET['address'];
            $citystatezip = $_GET['citystatezip'];
            
            //Encode queryString to replace spaces with +
            $address = urlencode($address);
            $citystatezip = urlencode($citystatezip);
            
            //Set user inputs into SEARCH_URL to get zpid
            $SEARCH_URL = "http://www.zillow.com/webservice/GetSearchResults.htm?zws-id=$ZWSID&address=$address&citystatezip=$citystatezip";
            //Load URL, get XML string labelled 'zpid' and put into variables
            $SEARCH_RESULT = file_get_contents($SEARCH_URL);
            $SEARCH_DATA = simplexml_load_string($SEARCH_RESULT);
            $ZPID = $SEARCH_DATA->response->results->result->zpid;
            $SEARCH_CODE = $SEARCH_DATA->message->code;
            
            //Enter newly acquired zpid into ZESTIMATE_URL
            $ZESTIMATE_URL = "http://www.zillow.com/webservice/GetZestimate.htm?zws-id=$ZWSID&zpid=$ZPID";
            //Load URL, get XML string labelled 'amount' and put into variables
            $ZESTIMATE_RESULT = file_get_contents($ZESTIMATE_URL);
            $ZESTIMATE_DATA = simplexml_load_string($ZESTIMATE_RESULT);
            $amountstring = $ZESTIMATE_DATA->response->zestimate->amount;
            //Save amount as number
            $amount = intval($amountstring);
            
            //Get chart from URL with zpid
            $CHART_URL = "http://www.zillow.com/webservice/GetChart.htm?zws-id=$ZWSID&unit-type=percent&zpid=$ZPID&width=300&height=150";
            //Load URL, get image URL
            $CHART_RESULT = file_get_contents($CHART_URL);
            $CHART_DATA = simplexml_load_string($CHART_RESULT);
            $IMAGE_URL = $CHART_DATA->response->url;
            
            //Finally output all results
            if($SEARCH_CODE == "0"){
                echo "<br>This property's zpid is $ZPID<br>";
                setlocale(LC_MONETARY, 'en_US');
                echo "This property is worth " . money_format('%.2n', $amount) . "<br>";
                //Show image by using html img src
                echo "<img src=\"$IMAGE_URL\"/>";
            }
            
            //Error handling
            if($SEARCH_CODE == "508"){
                echo $SEARCH_DATA->message->text;
            }
        ?>
    </body>
</html>
