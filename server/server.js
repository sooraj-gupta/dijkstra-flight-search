const express = require('express');
const path = require('path');
const app = express();
const port = 3000;

// Serve static files from the 'public' directory on /airlines
app.use('/airlines', express.static(path.join(__dirname, 'public')));


// Define a route to run JavaScript code
app.get('/airlines/load', (req, res) => {
    let assetsLocation = req.query.location;
    let assets = require("./" + assetsLocation+'airports_airlines.json');
    res.json(assets);
});

app.get('/airlines/search', (req, res) => {
  
  if(req.query.departure == null || req.query.destination == null){
    res.json("Please provide a departure and destination");
    return;
  }

  var exec = require('child_process').exec;
  console.log('java FlightFrontend "assets=.&'+req.url.split('?')[1]);
  let script = exec('java FlightFrontend "assets=.&'+req.url.split('?')[1]+"\"");
  
  script.stdout.on('data', function(data){
    res.json(data);
  });
});

// Start the server
app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});
