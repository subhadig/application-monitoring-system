db = new Mongo().getDB("ams");
db.createCollection("data-collection-configs");
db.createCollection("responses");
