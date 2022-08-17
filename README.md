# ZIO ProtoQuill Example With Caliban.

## Instructions
1. Execute SQL queries at src/main/sql/test_data.sql on the protoquill database for PostgreSQL.
2. Run with "sbt '~reStart'" command for hot reload.
3. Run the examples following Graphql query for examples purposes:
   `query{
   getPeople(age: 20){
   name
   }
}`
