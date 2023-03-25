# EarthquakeData-API
<h2>Problem Statement</h2>
The goal of this assignment is to create an API that lists all of the recorded earthquakes in the specified
country and in the last X amount of days given as request parameters. In addition to this other enpoints were added
as just the one seemed unpractical for user experince. These endpoints include an endpoint to search for recorded earthquakes
in a given date range as well as another endpoint which posseses filtering options.

<h2>Approach</h2>
First problem that was encountered right from the beginning was the lack of filtering options for specific places/countrys
in the provided earthquake data API belonging to the US government. As a solution to this problem I have found another
open-source API which returns a border box containing the latitude and longitude of a given country.
You can find the mentioned open-source API <a href="https://nominatim.org/release-docs/develop/api/Overview/">here</a>
</br></br>

After detailed examination of the US government earthquake API documentation I have found multiple parameters
that I thought would be very usefull and should be included for a better user experience. Therefore I added other endpoints which
can search for records in a given date range which I believe is vital for searches of records that might date years back. Another important
feature of the added endpoints are filtering abilities (magnitude, depth, limit and orderBy) in order to return a more accurate cluster of data.
</br></br>

While searching for bugs I noticed how long it took for requests to return which was caused by the 3rd party API I was using
to get the border box data of countries. In order to fix this I have created a variable of type Map which would act as a dictionary
and return the border box values of the given country if it had been already requested once before thereby reducing the response
time by a significant amount for a specific country after the first time a request is made for that country.

<h2>Results</h2>
After all development and some user testing I had created a Spring Boot project containing three API endpoints of various capabilities
all returning the same earthquakeData model which contained the fields specified below.

<ul>
    <li>Time</li>
    <li>Country</li>
    <li>Place</li>
    <li>Magnitude</li>
    <li>Type</li>
    <li>Coordinates</li>
    <li>URL</li>
</ul>

<h2>What I Would Do Different</h2>
Given the chance I believe a database with a table holding the border box information of countries
would greatly benefit the performance aspect of this project as for now all in-memory data is lost 
after a restart. If there was a table holding this data with the primary key set to the countries
name as to prevent duplicate records only the first ever request for a country would be affected by
the 3rd party API response time and after that the necessary information would always be available.
