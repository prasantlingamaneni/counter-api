# counter-api
Calculate Unique words and their count

PROBLEM:
Implement RESTful Services, which provide the solution of the following tasks, given the sample paragraphs. Also protect the services with SpringSecurity

Task 1: Search the following texts, which will return thecounts respectively.

User should be able to execute the following curl command and returning results in the jsonformat:

SampleRequest
> curl http://host/counter-api/search -H"Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" - d’{“searchText”:[“Duis”, “Sed”, “Donec”, “Augue”, “Pellentesque”, “123”]}’ -H"Content- Type: application/json" –XPOST

Result inJSON:
> {"counts": [{"Duis": 11}, {"Sed": 16}, {"Donec": 8}, {"Augue": 7}, {"Pellentesque":6},
{"123":0}]}

Note: Basic authorization header is using Base64 format with UTF-8CharSet

Task 2: Provide the top 20 (as Path Variable) Texts, which hasthe highest counts in the Sample Paragraphsprovided.

As a user, I will be able to execute the following curl command and expecting result in csv format. I will be able to put 10, 20, 30 or 5 as the toplisting.

SampleRequest
> curl http://host/counter-api/top/20 -H"Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==" - H”Accept:text/csv”

As an example of the result if I put/top/5: text1|100
text2|91 text3|80 text4|70 text5|60


Technologies used
-----------------
Core Java, Spring

Libraries Used
----------------
Junit, json-simple, jackson-databind