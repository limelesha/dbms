Project GitHub Repository
<https://github.com/limelesha/dbms>


Prerequisites

Running the below commands requires one to have maven and jdk installed.
Additionally, you will require the PostgreSQL database server running on your device. Configure the hibernate.properties file with proper credentials.


How to Build the Project?

To run the project, obtain the copy of the source code, then, being at the root directory, run
`mvn compile`
After successful compilation run
`mvn exec:java -Dexec.mainClass="com.group7.dbms.Main"`
to launch the server
