# Simple ZIO ProtoQuill Example

Simple example of using ZIO-ProtoQuill with some helpful scripts to automatically setup a postgres database.

## Instructions
1. Download and install docker.
2. Run the start script: `./start.sh`
3. Compile and run the examples: `sbt 'run example.module.Main'`
   ```
   > sbt 'run example.module.Main'
   List((Person(2,Vlad,Dracul,321),Address(2,Bran Castle,11111,Transylvania)), (Person(2,Vlad,Dracul,321),Address(2,Ambras Castle,11111,Innsbruck)))
   ```
4. Stop the container `./stop.sh`
