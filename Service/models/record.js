const mongoose = require("mongoose");
const Schema = mongoose.Schema;
const objectId = Schema.Types.ObjectId;

const recordSchema = new Schema({
    _id:objectId,
    key: String,
    value: String,
    createdAt: Date,
    counts: [Number]
});

mongoose.model("record", recordSchema, "records");

module.exports = mongoose.model("record");