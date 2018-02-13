const express = require("express");
const app = express();
const db = require("./db/index");
const searchRecordController = require("./controllers/searchRecordController");

app.use("/searchRecord", searchRecordController);

const port = process.env.PORT || 8080;
app.listen(port, () => {
    console.log("server listing port:" + port);
})

module.exports = app;