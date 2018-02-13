const express = require("express");
const bodyParser = require("body-parser");
const record = require("../models/record");
let router = express.Router();

router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());

router.post("/", (req, res) => {
    const startDate = new Date(req.body.startDate);
    const endDate = new Date(req.body.endDate);
    const minCount = parseInt(req.body.minCount);
    const maxCount = parseInt(req.body.maxCount);
    let response = {
        code: 0,
        msg: "",
        records: []
    };
    let query = [
        {
            "$match": {
                "createdAt": { "$gte": startDate, "$lte": endDate }
            }
        },
        {
            "$unwind": "$counts"
        },
        {
            "$group": {
                "_id": "$_id",
                "totalCount": { "$sum": "$counts" },
                "key": { "$first": "$key" },
                "createdAt": { "$first": "$createdAt" }
            }
        },
        {
            "$match": {
                "totalCount": { "$gte": minCount, "$lte": maxCount }
            }
        },
        {
            "$sort": {
                "totalCount": 1
            }
        }
    ];
    record.aggregate(query, (err, result) => {
        if (!err) {
            response.records = result;
            response.msg = "success";
            res.status(200).send(response);
        } else {
            console.log(err);
            response.msg = "err";
            res.status(500).send(response);
        }
    });
});


module.exports = router;