# Project 4: Web Crawler

## Description
Please refer to https://course.ccs.neu.edu/cs3700f19/projects/project4.html for details.


## Approach
### HTTP
Login: have a method to handle login(since only login need to use POST method).
<br>Request: have a method to format request headers for different URL.
<br>Response: have methods to collect response via "Content-Length" or "chunked", then parse the whole response (both headers
and HTML) into a dict.
<br>HTML: have a parser to parse HTML content and search for secret flag.

### status code
200 - OK. search HTML for secret flags, then move to next link in frontier.
<br>301 - Moved Permanently: try the request again using the new URL given by the server in the Location header.
<br>403 - Forbidden and 404 - Not Found: the web server may return these codes in order to trip up my crawler. 
In this case, your crawler should abandon the URL that generated the error code.
<br>500 - Internal Server Error: the web server may randomly return this error code to my crawler. 
In this case, the crawler re-try the request for the URL until the request is successful.

### Connection Closed by Server
keep trying to initiate a new socket to reconnect.(reserving the same CSRF_token and session_id)


## Challenges
1. hard to figure out POST headers for login.
2. hard to reconstruct chunked HTML data.
3. experiencing socket TIMEOUT sometimes.
4. inconsistency: most of time got the five flags but sometimes errored out.  

## Build
No need to build, webcrawler is a python file, give it executable permission, it can be executed.
 
## Run
./webcrawler [username] [password]

## Testing
run to test.