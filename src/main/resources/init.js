db.getCollection('invoice').insertMany([
  {
    "status": "CANCELED",
    "invoiceDate": ISODate("2023-05-30T00:00:00.000Z"),
    "reference": "VND9IE5F",
    "amount": 1730,
    "customer": {
      "id": "6487547190ab5e1957a6baf4",
      "name": "Melanie"
    }
  },
  {
    "status": "PENDING",
    "invoiceDate": ISODate("2023-02-10T00:00:00.000Z"),
    "reference": "ZW441HFT",
    "amount": 6555,
    "customer": {
      "id": "64875411ec782e4f2bf1d782",
      "name": "Linda C. McKenzie"
    }
  },
  {
    "status": "PENDING",
    "invoiceDate": ISODate("2023-02-10T00:00:00.000Z"),
    "reference": "T1YN5RFO",
    "amount": 2100,
    "customer": {
      "id": "6487542548dbdd4b679adfed",
      "name": "Michelle"
    }
  },
  {
    "status": "PAID",
    "invoiceDate": ISODate("2023-02-10T00:00:00.000Z"),
    "reference": "UCAT5PPO",
    "amount": 21200,
    "customer": {
      "id": "63e6a3007a58c92799c84240",
      "name": "Ali"
    }
  },
  {
    "status": "PENDING",
    "invoiceDate": ISODate("2023-02-10T00:00:00.000Z"),
    "reference": "C4DE4X4P",
    "amount": 15010,
    "customer": {
      "id": "64875443507589915b6f4875",
      "name": "Jose"
    }
  },
  {
    "status": "PENDING",
    "invoiceDate": ISODate("2023-02-10T00:00:00.000Z"),
    "reference": "DZ5J8H2X",
    "amount": 6335,
    "customer": {
      "id": "6487544bb55647ea39e2d925",
      "name": "Jacques N. Richmond"
    }
  },
  {
    "status": "PAID",
    "invoiceDate": ISODate("2023-02-10T00:00:00.000Z"),
    "reference": "34PUY8BW",
    "amount": 18200,
    "customer": {
      "id": "63e6a3007a58c92799c84240",
      "name": "Ali"
    }
  },
  {
    "status": "CANCELED",
    "invoiceDate": ISODate("2023-02-10T00:00:00.000Z"),
    "reference": "FO5TND59",
    "amount": 2300,
    "customer": {
      "id": "64875452afbec6984152b9b5",
      "name": "Calvin"
    }
  },
  {
    "status": "CANCELED",
    "invoiceDate": ISODate("2023-02-10T00:00:00.000Z"),
    "reference": "60EZ6AM1",
    "amount": 6770,
    "customer": {
      "id": "63e6a3007a58c92799c84240",
      "name": "Ali"
    }
  },
  {
    "status": "PAID",
    "invoiceDate": ISODate("2023-06-15T00:00:00.000Z"),
    "reference": "QP83JVAR",
    "amount": 7205,
    "customer": {
      "id": "6487545cb89dc4aabc14d89d",
      "name": "Alan"
    }
  },
  {
    "status": "PAID",
    "invoiceDate": ISODate("2023-06-10T00:00:00.000Z"),
    "reference": "98A60RKN",
    "amount": 13895,
    "customer": {
      "id": "6487546769ea02a11f345973",
      "name": "Kristen"
    }
  }
]);