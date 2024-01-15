// group all cities by state
db.getCollection('zips').aggregate(
    [
        {
            $group: {
                _id: { state: '$state' },
                cities: { $push: '$city' }
            }
        },
        {
            $sort: { '_id.state': 1 }
        },
    ]
);