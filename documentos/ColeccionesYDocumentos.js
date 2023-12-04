/* global use, db */
// MongoDB Playground
// Use Ctrl+Space inside a snippet or a string literal to trigger completions.

const database = 'ISIS2304B08202320';
const collection = 'NEW_COLLECTION_NAME';

// The current database to use.
use(database);

// Create a new collection.
db.createCollection(collection);

// The prototype form to create a collection:
/* db.createCollection( <name>,
  {
    capped: <boolean>,
    autoIndexId: <boolean>,
    size: <number>,
    max: <number>,
    storageEngine: <document>,
    validator: <document>,
    validationLevel: <string>,
    validationAction: <string>,
    indexOptionDefaults: <document>,
    viewOn: <string>,
    pipeline: <pipeline>,
    collation: <document>,
    writeConcern: <document>,
    timeseries: { // Added in MongoDB 5.0
      timeField: <string>, // required for time series collections
      metaField: <string>,
      granularity: <string>,
      bucketMaxSpanSeconds: <number>, // Added in MongoDB 6.3
      bucketRoundingSeconds: <number>, // Added in MongoDB 6.3
    },
    expireAfterSeconds: <number>,
    clusteredIndex: <document>, // Added in MongoDB 5.3
  }
)*/

// More information on the `createCollection` command can be found at:
// https://www.mongodb.com/docs/manual/reference/method/db.createCollection/

db.createCollection("tipoHabitacion")
db.tipoHabitacion.insertOne({
  "_id": "1",
  "nombre": "Suite Presidencial",
  "capacidad": 4,
  "dotacion": "Cama King, Baño con Jacuzzi, Vista al mar"
})

db.tipoHabitacion.insertOne({
  "_id": "2",
  "nombre": "Habitación Estándar"
  // Falta 'capacidad' y 'dotacion'
})


// Crear índices para Servicios/Productos
db.servicioProducto.createIndex({ nombre: 1 });
db.servicioProducto.createIndex({ esProducto: 1 });

db.createCollection("hotel", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["nombre", "habitaciones", "serviciosProductos"],
            properties: {
                nombre: {
                    bsonType: "string",
                    description: "Nombre del hotel"
                },
                habitaciones: {
                    bsonType: "array",
                    description: "Lista de habitaciones en el hotel",
                    items: {
                        bsonType: "object",
                        required: ["id", "descripcion", "tipoHabitacionId"],
                        properties: {
                            id: {
                                bsonType: "int",
                                description: "ID único de la habitación"
                            },
                            descripcion: {
                                bsonType: "string",
                                description: "Descripción de la habitación"
                            },
                            tipoHabitacionId: {
                                bsonType: "objectId",
                                description: "Referencia al tipo de habitación"
                            }
                        }
                    }
                },
                serviciosProductos: {
                    bsonType: "array",
                    items: {
                        bsonType: "objectId",
                        description: "Referencia a servicios o productos"
                    }
                }
            }
        }
    }
});

db.createCollection("cuentaServicio", {
  validator: {
      $jsonSchema: {
          bsonType: "object",
          required: ["descripcion", "fecha", "reservaId", "servicioId"],
          properties: {
              descripcion: {
                  bsonType: "string"
              },
              fecha: {
                  bsonType: "date"
              },
              reservaId: {
                  bsonType: "objectId",
                  description: "Referencia a la reserva"
              },
              servicioId: {
                  bsonType: "objectId",
                  description: "Referencia al servicio o producto"
              }
          }
      }
  }
});


db.createCollection("habitacion")
db.habitacion.insertOne({
    "_id": "1",
    "costo": 250,
    "tipoHabitacion": db.tipoHabitacion.findOne({ "_id": "1" })
})


db.habitacion.insertOne({
  "_id": "2",
  "costo": 150,
  "tipoHabitacion": { "nombre": "Habitación no existente" }
  // 'tipoHabitacion' debe ser una referencia válida a un documento en la colección 'tipoHabitacion'
})


db.createCollection("reserva")
db.reserva.insertOne({
    "_id": "3",
    "fechaLlegada": ISODate("2023-12-24T00:00:00Z"),
    "fechaSalida": ISODate("2023-12-31T00:00:00Z"),
    "tipoHabitacion": db.tipoHabitacion.findOne({ "_id": "1" }),
    "titular": "Juan Pérez",
    "habitacion": db.habitacion.findOne({ "_id": "3" })
})

db.reserva.insertOne({
  "_id": "4",
  "fechaLlegada": ISODate("2023-12-15T00:00:00Z"),
  "fechaSalida": ISODate("2023-12-20T00:00:00Z"),
  "tipoHabitacion": db.tipoHabitacion.findOne({ "_id": "1" }),
  "titular": "Ana Gómez",
  "habitacion": db.habitacion.findOne({ "_id": "99" }) // Esta habitación no existe
})


db.createCollection("estadia")
db.estadia.insertOne({
  "_id": "1",
  "fechaCheckIn": ISODate("2024-01-01T00:00:00Z"),
  "fechaCheckOut": ISODate("2024-01-07T00:00:00Z"),
  "checkInRealizado": false,
  "checkOutRealizado": false,
  "reserva": db.reserva.findOne({ "_id": "3" })
})

db.estadia.insertOne({
  "_id": "2",
  "fechaCheckIn": ISODate("2024-02-01T00:00:00Z"),
  "fechaCheckOut": ISODate("2024-02-07T00:00:00Z"),
  "checkInRealizado": false,
  "checkOutRealizado": false,
  "reserva": db.reserva.findOne({ "_id": "99" }) // Esta reserva no existe
})


  
  db.reserva.aggregate([
    {
      $match: {
        fechaLlegada: { $gte: ISODate("2023-01-01T00:00:00Z") },
        fechaSalida: { $lte: ISODate("2023-12-31T23:59:59Z") }
      }
    },
    {
      $project: {
        habitacion: "$habitacion.$id",
        duracionReserva: {
          $divide: [
            { $subtract: ["$fechaSalida", "$fechaLlegada"] },
            1000 * 60 * 60 * 24
          ]
        }
      }
    },
    {
      $group: {
        _id: "$habitacion",
        totalDiasOcupados: { $sum: "$duracionReserva" }
      }
    },
    {
      $project: {
        _id: 0,
        habitacion: "$_id",
        ocupacion: {
          $multiply: [
            { $divide: ["$totalDiasOcupados", 365] },
            100
          ]
        }
      }
    }
  ]);
  
  
  
  
  
  
  const mongoose = require('mongoose');

  mongoose.connect('mongodb://ISIS2304B08202320:YTAABuFuBWMw@157.253.236.88:8087/ISIS2304B08202320', {
    useNewUrlParser: true,
    useUnifiedTopology: true,
  });
  
  const tipoHabitacionSchema = new mongoose.Schema({
    _id: String,
    nombre: String,
    capacidad: Number,
    dotacion: String,
  });
  
  const TipoHabitacion = mongoose.model('TipoHabitacion', tipoHabitacionSchema);
  
  const habitacionSchema = new mongoose.Schema({
    _id: String,
    costo: Number,
    tipoHabitacion: {
      type: String,
      ref: 'TipoHabitacion',
    },
  });
  
  const Habitacion = mongoose.model('Habitacion', habitacionSchema);
  
  const reservaSchema = new mongoose.Schema({
    _id: String,
    fechaLlegada: Date,
    fechaSalida: Date,
    tipoHabitacion: {
      type: String,
      ref: 'TipoHabitacion',
    },
    titular: String,
    habitacion: {
      type: String,
      ref: 'Habitacion',
    },
  });
  
  const Reserva = mongoose.model('Reserva', reservaSchema);
  
  const estadiaSchema = new mongoose.Schema({
    _id: String,
    fechaCheckIn: Date,
    fechaCheckOut: Date,
    checkInRealizado: Boolean,
    checkOutRealizado: Boolean,
    reserva: {
      type: String,
      ref: 'Reserva',
    },
  });
  
  const Estadia = mongoose.model('Estadia', estadiaSchema);
  
  module.exports = {
    TipoHabitacion,
    Habitacion,
    Reserva,
    Estadia,
  };
  



  