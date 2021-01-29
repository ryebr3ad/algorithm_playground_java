# Algorithm Playground

This serves as the 'back-end' to the Anuglar based Algorithm Playground 'front-end'.  

Algorithm solutions are provided as REST endpoints, to be called by the Angular app.  More often than not, the response of the call will contain an "algorithm steps" object that contains data detailing the steps taken as an algorithm was run.  Think the state of an array after each loop of a sorting algorithm, or the state of a priority queue every time an element is added or removed.

While a bloated exercise, this allows me to work my front-end chops a bit and even helps solidify the workings of the algorithm -- I would hope so, as I have to stare at the results while tinkering with CSS and template formatting