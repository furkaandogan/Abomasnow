
const mongoose = require("mongoose");
const connectionString = "mongodb://dbUser:dbPassword@ds155428.mlab.com:55428/getir-bitaksi-hackathon";

mongoose.connect(connectionString, (err) => {
    if (!err) {
        console.log("connected");
    } else {
        console.log("error");
    }
});